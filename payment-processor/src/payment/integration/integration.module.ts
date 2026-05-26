import { Module } from '@nestjs/common';
import { SqsModule } from './aws/sqs/sqs.module';

@Module({
  imports: [SqsModule],
  providers: [],
})
export class IntegrationModule {}
