import { IsUUID } from 'class-validator';

export class PendingPixPaymentMessage {
  @IsUUID(4, { message: 'must be a UUIDv4' })
  orderId: string;
}
