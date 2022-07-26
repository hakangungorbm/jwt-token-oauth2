package com.hakangungorbm.jwttokenoauth2;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

/**
 * @author hmblocaladmin
 * @date 25.07.2022
 */
@SpringBootApplication
public class JwtTokenApplication extends SpringBootServletInitializer {

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(JwtTokenApplication.class);
    }

    public static void main(String[] args) {
        SpringApplication.run(JwtTokenApplication.class, args);
    }
}
