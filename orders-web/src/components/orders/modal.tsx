'use client'

import * as React from 'react'
import { useForm } from 'react-hook-form'
import { zodResolver } from '@hookform/resolvers/zod'
import * as z from 'zod'
import { PlusCircledIcon } from '@radix-ui/react-icons'

import { Button } from '@/components/ui/button'
import {
  Dialog,
  DialogContent,
  DialogDescription,
  DialogHeader,
  DialogTitle,
  DialogTrigger,
} from '@/components/ui/dialog'
import {
  Form,
  FormControl,
  FormField,
  FormItem,
  FormLabel,
  FormMessage,
} from '@/components/ui/form'
import { Input } from '@/components/ui/input'
import {
  AlertDialog,
  AlertDialogAction,
  AlertDialogCancel,
  AlertDialogContent,
  AlertDialogDescription,
  AlertDialogFooter,
  AlertDialogHeader,
  AlertDialogTitle,
} from '@/components/ui/alert-dialog'
import { toast } from 'sonner'
import {
  Select,
  SelectContent,
  SelectItem,
  SelectTrigger,
  SelectValue,
} from '../ui/select'
import { client } from '@/services/api/client/client.service'
import { Separator } from '../ui/separator'
import { maskCPF, maskCurrency, maskUUID } from '@/shared/utils'
import { handleFormError } from '@/common/errors/errors'
import { Loader2, PlusIcon } from 'lucide-react'

enum PaymentMethodType {
  PIX = 'PIX',
  CREDIT_CARD = 'CREDIT_CARD',
  DEBIT_CARD = 'DEBIT_CARD',
}

const formSchema = z.object({
  itemId: z.uuidv4('UUID inválido'),
  buyerName: z.string().min(4, 'Nome muito curto'),
  buyerCpf: z.string().min(11, 'CPF inválido'),
  amount: z.string().min(1, 'Informe o valor'),
  paymentMethod: z.enum(PaymentMethodType, 'Selecione o método'),
})

const select: Record<PaymentMethodType, string> = {
  PIX: 'PIX',
  CREDIT_CARD: 'Cartão de crédito',
  DEBIT_CARD: 'Cartão de débito',
}

interface PaymentMethod {
  id: string
  description: PaymentMethodType
}

interface CreateOrderModalProps {
  onCreateSuccess: () => void
}

