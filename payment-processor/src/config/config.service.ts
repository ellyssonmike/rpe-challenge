import { Injectable, Logger } from '@nestjs/common';
import { ApplicationError } from '@infra/common/errors/application.error';
import { ValidationErrorReason } from '@infra/common/errors/interfaces/errors.interfaces';
import { ValidationExceptionFactory } from '@infra/factories/validation-exception.factory';
import { ConfigService as GlobalConfigService } from '@nestjs/config';
import { manifest } from '@core/manifest';
import {
  IsBoolean,
  IsEnum,
  IsNotEmpty,
  IsString,
  Max,
  Min,
  validate,
  ValidateIf,
} from 'class-validator';

@Injectable()
export class ConfigService {
  constructor(
    private readonly logger: Logger,
    private readonly service: GlobalConfigService,
  ) {}

  async init() {
    const errors = await validate(this);
    if (errors.length > 0) {
      this.logger.error(
        'Não foi possível iniciar a aplicação, verifique as variáveis de ambiente.',
      );

      const error = new ApplicationError<ValidationErrorReason>(
        ValidationExceptionFactory(
          errors,
        ).getResponse() as ApplicationError<ValidationErrorReason>,
      );

      if (!error.errors?.length) return;
      for (const err of error.errors) {
        this.logger.error(err.property);
        for (let i = 0; i < err.messages.length; i++) {
          this.logger.error(`  • ${err.messages[i]}`);
        }
      }

      const errorMessage =
        'Variáveis de ambiente inválidas, verifique as configurações e tente novamente.';

      if (!this.SERVICE_THROW_ON_STARTUP_ERROR) {
        this.logger.error(errorMessage);
      }

      throw new Error(errorMessage);
    }
  }

  async onModuleInit() {
    await this.init();
  }

  get SERVICE_NAME(): string {
    return manifest.name;
  }

  get SERVICE_VERSION(): string {
    return manifest.version;
  }

  get SERVICE_DESCRIPTION(): string {
    return manifest.description;
  }

  @IsBoolean({
    message: 'Deve ser um formato booleano válido. Utilize 0, 1, false ou true',
  })
  get SERVICE_THROW_ON_STARTUP_ERROR(): boolean {
    const throwOnStartupError =
      this.service.get<string>('SERVICE_THROW_ON_STARTUP_ERROR') || 'true';
    return throwOnStartupError === 'true' || throwOnStartupError === '1';
  }

  @IsNotEmpty({ message: 'Não pode estar vazio' })
  @IsString({ message: 'Deve ser uma string válida' })
  get AWS_REGION(): string {
    return this.service.get<string>('AWS_REGION');
  }

  @IsNotEmpty({ message: 'Não pode estar vazio' })
  @IsString({ message: 'Deve ser uma string válida' })
  get AWS_ACCESS_KEY_ID(): string {
    return this.service.get<string>('AWS_ACCESS_KEY_ID');
  }

  @IsNotEmpty({ message: 'Não pode estar vazio' })
  @IsString({ message: 'Deve ser uma string válida' })
  get AWS_SECRET_ACCESS_KEY(): string {
    return this.service.get<string>('AWS_SECRET_ACCESS_KEY');
  }

  @IsNotEmpty({ message: 'Não pode estar vazio' })
  @IsString({ message: 'Deve ser uma string válida' })
  get AWS_SQS_ENDPOINT(): string {
    return this.service.get<string>('AWS_SQS_ENDPOINT');
  }

  @IsEnum(['https', 'http'], {
    message: 'Apenas "https" ou "http" são permitidos',
  })
  get PAYMENT_API_PROTOCOL(): string {
    return this.service.get<string>('PAYMENT_API_PROTOCOL');
  }

  get PAYMENT_API_ADDRESS(): string {
    return this.service.get<string>('PAYMENT_API_ADDRESS');
  }

  @Min(1, { message: 'Porta inválida' })
  @Max(65535, { message: 'Porta inválida' })
  get PAYMENT_API_PORT(): number {
    return Number(this.service.get<number>('PAYMENT_API_PORT', 3000));
  }

  get PAYMENT_API_URL(): string {
    const hiddenPort = [80, 443].includes(this.PAYMENT_API_PORT);
    return hiddenPort
      ? `${this.PAYMENT_API_PROTOCOL}://${this.PAYMENT_API_ADDRESS}`
      : `${this.PAYMENT_API_PROTOCOL}://${this.PAYMENT_API_ADDRESS}:${this.PAYMENT_API_PORT}`;
  }

  @IsNotEmpty({ message: 'Não pode estar vazio' })
  @IsString({ message: 'Deve ser uma string válida' })
  get PAYMENT_MONGODB_HOST(): string {
    return this.service.get<string>('PAYMENT_MONGODB_HOST');
  }

  @Min(1, { message: 'Porta inválida' })
  @Max(65535, { message: 'Porta inválida' })
  get PAYMENT_MONGODB_PORT(): number {
    return Number(this.service.get<number>('PAYMENT_MONGODB_PORT'));
  }

  @IsNotEmpty({ message: 'Não pode estar vazio' })
  @IsString({ message: 'Deve ser uma string válida' })
  get PAYMENT_MONGODB_DATABASE(): string {
    return this.service.get<string>('PAYMENT_MONGODB_DATABASE');
  }

  get PAYMENT_MONGODB_USERNAME(): string {
    return this.service.get<string>('PAYMENT_MONGODB_USERNAME') || '';
  }

  get PAYMENT_MONGODB_PASSWORD(): string {
    return this.service.get<string>('PAYMENT_MONGODB_PASSWORD') || '';
  }

  get MONGODB_URL(): string {
    return this.PAYMENT_MONGODB_USERNAME && this.PAYMENT_MONGODB_PASSWORD
      ? `mongodb://${this.PAYMENT_MONGODB_USERNAME}:${this.PAYMENT_MONGODB_PASSWORD}@${this.PAYMENT_MONGODB_HOST}:${this.PAYMENT_MONGODB_PORT}/${this.PAYMENT_MONGODB_DATABASE}`
      : `mongodb://${this.PAYMENT_MONGODB_HOST}:${this.PAYMENT_MONGODB_PORT}/${this.PAYMENT_MONGODB_DATABASE}`;
  }

  @IsBoolean({
    message: 'Deve ser um formato booleano válido. Utilize 0, 1, false ou true',
  })
  get PAYMENT_DOCS_ENABLED(): boolean {
    const docsEnabled = this.service.get<string>('PAYMENT_DOCS_ENABLED') || 'true';
    return docsEnabled === 'true' || docsEnabled === '1';
  }

  @ValidateIf((object) => object.PAYMENT_DOCS_ENABLED)
  @IsNotEmpty({
    message: 'Não pode estar vazio quando DOCS_ENABLED estiver habilitado',
  })
  @IsString({ message: 'Deve ser uma string válida' })
  get PAYMENT_DOCS_OPERATOR(): string {
    return this.service.get<string>('PAYMENT_DOCS_OPERATOR') ?? '';
  }

  @ValidateIf((object) => object.PAYMENT_DOCS_ENABLED)
  @IsNotEmpty({
    message: 'Não pode estar vazio quando DOCS_ENABLED estiver habilitado',
  })
  @IsString({ message: 'Deve ser uma string válida' })
  get PAYMENT_DOCS_PASSWORD(): string {
    return this.service.get<string>('PAYMENT_DOCS_PASSWORD') ?? '';
  }
}
