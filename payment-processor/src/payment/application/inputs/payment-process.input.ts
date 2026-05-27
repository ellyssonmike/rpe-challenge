import { PaymentMethod } from 'src/payment/domain/enums/payment-method.enum';

export abstract class PaymentProcessInput {
  paymentMethod: PaymentMethod;
  orderId: string;
  itemId: string;
  amount: number;
  buyerName: string;
  buyerCpf: string;
}
