package com.hakangungorbm.jwttokenoauth2.authorization;

import lombok.Data;

/**
 * @author hmblocaladmin
 * @date 24.07.2022
 */

/**
 * The POJO for mapping 'oauth2.clients' properties.
 */
@Data
public class OAuth2ClientProperties {

    private String clientId;
    private String secret;
    private String grantType;
    private String scope;
    private String[] redirects = new String[]{};
    private int tokenValidity = -1;
    private int refreshTokenValidity = 60*60;
}
