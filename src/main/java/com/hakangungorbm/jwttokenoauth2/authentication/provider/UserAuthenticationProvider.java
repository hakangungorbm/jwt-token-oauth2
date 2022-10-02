package com.hakangungorbm.jwttokenoauth2.authentication.provider;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;

/**
 * @author HakanGungorBm
 * @date 9.08.2022
 */
public class UserAuthenticationProvider extends DaoAuthenticationProvider {

    @Override
    protected Authentication createSuccessAuthentication(Object principal, Authentication authentication, UserDetails user) {

        return new UsernamePasswordAuthenticationToken(principal, user.getPassword(), user.getAuthorities());
    }
    public void variable() {

    }
}