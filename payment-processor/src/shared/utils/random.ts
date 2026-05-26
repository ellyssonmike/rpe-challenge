import { PaymentMethod } from 'src/payment/domain/enums/payment-method.enum';
import { PaymentStatus } from 'src/payment/domain/enums/payment-status.enum';

export type PaymentWeight<T extends PaymentMethod> = Record<T, PaymentConfig[]>;

export interface PaymentConfig {
  status: PaymentStatus;
  weight: number;
}

export interface TimeoutRange {
  min: number;
  max: number;
}

export interface TimeoutWeight {
  weight: number;
  range: TimeoutRange;
}

export function getStatus<T extends PaymentMethod>(
  paymentMethod: T,
  statusWeight: PaymentWeight<T>,
) {
  const sortedWeights = [...statusWeight[paymentMethod]].sort(
    (a, b) => b.weight - a.weight,
  );

  const total = statusWeight[paymentMethod].reduce(
    (sum, status) => sum + status.weight,
    0,
  );

  let random = Math.random() * total;
  for (const item of sortedWeights) {
    if (random < item.weight) {
      return item.status;
    }

    random -= item.weight;
  }

  return PaymentStatus.PAID;
}

export function getTimeout(timeoutWeights: TimeoutWeight[]) {
  const sortedWeights = [...timeoutWeights].sort((a, b) => b.weight - a.weight);
  const total = timeoutWeights.reduce((sum, timeout) => sum + timeout.weight, 0);

  let random = Math.random() * total;
  let range: TimeoutRange = { min: 0, max: 0 };

  for (const timeout of sortedWeights) {
    if (random < timeout.weight) {
      range = timeout.range;
      break;
    }

    random -= timeout.weight;
  }

  return (range.min + Math.random() * (range.max - range.min)) * 1000;
}
