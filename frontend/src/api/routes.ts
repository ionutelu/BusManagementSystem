import apiClient from './client'
import type { RouteResponse, RouteRequest, BusTripResponse, Page } from '../types/api'

export const routeApi = {
  list: (params?: Record<string, string | number>) =>
    apiClient.get<Page<RouteResponse>>('/routes', { params }).then((r) => r.data),

  getById: (id: number) =>
    apiClient.get<RouteResponse>(`/routes/${id}`).then((r) => r.data),

  getBusTrips: (id: number) =>
    apiClient.get<BusTripResponse[]>(`/routes/${id}/bus-trips`).then((r) => r.data),

  create: (data: RouteRequest) =>
    apiClient.post<RouteResponse>('/routes', data).then((r) => r.data),

  update: (id: number, data: RouteRequest) =>
    apiClient.put<RouteResponse>(`/routes/${id}`, data).then((r) => r.data),

  delete: (id: number) =>
    apiClient.delete(`/routes/${id}`),
}
