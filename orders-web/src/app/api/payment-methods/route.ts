import { getPaymentMethods } from "@/services/api/server/payment-methods.service"
import { NextResponse } from "next/server"

export async function GET(request: Request) {
  try {
    const response = await getPaymentMethods()
    const data = await response.json()
    if (!response.ok) {
      return NextResponse.json(data, { status: response.status })
    }

    return NextResponse.json(data)
  } catch (error) {
    return NextResponse.json({ message: "Erro interno no servidor" }, { status: 500 })
  }
}