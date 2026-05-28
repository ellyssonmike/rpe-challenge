'use client'

import { ColumnDef } from '@tanstack/react-table'
import { Checkbox } from '@/components/ui/checkbox'

import { DataTableColumnHeader } from './data-table-column-header'
import { Badge } from '../../ui/badge'
import { Skeleton } from '../../ui/skeleton'
import { formatCpf, formatCurrencyBR, formatDate } from '@/shared/utils'
import { Tooltip, TooltipContent, TooltipTrigger } from '../../ui/tooltip'
import { Order } from '@/types/order'

const config = {
  status: {
    PENDING: { label: 'Pendente', class: 'bg-muted text-muted-foreground' },
    PAID: { label: 'Paga', class: 'bg-emerald-100/70 text-emerald-700' },
    REFUSED: { label: 'Recusada', class: 'bg-amber-100/70 text-amber-700' },
    CANCELED: { label: 'Cancelada', class: 'bg-rose-100/70 text-rose-700' },
  },
  paymentMethod: {
    PIX: { label: 'PIX', class: 'bg-sky-100 text-sky-900' },
    CREDIT_CARD: { label: 'Crédito', class: 'bg-violet-100 text-violet-900' },
    DEBIT_CARD: { label: 'Débito', class: 'bg-indigo-100 text-indigo-900' },
  },
}

export const ordersColumns = (isLoading?: boolean): ColumnDef<Order>[] => [
  {
    id: 'select',
    header: ({ table }) => (
      <Checkbox
        checked={
          table.getIsAllPageRowsSelected() ||
          (table.getIsSomePageRowsSelected() && 'indeterminate')
        }
        onCheckedChange={(value) => table.toggleAllPageRowsSelected(!!value)}
        aria-label="Selecionar tudo"
        className="translate-y-0.5"
      />
    ),
    cell: ({ row }) => (
      <Checkbox
        checked={row.getIsSelected()}
        onCheckedChange={(value) => row.toggleSelected(!!value)}
        aria-label="Selecionar linha"
        className="translate-y-0.5"
      />
    ),
    enableSorting: false,
    enableHiding: false,
    enableGlobalFilter: false,
  },
  {
    accessorKey: 'id',
    header: ({ column }) => (
      <DataTableColumnHeader column={column} title={'ID'} />
    ),
    cell: ({ row }) =>
      isLoading ? (
        <Skeleton className="h-4 w-24 rounded-full" />
      ) : (
        <Tooltip>
          <TooltipTrigger className="text-muted-foreground max-w-16 truncate font-mono text-xs">
            {row.getValue('id')}
          </TooltipTrigger>
          <TooltipContent side="top" align="start">
            {row.getValue('id')}
          </TooltipContent>
        </Tooltip>
      ),
    enableHiding: true,
    enableSorting: false,
    enableGlobalFilter: false,
  },
  {
    accessorKey: 'paymentMethod',
    header: ({ column }) => (
      <DataTableColumnHeader column={column} title={'Meio'} />
    ),
    cell: ({ row }) =>
      isLoading ? (
        <Skeleton className="h-4 w-16 rounded-full" />
      ) : (
        <Badge
          variant="outline"
          className={
            config.paymentMethod[
              row.getValue('paymentMethod') as keyof typeof config.paymentMethod
            ].class
          }
        >
          {
            config.paymentMethod[
              row.getValue('paymentMethod') as keyof typeof config.paymentMethod
            ].label
          }
        </Badge>
      ),
    enableHiding: false,
    enableGlobalFilter: false,
  },
  {
    accessorKey: 'status',
    header: ({ column }) => (
      <DataTableColumnHeader column={column} title="Status" />
    ),
    cell: ({ row }) =>
      isLoading ? (
        <Skeleton className="h-4 w-20 rounded-full" />
      ) : (
        <div>
          <Badge
            variant="outline"
            className={
              config.status[
                row.getValue('status') as keyof typeof config.status
              ].class
            }
          >
            {
              config.status[
                row.getValue('status') as keyof typeof config.status
              ].label
            }
          </Badge>
        </div>
      ),
    enableGlobalFilter: false,
  },
  {
    accessorKey: 'buyerCpf',
    header: ({ column }) => (
      <DataTableColumnHeader column={column} title={'CPF'} />
    ),
    cell: ({ row }) =>
      isLoading ? (
        <Skeleton className="h-4 w-24 rounded-full" />
      ) : (
        <div className="text-xs">{formatCpf(row.getValue('buyerCpf'))}</div>
      ),
    enableHiding: false,
    enableGlobalFilter: true,
  },
  {
    accessorKey: 'buyerName',
    header: ({ column }) => (
      <DataTableColumnHeader column={column} title={'Nome do comprador'} />
    ),
    cell: ({ row }) =>
      isLoading ? (
        <Skeleton className="h-4 w-32 rounded-full" />
      ) : (
        <div className="text-xs">{row.getValue('buyerName')}</div>
      ),
    enableHiding: false,
    enableGlobalFilter: true,
  },
  {
    accessorKey: 'paymentDate',
    header: ({ column }) => (
      <DataTableColumnHeader column={column} title={'Data de pagamento'} />
    ),
    cell: ({ row }) =>
      isLoading ? (
        <Skeleton className="h-4 w-24 rounded-full" />
      ) : (
        <div className="text-xs">{formatDate(row.getValue('paymentDate'))}</div>
      ),
    enableHiding: true,
    enableGlobalFilter: false,
  },
  {
    accessorKey: 'createdAt',
    header: ({ column }) => (
      <DataTableColumnHeader column={column} title={'Data de criação'} />
    ),
    cell: ({ row }) =>
      isLoading ? (
        <Skeleton className="h-4 w-24 rounded-full" />
      ) : (
        <div className="text-xs">{formatDate(row.getValue('createdAt'))}</div>
      ),
    enableHiding: true,
    enableGlobalFilter: false,
  },

  {
    accessorKey: 'amount',
    header: ({ column }) => (
      <DataTableColumnHeader column={column} title={'Valor'} />
    ),
    cell: ({ row }) =>
      isLoading ? (
        <Skeleton className="h-4 w-16 rounded-full" />
      ) : (
        <div className="text-xs font-semibold">
          R$ {formatCurrencyBR(row.getValue('amount'), 'divide', 100)}
        </div>
      ),
    enableHiding: false,
    enableGlobalFilter: false,
  },
]
