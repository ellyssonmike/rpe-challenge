import { Injectable, Logger } from '@nestjs/common';
import { PendingPixPaymentMessage } from '../messages/pending-pix-payment.message';
import { Message } from '@aws-sdk/client-sqs';
import { getStatus, getTimeout } from '@shared/utils/random';
import { getColoredStatus } from '@shared/utils/color';
import { setTimeout } from 'timers/promises';
import { PaymentMethod } from '@payment/domain/enums/payment-method.enum';
import { PaymentRepository } from '@payment/database/repositories/payment.repository';
import { PixPaymentStatusProducer } from '../aws/sqs/producers/pix-payment-status.producer';
import config from '@payment/integration/config';
import { PaymentStatus } from '@payment/domain/enums/payment-status.enum';

@Injectable()
export class PixPaymentGatewayService {
  private readonly logger: Logger = new Logger(PixPaymentGatewayService.name);
  constructor(
    private readonly paymentRepository: PaymentRepository,
    private readonly pixPaymentStatusProducer: PixPaymentStatusProducer,
  ) {}

  async processPayment({ orderId }: PendingPixPaymentMessage, { MessageId }: Message) {
    this.logger.log(`(messageId=${MessageId}) Processing PIX payload`);
    this.logger.log(`(messageId=${MessageId}) • orderId=${orderId}`);

    const timeout = getTimeout(config.timeout);
    const timeoutString = timeout.toFixed(2);
    this.logger.log(`(messageId=${MessageId}) • Simulated timeout: ${timeoutString}ms)`);

    await setTimeout(timeout);
    const foundPayment = await this.paymentRepository.findByOrderId(orderId);
    if (foundPayment && foundPayment.paymentStatus !== PaymentStatus.PENDING) {
      this.logger.log(
        `(messageId=${MessageId}) • [${getColoredStatus(foundPayment.paymentStatus)}]`,
      );

      return this.pixPaymentStatusProducer.send(
        orderId,
        foundPayment.paymentStatus,
        foundPayment.paymentDate,
      );
    }

    const paymentStatus = getStatus(PaymentMethod.PIX, config.status);

    this.logger.log(`(messageId=${MessageId}) • [${getColoredStatus(paymentStatus)}]`);
    await this.pixPaymentStatusProducer.send(
      orderId,
      paymentStatus,
      this.isPaid(paymentStatus) ? new Date() : null,
    );
  }

  private isPaid(paymentStatus: PaymentStatus): boolean {
    return paymentStatus === PaymentStatus.PAID;
  }
}
