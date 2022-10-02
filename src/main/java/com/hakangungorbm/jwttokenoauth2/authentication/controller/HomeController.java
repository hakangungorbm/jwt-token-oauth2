package com.hakangungorbm.jwttokenoauth2.authentication.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author HakanGungorBm
 * @date 5.08.2022
 */
@RestController
public class HomeController {

    @GetMapping()
    public String home() {
        return "Hello JWT";
    }

}
