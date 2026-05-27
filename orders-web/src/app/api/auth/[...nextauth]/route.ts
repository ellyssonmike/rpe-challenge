import NextAuth, { NextAuthOptions } from 'next-auth'
import CredentialsProvider from 'next-auth/providers/credentials'
import { authLogin } from '@/services/api/server/auth.service'

export const authOptions: NextAuthOptions = {
  providers: [
    CredentialsProvider({
      name: 'Credentials',
      credentials: {
        email: { label: 'Email', type: 'email' },
        password: { label: 'Password', type: 'password' },
      },
      async authorize(credentials) {
        if (!credentials?.email || !credentials?.password) return null

        try {
          const response = await authLogin(credentials.email, credentials.password);
          const result = await response.json()
          if (!response.ok) {
            throw new Error(
              result?.message ||
                'Ocorreu uma falha desconhecida ao se autenticar',
            )
          }

          if (result && result.data) {
            const { data } = result
            if (!data.accessToken) return null

            return {
              id: credentials.email,
              email: credentials.email,
              accessToken: data.accessToken,
            }
          }
          return null
        } catch (error: any) {
          throw new Error(error.message)
        }
      },
    }),
  ],
  callbacks: {
    async jwt({ token, user }) {
      if (user) {
        token.accessToken = (user as any).accessToken
      }
      return token
    },
    async session({ session, token }) {
      session.accessToken = token.accessToken
      return session
    },
  },
  pages: {
    signIn: '/login',
  },
  secret: process.env.NEXTAUTH_SECRET,
}

const handler = NextAuth(authOptions)
export { handler as GET, handler as POST }
