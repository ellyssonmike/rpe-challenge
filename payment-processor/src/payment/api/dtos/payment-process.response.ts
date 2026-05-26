import { Nullable } from 'src/shared/types/nullable';
import { PaymentStatus } from '../../domain/enums/payment-status.enum';

export abstract class PaymentProcessResponse {
  paymentStatus: PaymentStatus;
  paymentDate: Nullable<Date>;
  processedAt: Date;
}
