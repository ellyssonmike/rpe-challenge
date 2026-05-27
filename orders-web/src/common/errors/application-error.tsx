export interface ApplicationErrorOptions {
  code: string
  message: string
}

export class ApplicationError extends Error {
  constructor({ code, message }: ApplicationErrorOptions) {
    super(ApplicationError.serialize({ code, message }))
  }

  private static serialize(options: ApplicationErrorOptions) {
    return JSON.stringify(options)
  }
}
