import { PaymentStatus } from '@payment/domain/enums/payment-status.enum';
/* eslint-disable prettier/prettier */
export default {
  status: {
    PIX: [
      { status: PaymentStatus.PAID,     weight: 80 },
      { status: PaymentStatus.REFUSED,  weight: 10 },
      { status: PaymentStatus.CANCELED, weight: 10 },
    ],
    CREDIT_CARD: [
      { status: PaymentStatus.PAID,     weight: 80 },
      { status: PaymentStatus.REFUSED,  weight: 10 },
      { status: PaymentStatus.CANCELED, weight: 10 },
    ],
    DEBIT_CARD: [
      { status: PaymentStatus.PAID,     weight: 80 },
      { status: PaymentStatus.REFUSED,  weight: 15 },
      { status: PaymentStatus.CANCELED, weight: 5 },
    ],
  },
  timeout: [
    { weight: 85, range: { min: 0.0, max: 0.2 } },
    { weight: 10, range: { min: 0.2, max: 1.0 } },
    { weight: 3,  range: { min: 1.0, max: 5.0 } },
    { weight: 2,  range: { min: 5.0, max: 90  } },
  ],
};
