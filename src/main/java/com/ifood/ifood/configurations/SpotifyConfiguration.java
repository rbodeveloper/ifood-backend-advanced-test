package com.ifood.ifood.configurations;

import feign.RequestInterceptor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.security.oauth2.client.feign.OAuth2FeignRequestInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.security.oauth2.client.DefaultOAuth2ClientContext;
import org.springframework.security.oauth2.client.token.grant.client.ClientCredentialsResourceDetails;

/**
 * Configuration what will be provided the OAuth2 resources to the feign client connect on spotify API
 */
@Slf4j
public class SpotifyConfiguration {
    @Value("${spotify.auth-url}")
    private String authUrl;
    @Value("${spotify.client-id}")
    private String clientId;
    @Value("${spotify.client-secret}")
    private String clientSecret;

    @Bean
    public RequestInterceptor oauth2FeignRequestInterceptor() {
        return new OAuth2FeignRequestInterceptor(new DefaultOAuth2ClientContext(), createOAuth2resource());
    }

    private ClientCredentialsResourceDetails createOAuth2resource() {
        log.info("Using {} as client id and {} as client secret to spotify", clientId, clientSecret);
        ClientCredentialsResourceDetails resourceDetails = new ClientCredentialsResourceDetails();
        resourceDetails.setAccessTokenUri(authUrl);
        resourceDetails.setClientId(clientId);
        resourceDetails.setClientSecret(clientSecret);
        return resourceDetails;
    }
}
