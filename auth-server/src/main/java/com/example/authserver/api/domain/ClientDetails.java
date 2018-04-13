package com.example.authserver.api.domain;

import org.springframework.boot.autoconfigure.security.oauth2.resource.ResourceServerProperties;
import org.springframework.boot.context.properties.NestedConfigurationProperty;
import org.springframework.security.oauth2.client.token.grant.code.AuthorizationCodeResourceDetails;

public class ClientDetails {

    @NestedConfigurationProperty
    private AuthorizationCodeResourceDetails client;
    @NestedConfigurationProperty
    private ResourceServerProperties resource;

    public ClientDetails(){
        this.client = new AuthorizationCodeResourceDetails();
        this.resource = new ResourceServerProperties();
    }

    public AuthorizationCodeResourceDetails getClient() {
        return client;
    }

    public ResourceServerProperties getResource() {
        return resource;
    }
}
