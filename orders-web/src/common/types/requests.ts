import { PaymentMethod } from "@/types/order";
import { UserRole } from "@/types/user";

export interface CreateOrderRequest {
  itemId: string;
  buyerName: string;
  buyerCpf: string;
  paymentMethod: PaymentMethod;
  amount: number;
}

export interface CreateUserRequest {
  firstName: string;
  lastName?: string;
  email: string;
  password: string;
  role: UserRole;
}