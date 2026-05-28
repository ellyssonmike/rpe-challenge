'use client'

import { Button } from '@/components/ui/button'
import { LogOut } from 'lucide-react'
import { signOut } from 'next-auth/react'

export function Logout() {
  return (
    <div className="absolute top-8 right-8">
      <Button
        variant="ghost"
        onClick={() => signOut({ callbackUrl: '/login' })}
        className="text-destructive"
      >
        <LogOut className="mr-2 h-4 w-4" /> Sair
      </Button>
    </div>
  )
}
