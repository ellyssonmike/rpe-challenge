export interface APIResponse<T> {
  timestamp: Date;
  requestId: string;
  data: T;
}