import { ApplicationError } from '@/common/errors/application-error'
import { OrderTable } from './table'
import { getOrders } from '@/services/api/server/orders.service'
import { CardDescription } from '@/components/ui/card'
import { refreshOrders } from '@/actions/refresh-orders'

export default async function OrdersPage() {
  const res = await getOrders()
  if (!res.ok) {
    const error = await res.json()
    throw new ApplicationError({
      code: error.code,
      message: error.message,
    })
  }

  const result = await res.json()
  const orders = result.data

  return (
    <div className="p-8">
      <div className="text-3xl font-bold">Ordens de serviço</div>
      <CardDescription className="mb-4 ml-1">
        Gerenciamento de ordens de serviço
      </CardDescription>
      <OrderTable orders={orders} onCreated={refreshOrders} />
    </div>
  )
}
