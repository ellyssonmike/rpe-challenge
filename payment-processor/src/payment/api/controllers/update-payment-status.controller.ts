import { Body, Controller, HttpCode, HttpStatus, Patch } from '@nestjs/common';
import { UpdatePaymentStatusService } from '@payment/application/services/update-payment-status.service';
import { UpdatePaymentStatusRequest } from '../dtos/update-payment-status.request';
import { PaymentDocs } from '@payment/payment.docs';
import { ApiTags } from '@nestjs/swagger';
import { Docs } from '@docs/index';

@ApiTags('Pagamentos')
@Controller('/payments')
export class UpdatePaymentStatusController {
  constructor(private readonly updatePaymentStatus: UpdatePaymentStatusService) {}

  @Patch('/status')
  @HttpCode(HttpStatus.NO_CONTENT)
  @Docs(PaymentDocs.updateStatus())
  async updateStatus(@Body() request: UpdatePaymentStatusRequest) {
    return this.updatePaymentStatus.execute(request);
  }
}
