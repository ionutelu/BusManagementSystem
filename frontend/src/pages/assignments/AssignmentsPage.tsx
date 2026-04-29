import { useState } from 'react'
import { useQuery, useMutation, useQueryClient } from '@tanstack/react-query'
import { assignmentApi } from '../../api/assignments'
import { busTripApi } from '../../api/busTrips'
import { driverApi } from '../../api/drivers'
import { tripManagerApi } from '../../api/tripManagers'
import type { DutyAssignmentRequest, DutyAssignmentResponse, DriverRole } from '../../types/api'

const ROLE_OPTIONS: DriverRole[] = ['PRIMARY_DRIVER', 'RESERVE_DRIVER']
const ROLE_LABELS: Record<DriverRole, string> = {
  PRIMARY_DRIVER: 'Primary Driver',
  RESERVE_DRIVER: 'Reserve Driver',
}

const emptyForm = (): DutyAssignmentRequest => ({ busTripId: 0, staffId: 0, role: 'PRIMARY_DRIVER' })

export default function AssignmentsPage() {
  const queryClient = useQueryClient()
  const [editing, setEditing] = useState<DutyAssignmentResponse | null>(null)
  const [creating, setCreating] = useState(false)
  const [form, setForm] = useState<DutyAssignmentRequest>(emptyForm())
  const [error, setError] = useState<string | null>(null)

  const { data: assignments = [], isLoading } = useQuery({ queryKey: ['assignments'], queryFn: () => assignmentApi.list() })
  const { data: trips = [] } = useQuery({ queryKey: ['bus-trips'], queryFn: () => busTripApi.list() })
  const { data: drivers = [] } = useQuery({ queryKey: ['drivers'], queryFn: () => driverApi.list() })
  const { data: managers = [] } = useQuery({ queryKey: ['trip-managers'], queryFn: () => tripManagerApi.list() })

  // Combined staff list: drivers + trip managers
  const allStaff = [
    ...drivers.map((d) => ({ id: d.id, label: `${d.name} (Driver)` })),
    ...managers.map((m) => ({ id: m.id, label: `${m.name} (Manager)` })),
  ]

  const createMutation = useMutation({
    mutationFn: (data: DutyAssignmentRequest) => assignmentApi.create(data),
    onSuccess: () => { queryClient.invalidateQueries({ queryKey: ['assignments'] }); closeModal() },
    onError: (err: any) => setError(err.response?.data?.message ?? 'Create failed'),
  })

  const updateMutation = useMutation({
    mutationFn: ({ id, data }: { id: number; data: DutyAssignmentRequest }) => assignmentApi.update(id, data),
    onSuccess: () => { queryClient.invalidateQueries({ queryKey: ['assignments'] }); closeModal() },
    onError: (err: any) => setError(err.response?.data?.message ?? 'Update failed'),
  })

  const deleteMutation = useMutation({
    mutationFn: (id: number) => assignmentApi.delete(id),
    onSuccess: () => queryClient.invalidateQueries({ queryKey: ['assignments'] }),
    onError: (err: any) => setError(err.response?.data?.message ?? 'Delete failed'),
  })

  const openCreate = () => { setForm(emptyForm()); setCreating(true); setError(null) }
  const openEdit = (a: DutyAssignmentResponse) => {
    setEditing(a)
    setForm({ busTripId: a.busTripId, staffId: a.staffId, role: a.role })
    setError(null)
  }
  const closeModal = () => { setCreating(false); setEditing(null); setError(null) }

  const handleSubmit = (e: React.FormEvent) => {
    e.preventDefault()
    if (editing) updateMutation.mutate({ id: editing.id, data: form })
    else createMutation.mutate(form)
  }

  const roleColor: Record<DriverRole, string> = {
    PRIMARY_DRIVER: 'bg-blue-100 text-blue-700',
    RESERVE_DRIVER: 'bg-gray-100 text-gray-700',
  }

  return (
    <div>
      <div className="flex justify-between items-center mb-6">
        <h1 className="text-2xl font-bold">Duty Assignments</h1>
        <button onClick={openCreate} className="bg-blue-600 text-white px-4 py-2 rounded hover:bg-blue-700 text-sm">+ New Assignment</button>
      </div>
      {error && <div className="mb-4 p-3 bg-red-100 text-red-700 rounded text-sm">{error}</div>}
      {isLoading ? <p className="text-gray-500">Loading…</p> : (
        <div className="overflow-x-auto bg-white rounded shadow">
          <table className="min-w-full text-sm">
            <thead className="bg-gray-100 text-left text-gray-600 uppercase text-xs">
              <tr>
                <th className="px-4 py-3">ID</th>
                <th className="px-4 py-3">Trip</th>
                <th className="px-4 py-3">Staff</th>
                <th className="px-4 py-3">Role</th>
                <th className="px-4 py-3">Actions</th>
              </tr>
            </thead>
            <tbody>
              {assignments.map((a) => (
                <tr key={a.id} className="border-t hover:bg-gray-50">
                  <td className="px-4 py-3 text-gray-500">{a.id}</td>
                  <td className="px-4 py-3 text-xs">{a.busTripSummary}</td>
                  <td className="px-4 py-3">{a.staffName}</td>
                  <td className="px-4 py-3">
                    <span className={`px-2 py-1 rounded text-xs font-semibold ${roleColor[a.role]}`}>
                      {a.roleDescription}
                    </span>
                  </td>
                  <td className="px-4 py-3 flex gap-2">
                    <button onClick={() => openEdit(a)} className="text-blue-600 hover:underline text-xs">Edit</button>
                    <button onClick={() => deleteMutation.mutate(a.id)} className="text-red-600 hover:underline text-xs">Delete</button>
                  </td>
                </tr>
              ))}
              {assignments.length === 0 && <tr><td colSpan={5} className="px-4 py-6 text-center text-gray-400">No assignments found.</td></tr>}
            </tbody>
          </table>
        </div>
      )}

      {(creating || editing) && (
        <div className="fixed inset-0 bg-black/40 flex items-center justify-center z-50">
          <div className="bg-white rounded-lg shadow-xl w-full max-w-md p-6">
            <h2 className="text-lg font-bold mb-4">{editing ? 'Edit Assignment' : 'New Assignment'}</h2>
            {error && <div className="mb-4 p-3 bg-red-100 text-red-700 rounded text-sm">{error}</div>}
            <form onSubmit={handleSubmit} className="space-y-4">
              <div>
                <label className="block text-sm font-medium mb-1">Bus Trip</label>
                <select value={form.busTripId} onChange={(e) => setForm({ ...form, busTripId: Number(e.target.value) })}
                  className="w-full border rounded px-3 py-2 text-sm" required>
                  <option value={0} disabled>Select trip…</option>
                  {trips.map((t) => (
                    <option key={t.id} value={t.id}>
                      #{t.id} — {t.routeSummary} @ {t.startTime?.replace('T', ' ')}
                    </option>
                  ))}
                </select>
              </div>
              <div>
                <label className="block text-sm font-medium mb-1">Staff Member</label>
                <select value={form.staffId} onChange={(e) => setForm({ ...form, staffId: Number(e.target.value) })}
                  className="w-full border rounded px-3 py-2 text-sm" required>
                  <option value={0} disabled>Select staff…</option>
                  {allStaff.map((s) => <option key={s.id} value={s.id}>{s.label}</option>)}
                </select>
              </div>
              <div>
                <label className="block text-sm font-medium mb-1">Role</label>
                <select value={form.role} onChange={(e) => setForm({ ...form, role: e.target.value as DriverRole })}
                  className="w-full border rounded px-3 py-2 text-sm">
                  {ROLE_OPTIONS.map((r) => <option key={r} value={r}>{ROLE_LABELS[r]}</option>)}
                </select>
              </div>
              <div className="flex justify-end gap-2 pt-2">
                <button type="button" onClick={closeModal} className="px-4 py-2 text-sm rounded border hover:bg-gray-50">Cancel</button>
                <button type="submit" className="px-4 py-2 text-sm rounded bg-blue-600 text-white hover:bg-blue-700">
                  {editing ? 'Save' : 'Create'}
                </button>
              </div>
            </form>
          </div>
        </div>
      )}
    </div>
  )
}

