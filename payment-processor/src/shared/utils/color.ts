import { PaymentStatus } from 'src/payment/domain/enums/payment-status.enum';
import { format } from 'util';

export function getColoredStatus(paymentStatus: PaymentStatus) {
  const colorFormat: Record<Exclude<PaymentStatus, PaymentStatus.PENDING>, string> = {
    [PaymentStatus.PAID]: `\u001b[1;32m%s\u001b[0m`,
    [PaymentStatus.REFUSED]: `\u001b[1;31m%s\u001b[0m`,
    [PaymentStatus.CANCELED]: `\u001b[1;31m%s\u001b[0m`,
  };

  return colorFormat[paymentStatus]
    ? format(colorFormat[paymentStatus], paymentStatus)
    : paymentStatus;
}
