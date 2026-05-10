package com.example.busstation.api.controller;

import com.example.busstation.api.dto.busstation.BusStationResponseDto;
import com.example.busstation.api.dto.bustrip.BusTripRequestDto;
import com.example.busstation.api.dto.bustrip.BusTripResponseDto;
import com.example.busstation.model.*;
import com.example.busstation.service.BusService;
import com.example.busstation.service.BusStationService;
import com.example.busstation.service.BusTripService;
import com.example.busstation.service.RouteService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/bus-trips")
public class BusTripApiController {

    private final BusTripService busTripService;
    private final RouteService routeService;
    private final BusService busService;
    private final BusStationService busStationService;

    public BusTripApiController(BusTripService busTripService, RouteService routeService,
                                 BusService busService, BusStationService busStationService) {
        this.busTripService = busTripService;
        this.routeService = routeService;
        this.busService = busService;
        this.busStationService = busStationService;
    }

    @GetMapping
    public Page<BusTripResponseDto> list(
            @RequestParam(required = false) String route,
            @RequestParam(required = false) BusTripStatus status,
            @RequestParam(required = false, defaultValue = "id") String sortField,
            @RequestParam(required = false, defaultValue = "asc") String sortDirection,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size
    ) {
        return busTripService.findFilteredAndSortedPaged(route, status, sortField, sortDirection, page, size)
                .map(this::toDto);
    }

    @GetMapping("/{id}")
    public BusTripResponseDto getById(@PathVariable Long id) {
        return toDto(busTripService.findById(id));
    }

    @PostMapping
    public ResponseEntity<BusTripResponseDto> create(@Valid @RequestBody BusTripRequestDto dto) {
        BusTrip trip = fromDto(dto);
        BusTrip saved = busTripService.save(trip);
        return ResponseEntity.status(HttpStatus.CREATED).body(toDto(saved));
    }

    @PutMapping("/{id}")
    public BusTripResponseDto update(@PathVariable Long id, @Valid @RequestBody BusTripRequestDto dto) {
        BusTrip existing = busTripService.findById(id);
        existing.setRoute(routeService.findById(dto.getRouteId()));
        existing.setBus(busService.findById(dto.getBusId()));
        existing.setStartTime(dto.getStartTime());
        if (dto.getStatus() != null) {
            existing.setStatus(BusTripStatus.valueOf(dto.getStatus()));
        }
        return toDto(busTripService.save(existing));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        busTripService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{tripId}/stations/{stationId}")
    public BusTripResponseDto addStation(@PathVariable Long tripId, @PathVariable Long stationId) {
        BusTrip trip = busTripService.findById(tripId);
        BusStation station = busStationService.findById(stationId);
        trip.getBusStations().add(station);
        station.getTrips().add(trip);
        return toDto(busTripService.save(trip));
    }

    // ── Mappers (package-visible so RouteApiController can reuse) ──────────────

    BusTripResponseDto toDto(BusTrip trip) {
        BusTripResponseDto dto = new BusTripResponseDto();
        dto.setId(trip.getId());

        if (trip.getRoute() != null) {
            dto.setRouteId(trip.getRoute().getId());
            String origin = trip.getRoute().getOrigin() != null
                    ? trip.getRoute().getOrigin().getName() : "?";
            String dest = trip.getRoute().getDestination() != null
                    ? trip.getRoute().getDestination().getName() : "?";
            dto.setRouteSummary(origin + " → " + dest);
        }

        if (trip.getBus() != null) {
            dto.setBusId(trip.getBus().getId());
            dto.setBusRegistration(trip.getBus().getRegistrationNumber());
        }

        dto.setStartTime(trip.getStartTime());
        dto.setStatus(trip.getStatus() != null ? trip.getStatus().name() : null);
        dto.setTicketCount(trip.getTickets().size());
        dto.setAssignmentCount(trip.getAssignments().size());

        List<BusStationResponseDto> stops = trip.getBusStations().stream()
                .map(s -> new BusStationResponseDto(s.getId(), s.getName(), s.getCity(), s.getDamaged()))
                .collect(Collectors.toList());
        dto.setStops(stops);

        return dto;
    }

    private BusTrip fromDto(BusTripRequestDto dto) {
        BusTrip trip = new BusTrip();
        trip.setRoute(routeService.findById(dto.getRouteId()));
        trip.setBus(busService.findById(dto.getBusId()));
        trip.setStartTime(dto.getStartTime());
        if (dto.getStatus() != null) {
            trip.setStatus(BusTripStatus.valueOf(dto.getStatus()));
        }
        return trip;
    }
}

