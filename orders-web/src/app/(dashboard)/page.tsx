import {
  Card,
  CardContent,
  CardDescription,
  CardHeader,
  CardTitle,
} from '@/components/ui/card'
import { Button } from '@/components/ui/button'
import { User, FileText, Plus, EyeIcon } from 'lucide-react'
import Link from 'next/link'
import { Logout } from '@/components/logout'

export default function HomePage() {
  return (
    <div className="bg-muted/40 flex min-h-screen items-center justify-center p-6">
      <div className="grid w-full max-w-xs min-w-2xs grid-cols-1 gap-6 sm:max-w-2xl sm:grid-cols-2">
        <Card className="flex flex-col justify-between">
          <CardHeader>
            <div className="bg-primary/5 text-primary mb-2 flex h-10 w-10 items-center justify-center rounded-lg">
              <User className="h-5 w-5" />
            </div>
            <CardTitle>Usuários</CardTitle>
            <CardDescription>Criar novos usuários.</CardDescription>
          </CardHeader>
          <CardContent>
            <Button asChild className="hover:bg-accent-foreground/95! w-full">
              <Link href="/usuarios/novo">
                Criar novo usuário
                <Plus className="ml-2 h-6 w-6 transition-transform group-hover:translate-x-1" />
              </Link>
            </Button>
          </CardContent>
        </Card>
        <Card className="flex flex-col justify-between">
          <CardHeader>
            <div className="bg-primary/5 text-primary mb-2 flex h-10 w-10 items-center justify-center rounded-lg">
              <FileText className="h-5 w-5" />
            </div>
            <CardTitle>Ordens</CardTitle>
            <CardDescription>
              Visualize e gerencie as ordens de serviço.
            </CardDescription>
          </CardHeader>
          <CardContent>
            <Button asChild variant="outline" className="w-full">
              <Link href="/ordens">
                Visualizar ordens
                <EyeIcon className="ml-2 h-4 w-4" />
              </Link>
            </Button>
          </CardContent>
        </Card>
      </div>
    </div>
  )
}
