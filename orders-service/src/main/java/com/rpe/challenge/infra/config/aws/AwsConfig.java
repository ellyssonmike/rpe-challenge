package com.rpe.challenge.infra.config.aws;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import software.amazon.awssdk.regions.Region;

@Data
@ConfigurationProperties(prefix = "aws")
public class AwsConfig {
	private Region region;
	private String accessKeyId;
	private String secretAccessKey;
}
