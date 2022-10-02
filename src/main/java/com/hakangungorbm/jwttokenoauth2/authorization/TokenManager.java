package com.hakangungorbm.jwttokenoauth2.authorization;

import com.hakangungorbm.jwttokenoauth2.configuration.JwtProperties;
import io.jsonwebtoken.*;
import liquibase.util.StringUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author HakanGungorBm
 * @date 31.07.2022
 */

@Component
@RequiredArgsConstructor
@Slf4j
public class TokenManager {

    private final JwtProperties jwtProperties;
    private static final String DEFAULT_AUTHORITY_PREFIX = "SCOPE_";
    private String authorityPrefix = DEFAULT_AUTHORITY_PREFIX;

    private static final int EXPIRES_IN = 10 * 1000;

    //1. Method = Token olusturuyoruz
    public String generateToken(UserDetails userDetails, Integer userId) {

        Map<String, Object> claims = new ConcurrentHashMap<String, Object>();

        Collection<? extends GrantedAuthority> roleList = userDetails.getAuthorities();

        if (roleList.contains(new SimpleGrantedAuthority(this.authorityPrefix + "ADMIN"))) {
            claims.put("isAdmin", true);
        }

        if (roleList.contains(new SimpleGrantedAuthority(this.authorityPrefix + "USER"))) {
            claims.put("isUser", true);
        }

        return generateJWT(claims, userDetails.getUsername());
    }

    private String generateJWT(Map<String, Object> claims, String  username) {

        return Jwts.builder()
                .setClaims(claims)
                .setIssuer("HakanGungor")
                .setSubject(username)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRES_IN))
                .signWith(SignatureAlgorithm.HS512, jwtProperties.getSignkey())
                .compact();
    }

    //2. Method: Token'in valid olup olmadigini dogruluyoruz

    public boolean validateToken(String jwt) {
        try {
            Jwts.parser().setSigningKey(jwtProperties.getSignkey()).parseClaimsJws(jwt);
            return true;
        }catch (SignatureException | MalformedJwtException | UnsupportedJwtException | IllegalArgumentException ex) {
            throw new BadCredentialsException("INVALID_CREDENTIALS", ex);
        }
        catch (ExpiredJwtException ex) {
            throw ex;
        }
    }

    //Gelen  request içerisinden Token alan method
    public String getJwtFromRequest(HttpServletRequest request) {
        final String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
        if(StringUtils.hasText(authHeader) && authHeader.startsWith("Bearer ")) {
            return authHeader.substring(7,authHeader.length());
        }
        return null;
    }

    public String getUsernameFromJwt(String jwt) {
        try {
            log.info("Username alindi: ", getClaims(jwt).getSubject());
            return getClaims(jwt).getSubject();
        }catch (Exception e) {
            throw new RuntimeException("JWT icerisinden USERNAME bilgisi alınamadı");
        }
    }

    public List<SimpleGrantedAuthority> getRolesFromToken(String jwt) {
        Claims claims = getClaims(jwt);
        List<SimpleGrantedAuthority> roles = new ArrayList<>();

        Boolean admin = claims.get("isAdmin", Boolean.class);
        if (admin != null && admin) {
            roles.add(new SimpleGrantedAuthority(this.authorityPrefix + "ADMIN"));
        }

        Boolean user = claims.get("isUser", Boolean.class);
        if (user != null && user) {
            roles.add(new SimpleGrantedAuthority(this.authorityPrefix + "USER"));
        }
        return roles;
    }

    private boolean isJwtExpired(String jwt) {
        return getClaims(jwt).getExpiration().before(new Date(System.currentTimeMillis()));
    }
    private Claims getClaims(String jwt) {
        return Jwts.parser().setSigningKey(jwtProperties.getSignkey()).parseClaimsJws(jwt).getBody();
    }

}
