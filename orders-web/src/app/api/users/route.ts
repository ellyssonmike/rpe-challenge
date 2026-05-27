import { createUser } from "@/services/api/server/users.service"
import { NextResponse } from "next/server"

export async function POST(request: Request) {
  const body = await request.json()

  try {
    const response = await createUser(body);
    const data = await response.json()
    if (!response.ok) {
      return NextResponse.json(data, { status: response.status })
    }

    return NextResponse.json(data)
  } catch (error) {
    return NextResponse.json({ message: "Erro interno no servidor" }, { status: 500 })
  }
}