export function CreateOrderModal({ onCreateSuccess }: CreateOrderModalProps) {
  const [open, setOpen] = React.useState(false)
  const [showExitConfirm, setShowExitConfirm] = React.useState(false)
  const [methods, setMethods] = React.useState<PaymentMethod[]>([])

  React.useEffect(() => {
    if (open) {
      client
        .get('/api/payment-methods')
        .then((res) => res.json())
        .then(({ data }) => setMethods(data))
    }
  }, [open])

  const form = useForm<z.infer<typeof formSchema>>({
    resolver: zodResolver(formSchema),
    defaultValues: {
      itemId: '',
      buyerName: '',
      buyerCpf: '',
      amount: '',
    },
  })

  const { isSubmitting, isDirty } = form.formState

  const handleCancelClick = () => {
    if (isDirty) {
      setShowExitConfirm(true)
    } else {
      setOpen(false)
      form.reset()
    }
  }

  async function onSubmit(values: z.infer<typeof formSchema>) {
    try {
      const response = await client.post('/api/orders', {
        ...values,
        amount: parseInt(values.amount.replace(/\D/g, ''), 10),
      })

      if (!response.ok) {
        const result = await response.json()
        handleFormError(form, result)
        return
      }

      onCreateSuccess?.()
      toast.success('Ordem de serviço criada com sucesso.')
      setOpen(false)
      form.reset()
    } catch (error: any) {
      toast.error(
        error.message || 'Não foi possível criar a ordem de serviço',
        {
          id: 'order-error',
          description: 'Tente novamente.',
        },
      )
    }
  }

  return (
    <>
      <Dialog
        open={open}
        onOpenChange={(val) => {
          if (!val && isDirty) {
            setShowExitConfirm(true)
          } else {
            setOpen(val)
            if (!val) form.reset()
          }
        }}
      >
        <DialogTrigger asChild>
          <Button variant="default" size="sm" className="mr-2 flex h-8">
            <PlusCircledIcon className="mr-2 h-4 w-4" />
            Nova ordem de serviço
          </Button>
        </DialogTrigger>
        <DialogContent
          aria-describedby="Create service order"
          className="max-w-xs min-w-72 p-5 sm:max-w-sm"
        >
          <DialogHeader>
            <DialogTitle>Criar ordem de serviço</DialogTitle>
            <DialogDescription className="max-w-11/12">
              Preencha os campos informados para criar uma nova ordem de serviço
            </DialogDescription>
          </DialogHeader>
          <Separator />
          <Form {...form}>
            <form onSubmit={form.handleSubmit(onSubmit)} className="space-y-4">
              <FormField
                control={form.control}
                name="itemId"
                render={({ field }) => (
                  <FormItem>
                    <FormLabel>Item</FormLabel>
                    <FormControl>
                      <Input
                        className="text-xs!"
                        {...field}
                        onChange={(e) => {
                          const maskedValue = maskUUID(e.target.value)
                          field.onChange(maskedValue)
                        }}
                      />
                    </FormControl>
                    <FormMessage />
                  </FormItem>
                )}
              />
              <FormField
                control={form.control}
                name="buyerName"
                render={({ field }) => (
                  <FormItem>
                    <FormLabel>Nome do comprador</FormLabel>
                    <FormControl>
                      <Input className="text-xs!" {...field} />
                    </FormControl>
                    <FormMessage />
                  </FormItem>
                )}
              />
              <div className="grid grid-cols-1 gap-2 sm:grid-cols-2">
                <FormField
                  control={form.control}
                  name="buyerCpf"
                  render={({ field }) => (
                    <FormItem>
                      <FormLabel>CPF</FormLabel>
                      <FormControl>
                        <Input
                          className="text-xs!"
                          placeholder="000.000.000-00"
                          {...field}
                          onChange={(e) => {
                            const maskedValue = maskCPF(e.target.value)
                            field.onChange(maskedValue)
                          }}
                        />
                      </FormControl>
                      <FormMessage />
                    </FormItem>
                  )}
                />
                <FormField
                  control={form.control}
                  name="amount"
                  render={({ field }) => (
                    <FormItem>
                      <FormLabel>Valor</FormLabel>
                      <FormControl>
                        <Input
                          className="text-xs!"
                          type="text"
                          placeholder="R$ 0.00"
                          {...field}
                          onChange={(e) => {
                            const maskedValue = maskCurrency(e.target.value)
                            field.onChange(maskedValue)
                          }}
                        />
                      </FormControl>
                      <FormMessage />
                    </FormItem>
                  )}
                />
              </div>
              <FormField
                control={form.control}
                name="paymentMethod"
                render={({ field }) => (
                  <FormItem>
                    <FormLabel>Meio de pagamento</FormLabel>
                    <Select
                      onValueChange={field.onChange}
                      defaultValue={field.value}
                    >
                      <FormControl>
                        <SelectTrigger className="w-full">
                          <SelectValue placeholder="Selecione..." />
                        </SelectTrigger>
                      </FormControl>
                      <SelectContent>
                        {methods.map((method) => (
                          <SelectItem
                            key={method.id}
                            value={method.description}
                          >
                            {select[method.description]}
                          </SelectItem>
                        ))}
                      </SelectContent>
                    </Select>
                    <FormMessage />
                  </FormItem>
                )}
              />
              <div className="flex justify-end space-x-2 pt-4">
                <Button
                  type="button"
                  variant="ghost"
                  onClick={handleCancelClick}
                >
                  Cancelar
                </Button>
                <Button
                  type="submit"
                  className="hover:bg-accent-foreground/95! mt-6 w-full cursor-pointer"
                  disabled={isSubmitting}
                >
                  {isSubmitting ? (
                    <>
                      <Loader2 className="mr-2 h-4 w-4 animate-spin" />
                      Criando ordem...
                    </>
                  ) : (
                    <>
                      Criar ordem
                      <PlusIcon className="mr-2 h-6 w-6" />
                    </>
                  )}
                </Button>
              </div>
            </form>
          </Form>
        </DialogContent>
      </Dialog>
      <AlertDialog open={showExitConfirm} onOpenChange={setShowExitConfirm}>
        <AlertDialogContent>
          <AlertDialogHeader>
            <AlertDialogTitle>Descartar alterações?</AlertDialogTitle>
            <AlertDialogDescription>
              Você preencheu alguns campos. Se sair agora, os dados serão
              perdidos.
            </AlertDialogDescription>
          </AlertDialogHeader>
          <AlertDialogFooter>
            <AlertDialogCancel>Continuar criando</AlertDialogCancel>
            <AlertDialogAction
              onClick={() => {
                setShowExitConfirm(false)
                setOpen(false)
                form.reset()
              }}
            >
              Descartar e sair
            </AlertDialogAction>
          </AlertDialogFooter>
        </AlertDialogContent>
      </AlertDialog>
    </>
  )
}
