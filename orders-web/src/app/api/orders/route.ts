import { NextResponse } from "next/server"
import { createOrder, getOrders } from "@/services/api/server/orders.service"
import { isRedirectError } from "next/dist/client/components/redirect-error"
import { redirect } from "next/navigation"

export async function GET(request: Request) {
  try {
    const response = await getOrders()
    const data = await response.json()
    if (!response.ok) {
      return NextResponse.json(data, { status: response.status })
    }

    return NextResponse.json(data)
  } catch (error) {
    return NextResponse.json({ message: '' }, { status: 500 })
  }
}

export async function POST(request: Request) {
  const body = await request.json();

  try {
    const response = await createOrder(body)
    const data = await response.json()
    if (!response.ok) {
      return NextResponse.json(data, { status: response.status })
    }

    return NextResponse.json(data)
  } catch (error) {
    return NextResponse.json({ message: "Erro interno no servidor" }, { status: 500 })
  }
}