export type RequestMethod = 'GET' | 'POST';

export class HTTPService {
  public async request<T>(method: RequestMethod, endpoint: string, body?: T, headers?: HeadersInit) {
    const res = await fetch(endpoint, {
      method,
      headers: { 'Content-Type': 'application/json', ...headers },
      ...(body && {
        body: JSON.stringify(body),
      }),
    })

  return res;
  }

  public get(endpoint: string, headers?: HeadersInit) {
    return this.request('GET', endpoint, null, headers);
  }

  public post<T>(endpoint: string, body: T, headers?: HeadersInit) {
    return this.request("POST", endpoint, body, headers);
  }
}