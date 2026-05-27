import { Nullable } from '@shared/types/nullable';

export abstract class UpdatePaymentStatusInput {
  orderId: string;
  paymentStatus: string;
  paymentDate: Nullable<Date>;
}
