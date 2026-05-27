import { HTTPService, RequestMethod } from "@/services/http.service";

class ClientHTTPService extends HTTPService {
  public async request<T>(method: RequestMethod, endpoint: string, body?: T, headers?: HeadersInit) {
    const response = await super.request(method, endpoint, body, headers);
    if (response.status === 401) {
      window.location.href = 'auth/logout';
    }

    return response;
  }
}

export const client = new ClientHTTPService()
