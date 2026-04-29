import { Routes, Route, Link } from 'react-router-dom'
import BusesPage        from './pages/buses/BusesPage'
import BusStationsPage  from './pages/busStations/BusStationsPage'
import RoutesPage       from './pages/routes/RoutesPage'
import BusTripsPage     from './pages/busTrips/BusTripsPage'
import DriversPage      from './pages/drivers/DriversPage'
import TripManagersPage from './pages/tripManagers/TripManagersPage'
import PassengersPage   from './pages/passengers/PassengersPage'
import TicketsPage      from './pages/tickets/TicketsPage'
import AssignmentsPage  from './pages/assignments/AssignmentsPage'

const navItems = [
  { to: '/buses',         label: '🚌 Buses' },
  { to: '/bus-stations',  label: '🏛 Stations' },
  { to: '/routes',        label: '🗺 Routes' },
  { to: '/bus-trips',     label: '🛣 Trips' },
  { to: '/drivers',       label: '👤 Drivers' },
  { to: '/trip-managers', label: '🗂 Managers' },
  { to: '/passengers',    label: '🧳 Passengers' },
  { to: '/tickets',       label: '🎫 Tickets' },
  { to: '/assignments',   label: '📋 Assignments' },
]

export default function App() {
  return (
    <div className="flex min-h-screen bg-gray-50">
      {/* Sidebar */}
      <nav className="w-56 bg-white shadow-md flex flex-col p-4 gap-2">
        <span className="font-bold text-lg mb-4 text-blue-700">Bus Station</span>
        {navItems.map((item) => (
          <Link
            key={item.to}
            to={item.to}
            className="px-3 py-2 rounded hover:bg-blue-50 text-sm text-gray-700 hover:text-blue-700 transition"
          >
            {item.label}
          </Link>
        ))}
      </nav>

      {/* Main content */}
      <main className="flex-1 p-8">
        <Routes>
          <Route path="/"               element={<BusesPage />} />
          <Route path="/buses"          element={<BusesPage />} />
          <Route path="/bus-stations"   element={<BusStationsPage />} />
          <Route path="/routes"         element={<RoutesPage />} />
          <Route path="/bus-trips"      element={<BusTripsPage />} />
          <Route path="/drivers"        element={<DriversPage />} />
          <Route path="/trip-managers"  element={<TripManagersPage />} />
          <Route path="/passengers"     element={<PassengersPage />} />
          <Route path="/tickets"        element={<TicketsPage />} />
          <Route path="/assignments"    element={<AssignmentsPage />} />
        </Routes>
      </main>
    </div>
  )
}

