import { Injectable } from '@nestjs/common';
import { SqsService } from '@ssut/nestjs-sqs';

@Injectable()
export class PendingPixPaymentProducer {
  constructor(private readonly sqsService: SqsService) {}

  async send(orderId: string): Promise<void> {
    await this.sqsService.send('pending-pix-payment', {
      id: orderId,
      groupId: 'pix',
      body: {
        orderId,
      },
    });
  }
}
