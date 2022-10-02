package com.hakangungorbm.jwttokenoauth2.configuration;

import com.hakangungorbm.jwttokenoauth2.authentication.entrypoint.AuthenticationFailureEntryPoint;
import com.hakangungorbm.jwttokenoauth2.authentication.provider.UserAuthenticationProvider;
import com.hakangungorbm.jwttokenoauth2.authorization.JwtAuthorizationFilter;
import com.hakangungorbm.jwttokenoauth2.authorization.TokenManager;
import com.hakangungorbm.jwttokenoauth2.authorization.UserDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

/**
 * @author HakanGungorBm
 * @date 9.08.2022
 */

public class WebSecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Autowired
    private TokenManager tokenManager;

    @Autowired
    private UserDetailService userDetailService;

    @Autowired
    PasswordConfig passwordEncoder;

    @Autowired
    private AuthenticationFailureEntryPoint failureEntryPoint;

    /**
     * General Spring Security configuration
     *
     * @param http provided by Spring Security
     * @throws Exception general exception
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http = http.csrf().disable();

        http = http
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and();

        http = http
                .exceptionHandling()
                .authenticationEntryPoint(failureEntryPoint)
                .and();

        http.authorizeRequests()
                .antMatchers("/v2/api-docs", "/api-docs",
                        "/configuration/ui", "/configuration/security",
                        "/swagger-ui/**", "/swagger-resources/**", "/swagger-ui.html", "/webjars/**").permitAll()
                .antMatchers(HttpMethod.POST, "/rest/api/v1/login").permitAll()
                .antMatchers(HttpMethod.POST, "/rest/api/v1/user").permitAll()
                .anyRequest().authenticated()
                .and()
                .addFilter(new JwtAuthorizationFilter(authenticationManager(), tokenManager));

    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) {

        UserAuthenticationProvider authenticationProvider = new UserAuthenticationProvider();
        authenticationProvider.setPasswordEncoder(passwordEncoder.passwordEncoder());
        authenticationProvider.setUserDetailsService(userDetailService);
        auth.authenticationProvider(authenticationProvider);
    }


    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();

    }

    @Bean
    public InMemoryUserDetailsManager users() {
        return new InMemoryUserDetailsManager(
                User.withUsername("dvega")
                        .password("{noop}password")
                        .authorities("read")
                        .build()
        );
    }
}