package com.hakangungorbm.jwttokenoauth2.configuration;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author HakanGungorBm
 * @date 31.07.2022
 */
@Component
@ConfigurationProperties(prefix = "jwt" )
@Data
public class JwtProperties {
    private String encryptkey;
    private String signkey;
    private String type;

}
