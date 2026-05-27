import { LoginForm } from '@/components/login/form'
import { Card, CardContent } from '@/components/ui/card'

export default function LoginPage() {
  return (
    <div className="bg-muted/40 flex min-h-screen w-full items-center justify-center p-4">
      <Card className="w-full max-w-sm">
        <CardContent>
          <LoginForm />
        </CardContent>
      </Card>
    </div>
  )
}
