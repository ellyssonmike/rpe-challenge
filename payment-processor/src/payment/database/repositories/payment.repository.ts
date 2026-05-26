import { Injectable } from '@nestjs/common';
import { InjectModel } from '@nestjs/mongoose';
import { Model } from 'mongoose';
import { Payment } from '@payment/database/documents/payment.schema';

@Injectable()
export class PaymentRepository {
  constructor(
    @InjectModel(Payment.name)
    private readonly paymentModel: Model<Payment>,
  ) {}

  async create(data: Partial<Payment>): Promise<Payment> {
    return this.paymentModel.create(data);
  }

  async findByOrderId(orderId: string): Promise<Payment | null> {
    return this.paymentModel.findOne({
      orderId,
    });
  }

  async patchByOrderId(orderId: string, data: Partial<Payment>): Promise<void> {
    await this.paymentModel.updateOne({ orderId }, { $set: data });
  }
}
