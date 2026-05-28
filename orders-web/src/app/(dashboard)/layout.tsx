import { Logout } from '@/components/logout'

export default function DashboardLayout({
  children,
}: {
  children: React.ReactNode
}) {
  return (
    <div>
      <Logout />
      <main className="flex-1 overflow-y-auto">
        <div>{children}</div>
      </main>
    </div>
  )
}
