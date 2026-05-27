'use client'

import { ApplicationErrorOptions } from '@/common/errors/application-error'
import { Button } from '@/components/ui/button'
import { Separator } from '@/components/ui/separator'
import Link from 'next/link'

function getErrorMessage(error: Error) {
  try {
    const err = JSON.parse(error.message) as ApplicationErrorOptions
    return (
      <>
        <div>
          {err.message}
          <div className="text-accent-foreground text-xs font-semibold">
            {err.code}
          </div>
        </div>
      </>
    )
  } catch (err) {
    return (
      <>
        <div>Parece que algo não está funcionando muito bem por aqui.</div>
      </>
    )
  }
}

export default function Error({
  error,
  reset,
}: {
  error: Error
  reset: () => void
}) {
  return (
    <div className="flex h-screen flex-col items-center justify-center">
      <h2 className="text-2xl font-bold">Ooops, ocorreu um erro!</h2>
      <div className="max-w-72 text-center">
        <div className="text-gray-500">{getErrorMessage(error)}</div>
      </div>
      <Button
        onClick={() => reset()}
        className="bg-primary hover:bg-accent-foreground/95 mt-4 cursor-pointer rounded px-4 py-2 text-white"
      >
        Tentar novamente
      </Button>
      <div className="my-4 flex w-full max-w-72 flex-row items-center justify-center gap-3">
        <Separator className="flex-1" />
        <span className="text-sm text-gray-400">ou</span>
        <Separator className="flex-1" />
      </div>
      <Link
        className="text-accent-foreground"
        href="mailto: suporte@challenge.rpe.com"
      >
        Entre em contato com nosso suporte
      </Link>
    </div>
  )
}
