import apiClient from './client'
import type { TicketResponse, TicketRequest, Page } from '../types/api'

export const ticketApi = {
  list: (params?: Record<string, string | number>) =>
    apiClient.get<Page<TicketResponse>>('/tickets', { params }).then((r) => r.data),

  getById: (id: number) =>
    apiClient.get<TicketResponse>(`/tickets/${id}`).then((r) => r.data),

  create: (data: TicketRequest) =>
    apiClient.post<TicketResponse>('/tickets', data).then((r) => r.data),

  update: (id: number, data: TicketRequest) =>
    apiClient.put<TicketResponse>(`/tickets/${id}`, data).then((r) => r.data),

  delete: (id: number) =>
    apiClient.delete(`/tickets/${id}`),
}
