import { Injectable, Logger } from '@nestjs/common';
import { setTimeout } from 'timers/promises';
import { PaymentMethod } from '@payment/domain/enums/payment-method.enum';
import { getStatus, getTimeout } from '@utils/random';
import { getColoredStatus } from '@utils/color';
import config from '@payment/integration/config';
import { randomUUID } from 'crypto';
import { PaymentStatus } from '@payment/domain/enums/payment-status.enum';
import { PaymentRepository } from '@payment/database/repositories/payment.repository';

type PaymentMethods = Exclude<PaymentMethod, PaymentMethod.PIX>;

@Injectable()
export class CardPaymentGatewayService {
  private readonly logger: Logger = new Logger(CardPaymentGatewayService.name);

  constructor(private readonly paymentRepository: PaymentRepository) {}

  async processPayment(orderId: string, paymentMethod: PaymentMethods) {
    this.logger.log(`[${orderId}] • Processing ${paymentMethod} payment request)`);

    const timeout = getTimeout(config.timeout);
    this.logger.log(`[${orderId}] • Simulated timeout: ${timeout.toFixed(2)}ms)`);

    await setTimeout(timeout);
    const foundPayment = await this.paymentRepository.findByOrderId(orderId);
    if (foundPayment) {
      this.logger.log(`[${orderId}] • [${getColoredStatus(foundPayment.paymentStatus)}]`);

      return {
        id: randomUUID(),
        paymentDate: foundPayment.paymentDate,
        paymentStatus: foundPayment.paymentStatus,
      };
    }

    const paymentStatus = getStatus(paymentMethod, config.status);
    this.logger.log(`[${orderId}] • [${getColoredStatus(paymentStatus)}]`);

    return {
      id: randomUUID(),
      paymentDate: this.isPaid(paymentStatus) ? new Date() : null,
      paymentStatus,
    };
  }

  private isPaid(paymentStatus: PaymentStatus): boolean {
    return paymentStatus === PaymentStatus.PAID;
  }
}
