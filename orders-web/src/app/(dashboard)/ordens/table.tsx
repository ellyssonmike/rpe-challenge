'use client'

import { ordersColumns } from '@/components/orders/data-table/columns'
import { DataTable } from '@/components/orders/data-table/table'
import { Order } from '@/types/order'

interface OrderTableProps {
  orders: Order[]
  onCreated: () => void
}

export function OrderTable({ orders, onCreated }: OrderTableProps) {
  return (
    <DataTable
      data={orders}
      columns={ordersColumns(false)}
      onCreated={onCreated}
    />
  )
}
