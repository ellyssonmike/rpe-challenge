import { Module } from '@nestjs/common';
import { SqsClientModule } from './sqs-client/sqs-client.module';
import { SqsModule as AwsSqsModule } from '@ssut/nestjs-sqs';
import { PendingPixPaymentProducer } from './producers/pending-pix-payment.producer';
import { PixPaymentStatusProducer } from './producers/pix-payment-status.producer';
import { SQS_CLIENT } from './sqs-client/sqs-client-provider';
import { SQSClient } from '@aws-sdk/client-sqs';

@Module({
  imports: [
    AwsSqsModule.registerAsync({
      imports: [SqsClientModule],
      inject: [SQS_CLIENT],
      useFactory: (client: SQSClient) => {
        return {
          consumers: [
            {
              name: 'pending-pix-payment',
              queueUrl: 'pending-pix-payment.fifo',
              sqs: client,
              waitTimeSeconds: 1,
              /**
               * Esta é uma limitação da biblioteca @ssut/nestjs-sqs, que não consegue garantir
               * completamente a ordem de filas FIFO. Nesse caso, o ideal seria implementar um consumer customizado.
               *
               * Porém, acredito que pra esse caso de uso, é aceitável, considerando que as mensagens são
               * independentes entre si no fluxo de pedidos e pagamentos.
               */
              suppressFifoWarning: true,
            },
          ],

          producers: [
            {
              name: 'pending-pix-payment',
              queueUrl: 'pending-pix-payment.fifo',
              sqs: client,
            },
            {
              name: 'pix-payment-status',
              queueUrl: 'pix-payment-status',
              sqs: client,
            },
          ],
        };
      },
    }),
  ],
  providers: [PendingPixPaymentProducer, PixPaymentStatusProducer],
  exports: [PendingPixPaymentProducer, PixPaymentStatusProducer],
})
export class SqsModule {}
