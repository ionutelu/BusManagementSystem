import apiClient from './client'
import type { DriverResponse, DriverRequest, Page } from '../types/api'

export const driverApi = {
  list: (params?: Record<string, string | number>) =>
    apiClient.get<Page<DriverResponse>>('/drivers', { params }).then((r) => r.data),

  getById: (id: number) =>
    apiClient.get<DriverResponse>(`/drivers/${id}`).then((r) => r.data),

  create: (data: DriverRequest) =>
    apiClient.post<DriverResponse>('/drivers', data).then((r) => r.data),

  update: (id: number, data: DriverRequest) =>
    apiClient.put<DriverResponse>(`/drivers/${id}`, data).then((r) => r.data),

  delete: (id: number) =>
    apiClient.delete(`/drivers/${id}`),
}
