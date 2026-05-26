import { IsEnum, IsNotEmpty, IsNumber, IsUUID, Min } from 'class-validator';
import { ApiProperty } from '@nestjs/swagger';
import { PaymentMethod } from 'src/payment/domain/enums/payment-method.enum';
import { IsCPF } from '@infra/common/decorators/validation/is-cpf.decorator';

export class PaymentProcessRequest {
  @ApiProperty({ enum: PaymentMethod, description: 'Método de pagamento' })
  @IsEnum(PaymentMethod)
  paymentMethod: PaymentMethod;

  @ApiProperty({ type: String, format: 'uuid', description: 'ID da ordem' })
  @IsUUID()
  orderId: string;

  @ApiProperty({
    type: String,
    format: 'uuid',
    description: 'ID do item a ser efetuado o pagamento',
  })
  @IsUUID()
  itemId: string;

  @ApiProperty({
    type: Number,
    format: 'int64',
    description: 'Valor total do pagamento (em centavos)',
  })
  @IsNumber()
  @Min(1)
  amount: number;

  @ApiProperty({ type: String, description: 'Nome do comprador' })
  @IsNotEmpty()
  buyerName: string;

  @ApiProperty({
    type: String,
    format: 'formato: "12345678910" ou "123.456.789-10"',
    description: 'CPF do comprador',
  })
  @IsNotEmpty()
  @IsCPF()
  buyerCpf: string;
}
