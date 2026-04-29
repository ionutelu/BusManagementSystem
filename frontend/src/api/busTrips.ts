import apiClient from './client'
import type { BusTripResponse, BusTripRequest } from '../types/api'

export const busTripApi = {
  list: (params?: Record<string, string>) =>
    apiClient.get<BusTripResponse[]>('/bus-trips', { params }).then((r) => r.data),

  getById: (id: number) =>
    apiClient.get<BusTripResponse>(`/bus-trips/${id}`).then((r) => r.data),

  create: (data: BusTripRequest) =>
    apiClient.post<BusTripResponse>('/bus-trips', data).then((r) => r.data),

  update: (id: number, data: BusTripRequest) =>
    apiClient.put<BusTripResponse>(`/bus-trips/${id}`, data).then((r) => r.data),

  delete: (id: number) =>
    apiClient.delete(`/bus-trips/${id}`),

  addStation: (tripId: number, stationId: number) =>
    apiClient.post<BusTripResponse>(`/bus-trips/${tripId}/stations/${stationId}`).then((r) => r.data),
}

