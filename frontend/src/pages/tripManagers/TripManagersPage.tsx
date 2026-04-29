import { useState } from 'react'
import { useQuery, useMutation, useQueryClient } from '@tanstack/react-query'
import { tripManagerApi } from '../../api/tripManagers'
import type { TripManagerRequest, TripManagerResponse } from '../../types/api'

const emptyForm = (): TripManagerRequest => ({ name: '', email: '', employeeCode: '' })

export default function TripManagersPage() {
  const queryClient = useQueryClient()
  const [editing, setEditing] = useState<TripManagerResponse | null>(null)
  const [creating, setCreating] = useState(false)
  const [form, setForm] = useState<TripManagerRequest>(emptyForm())
  const [error, setError] = useState<string | null>(null)

  const { data: managers = [], isLoading } = useQuery({ queryKey: ['trip-managers'], queryFn: () => tripManagerApi.list() })

  const createMutation = useMutation({
    mutationFn: (data: TripManagerRequest) => tripManagerApi.create(data),
    onSuccess: () => { queryClient.invalidateQueries({ queryKey: ['trip-managers'] }); closeModal() },
    onError: (err: any) => setError(err.response?.data?.message ?? 'Create failed'),
  })

  const updateMutation = useMutation({
    mutationFn: ({ id, data }: { id: number; data: TripManagerRequest }) => tripManagerApi.update(id, data),
    onSuccess: () => { queryClient.invalidateQueries({ queryKey: ['trip-managers'] }); closeModal() },
    onError: (err: any) => setError(err.response?.data?.message ?? 'Update failed'),
  })

  const deleteMutation = useMutation({
    mutationFn: (id: number) => tripManagerApi.delete(id),
    onSuccess: () => queryClient.invalidateQueries({ queryKey: ['trip-managers'] }),
    onError: (err: any) => setError(err.response?.data?.message ?? 'Delete failed'),
  })

  const openCreate = () => { setForm(emptyForm()); setCreating(true); setError(null) }
  const openEdit = (m: TripManagerResponse) => {
    setEditing(m); setForm({ name: m.name, email: m.email, employeeCode: m.employeeCode }); setError(null)
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
        <h1 className="text-2xl font-bold">Trip Managers</h1>
        <button onClick={openCreate} className="bg-blue-600 text-white px-4 py-2 rounded hover:bg-blue-700 text-sm">+ New Manager</button>
      </div>
      {error && <div className="mb-4 p-3 bg-red-100 text-red-700 rounded text-sm">{error}</div>}
      {isLoading ? <p className="text-gray-500">Loading…</p> : (
        <div className="overflow-x-auto bg-white rounded shadow">
          <table className="min-w-full text-sm">
            <thead className="bg-gray-100 text-left text-gray-600 uppercase text-xs">
              <tr>
                <th className="px-4 py-3">ID</th>
                <th className="px-4 py-3">Name</th>
                <th className="px-4 py-3">Email</th>
                <th className="px-4 py-3">Employee Code</th>
                <th className="px-4 py-3">Actions</th>
              </tr>
            </thead>
            <tbody>
              {managers.map((m) => (
                <tr key={m.id} className="border-t hover:bg-gray-50">
                  <td className="px-4 py-3 text-gray-500">{m.id}</td>
                  <td className="px-4 py-3 font-medium">{m.name}</td>
                  <td className="px-4 py-3">{m.email}</td>
                  <td className="px-4 py-3 font-mono text-xs">{m.employeeCode}</td>
                  <td className="px-4 py-3 flex gap-2">
                    <button onClick={() => openEdit(m)} className="text-blue-600 hover:underline text-xs">Edit</button>
                    <button onClick={() => deleteMutation.mutate(m.id)} className="text-red-600 hover:underline text-xs">Delete</button>
                  </td>
                </tr>
              ))}
              {managers.length === 0 && <tr><td colSpan={5} className="px-4 py-6 text-center text-gray-400">No managers found.</td></tr>}
            </tbody>
          </table>
        </div>
      )}

      {(creating || editing) && (
        <div className="fixed inset-0 bg-black/40 flex items-center justify-center z-50">
          <div className="bg-white rounded-lg shadow-xl w-full max-w-md p-6">
            <h2 className="text-lg font-bold mb-4">{editing ? 'Edit Manager' : 'New Manager'}</h2>
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
                <label className="block text-sm font-medium mb-1">Employee Code</label>
                <input value={form.employeeCode} onChange={(e) => setForm({ ...form, employeeCode: e.target.value })}
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

