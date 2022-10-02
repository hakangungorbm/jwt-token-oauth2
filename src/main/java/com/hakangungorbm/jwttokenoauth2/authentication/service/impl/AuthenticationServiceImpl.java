package com.hakangungorbm.jwttokenoauth2.authentication.service.impl;

import com.hakangungorbm.jwttokenoauth2.authentication.dto.AuthenticationResponse;
import com.hakangungorbm.jwttokenoauth2.authentication.service.AuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.util.StringUtils;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

/**
 * @author HakanGungorBm
 * @date 9.08.2022
 */
public class AuthenticationServiceImpl implements AuthenticationService {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Override
    public AuthenticationResponse authenticate(String authorization) {

        if(!StringUtils.hasText(authorization)) {
            throw new RuntimeException("INVALID AUTH PAYLOAD");
        }

        String[] httpBasicAuthPayload = parseHttpBasicPayload(authorization);
        String email = httpBasicAuthPayload[0];
        String password = httpBasicAuthPayload[1];

        Authentication authenticate = authenticationManager
                .authenticate(
                        new UsernamePasswordAuthenticationToken(email, password)
                );

        return null;
    }

    private String[] parseHttpBasicPayload(String authorization) {

        if (authorization != null && authorization.toLowerCase().startsWith("basic")) {

            String base64Credentials = authorization.substring("Basic".length()).trim();
            byte[] credDecoded = Base64.getDecoder().decode(base64Credentials);
            String credentials = new String(credDecoded, StandardCharsets.UTF_8);
            return credentials.split(":", 2);
        }
        return null;
    }
}
