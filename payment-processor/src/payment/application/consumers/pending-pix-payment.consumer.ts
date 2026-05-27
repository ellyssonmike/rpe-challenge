import { Injectable, Logger } from '@nestjs/common';
import { ApplicationError } from '@infra/common/errors/application.error';
import { ValidationErrorReason } from '@infra/common/errors/interfaces/errors.interfaces';
import { ValidationExceptionFactory } from '@infra/factories/validation-exception.factory';
import { PixPaymentGatewayService } from '@payment/integration/services/pix-payment-gateway.service';
import { PendingPixPaymentMessage } from '@payment/integration/messages/pending-pix-payment.message';
import { SqsMessageHandler } from '@ssut/nestjs-sqs';
import { plainToInstance } from 'class-transformer';
import { Message } from '@aws-sdk/client-sqs';
import { validate } from 'class-validator';

@Injectable()
export class PendingPixPaymentConsumer {
  private readonly logger: Logger = new Logger(PendingPixPaymentConsumer.name);
  constructor(private readonly pixPaymentService: PixPaymentGatewayService) {}

  @SqsMessageHandler('pending-pix-payment', false)
  async handleMessage(message: Message) {
    this.logger.log(`(messageId=${message.MessageId}) Message received`);
    const payload = await this.validate(message);
    if (!payload) return;

    return this.pixPaymentService.processPayment(payload, message);
  }

  private async validate(message: Message) {
    const payload = plainToInstance(PendingPixPaymentMessage, JSON.parse(message.Body));
    const errors = await validate(payload);
    if (errors.length > 0) {
      this.logger.error(`(messageId=${message.MessageId}) • Failed to process message `);

      const error = new ApplicationError<ValidationErrorReason>(
        ValidationExceptionFactory(
          errors,
        ).getResponse() as ApplicationError<ValidationErrorReason>,
      );

      const field = error.errors?.[0]?.property ?? 'unknown';
      const fieldMessage = error.errors?.[0]?.messages?.[0] ?? 'Payload inválido';
      this.logger.error(`(messageId=${message.MessageId}) • ${field}: ${fieldMessage}`);
      return null;
    }

    return payload;
  }
}
