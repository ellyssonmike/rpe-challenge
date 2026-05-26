import { Injectable } from '@nestjs/common';
import { PaymentStatus } from '@payment/domain/enums/payment-status.enum';
import { Nullable } from '@shared/types/nullable';
import { SqsService } from '@ssut/nestjs-sqs';

@Injectable()
export class PixPaymentStatusProducer {
  constructor(private readonly sqsService: SqsService) {}

  async send(
    orderId: string,
    paymentStatus: PaymentStatus,
    paymentDate?: Nullable<Date>,
  ): Promise<void> {
    await this.sqsService.send('pix-payment-status', {
      id: orderId,
      groupId: 'pix',
      body: {
        orderId,
        paymentStatus,
        paymentDate,
      },
    });
  }
}
