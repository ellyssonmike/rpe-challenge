import { IsDate, IsEnum, IsUUID, ValidateIf } from 'class-validator';
import { PaymentStatus } from '@payment/domain/enums/payment-status.enum';
import { ApiProperty } from '@nestjs/swagger';
import { ParseDate } from '@infra/common/decorators/validation/parse-date.decorator';

export class UpdatePaymentStatusRequest {
  @ApiProperty({ type: String, format: 'uuid', description: 'ID da ordem' })
  @IsUUID()
  orderId: string;

  @ApiProperty({
    type: Date,
    format: 'date-time',
    description: 'Data do pagamento efetivado',
  })
  @ValidateIf((_, value) => value)
  @ParseDate()
  @IsDate()
  paymentDate: Date;

  @ApiProperty({ enum: PaymentStatus, description: 'Status do pagamento' })
  @IsEnum(PaymentStatus)
  paymentStatus: string;
}
