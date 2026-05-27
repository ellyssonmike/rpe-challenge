import { notify } from '@/components/notifications'
import { FieldPath, Form, UseFormReturn } from 'react-hook-form'

export interface APIError {
  timestamp: Date
  requestId: string
  error: string
  module: APIErrorModule
  code: string
  message: string
  fields?: APIErrorField[]
  details?: Object
}

type APIErrorModule = 'Auth' | 'Users' | 'Orders'
interface APIErrorField {
  field: string
  message: string[]
}

export function hasFieldErrors(error: APIError): boolean {
  return error.fields !== undefined && Array.isArray(error.fields)
}

export function parseError(error: any): APIError {
  return {
    timestamp: new Date(error.timestamp),
    requestId: error?.requestId ?? '',
    error: error?.error ?? '',
    module: error?.module ?? 'Unknown',
    code: error?.code ?? 'OU.UNKNOWN-ERR.API',
    message: error?.message ?? '',
    ...(hasFieldErrors(error) && {
      fields: (error.fields as APIErrorField[]).map(({ field, message }) => ({
        field,
        message,
      })),
    }),
    details: error?.details,
  }
}

export function setFormErrors<T extends Record<string, any>>(
  form: UseFormReturn<T>,
  error: APIError,
) {
  if (!hasFieldErrors(error) || !error.fields) return

  for (const { field, message } of error.fields) {
    form.setError(field as FieldPath<T>, {
      type: 'manual',
      message: message[0],
    })
  }
}

export function handleFormError<T extends Record<string, any>>(
  form: UseFormReturn<T>,
  error: APIError,
) {
  if (!hasFieldErrors(error)) {
    return notify.error(error.message)
  }

  setFormErrors(form, error)
}
