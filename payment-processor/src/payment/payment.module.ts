import { Module } from '@nestjs/common';
import { DatabaseModule } from './database/database.module';
import { IntegrationModule } from './integration/integration.module';
import { PaymentProcessController } from './api/controllers/payment-process.controller';
import { PaymentProcessService } from './application/services/payment-process.service';
import { CardPaymentGatewayService } from './integration/services/card-payment-gateway.service';
import { PixPaymentGatewayService } from './integration/services/pix-payment-gateway.service';
import { PixPaymentStatusProducer } from './integration/aws/sqs/producers/pix-payment-status.producer';
import { PendingPixPaymentProducer } from './integration/aws/sqs/producers/pending-pix-payment.producer';
import { PendingPixPaymentConsumer } from './application/consumers/pending-pix-payment.consumer';

@Module({
  imports: [DatabaseModule, IntegrationModule],
  controllers: [PaymentProcessController],
  providers: [
    PaymentProcessService,
    CardPaymentGatewayService,
    PixPaymentGatewayService,
    PixPaymentStatusProducer,
    PendingPixPaymentProducer,
    PendingPixPaymentConsumer,
  ],
})
export class PaymentModule {}
