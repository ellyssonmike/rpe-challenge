import { getServerSession } from "next-auth";
import { authOptions } from "@/app/api/auth/[...nextauth]/route";
import { HTTPService, RequestMethod } from "../../http.service";
import { redirect } from "next/navigation";
import path from "path";

class AuthenticatedAPI {
  private readonly http: HTTPService = new HTTPService();
  private readonly baseUrl: string = process.env.ORDERS_API_URL as string;

  constructor() {
    if (!process.env.ORDERS_API_URL) {
      throw new Error('missing environment variable ORDERS_API_URL');
    }
  }

  private async getAuthHeaders(requiresAuth: boolean): Promise<HeadersInit> {
    const headers: HeadersInit = { 'Content-Type': 'application/json' };
    
    if (requiresAuth) {
      const session = await getServerSession(authOptions);
      if (session?.accessToken) {
        headers['Authorization'] = `Bearer ${session.accessToken}`;
      }
    }

    return headers;
  }

  private async request<T>(method: RequestMethod, endpoint: string, body?: T, headers?: HeadersInit) {
    const response = await this.http.request(method, endpoint, body, headers);
    if (response.status === 401) {
      const result = await response.clone().json();
      if (result.code !== 'IN.AUTH-CRD.INVALID')
        return redirect('/auth/logout');

      return response;
    }

    return response;
  }

  public async get(endpoint: string, requiresAuth: boolean = false) {
    const authHeaders = await this.getAuthHeaders(requiresAuth);
    return this.request('GET', this.buildEndpoint(endpoint), undefined, authHeaders);
  }

  public async post<T>(endpoint: string, body: T, requiresAuth: boolean = false) {
    const authHeaders = await this.getAuthHeaders(requiresAuth);
    
    return this.request('POST', this.buildEndpoint(endpoint), body, authHeaders);
  }

  private buildEndpoint(endpoint: string) {
    return path.join(this.baseUrl, endpoint);
  }
}

export const api = new AuthenticatedAPI();