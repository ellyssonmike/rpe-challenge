import { Nullable } from 'src/shared/types/nullable';
import { PaymentStatus } from '../../domain/enums/payment-status.enum';
import { ApiOkResponse, ApiProperty, ApiPropertyOptional } from '@nestjs/swagger';
import { SwaggerResponse } from '@docs/index';

@SwaggerResponse(ApiOkResponse)
export class PaymentProcessResponse {
  @ApiProperty({ enum: PaymentStatus, description: 'Status do pagamento' })
  paymentStatus: PaymentStatus;

  @ApiPropertyOptional({
    type: Date,
    format: 'date-time',
    description: 'Data do pagamento efetivado (quando status for PAID)',
  })
  paymentDate: Nullable<Date>;

  @ApiProperty({
    type: Date,
    format: 'date-time',
    description: 'Data de processamento do pagamento',
  })
  processedAt: Date;
}
