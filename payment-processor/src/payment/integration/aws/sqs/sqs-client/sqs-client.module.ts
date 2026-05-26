import { Module } from '@nestjs/common';
import { SqsClientProvider } from './sqs-client-provider';

@Module({
  providers: [SqsClientProvider],
  exports: [SqsClientProvider],
})
export class SqsClientModule {}
