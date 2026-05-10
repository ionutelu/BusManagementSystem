import { useState } from 'react'
import { useQuery, useMutation, useQueryClient } from '@tanstack/react-query'
import { driverApi } from '../../api/drivers'
import type { DriverRequest, DriverResponse } from '../../types/api'

const PAGE_SIZE = 20
const emptyForm = (): DriverRequest => ({ name: '', email: '', experienceYears: 1 })

export default function DriversPage() {
  const queryClient = useQueryClient()
  const [editing, setEditing] = useState<DriverResponse | null>(null)
  const [creating, setCreating] = useState(false)
  const [form, setForm] = useState<DriverRequest>(emptyForm())
  const [error, setError] = useState<string | null>(null)
  const [page, setPage] = useState(0)

  const { data, isLoading } = useQuery({
    queryKey: ['drivers', page],
    queryFn: () => driverApi.list({ page, size: PAGE_SIZE }),
  })
  const drivers = data?.content ?? []

  const createMutation = useMutation({
    mutationFn: (data: DriverRequest) => driverApi.create(data),
    onSuccess: () => { queryClient.invalidateQueries({ queryKey: ['drivers'] }); closeModal() },
    onError: (err: any) => setError(err.response?.data?.message ?? 'Create failed'),
  })

  const updateMutation = useMutation({
    mutationFn: ({ id, data }: { id: number; data: DriverRequest }) => driverApi.update(id, data),
    onSuccess: () => { queryClient.invalidateQueries({ queryKey: ['drivers'] }); closeModal() },
    onError: (err: any) => setError(err.response?.data?.message ?? 'Update failed'),
  })

  const deleteMutation = useMutation({
    mutationFn: (id: number) => driverApi.delete(id),
    onSuccess: () => queryClient.invalidateQueries({ queryKey: ['drivers'] }),
    onError: (err: any) => setError(err.response?.data?.message ?? 'Delete failed'),
  })

  const openCreate = () => { setForm(emptyForm()); setCreating(true); setError(null) }
  const openEdit = (d: DriverResponse) => {
    setEditing(d); setForm({ name: d.name, email: d.email, experienceYears: d.experienceYears }); setError(null)
  }
  const closeModal = () => { setCreating(false); setEditing(null); setError(null) }

  const handleSubmit = (e: React.FormEvent) => {
    e.preventDefault()
    if (editing) updateMutation.mutate({ id: editing.id, data: form })
    else createMutation.mutate(form)
  }

  return (
    <div>
      <div className="flex justify-between items-center mb-6">
        <h1 className="text-2xl font-bold">Drivers</h1>
        <button onClick={openCreate} className="bg-blue-600 text-white px-4 py-2 rounded hover:bg-blue-700 text-sm">+ New Driver</button>
      </div>
      {error && <div className="mb-4 p-3 bg-red-100 text-red-700 rounded text-sm">{error}</div>}
      {isLoading ? <p className="text-gray-500">Loading…</p> : (
        <>
          <div className="overflow-x-auto bg-white rounded shadow">
            <table className="min-w-full text-sm">
              <thead className="bg-gray-100 text-left text-gray-600 uppercase text-xs">
                <tr>
                  <th className="px-4 py-3">ID</th>
                  <th className="px-4 py-3">Name</th>
                  <th className="px-4 py-3">Email</th>
                  <th className="px-4 py-3">Experience (yrs)</th>
                  <th className="px-4 py-3">Actions</th>
                </tr>
              </thead>
              <tbody>
                {drivers.map((d) => (
                  <tr key={d.id} className="border-t hover:bg-gray-50">
                    <td className="px-4 py-3 text-gray-500">{d.id}</td>
                    <td className="px-4 py-3 font-medium">{d.name}</td>
                    <td className="px-4 py-3">{d.email}</td>
                    <td className="px-4 py-3">{d.experienceYears}</td>
                    <td className="px-4 py-3 flex gap-2">
                      <button onClick={() => openEdit(d)} className="text-blue-600 hover:underline text-xs">Edit</button>
                      <button onClick={() => deleteMutation.mutate(d.id)} className="text-red-600 hover:underline text-xs">Delete</button>
                    </td>
                  </tr>
                ))}
                {drivers.length === 0 && <tr><td colSpan={5} className="px-4 py-6 text-center text-gray-400">No drivers found.</td></tr>}
              </tbody>
            </table>
          </div>
          {data && data.totalPages > 1 && (
            <div className="flex items-center justify-between mt-4 text-sm text-gray-600">
              <span>{data.totalElements} total · Page {data.number + 1} of {data.totalPages}</span>
              <div className="flex gap-2">
                <button onClick={() => setPage((p) => p - 1)} disabled={data.first}
                  className="px-3 py-1 rounded border disabled:opacity-40 hover:bg-gray-50">← Prev</button>
                <button onClick={() => setPage((p) => p + 1)} disabled={data.last}
                  className="px-3 py-1 rounded border disabled:opacity-40 hover:bg-gray-50">Next →</button>
              </div>
            </div>
          )}
        </>
      )}

      {(creating || editing) && (
        <div className="fixed inset-0 bg-black/40 flex items-center justify-center z-50">
          <div className="bg-white rounded-lg shadow-xl w-full max-w-md p-6">
            <h2 className="text-lg font-bold mb-4">{editing ? 'Edit Driver' : 'New Driver'}</h2>
            {error && <div className="mb-4 p-3 bg-red-100 text-red-700 rounded text-sm">{error}</div>}
            <form onSubmit={handleSubmit} className="space-y-4">
              <div>
                <label className="block text-sm font-medium mb-1">Name</label>
                <input value={form.name} onChange={(e) => setForm({ ...form, name: e.target.value })}
                  className="w-full border rounded px-3 py-2 text-sm" required />
              </div>
              <div>
                <label className="block text-sm font-medium mb-1">Email</label>
                <input type="email" value={form.email} onChange={(e) => setForm({ ...form, email: e.target.value })}
                  className="w-full border rounded px-3 py-2 text-sm" required />
              </div>
              <div>
                <label className="block text-sm font-medium mb-1">Years of Experience</label>
                <input type="number" min={1} value={form.experienceYears}
                  onChange={(e) => setForm({ ...form, experienceYears: Number(e.target.value) })}
                  className="w-full border rounded px-3 py-2 text-sm" required />
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

