import { Provider } from '@nestjs/common';
import { SQSClient } from '@aws-sdk/client-sqs';
import { ConfigService } from '@config/config.service';

export const SQS_CLIENT = 'SQS_CLIENT';

export const SqsClientProvider: Provider = {
  inject: [ConfigService],
  provide: SQS_CLIENT,
  useFactory: (config: ConfigService) => {
    return new SQSClient({
      region: config.AWS_REGION,
      endpoint: config.AWS_SQS_ENDPOINT,
      credentials: {
        accessKeyId: config.AWS_ACCESS_KEY_ID,
        secretAccessKey: config.AWS_SECRET_ACCESS_KEY,
      },
    });
  },
};
