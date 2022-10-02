package com.hakangungorbm.jwttokenoauth2.authentication.dto;

import java.io.Serializable;

/**
 * @author HakanGungorBm
 * @date 9.08.2022
 */
public class AuthenticationResponse implements Serializable {

    private static final long serialVersionUID = 8929499253726531083L;

    private Long userId;
    private String token;
}
