import apiClient from './client'
import type { BusResponse, BusRequest, Page } from '../types/api'

export const busApi = {
  list: (params?: Record<string, string | number>) =>
    apiClient.get<Page<BusResponse>>('/buses', { params }).then((r) => r.data),

  getById: (id: number) =>
    apiClient.get<BusResponse>(`/buses/${id}`).then((r) => r.data),

  create: (data: BusRequest) =>
    apiClient.post<BusResponse>('/buses', data).then((r) => r.data),

  update: (id: number, data: BusRequest) =>
    apiClient.put<BusResponse>(`/buses/${id}`, data).then((r) => r.data),

  delete: (id: number) =>
    apiClient.delete(`/buses/${id}`),
}
