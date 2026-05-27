export type OrderStatus  = 'PENDING' | 'PAID' | 'CANCELED' | 'REFUSED';
export type PaymentMethod = 'PIX' | 'CREDIT_CARD' | 'DEBIT_CARD';

export interface Order {
  id: string;
  status: OrderStatus;
  paymentMethod: PaymentMethod;
  itemId: string;
  amount: number;
  buyerName: string;
  buyerCpf: string;
  paymentDate: Date;
  createdAt: Date;
  updatedAt: Date;
}