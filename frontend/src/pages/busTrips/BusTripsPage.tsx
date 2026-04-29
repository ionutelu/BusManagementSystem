import { useState } from 'react'
import { useQuery, useMutation, useQueryClient } from '@tanstack/react-query'
import { busTripApi } from '../../api/busTrips'
import { routeApi } from '../../api/routes'
import { busApi } from '../../api/buses'
import { busStationApi } from '../../api/busStations'
import type { BusTripRequest, BusTripResponse, BusTripStatus } from '../../types/api'

const STATUS_OPTIONS: BusTripStatus[] = ['PLANNED', 'COMPLETED', 'CANCELLED']

const emptyForm = (): BusTripRequest => ({
  routeId: 0,
  busId: 0,
  startTime: '',
  status: 'PLANNED',
})

export default function BusTripsPage() {
  const queryClient = useQueryClient()
  const [editing, setEditing] = useState<BusTripResponse | null>(null)
  const [creating, setCreating] = useState(false)
  const [form, setForm] = useState<BusTripRequest>(emptyForm())
  const [error, setError] = useState<string | null>(null)
  const [addingStation, setAddingStation] = useState<BusTripResponse | null>(null)
  const [selectedStation, setSelectedStation] = useState<number>(0)

  const { data: trips = [], isLoading } = useQuery({ queryKey: ['bus-trips'], queryFn: () => busTripApi.list() })
  const { data: routes = [] } = useQuery({ queryKey: ['routes'], queryFn: () => routeApi.list() })
  const { data: buses = [] } = useQuery({ queryKey: ['buses'], queryFn: () => busApi.list() })
  const { data: stations = [] } = useQuery({ queryKey: ['bus-stations'], queryFn: () => busStationApi.list() })

  const createMutation = useMutation({
    mutationFn: (data: BusTripRequest) => busTripApi.create(data),
    onSuccess: () => { queryClient.invalidateQueries({ queryKey: ['bus-trips'] }); closeModal() },
    onError: (err: any) => setError(err.response?.data?.message ?? 'Create failed'),
  })

  const updateMutation = useMutation({
    mutationFn: ({ id, data }: { id: number; data: BusTripRequest }) => busTripApi.update(id, data),
    onSuccess: () => { queryClient.invalidateQueries({ queryKey: ['bus-trips'] }); closeModal() },
    onError: (err: any) => setError(err.response?.data?.message ?? 'Update failed'),
  })

  const deleteMutation = useMutation({
    mutationFn: (id: number) => busTripApi.delete(id),
    onSuccess: () => queryClient.invalidateQueries({ queryKey: ['bus-trips'] }),
    onError: (err: any) => setError(err.response?.data?.message ?? 'Delete failed'),
  })

  const addStationMutation = useMutation({
    mutationFn: ({ tripId, stationId }: { tripId: number; stationId: number }) =>
      busTripApi.addStation(tripId, stationId),
    onSuccess: () => { queryClient.invalidateQueries({ queryKey: ['bus-trips'] }); setAddingStation(null) },
    onError: (err: any) => setError(err.response?.data?.message ?? 'Failed to add station'),
  })

  const openCreate = () => { setForm(emptyForm()); setCreating(true); setError(null) }
  const openEdit = (t: BusTripResponse) => {
    setEditing(t)
    setForm({ routeId: t.routeId, busId: t.busId, startTime: t.startTime, status: t.status })
    setError(null)
  }
  const closeModal = () => { setCreating(false); setEditing(null); setError(null) }

  const handleSubmit = (e: React.FormEvent) => {
    e.preventDefault()
    if (editing) updateMutation.mutate({ id: editing.id, data: form })
    else createMutation.mutate(form)
  }

  const statusColor: Record<BusTripStatus, string> = {
    PLANNED: 'bg-blue-100 text-blue-700',
    COMPLETED: 'bg-green-100 text-green-700',
    CANCELLED: 'bg-red-100 text-red-700',
  }

  return (
    <div>
      <div className="flex justify-between items-center mb-6">
        <h1 className="text-2xl font-bold">Bus Trips</h1>
        <button onClick={openCreate} className="bg-blue-600 text-white px-4 py-2 rounded hover:bg-blue-700 text-sm">+ New Trip</button>
      </div>
      {error && <div className="mb-4 p-3 bg-red-100 text-red-700 rounded text-sm">{error}</div>}
      {isLoading ? <p className="text-gray-500">Loading…</p> : (
        <div className="overflow-x-auto bg-white rounded shadow">
          <table className="min-w-full text-sm">
            <thead className="bg-gray-100 text-left text-gray-600 uppercase text-xs">
              <tr>
                <th className="px-4 py-3">ID</th>
                <th className="px-4 py-3">Route</th>
                <th className="px-4 py-3">Bus</th>
                <th className="px-4 py-3">Departure</th>
                <th className="px-4 py-3">Status</th>
                <th className="px-4 py-3">Tickets</th>
                <th className="px-4 py-3">Stops</th>
                <th className="px-4 py-3">Actions</th>
              </tr>
            </thead>
            <tbody>
              {trips.map((t) => (
                <tr key={t.id} className="border-t hover:bg-gray-50">
                  <td className="px-4 py-3 text-gray-500">{t.id}</td>
                  <td className="px-4 py-3">{t.routeSummary}</td>
                  <td className="px-4 py-3 font-mono text-xs">{t.busRegistration}</td>
                  <td className="px-4 py-3">{t.startTime?.replace('T', ' ')}</td>
                  <td className="px-4 py-3">
                    <span className={`px-2 py-1 rounded text-xs font-semibold ${statusColor[t.status]}`}>{t.status}</span>
                  </td>
                  <td className="px-4 py-3 text-center">{t.ticketCount}</td>
                  <td className="px-4 py-3 text-center">{t.stops?.length ?? 0}</td>
                  <td className="px-4 py-3 flex gap-2">
                    <button onClick={() => openEdit(t)} className="text-blue-600 hover:underline text-xs">Edit</button>
                    <button onClick={() => { setAddingStation(t); setSelectedStation(0); setError(null) }}
                      className="text-purple-600 hover:underline text-xs">+Stop</button>
                    <button onClick={() => deleteMutation.mutate(t.id)} className="text-red-600 hover:underline text-xs">Delete</button>
                  </td>
                </tr>
              ))}
              {trips.length === 0 && <tr><td colSpan={8} className="px-4 py-6 text-center text-gray-400">No trips found.</td></tr>}
            </tbody>
          </table>
        </div>
      )}

      {/* Create / Edit modal */}
      {(creating || editing) && (
        <div className="fixed inset-0 bg-black/40 flex items-center justify-center z-50">
          <div className="bg-white rounded-lg shadow-xl w-full max-w-md p-6">
            <h2 className="text-lg font-bold mb-4">{editing ? 'Edit Trip' : 'New Trip'}</h2>
            {error && <div className="mb-4 p-3 bg-red-100 text-red-700 rounded text-sm">{error}</div>}
            <form onSubmit={handleSubmit} className="space-y-4">
              <div>
                <label className="block text-sm font-medium mb-1">Route</label>
                <select value={form.routeId} onChange={(e) => setForm({ ...form, routeId: Number(e.target.value) })}
                  className="w-full border rounded px-3 py-2 text-sm" required>
                  <option value={0} disabled>Select route…</option>
                  {routes.map((r) => <option key={r.id} value={r.id}>{r.originName} → {r.destinationName}</option>)}
                </select>
              </div>
              <div>
                <label className="block text-sm font-medium mb-1">Bus</label>
                <select value={form.busId} onChange={(e) => setForm({ ...form, busId: Number(e.target.value) })}
                  className="w-full border rounded px-3 py-2 text-sm" required>
                  <option value={0} disabled>Select bus…</option>
                  {buses.map((b) => <option key={b.id} value={b.id}>{b.registrationNumber} ({b.status})</option>)}
                </select>
              </div>
              <div>
                <label className="block text-sm font-medium mb-1">Departure Time</label>
                <input type="datetime-local" value={form.startTime}
                  onChange={(e) => setForm({ ...form, startTime: e.target.value })}
                  className="w-full border rounded px-3 py-2 text-sm" required />
              </div>
              <div>
                <label className="block text-sm font-medium mb-1">Status</label>
                <select value={form.status} onChange={(e) => setForm({ ...form, status: e.target.value as BusTripStatus })}
                  className="w-full border rounded px-3 py-2 text-sm">
                  {STATUS_OPTIONS.map((s) => <option key={s} value={s}>{s}</option>)}
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

      {/* Add stop modal */}
      {addingStation && (
        <div className="fixed inset-0 bg-black/40 flex items-center justify-center z-50">
          <div className="bg-white rounded-lg shadow-xl w-full max-w-sm p-6">
            <h2 className="text-lg font-bold mb-4">Add Stop to Trip #{addingStation.id}</h2>
            {error && <div className="mb-4 p-3 bg-red-100 text-red-700 rounded text-sm">{error}</div>}
            <div className="mb-4">
              <label className="block text-sm font-medium mb-1">Station</label>
              <select value={selectedStation} onChange={(e) => setSelectedStation(Number(e.target.value))}
                className="w-full border rounded px-3 py-2 text-sm">
                <option value={0} disabled>Select station…</option>
                {stations.map((s) => <option key={s.id} value={s.id}>{s.name} ({s.city})</option>)}
              </select>
            </div>
            <div className="flex justify-end gap-2">
              <button onClick={() => setAddingStation(null)} className="px-4 py-2 text-sm rounded border hover:bg-gray-50">Cancel</button>
              <button
                onClick={() => selectedStation && addStationMutation.mutate({ tripId: addingStation.id, stationId: selectedStation })}
                className="px-4 py-2 text-sm rounded bg-purple-600 text-white hover:bg-purple-700">
                Add Stop
              </button>
            </div>
          </div>
        </div>
      )}
    </div>
  )
}

