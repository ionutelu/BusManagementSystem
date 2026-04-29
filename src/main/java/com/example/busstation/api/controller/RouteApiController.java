package com.example.busstation.api.controller;

import com.example.busstation.api.dto.busstation.BusStationResponseDto;
import com.example.busstation.api.dto.bustrip.BusTripResponseDto;
import com.example.busstation.api.dto.route.RouteRequestDto;
import com.example.busstation.api.dto.route.RouteResponseDto;
import com.example.busstation.model.BusStation;
import com.example.busstation.model.BusTrip;
import com.example.busstation.model.Route;
import com.example.busstation.service.BusStationService;
import com.example.busstation.service.RouteService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/routes")
public class RouteApiController {

    private final RouteService routeService;
    private final BusStationService busStationService;

    public RouteApiController(RouteService routeService,
                               BusStationService busStationService) {
        this.routeService = routeService;
        this.busStationService = busStationService;
    }

    @GetMapping
    public List<RouteResponseDto> list(
            @RequestParam(required = false) String origin,
            @RequestParam(required = false) String destination,
            @RequestParam(required = false) Float maxDistance,
            @RequestParam(required = false, defaultValue = "id") String sortField,
            @RequestParam(required = false, defaultValue = "asc") String sortDirection
    ) {
        return routeService.findFilteredAndSorted(origin, destination, maxDistance, sortField, sortDirection)
                .stream().map(this::toDto).collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public RouteResponseDto getById(@PathVariable Long id) {
        return toDto(routeService.findById(id));
    }

    @GetMapping("/{id}/bus-trips")
    public List<BusTripResponseDto> getBusTrips(@PathVariable Long id) {
        Route route = routeService.findById(id);
        return route.getTrips().stream()
                .map(this::tripToDto)
                .collect(Collectors.toList());
    }

    @PostMapping
    public ResponseEntity<RouteResponseDto> create(@Valid @RequestBody RouteRequestDto dto) {
        Route route = fromDto(dto);
        routeService.save(route);
        return ResponseEntity.status(HttpStatus.CREATED).body(toDto(route));
    }

    @PutMapping("/{id}")
    public RouteResponseDto update(@PathVariable Long id, @Valid @RequestBody RouteRequestDto dto) {
        Route existing = routeService.findById(id);
        BusStation origin = busStationService.findById(dto.getOriginStationId());
        BusStation destination = busStationService.findById(dto.getDestinationStationId());
        existing.setOrigin(origin);
        existing.setDestination(destination);
        existing.setDistance(dto.getDistance());
        routeService.save(existing);
        return toDto(existing);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        routeService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    // ── Mappers ────────────────────────────────────────────────────────────────

    RouteResponseDto toDto(Route route) {
        return new RouteResponseDto(
                route.getId(),
                route.getOrigin() != null ? route.getOrigin().getId() : null,
                route.getOrigin() != null ? route.getOrigin().getName() : null,
                route.getOrigin() != null ? route.getOrigin().getCity() : null,
                route.getDestination() != null ? route.getDestination().getId() : null,
                route.getDestination() != null ? route.getDestination().getName() : null,
                route.getDestination() != null ? route.getDestination().getCity() : null,
                route.getDistance()
        );
    }

    private BusTripResponseDto tripToDto(BusTrip trip) {
        BusTripResponseDto dto = new BusTripResponseDto();
        dto.setId(trip.getId());
        dto.setRouteId(trip.getRoute() != null ? trip.getRoute().getId() : null);
        dto.setBusId(trip.getBus() != null ? trip.getBus().getId() : null);
        dto.setBusRegistration(trip.getBus() != null ? trip.getBus().getRegistrationNumber() : null);
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

    private Route fromDto(RouteRequestDto dto) {
        Route route = new Route();
        BusStation origin = busStationService.findById(dto.getOriginStationId());
        BusStation destination = busStationService.findById(dto.getDestinationStationId());
        route.setOrigin(origin);
        route.setDestination(destination);
        route.setDistance(dto.getDistance());
        return route;
    }
}




