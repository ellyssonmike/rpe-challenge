import { Module } from '@nestjs/common';
import { DatabaseModule } from './database/database.module';
import { IntegrationModule } from './integration/integration.module';
import { PaymentProcessService } from './application/services/payment-process.service';
import { UpdatePaymentStatusService } from './application/services/update-payment-status.service';
import { CardPaymentGatewayService } from './integration/services/card-payment-gateway.service';
import { PixPaymentGatewayService } from './integration/services/pix-payment-gateway.service';
import { PixPaymentStatusProducer } from './integration/aws/sqs/producers/pix-payment-status.producer';
import { PendingPixPaymentProducer } from './integration/aws/sqs/producers/pending-pix-payment.producer';

@Module({
  imports: [DatabaseModule, IntegrationModule],
  providers: [
    PaymentProcessService,
    UpdatePaymentStatusService,
    CardPaymentGatewayService,
    PixPaymentGatewayService,
    PixPaymentStatusProducer,
    PendingPixPaymentProducer,
  ],
})
export class PaymentModule {}
