export type UserRole = 'ADMIN' | 'MANAGER' | 'USER';
export type AuthenticatedUser = User & { password: string };
export interface User {
  id: string;
  firstName: string;
  lastName: string;
  email: string;
  role: UserRole;
  createdAt: Date;
  updateAt: Date;
}