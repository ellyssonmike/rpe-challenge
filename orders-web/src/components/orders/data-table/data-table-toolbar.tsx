'use client'

import { Cross2Icon } from '@radix-ui/react-icons'
import { Table } from '@tanstack/react-table'

import { Button } from '@/components/ui/button'
import { Input } from '@/components/ui/input'
import { DataTableViewOptions } from './data-table-view-options'
import { CreateOrderModal } from '../modal'

interface DataTableToolbarProps<TData> {
  table: Table<TData>
  onCreateOrderSuccess: () => void
}

export function DataTableToolbar<TData>({
  table,
  onCreateOrderSuccess,
}: DataTableToolbarProps<TData>) {
  const isFiltered = table.getState().columnFilters.length > 0
  const globalFilter = table.getState().globalFilter ?? ''

  return (
    <div className="flex items-center justify-between">
      <div className="flex flex-1 items-center space-x-2">
        <Input
          placeholder="Buscar por nome ou CPF..."
          value={globalFilter}
          onChange={(event) => table.setGlobalFilter(event.target.value)}
          className="h-8 w-62.5 lg:w-87.5"
        />
        {isFiltered && (
          <Button
            variant="ghost"
            onClick={() => table.resetColumnFilters()}
            className="h-8 px-2 lg:px-3"
          >
            Remover filtros
            <Cross2Icon className="ml-2 h-4 w-4" />
          </Button>
        )}
      </div>
      <CreateOrderModal onCreateSuccess={onCreateOrderSuccess} />
      <DataTableViewOptions table={table} />
    </div>
  )
}
