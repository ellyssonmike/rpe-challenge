package com.rpe.challenge.infra.security.jwt;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Getter
@Setter
@ConfigurationProperties(prefix = "auth.security.jwt")
class JwtProperties {
	private String secret;
	private String expirationTime;
}
