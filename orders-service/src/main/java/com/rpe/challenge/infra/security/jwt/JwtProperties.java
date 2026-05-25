package com.rpe.challenge.infra.security.jwt;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.time.Duration;

@Data
@ConfigurationProperties(prefix = "auth.security.jwt")
class JwtProperties {
	private String secret;
	private Duration expirationTime;
}
