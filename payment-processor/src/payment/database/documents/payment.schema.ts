import { Prop, Schema, SchemaFactory } from '@nestjs/mongoose';
import { HydratedDocument } from 'mongoose';

import { PaymentMethod } from '@payment/domain/enums/payment-method.enum';
import { PaymentStatus } from '@payment/domain/enums/payment-status.enum';
import { OrderDetails } from './order-details.schema';

export type PaymentDocument = HydratedDocument<Payment>;

@Schema({
  collection: 'payments',
  timestamps: true,
})
export class Payment {
  @Prop({
    required: true,
    unique: true,
  })
  orderId: string;

  @Prop({
    required: true,
    enum: PaymentMethod,
  })
  paymentMethod: PaymentMethod;

  @Prop({
    required: true,
    enum: PaymentStatus,
  })
  paymentStatus: PaymentStatus;

  @Prop()
  paymentDate?: Date;

  @Prop()
  processedAt?: Date;

  @Prop({
    type: OrderDetails,
    required: true,
  })
  orderDetails: OrderDetails;

  @Prop({ type: Object })
  gatewayResponse?: unknown;
}

export const PaymentSchema = SchemaFactory.createForClass(Payment);
