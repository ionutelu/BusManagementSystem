// ── Shared ─────────────────────────────────────────────────────────────────

export interface ApiErrorResponse {
  timestamp: string
  status: number
  error: string
  message: string
  path: string
}

// ── Bus ─────────────────────────────────────────────────────────────────────

export type BusStatus = 'ACTIVE' | 'DOWN'

export interface BusResponse {
  id: number
  vin: string
  registrationNumber: string
  capacity: number
  status: BusStatus
}

export interface BusRequest {
  vin: string
  registrationNumber: string
  capacity: number
  status: BusStatus
}

// ── BusStation ───────────────────────────────────────────────────────────────

export interface BusStationResponse {
  id: number
  name: string
  city: string
  isDamaged: boolean | null
}

export interface BusStationRequest {
  name: string
  city: string
  isDamaged?: boolean
}

// ── Route ────────────────────────────────────────────────────────────────────

export interface RouteResponse {
  id: number
  originStationId: number
  originName: string
  originCity: string
  destinationStationId: number
  destinationName: string
  destinationCity: string
  distance: number
}

export interface RouteRequest {
  originStationId: number
  destinationStationId: number
  distance: number
}

// ── BusTrip ──────────────────────────────────────────────────────────────────

export type BusTripStatus = 'PLANNED' | 'COMPLETED' | 'CANCELLED'

export interface BusTripResponse {
  id: number
  routeId: number
  routeSummary: string
  busId: number
  busRegistration: string
  startTime: string
  status: BusTripStatus
  ticketCount: number
  assignmentCount: number
  stops: BusStationResponse[]
}

export interface BusTripRequest {
  routeId: number
  busId: number
  startTime: string
  status?: BusTripStatus
}

// ── Driver ───────────────────────────────────────────────────────────────────

export interface DriverResponse {
  id: number
  name: string
  email: string
  experienceYears: number
}

export interface DriverRequest {
  name: string
  email: string
  experienceYears: number
}

// ── TripManager ──────────────────────────────────────────────────────────────

export interface TripManagerResponse {
  id: number
  name: string
  email: string
  employeeCode: string
}

export interface TripManagerRequest {
  name: string
  email: string
  employeeCode: string
}

// ── Passenger ────────────────────────────────────────────────────────────────

export interface PassengerResponse {
  id: number
  name: string
  currency: string
}

export interface PassengerRequest {
  name: string
  currency: string
}

// ── Ticket ───────────────────────────────────────────────────────────────────

export interface TicketResponse {
  id: number
  busTripId: number
  busTripSummary: string
  passengerId: number
  passengerName: string
  seatNumber: string
  price: number
}

export interface TicketRequest {
  busTripId: number
  passengerId: number
  seatNumber: string
  price: number
}

// ── DutyAssignment ───────────────────────────────────────────────────────────

export type DriverRole = 'PRIMARY_DRIVER' | 'RESERVE_DRIVER'

export interface DutyAssignmentResponse {
  id: number
  busTripId: number
  busTripSummary: string
  staffId: number
  staffName: string
  role: DriverRole
  roleDescription: string
}

export interface DutyAssignmentRequest {
  busTripId: number
  staffId: number
  role?: DriverRole
}

