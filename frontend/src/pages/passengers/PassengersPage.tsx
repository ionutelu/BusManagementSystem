import { useState } from 'react'
import { useQuery, useMutation, useQueryClient } from '@tanstack/react-query'
import { passengerApi } from '../../api/passengers'
import type { PassengerRequest, PassengerResponse } from '../../types/api'

const CURRENCIES = ['USD', 'EUR', 'RON', 'GBP', 'CHF']
const emptyForm = (): PassengerRequest => ({ name: '', currency: 'EUR' })

export default function PassengersPage() {
  const queryClient = useQueryClient()
  const [editing, setEditing] = useState<PassengerResponse | null>(null)
  const [creating, setCreating] = useState(false)
  const [form, setForm] = useState<PassengerRequest>(emptyForm())
  const [error, setError] = useState<string | null>(null)
  const [viewingTickets, setViewingTickets] = useState<PassengerResponse | null>(null)

  const { data: passengers = [], isLoading } = useQuery({ queryKey: ['passengers'], queryFn: () => passengerApi.list() })
  const { data: tickets = [] } = useQuery({
    queryKey: ['passenger-tickets', viewingTickets?.id],
    queryFn: () => passengerApi.getTickets(viewingTickets!.id),
    enabled: !!viewingTickets,
  })

  const createMutation = useMutation({
    mutationFn: (data: PassengerRequest) => passengerApi.create(data),
    onSuccess: () => { queryClient.invalidateQueries({ queryKey: ['passengers'] }); closeModal() },
    onError: (err: any) => setError(err.response?.data?.message ?? 'Create failed'),
  })

  const updateMutation = useMutation({
    mutationFn: ({ id, data }: { id: number; data: PassengerRequest }) => passengerApi.update(id, data),
    onSuccess: () => { queryClient.invalidateQueries({ queryKey: ['passengers'] }); closeModal() },
    onError: (err: any) => setError(err.response?.data?.message ?? 'Update failed'),
  })

  const deleteMutation = useMutation({
    mutationFn: (id: number) => passengerApi.delete(id),
    onSuccess: () => queryClient.invalidateQueries({ queryKey: ['passengers'] }),
    onError: (err: any) => setError(err.response?.data?.message ?? 'Delete failed'),
  })

  const openCreate = () => { setForm(emptyForm()); setCreating(true); setError(null) }
  const openEdit = (p: PassengerResponse) => {
    setEditing(p); setForm({ name: p.name, currency: p.currency }); setError(null)
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
        <h1 className="text-2xl font-bold">Passengers</h1>
        <button onClick={openCreate} className="bg-blue-600 text-white px-4 py-2 rounded hover:bg-blue-700 text-sm">+ New Passenger</button>
      </div>
      {error && <div className="mb-4 p-3 bg-red-100 text-red-700 rounded text-sm">{error}</div>}
      {isLoading ? <p className="text-gray-500">Loading…</p> : (
        <div className="overflow-x-auto bg-white rounded shadow">
          <table className="min-w-full text-sm">
            <thead className="bg-gray-100 text-left text-gray-600 uppercase text-xs">
              <tr>
                <th className="px-4 py-3">ID</th>
                <th className="px-4 py-3">Name</th>
                <th className="px-4 py-3">Currency</th>
                <th className="px-4 py-3">Actions</th>
              </tr>
            </thead>
            <tbody>
              {passengers.map((p) => (
                <tr key={p.id} className="border-t hover:bg-gray-50">
                  <td className="px-4 py-3 text-gray-500">{p.id}</td>
                  <td className="px-4 py-3 font-medium">{p.name}</td>
                  <td className="px-4 py-3">
                    <span className="px-2 py-1 bg-gray-100 rounded text-xs font-mono">{p.currency}</span>
                  </td>
                  <td className="px-4 py-3 flex gap-2">
                    <button onClick={() => setViewingTickets(p)} className="text-purple-600 hover:underline text-xs">Tickets</button>
                    <button onClick={() => openEdit(p)} className="text-blue-600 hover:underline text-xs">Edit</button>
                    <button onClick={() => deleteMutation.mutate(p.id)} className="text-red-600 hover:underline text-xs">Delete</button>
                  </td>
                </tr>
              ))}
              {passengers.length === 0 && <tr><td colSpan={4} className="px-4 py-6 text-center text-gray-400">No passengers found.</td></tr>}
            </tbody>
          </table>
        </div>
      )}

      {/* Create / Edit modal */}
      {(creating || editing) && (
        <div className="fixed inset-0 bg-black/40 flex items-center justify-center z-50">
          <div className="bg-white rounded-lg shadow-xl w-full max-w-md p-6">
            <h2 className="text-lg font-bold mb-4">{editing ? 'Edit Passenger' : 'New Passenger'}</h2>
            {error && <div className="mb-4 p-3 bg-red-100 text-red-700 rounded text-sm">{error}</div>}
            <form onSubmit={handleSubmit} className="space-y-4">
              <div>
                <label className="block text-sm font-medium mb-1">Name</label>
                <input value={form.name} onChange={(e) => setForm({ ...form, name: e.target.value })}
                  className="w-full border rounded px-3 py-2 text-sm" required />
              </div>
              <div>
                <label className="block text-sm font-medium mb-1">Preferred Currency</label>
                <select value={form.currency} onChange={(e) => setForm({ ...form, currency: e.target.value })}
                  className="w-full border rounded px-3 py-2 text-sm">
                  {CURRENCIES.map((c) => <option key={c} value={c}>{c}</option>)}
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

      {/* Tickets viewer */}
      {viewingTickets && (
        <div className="fixed inset-0 bg-black/40 flex items-center justify-center z-50">
          <div className="bg-white rounded-lg shadow-xl w-full max-w-lg p-6">
            <h2 className="text-lg font-bold mb-4">Tickets for {viewingTickets.name}</h2>
            {tickets.length === 0 ? (
              <p className="text-gray-400 text-sm">No tickets found.</p>
            ) : (
              <table className="min-w-full text-sm mb-4">
                <thead className="bg-gray-100 text-left text-xs text-gray-600 uppercase">
                  <tr>
                    <th className="px-3 py-2">Trip</th>
                    <th className="px-3 py-2">Seat</th>
                    <th className="px-3 py-2">Price</th>
                  </tr>
                </thead>
                <tbody>
                  {tickets.map((t) => (
                    <tr key={t.id} className="border-t">
                      <td className="px-3 py-2">{t.busTripSummary}</td>
                      <td className="px-3 py-2 font-mono">{t.seatNumber}</td>
                      <td className="px-3 py-2">{t.price}</td>
                    </tr>
                  ))}
                </tbody>
              </table>
            )}
            <div className="flex justify-end">
              <button onClick={() => setViewingTickets(null)} className="px-4 py-2 text-sm rounded border hover:bg-gray-50">Close</button>
            </div>
          </div>
        </div>
      )}
    </div>
  )
}

