import { useState } from 'react'
import { useQuery, useMutation, useQueryClient } from '@tanstack/react-query'
import { ticketApi } from '../../api/tickets'
import { busTripApi } from '../../api/busTrips'
import { passengerApi } from '../../api/passengers'
import type { TicketRequest, TicketResponse } from '../../types/api'

const emptyForm = (): TicketRequest => ({ busTripId: 0, passengerId: 0, seatNumber: '', price: 0 })

export default function TicketsPage() {
  const queryClient = useQueryClient()
  const [editing, setEditing] = useState<TicketResponse | null>(null)
  const [creating, setCreating] = useState(false)
  const [form, setForm] = useState<TicketRequest>(emptyForm())
  const [error, setError] = useState<string | null>(null)

  const { data: tickets = [], isLoading } = useQuery({ queryKey: ['tickets'], queryFn: () => ticketApi.list() })
  const { data: trips = [] } = useQuery({ queryKey: ['bus-trips'], queryFn: () => busTripApi.list() })
  const { data: passengers = [] } = useQuery({ queryKey: ['passengers'], queryFn: () => passengerApi.list() })

  const createMutation = useMutation({
    mutationFn: (data: TicketRequest) => ticketApi.create(data),
    onSuccess: () => { queryClient.invalidateQueries({ queryKey: ['tickets'] }); closeModal() },
    onError: (err: any) => setError(err.response?.data?.message ?? 'Create failed'),
  })

  const updateMutation = useMutation({
    mutationFn: ({ id, data }: { id: number; data: TicketRequest }) => ticketApi.update(id, data),
    onSuccess: () => { queryClient.invalidateQueries({ queryKey: ['tickets'] }); closeModal() },
    onError: (err: any) => setError(err.response?.data?.message ?? 'Update failed'),
  })

  const deleteMutation = useMutation({
    mutationFn: (id: number) => ticketApi.delete(id),
    onSuccess: () => queryClient.invalidateQueries({ queryKey: ['tickets'] }),
    onError: (err: any) => setError(err.response?.data?.message ?? 'Delete failed'),
  })

  const openCreate = () => { setForm(emptyForm()); setCreating(true); setError(null) }
  const openEdit = (t: TicketResponse) => {
    setEditing(t)
    setForm({ busTripId: t.busTripId, passengerId: t.passengerId, seatNumber: t.seatNumber, price: t.price })
    setError(null)
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
        <h1 className="text-2xl font-bold">Tickets</h1>
        <button onClick={openCreate} className="bg-blue-600 text-white px-4 py-2 rounded hover:bg-blue-700 text-sm">+ New Ticket</button>
      </div>
      {error && <div className="mb-4 p-3 bg-red-100 text-red-700 rounded text-sm">{error}</div>}
      {isLoading ? <p className="text-gray-500">Loading…</p> : (
        <div className="overflow-x-auto bg-white rounded shadow">
          <table className="min-w-full text-sm">
            <thead className="bg-gray-100 text-left text-gray-600 uppercase text-xs">
              <tr>
                <th className="px-4 py-3">ID</th>
                <th className="px-4 py-3">Trip</th>
                <th className="px-4 py-3">Passenger</th>
                <th className="px-4 py-3">Seat</th>
                <th className="px-4 py-3">Price</th>
                <th className="px-4 py-3">Actions</th>
              </tr>
            </thead>
            <tbody>
              {tickets.map((t) => (
                <tr key={t.id} className="border-t hover:bg-gray-50">
                  <td className="px-4 py-3 text-gray-500">{t.id}</td>
                  <td className="px-4 py-3 text-xs">{t.busTripSummary}</td>
                  <td className="px-4 py-3">{t.passengerName}</td>
                  <td className="px-4 py-3 font-mono">{t.seatNumber}</td>
                  <td className="px-4 py-3">{t.price}</td>
                  <td className="px-4 py-3 flex gap-2">
                    <button onClick={() => openEdit(t)} className="text-blue-600 hover:underline text-xs">Edit</button>
                    <button onClick={() => deleteMutation.mutate(t.id)} className="text-red-600 hover:underline text-xs">Delete</button>
                  </td>
                </tr>
              ))}
              {tickets.length === 0 && <tr><td colSpan={6} className="px-4 py-6 text-center text-gray-400">No tickets found.</td></tr>}
            </tbody>
          </table>
        </div>
      )}

      {(creating || editing) && (
        <div className="fixed inset-0 bg-black/40 flex items-center justify-center z-50">
          <div className="bg-white rounded-lg shadow-xl w-full max-w-md p-6">
            <h2 className="text-lg font-bold mb-4">{editing ? 'Edit Ticket' : 'New Ticket'}</h2>
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
                <label className="block text-sm font-medium mb-1">Passenger</label>
                <select value={form.passengerId} onChange={(e) => setForm({ ...form, passengerId: Number(e.target.value) })}
                  className="w-full border rounded px-3 py-2 text-sm" required>
                  <option value={0} disabled>Select passenger…</option>
                  {passengers.map((p) => <option key={p.id} value={p.id}>{p.name}</option>)}
                </select>
              </div>
              <div>
                <label className="block text-sm font-medium mb-1">Seat Number</label>
                <input value={form.seatNumber} onChange={(e) => setForm({ ...form, seatNumber: e.target.value })}
                  className="w-full border rounded px-3 py-2 text-sm" placeholder="e.g. 12A" required />
              </div>
              <div>
                <label className="block text-sm font-medium mb-1">Price</label>
                <input type="number" min={0.01} step="0.01" value={form.price}
                  onChange={(e) => setForm({ ...form, price: Number(e.target.value) })}
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

