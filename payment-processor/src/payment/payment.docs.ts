import { ControllerSchema } from '@docs';
import { PaymentProcessResponse } from './api/dtos/payment-process.response';

export class PaymentDocs {
  static process(): ControllerSchema {
    return {
      summary: 'Processar pagamento',
      description: 'Processa pagamentos do serviço de Ordens',
      validated: true,
      responses: [PaymentProcessResponse],
    };
  }
}
