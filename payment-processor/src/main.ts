import 'reflect-metadata';
import { NestFactory } from '@nestjs/core';
import { AppModule } from './app.module';
import { ValidationPipe } from '@nestjs/common';
import { ConfigService } from '@config/config.service';
import { DocumentBuilder, SwaggerModule } from '@nestjs/swagger';
import { DocsBuilderService } from '@docs/services/docs-builder.service';
import { ValidationExceptionFactory } from './infra/factories/validation-exception.factory';
import { ApplicationExceptionFilter } from './infra/filters/application-exception-filter';
import { BasichAuthAuthorizer } from './docs/basic-auth.authorizer';
import basicAuth from 'express-basic-auth';

async function bootstrap() {
  const app = await NestFactory.create(AppModule);
  const config = app.get(ConfigService);
  const authorizer = app.get(BasichAuthAuthorizer);

  app.enableCors({
    origin: '*',
    methods: 'GET,HEAD,PUT,PATCH,POST,DELETE,OPTIONS',
    preflightContinue: false,
    optionsSuccessStatus: 204,
    credentials: true,
  });

  app.useGlobalPipes(
    new ValidationPipe({
      transform: true,
      whitelist: true,
      forbidNonWhitelisted: true,
      forbidUnknownValues: true,
      exceptionFactory: ValidationExceptionFactory,
    }),
  );

  app.useGlobalFilters(new ApplicationExceptionFilter());

  if (config.PAYMENT_DOCS_ENABLED) {
    app.use(
      ['/docs'],
      basicAuth({
        challenge: true,
        authorizer: authorizer.authorize.bind(authorizer),
        unauthorizedResponse: authorizer.getUnauthorizedResponse(),
        authorizeAsync: true,
        realm: 'Payment Processor',
      }),
    );

    const swaggerBuilder = app.get(DocsBuilderService);
    swaggerBuilder.execute();

    const swaggerConfigs = new DocumentBuilder()
      .setTitle('Payment Processor')
      .setDescription(config.SERVICE_DESCRIPTION)
      .setVersion(config.SERVICE_VERSION)
      .addTag('Status')
      .addTag('Pagamentos')
      .addServer(config.PAYMENT_API_URL)
      .build();

    const document = SwaggerModule.createDocument(app, swaggerConfigs);
    SwaggerModule.setup('/docs', app, document);
  }

  await app.listen(config.PAYMENT_API_PORT);
}
void bootstrap();
