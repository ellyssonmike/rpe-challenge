import { IsCPF } from '@infra/common/decorators/validation/is-cpf.decorator';
import { IsEnum, IsNotEmpty, IsNumber, IsUUID, Min } from 'class-validator';
import { PaymentMethod } from 'src/payment/domain/enums/payment-method.enum';

export class PaymentProcessRequest {
  @IsEnum(PaymentMethod)
  paymentMethod: PaymentMethod;

  @IsUUID()
  orderId: string;

  @IsUUID()
  itemId: string;

  @IsNumber()
  @Min(1)
  amount: number;

  @IsNotEmpty()
  buyerName: string;

  @IsNotEmpty()
  @IsCPF()
  buyerCpf: string;
}
