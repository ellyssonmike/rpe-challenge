package com.rpe.challenge.infra.config.aws.sqs;

import io.awspring.cloud.sqs.config.SqsMessageListenerContainerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import software.amazon.awssdk.services.sqs.SqsAsyncClient;

@Configuration
public class SqsListenerConfig {
	@Bean
	public SqsMessageListenerContainerFactory<Object> defaultSqsListenerContainerFactory(
		SqsAsyncClient sqsAsyncClient
	) {
		return SqsMessageListenerContainerFactory.builder()
			.sqsAsyncClient(sqsAsyncClient)
			.build();
	}
}


