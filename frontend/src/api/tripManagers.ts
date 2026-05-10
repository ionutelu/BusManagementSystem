import apiClient from './client'
import type { TripManagerResponse, TripManagerRequest, Page } from '../types/api'

export const tripManagerApi = {
  list: (params?: Record<string, string | number>) =>
    apiClient.get<Page<TripManagerResponse>>('/trip-managers', { params }).then((r) => r.data),

  getById: (id: number) =>
    apiClient.get<TripManagerResponse>(`/trip-managers/${id}`).then((r) => r.data),

  create: (data: TripManagerRequest) =>
    apiClient.post<TripManagerResponse>('/trip-managers', data).then((r) => r.data),

  update: (id: number, data: TripManagerRequest) =>
    apiClient.put<TripManagerResponse>(`/trip-managers/${id}`, data).then((r) => r.data),

  delete: (id: number) =>
    apiClient.delete(`/trip-managers/${id}`),
}
