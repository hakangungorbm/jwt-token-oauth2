package com.hakangungorbm.jwttokenoauth2.authentication.controller;

import com.hakangungorbm.jwttokenoauth2.authorization.UserDetailService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

/**
 * @author HakanGungorBm
 * @date 5.08.2022
 */
@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/rest/api/v1/user")
public class UserController {
    private final UserDetailService userDetailService;

    @GetMapping(value = "/detail")
    @ApiOperation(value = "Username i verilen kullanıcının detaylarını döner")
    public UserDetails getUserDetail(@RequestParam("username") String username) {
        return userDetailService.loadUserByUsername(username);
    }

}
