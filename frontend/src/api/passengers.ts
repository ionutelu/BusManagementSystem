import apiClient from './client'
import type { PassengerResponse, PassengerRequest, TicketResponse, Page } from '../types/api'

export const passengerApi = {
  list: (params?: Record<string, string | number>) =>
    apiClient.get<Page<PassengerResponse>>('/passengers', { params }).then((r) => r.data),

  getById: (id: number) =>
    apiClient.get<PassengerResponse>(`/passengers/${id}`).then((r) => r.data),

  getTickets: (id: number) =>
    apiClient.get<TicketResponse[]>(`/passengers/${id}/tickets`).then((r) => r.data),

  create: (data: PassengerRequest) =>
    apiClient.post<PassengerResponse>('/passengers', data).then((r) => r.data),

  update: (id: number, data: PassengerRequest) =>
    apiClient.put<PassengerResponse>(`/passengers/${id}`, data).then((r) => r.data),

  delete: (id: number) =>
    apiClient.delete(`/passengers/${id}`),
}
