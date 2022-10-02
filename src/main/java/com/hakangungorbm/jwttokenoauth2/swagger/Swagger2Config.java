package com.hakangungorbm.jwttokenoauth2.swagger;


import com.hakangungorbm.jwttokenoauth2.configuration.OAuth2ClientProperties;
import com.hakangungorbm.jwttokenoauth2.configuration.OAuth2Properties;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.OAuthBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.*;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger.web.SecurityConfiguration;
import springfox.documentation.swagger.web.SecurityConfigurationBuilder;
import springfox.documentation.swagger2.annotations.EnableSwagger2;


import java.util.List;

@Configuration
@RequiredArgsConstructor
@EnableSwagger2
@Slf4j
public class Swagger2Config {

    private static final String SECURITY_NAME = "swagger_oauth";

    @Value("${spring.application.name:unknown}")
    private String applicationName;

    @Value("${server.port:8080}")
    private int port;

    private final OAuth2Properties properties;

    @Bean
    public Docket api() {

        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.any())
                .paths(PathSelectors.any())
                .build().apiInfo(apiInfo())
                .securitySchemes(List.of(securityScheme()))
                .securityContexts(List.of(securityContext()));
    }
    private ApiInfo apiInfo() {
        return new ApiInfoBuilder().title("JWT Token Oauth2 Library REST API")
                .description(applicationName + "JWT Token Oauth2 Library REST API Layer")
                .contact(new Contact("Hakan Güngör", "https://github.com/hakangungorbm", "engineer6619@gmail.com"))
                .license("Apache 2.0")
                .licenseUrl("http://www.apache.org/licenses/LICENSE-2.0.html")
                .version("1.0")
                .build();
    }

    @Bean
    public SecurityConfiguration security() {
        OAuth2ClientProperties swagger = properties.getClients().get("swagger");
        return SecurityConfigurationBuilder.builder()
                .clientId(swagger.getClientId())
                .clientSecret(swagger.getSecret())
                .build();
    }

    @Bean
    public SecurityScheme securityScheme() {
        GrantType grantType = new ResourceOwnerPasswordCredentialsGrant(tokenUrl());
        return new OAuthBuilder()
                .name(SECURITY_NAME)
                .grantTypes(List.of(grantType))
                .scopes(List.of(scopes()))
                .build();
    }

    private String tokenUrl() {
            return String.format("http://localhost:%d/oauth/token", port);
    }


    @Bean
    public SecurityContext securityContext() {
        return SecurityContext.builder()
                .securityReferences(List.of(new SecurityReference(SECURITY_NAME, scopes())))
                .build();
    }

    private AuthorizationScope[] scopes() {
        OAuth2ClientProperties swagger = properties.getClients().get("swagger");
        return new AuthorizationScope[]{
                new AuthorizationScope(swagger.getScope(), "Swagger Auth Scope")
        };
    }

}
