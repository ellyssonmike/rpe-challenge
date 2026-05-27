import { api } from "./api.service";

export function authLogin(email: string, password: string) {
  return api.post('/auth/login', {
    email,
    password,
  });
}