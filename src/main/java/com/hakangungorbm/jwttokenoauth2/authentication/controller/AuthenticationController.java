package com.hakangungorbm.jwttokenoauth2.authentication.controller;

import com.hakangungorbm.jwttokenoauth2.authorization.UserDetailService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author HakanGungorBm
 * @date 5.08.2022
 */
@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/rest/api/v1")
public class AuthenticationController {
    private final UserDetailService userDetailService;

    @PostMapping(value = "/login")
    public UserDetails getUserDetail(@RequestParam("username") String username) {
        return userDetailService.loadUserByUsername(username);
    }

}
