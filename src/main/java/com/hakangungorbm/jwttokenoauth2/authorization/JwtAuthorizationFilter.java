package com.hakangungorbm.jwttokenoauth2.authorization;

import io.jsonwebtoken.ExpiredJwtException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.util.StringUtils;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * @author HakanGungorBm
 * @date 6.08.2022
 */
@Slf4j
public class JwtAuthorizationFilter extends BasicAuthenticationFilter {

    private TokenManager tokenManager;

    //BasicAuthenticationFilter veya OncePerRequestFilter serviceleri icin default constructor
    public JwtAuthorizationFilter(AuthenticationManager authenticationManager, TokenManager tokenManager) {
        super(authenticationManager);
        this.tokenManager = tokenManager;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws ServletException, IOException {
        try {
            final String jwtFromRequest = tokenManager.getJwtFromRequest(request);
            if(jwtFromRequest !=null) {
                log.info("UsernamePasswordAuthenticationFilter is passing request down the filter chain");
                chain.doFilter(request,response);
                return;
            }
            if (StringUtils.hasText(jwtFromRequest) && tokenManager.validateToken(jwtFromRequest)) {
                final String kullanici = tokenManager.getUsernameFromJwt(jwtFromRequest);
                final List<SimpleGrantedAuthority> roles = tokenManager.getRolesFromToken(jwtFromRequest);

                UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
                        kullanici, null, roles);

                SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
                chain.doFilter(request, response);
                return;
            } else {
                if(isBasicAuthRequest(request)) {
                    SecurityContextHolder.clearContext();
                    chain.doFilter(request, response);
                    return;
                }
                prepareInvalidAuthResponse(response);
                return;
            }

        }catch (ExpiredJwtException | BadCredentialsException ex) {
            prepareInvalidAuthResponse(response);
            return;
        }
        catch(Exception ex) {
            prepareInvalidAuthResponse(response);
            return;
        }
    }

    private void prepareInvalidAuthResponse(HttpServletResponse response) {
        SecurityContextHolder.clearContext();
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.addHeader("INVALID-AUTH", "Invalid authentication attempt!");
    }

    private boolean isBasicAuthRequest(HttpServletRequest request) {
        String data = request.getHeader(HttpHeaders.AUTHORIZATION);
        return (StringUtils.hasText(data) && data.startsWith("Basic "));
    }

}
