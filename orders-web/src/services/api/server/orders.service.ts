import { api } from "./api.service";
import { CreateOrderRequest } from "@/common/types/requests";

export function getOrders() {
  return api.get('/orders', true);
}

export function createOrder(payload: CreateOrderRequest) {
  return api.post('/orders', payload, true);
}