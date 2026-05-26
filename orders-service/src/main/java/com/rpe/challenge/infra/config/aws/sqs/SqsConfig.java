package com.rpe.challenge.infra.config.aws.sqs;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties(prefix = "aws.sqs")
public class SqsConfig {
	private String endpoint;
}
