import { Module } from '@nestjs/common';
import { MongooseModule } from '@nestjs/mongoose';
import { PaymentRepository } from './repositories/payment.repository';
import { Payment, PaymentSchema } from './documents/payment.schema';
import { ConfigService } from '@config/config.service';

@Module({
  imports: [
    MongooseModule.forRootAsync({
      inject: [ConfigService],
      useFactory: (config: ConfigService) => ({
        uri: config.MONGODB_URL,
      }),
    }),
    MongooseModule.forFeature([{ name: Payment.name, schema: PaymentSchema }]),
  ],
  providers: [PaymentRepository],
  exports: [MongooseModule, PaymentRepository],
})
export class DatabaseModule {}
