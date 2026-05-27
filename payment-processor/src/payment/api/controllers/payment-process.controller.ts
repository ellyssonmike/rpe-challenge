import { Body, Controller, HttpCode, HttpStatus, Post } from '@nestjs/common';
import { PaymentProcessService } from 'src/payment/application/services/payment-process.service';
import { PaymentProcessRequest } from '../dtos/payment-process.request';
import { PaymentProcessResponse } from '../dtos/payment-process.response';
import { PaymentDocs } from '@payment/payment.docs';
import { ApiTags } from '@nestjs/swagger';
import { Docs } from '@docs/index';

@ApiTags('Pagamentos')
@Controller('/payments')
export class PaymentProcessController {
  constructor(private readonly paymentProcess: PaymentProcessService) {}

  @Post('/process')
  @HttpCode(HttpStatus.OK)
  @Docs(PaymentDocs.process())
  async process(@Body() request: PaymentProcessRequest): Promise<PaymentProcessResponse> {
    return this.paymentProcess.execute(request);
  }
}
