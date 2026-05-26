package com.rpe.challenge.infra.config.cache;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.time.Duration;

@Data
@ConfigurationProperties(prefix = "app.cache")
public class CacheProperties {
	private Duration defaultTtl;
}
