import { useState } from 'react'
import { useQuery, useMutation, useQueryClient } from '@tanstack/react-query'
import { busApi } from '../../api/buses'
import type { BusRequest, BusResponse, BusStatus } from '../../types/api'

const PAGE_SIZE = 20
const STATUS_OPTIONS: BusStatus[] = ['ACTIVE', 'DOWN']

const emptyForm = (): BusRequest => ({
  vin: '',
  registrationNumber: '',
  capacity: 30,
  status: 'ACTIVE',
})

export default function BusesPage() {
  const queryClient = useQueryClient()
  const [editing, setEditing] = useState<BusResponse | null>(null)
  const [creating, setCreating] = useState(false)
  const [form, setForm] = useState<BusRequest>(emptyForm())
  const [error, setError] = useState<string | null>(null)
  const [page, setPage] = useState(0)

  const { data, isLoading } = useQuery({
    queryKey: ['buses', page],
    queryFn: () => busApi.list({ page, size: PAGE_SIZE }),
  })
  const buses = data?.content ?? []

  const createMutation = useMutation({
    mutationFn: (data: BusRequest) => busApi.create(data),
    onSuccess: () => {
      queryClient.invalidateQueries({ queryKey: ['buses'] })
      setCreating(false)
      setForm(emptyForm())
      setError(null)
    },
    onError: (err: any) => setError(err.response?.data?.message ?? 'Create failed'),
  })

  const updateMutation = useMutation({
    mutationFn: ({ id, data }: { id: number; data: BusRequest }) => busApi.update(id, data),
    onSuccess: () => {
      queryClient.invalidateQueries({ queryKey: ['buses'] })
      setEditing(null)
      setForm(emptyForm())
      setError(null)
    },
    onError: (err: any) => setError(err.response?.data?.message ?? 'Update failed'),
  })

  const deleteMutation = useMutation({
    mutationFn: (id: number) => busApi.delete(id),
    onSuccess: () => queryClient.invalidateQueries({ queryKey: ['buses'] }),
    onError: (err: any) => setError(err.response?.data?.message ?? 'Delete failed'),
  })

  const openCreate = () => { setForm(emptyForm()); setCreating(true); setError(null) }
  const openEdit = (bus: BusResponse) => {
    setEditing(bus)
    setForm({ vin: bus.vin, registrationNumber: bus.registrationNumber, capacity: bus.capacity, status: bus.status })
    setError(null)
  }
  const closeModal = () => { setCreating(false); setEditing(null); setError(null) }

  const handleSubmit = (e: React.FormEvent) => {
    e.preventDefault()
    if (editing) updateMutation.mutate({ id: editing.id, data: form })
    else createMutation.mutate(form)
  }

  const showModal = creating || editing !== null

  return (
    <div>
      <div className="flex justify-between items-center mb-6">
        <h1 className="text-2xl font-bold">Buses</h1>
        <button onClick={openCreate}
          className="bg-blue-600 text-white px-4 py-2 rounded hover:bg-blue-700 text-sm">
          + New Bus
        </button>
      </div>

      {error && <div className="mb-4 p-3 bg-red-100 text-red-700 rounded">{error}</div>}

      {isLoading ? (
        <p className="text-gray-500">Loading…</p>
      ) : (
        <>
          <div className="overflow-x-auto bg-white rounded shadow">
            <table className="min-w-full text-sm">
              <thead className="bg-gray-100 text-left text-gray-600 uppercase text-xs">
                <tr>
                  <th className="px-4 py-3">ID</th>
                  <th className="px-4 py-3">VIN</th>
                  <th className="px-4 py-3">Registration</th>
                  <th className="px-4 py-3">Capacity</th>
                  <th className="px-4 py-3">Status</th>
                  <th className="px-4 py-3">Actions</th>
                </tr>
              </thead>
              <tbody>
                {buses.map((bus) => (
                  <tr key={bus.id} className="border-t hover:bg-gray-50">
                    <td className="px-4 py-3 text-gray-500">{bus.id}</td>
                    <td className="px-4 py-3 font-mono">{bus.vin}</td>
                    <td className="px-4 py-3">{bus.registrationNumber}</td>
                    <td className="px-4 py-3">{bus.capacity}</td>
                    <td className="px-4 py-3">
                      <span className={`px-2 py-1 rounded text-xs font-semibold ${
                        bus.status === 'ACTIVE'
                          ? 'bg-green-100 text-green-700'
                          : 'bg-red-100 text-red-700'
                      }`}>{bus.status}</span>
                    </td>
                    <td className="px-4 py-3 flex gap-2">
                      <button onClick={() => openEdit(bus)}
                        className="text-blue-600 hover:underline text-xs">Edit</button>
                      <button onClick={() => deleteMutation.mutate(bus.id)}
                        className="text-red-600 hover:underline text-xs">Delete</button>
                    </td>
                  </tr>
                ))}
                {buses.length === 0 && (
                  <tr><td colSpan={6} className="px-4 py-6 text-center text-gray-400">No buses found.</td></tr>
                )}
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

      {/* Modal */}
      {showModal && (
        <div className="fixed inset-0 bg-black/40 flex items-center justify-center z-50">
          <div className="bg-white rounded-lg shadow-xl w-full max-w-md p-6">
            <h2 className="text-lg font-bold mb-4">{editing ? 'Edit Bus' : 'New Bus'}</h2>
            {error && <div className="mb-4 p-3 bg-red-100 text-red-700 rounded text-sm">{error}</div>}
            <form onSubmit={handleSubmit} className="space-y-4">
              <div>
                <label className="block text-sm font-medium mb-1">VIN</label>
                <input value={form.vin} onChange={(e) => setForm({ ...form, vin: e.target.value })}
                  className="w-full border rounded px-3 py-2 text-sm" required />
              </div>
              <div>
                <label className="block text-sm font-medium mb-1">Registration Number</label>
                <input value={form.registrationNumber} onChange={(e) => setForm({ ...form, registrationNumber: e.target.value })}
                  className="w-full border rounded px-3 py-2 text-sm" required />
              </div>
              <div>
                <label className="block text-sm font-medium mb-1">Capacity (20–80)</label>
                <input type="number" min={20} max={80} value={form.capacity}
                  onChange={(e) => setForm({ ...form, capacity: Number(e.target.value) })}
                  className="w-full border rounded px-3 py-2 text-sm" required />
              </div>
              <div>
                <label className="block text-sm font-medium mb-1">Status</label>
                <select value={form.status} onChange={(e) => setForm({ ...form, status: e.target.value as BusStatus })}
                  className="w-full border rounded px-3 py-2 text-sm">
                  {STATUS_OPTIONS.map((s) => <option key={s} value={s}>{s}</option>)}
                </select>
              </div>
              <div className="flex justify-end gap-2 pt-2">
                <button type="button" onClick={closeModal}
                  className="px-4 py-2 text-sm rounded border hover:bg-gray-50">Cancel</button>
                <button type="submit"
                  className="px-4 py-2 text-sm rounded bg-blue-600 text-white hover:bg-blue-700">
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

