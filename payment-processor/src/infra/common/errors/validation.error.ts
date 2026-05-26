import { HttpStatus, UnprocessableEntityException } from '@nestjs/common';

export class ValidationError extends UnprocessableEntityException {
  static readonly status: HttpStatus = HttpStatus.UNPROCESSABLE_ENTITY;
}
