import apiClient from './client'
import type { BusStationResponse, BusStationRequest, Page } from '../types/api'

export const busStationApi = {
  list: (params?: Record<string, string | number>) =>
    apiClient.get<Page<BusStationResponse>>('/bus-stations', { params }).then((r) => r.data),

  getById: (id: number) =>
    apiClient.get<BusStationResponse>(`/bus-stations/${id}`).then((r) => r.data),

  create: (data: BusStationRequest) =>
    apiClient.post<BusStationResponse>('/bus-stations', data).then((r) => r.data),

  update: (id: number, data: BusStationRequest) =>
    apiClient.put<BusStationResponse>(`/bus-stations/${id}`, data).then((r) => r.data),

  delete: (id: number) =>
    apiClient.delete(`/bus-stations/${id}`),
}
