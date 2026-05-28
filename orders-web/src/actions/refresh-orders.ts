'use server'

import { revalidatePath } from 'next/cache'

export async function refreshOrders() {
  revalidatePath('/orders')
}