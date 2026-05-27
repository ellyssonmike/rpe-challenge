import { api } from "./api.service";

export function getPaymentMethods() {
  return api.get('/payment-methods');
}