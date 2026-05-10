import apiClient from './client'
import type { DutyAssignmentResponse, DutyAssignmentRequest, Page } from '../types/api'

export const assignmentApi = {
  list: (params?: Record<string, string | number>) =>
    apiClient.get<Page<DutyAssignmentResponse>>('/assignments', { params }).then((r) => r.data),

  getById: (id: number) =>
    apiClient.get<DutyAssignmentResponse>(`/assignments/${id}`).then((r) => r.data),

  create: (data: DutyAssignmentRequest) =>
    apiClient.post<DutyAssignmentResponse>('/assignments', data).then((r) => r.data),

  update: (id: number, data: DutyAssignmentRequest) =>
    apiClient.put<DutyAssignmentResponse>(`/assignments/${id}`, data).then((r) => r.data),

  delete: (id: number) =>
    apiClient.delete(`/assignments/${id}`),
}
