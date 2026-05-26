import { Prop, Schema } from '@nestjs/mongoose';

import { PaymentMethod } from '@payment/domain/enums/payment-method.enum';

@Schema({
  _id: false,
})
export class OrderDetails {
  @Prop({
    required: true,
  })
  orderId: string;

  @Prop({
    required: true,
  })
  itemId: string;

  @Prop({
    required: true,
  })
  amount: number;

  @Prop({
    required: true,
  })
  buyerName: string;

  @Prop({
    required: true,
  })
  buyerCpf: string;

  @Prop({
    required: true,
    enum: PaymentMethod,
  })
  paymentMethod: PaymentMethod;
}
