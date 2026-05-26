import { Module } from '@nestjs/common';
import { DatabaseModule } from './database/database.module';
import { IntegrationModule } from './integration/integration.module';

@Module({
  imports: [DatabaseModule, IntegrationModule],
})
export class PaymentModule {}
