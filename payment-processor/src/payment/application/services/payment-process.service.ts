import { Injectable, Logger } from '@nestjs/common';
import { CardPaymentGatewayService } from '@payment/integration/services/card-payment-gateway.service';
import { PendingPixPaymentProducer } from '@payment/integration/aws/sqs/producers/pending-pix-payment.producer';
import { PaymentRepository } from 'src/payment/database/repositories/payment.repository';
import { PaymentProcessInput } from '../inputs/payment-process.input';
import { PaymentProcessResponse } from 'src/payment/api/dtos/payment-process.response';
import { PaymentStatus } from 'src/payment/domain/enums/payment-status.enum';
import { PaymentMethod } from 'src/payment/domain/enums/payment-method.enum';

@Injectable()
export class PaymentProcessService {
  private readonly logger: Logger = new Logger(PaymentProcessService.name);
  constructor(
    private readonly gateway: CardPaymentGatewayService,
    private readonly paymentRepository: PaymentRepository,
    private readonly pendingPixPaymentProducer: PendingPixPaymentProducer,
  ) {}

  async execute({
    orderId,
    paymentMethod,
    ...input
  }: PaymentProcessInput): Promise<PaymentProcessResponse> {
    this.logger.log(`[${orderId}] Received process request for [${paymentMethod}]`);

    if (paymentMethod === PaymentMethod.PIX)
      return this.handlePix({ orderId, paymentMethod, ...input });

    const foundPayment = await this.paymentRepository.findByOrderId(orderId);
    if (foundPayment) {
      return {
        paymentStatus: foundPayment.paymentStatus,
        paymentDate: foundPayment.paymentDate,
        processedAt: foundPayment.processedAt,
      };
    }

    try {
      const response = await this.gateway.processPayment(orderId, paymentMethod);
      const payment = await this.paymentRepository.create({
        gatewayResponse: response,
        orderDetails: { orderId, paymentMethod, ...input },
        orderId,
        paymentMethod,
        paymentStatus: response.paymentStatus,
        processedAt: new Date(),
        ...(response.paymentStatus == PaymentStatus.PAID && {
          paymentDate: response.paymentDate,
        }),
      });

      return {
        paymentStatus: payment.paymentStatus,
        processedAt: payment.processedAt,
        paymentDate: payment.paymentDate,
      };
    } catch (error) {
      if (this.duplicatedKeyError(error.code)) {
        const existingPayment = await this.paymentRepository.findByOrderId(orderId);
        return {
          paymentStatus: existingPayment.paymentStatus,
          paymentDate: existingPayment.paymentDate,
          processedAt: existingPayment.processedAt,
        };
      }

      this.logger.error(`[${orderId}] Error while process payment in Gateway: ${error}`);
      return {
        paymentStatus: PaymentStatus.PENDING,
        paymentDate: null,
        processedAt: new Date(),
      };
    }
  }

  async handlePix({
    orderId,
    paymentMethod,
    ...input
  }: PaymentProcessInput): Promise<PaymentProcessResponse> {
    const payment = await this.paymentRepository.create({
      orderId,
      paymentMethod,
      orderDetails: { orderId, paymentMethod, ...input },
      paymentStatus: PaymentStatus.PENDING,
      processedAt: new Date(),
    });

    await this.pendingPixPaymentProducer.send(orderId);

    return {
      paymentStatus: payment.paymentStatus,
      processedAt: payment.processedAt,
      paymentDate: null,
    };
  }

  private duplicatedKeyError(code: number) {
    return code === 11000;
  }
}
