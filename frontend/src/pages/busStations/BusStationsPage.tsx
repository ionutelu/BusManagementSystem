import { useState } from 'react'
import { useQuery, useMutation, useQueryClient } from '@tanstack/react-query'
import { busStationApi } from '../../api/busStations'
import type { BusStationRequest, BusStationResponse } from '../../types/api'

const emptyForm = (): BusStationRequest => ({ name: '', city: '', isDamaged: false })

export default function BusStationsPage() {
  const queryClient = useQueryClient()
  const [editing, setEditing] = useState<BusStationResponse | null>(null)
  const [creating, setCreating] = useState(false)
  const [form, setForm] = useState<BusStationRequest>(emptyForm())
  const [error, setError] = useState<string | null>(null)

  const { data: stations = [], isLoading } = useQuery({
    queryKey: ['bus-stations'],
    queryFn: () => busStationApi.list(),
  })

  const createMutation = useMutation({
    mutationFn: (data: BusStationRequest) => busStationApi.create(data),
    onSuccess: () => { queryClient.invalidateQueries({ queryKey: ['bus-stations'] }); closeModal() },
    onError: (err: any) => setError(err.response?.data?.message ?? 'Create failed'),
  })

  const updateMutation = useMutation({
    mutationFn: ({ id, data }: { id: number; data: BusStationRequest }) => busStationApi.update(id, data),
    onSuccess: () => { queryClient.invalidateQueries({ queryKey: ['bus-stations'] }); closeModal() },
    onError: (err: any) => setError(err.response?.data?.message ?? 'Update failed'),
  })

  const deleteMutation = useMutation({
    mutationFn: (id: number) => busStationApi.delete(id),
    onSuccess: () => queryClient.invalidateQueries({ queryKey: ['bus-stations'] }),
    onError: (err: any) => setError(err.response?.data?.message ?? 'Delete failed'),
  })

  const openCreate = () => { setForm(emptyForm()); setCreating(true); setError(null) }
  const openEdit = (s: BusStationResponse) => {
    setEditing(s); setForm({ name: s.name, city: s.city, isDamaged: s.isDamaged ?? false }); setError(null)
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
        <h1 className="text-2xl font-bold">Bus Stations</h1>
        <button onClick={openCreate} className="bg-blue-600 text-white px-4 py-2 rounded hover:bg-blue-700 text-sm">+ New Station</button>
      </div>
      {error && <div className="mb-4 p-3 bg-red-100 text-red-700 rounded text-sm">{error}</div>}
      {isLoading ? <p className="text-gray-500">Loading…</p> : (
        <div className="overflow-x-auto bg-white rounded shadow">
          <table className="min-w-full text-sm">
            <thead className="bg-gray-100 text-left text-gray-600 uppercase text-xs">
              <tr>
                <th className="px-4 py-3">ID</th>
                <th className="px-4 py-3">Name</th>
                <th className="px-4 py-3">City</th>
                <th className="px-4 py-3">Damaged</th>
                <th className="px-4 py-3">Actions</th>
              </tr>
            </thead>
            <tbody>
              {stations.map((s) => (
                <tr key={s.id} className="border-t hover:bg-gray-50">
                  <td className="px-4 py-3 text-gray-500">{s.id}</td>
                  <td className="px-4 py-3 font-medium">{s.name}</td>
                  <td className="px-4 py-3">{s.city}</td>
                  <td className="px-4 py-3">
                    <span className={`px-2 py-1 rounded text-xs font-semibold ${s.isDamaged ? 'bg-red-100 text-red-700' : 'bg-green-100 text-green-700'}`}>
                      {s.isDamaged ? 'Yes' : 'No'}
                    </span>
                  </td>
                  <td className="px-4 py-3 flex gap-2">
                    <button onClick={() => openEdit(s)} className="text-blue-600 hover:underline text-xs">Edit</button>
                    <button onClick={() => deleteMutation.mutate(s.id)} className="text-red-600 hover:underline text-xs">Delete</button>
                  </td>
                </tr>
              ))}
              {stations.length === 0 && <tr><td colSpan={5} className="px-4 py-6 text-center text-gray-400">No stations found.</td></tr>}
            </tbody>
          </table>
        </div>
      )}

      {(creating || editing) && (
        <div className="fixed inset-0 bg-black/40 flex items-center justify-center z-50">
          <div className="bg-white rounded-lg shadow-xl w-full max-w-md p-6">
            <h2 className="text-lg font-bold mb-4">{editing ? 'Edit Station' : 'New Station'}</h2>
            {error && <div className="mb-4 p-3 bg-red-100 text-red-700 rounded text-sm">{error}</div>}
            <form onSubmit={handleSubmit} className="space-y-4">
              <div>
                <label className="block text-sm font-medium mb-1">Name</label>
                <input value={form.name} onChange={(e) => setForm({ ...form, name: e.target.value })}
                  className="w-full border rounded px-3 py-2 text-sm" required />
              </div>
              <div>
                <label className="block text-sm font-medium mb-1">City</label>
                <input value={form.city} onChange={(e) => setForm({ ...form, city: e.target.value })}
                  className="w-full border rounded px-3 py-2 text-sm" required />
              </div>
              <div className="flex items-center gap-2">
                <input type="checkbox" id="damaged" checked={form.isDamaged ?? false}
                  onChange={(e) => setForm({ ...form, isDamaged: e.target.checked })} />
                <label htmlFor="damaged" className="text-sm font-medium">Damaged</label>
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

