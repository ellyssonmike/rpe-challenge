import { Injectable, Logger } from '@nestjs/common';
import { PaymentRepository } from '@payment/database/repositories/payment.repository';
import { UpdatePaymentStatusInput } from '../inputs/update-payment-status.input';
import { PaymentStatus } from '@payment/domain/enums/payment-status.enum';

@Injectable()
export class UpdatePaymentStatusService {
  private readonly logger: Logger = new Logger(UpdatePaymentStatusService.name);
  constructor(private readonly paymentRepository: PaymentRepository) {}

  async execute({ orderId, paymentStatus, paymentDate }: UpdatePaymentStatusInput) {
    this.logger.log(`[${orderId}] Received payment update request (${paymentStatus})`);

    await this.paymentRepository.patchByOrderId(orderId, {
      paymentStatus: paymentStatus as PaymentStatus,
      ...(paymentDate && {
        paymentDate,
      }),
    });

    this.logger.log(`[${orderId}] Payment updated successfully (${paymentStatus})`);
  }
}
