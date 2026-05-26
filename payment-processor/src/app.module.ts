import { Module } from '@nestjs/common';
import { AppController } from './app.controller';
import { AppService } from './app.service';
import { ConfigModule } from '@config/config.module';
import { PaymentModule } from '@payment/payment.module';
import { DocsModule } from '@docs/docs.module';

@Module({
  imports: [ConfigModule, DocsModule, PaymentModule],
  controllers: [AppController],
  providers: [AppService],
})
export class AppModule {}
