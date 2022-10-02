package com.hakangungorbm.jwttokenoauth2.authentication.service;

import com.hakangungorbm.jwttokenoauth2.authentication.dto.AuthenticationResponse;
import org.springframework.stereotype.Service;

/**
 * @author HakanGungorBm
 * @date 9.08.2022
 */

public interface AuthenticationService {
    public AuthenticationResponse authenticate(String authorization);
}
