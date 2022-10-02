package com.hakangungorbm.jwttokenoauth2.configuration;

/**
 * @author HakanGungorBm
 * @date 24.07.2022
 */

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * The configuration class for mapping properties prefixed with 'oauth2' to Java object.
 */
@Component
@ConfigurationProperties(prefix = "oauth2" )
@Data
public class OAuth2Properties {
    private Map<String, OAuth2ClientProperties> clients = new HashMap<>();
}