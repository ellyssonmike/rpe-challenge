import { CreateUserRequest } from "@/common/types/requests";
import { api } from "./api.service";

export function createUser(payload: CreateUserRequest) {
  return api.post('/users', payload);
}