import { OrderStatus, PaymentMethod } from "@/types/order";
import { UserRole } from "@/types/user";

export interface CreateOrderResponse {
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

export interface CreateUserResponse {
  id: string;
  firstName: string;
  lastName?: string;
  email: string;
  role: UserRole;
  createdAt: Date;
  updatedAt: Date;
}