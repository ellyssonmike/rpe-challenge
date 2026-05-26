package com.rpe.challenge.infra.config.aws.sqs;

import com.rpe.challenge.infra.config.aws.AwsConfig;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.services.sqs.SqsAsyncClient;

import java.net.URI;

@Configuration
@RequiredArgsConstructor
public class SqsClientConfig {
	private final AwsConfig awsConfig;
	private final SqsConfig sqsConfig;

	@Bean
	public SqsAsyncClient sqsAsyncClient() {
		return SqsAsyncClient.builder()
			.region(awsConfig.getRegion())
			.credentialsProvider(
				StaticCredentialsProvider.create(
					AwsBasicCredentials.create(awsConfig.getAccessKeyId(), awsConfig.getSecretAccessKey())
				)
			)
			.endpointOverride(URI.create(sqsConfig.getEndpoint()))
			.build();
	}
}