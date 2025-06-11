package com.example.threed.auth.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import lombok.Getter;

@Getter
@Component
@ConfigurationProperties(prefix = "auth.google")
public class GoogleOAuthProperties {
	private String clientId;
	private String clientSecret;
	private String redirectUri;
}
