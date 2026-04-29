package com.example.busstation.api.controller;

import com.example.busstation.api.dto.passenger.PassengerRequestDto;
import com.example.busstation.api.dto.passenger.PassengerResponseDto;
import com.example.busstation.api.dto.ticket.TicketResponseDto;
import com.example.busstation.model.BusTrip;
import com.example.busstation.model.Passenger;
import com.example.busstation.model.Ticket;
import com.example.busstation.service.PassengerService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/passengers")
public class PassengerApiController {

    private final PassengerService passengerService;

    public PassengerApiController(PassengerService passengerService) {
        this.passengerService = passengerService;
    }

    @GetMapping
    public List<PassengerResponseDto> list(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String currency,
            @RequestParam(required = false, defaultValue = "id") String sortField,
            @RequestParam(required = false, defaultValue = "asc") String sortDirection
    ) {
        return passengerService.findFilteredAndSorted(name, currency, sortField, sortDirection)
                .stream().map(this::toDto).collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public PassengerResponseDto getById(@PathVariable Long id) {
        return toDto(passengerService.findById(id));
    }

    @GetMapping("/{id}/tickets")
    public List<TicketResponseDto> getTickets(@PathVariable Long id) {
        Passenger passenger = passengerService.findById(id);
        return passenger.getTickets().stream()
                .map(this::ticketToDto)
                .collect(Collectors.toList());
    }

    @PostMapping
    public ResponseEntity<PassengerResponseDto> create(@Valid @RequestBody PassengerRequestDto dto) {
        Passenger saved = passengerService.save(fromDto(dto));
        return ResponseEntity.status(HttpStatus.CREATED).body(toDto(saved));
    }

    @PutMapping("/{id}")
    public PassengerResponseDto update(@PathVariable Long id, @Valid @RequestBody PassengerRequestDto dto) {
        Passenger existing = passengerService.findById(id);
        existing.setName(dto.getName());
        existing.setCurrency(dto.getCurrency());
        return toDto(passengerService.save(existing));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        passengerService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    // ── Mappers ────────────────────────────────────────────────────────────────

    private PassengerResponseDto toDto(Passenger p) {
        return new PassengerResponseDto(p.getId(), p.getName(), p.getCurrency());
    }

    private Passenger fromDto(PassengerRequestDto dto) {
        Passenger p = new Passenger();
        p.setName(dto.getName());
        p.setCurrency(dto.getCurrency());
        return p;
    }

    private TicketResponseDto ticketToDto(Ticket ticket) {
        TicketResponseDto dto = new TicketResponseDto();
        dto.setId(ticket.getId());
        if (ticket.getBusTrip() != null) {
            BusTrip trip = ticket.getBusTrip();
            dto.setBusTripId(trip.getId());
            String origin = (trip.getRoute() != null && trip.getRoute().getOrigin() != null)
                    ? trip.getRoute().getOrigin().getName() : "?";
            String dest = (trip.getRoute() != null && trip.getRoute().getDestination() != null)
                    ? trip.getRoute().getDestination().getName() : "?";
            dto.setBusTripSummary(origin + " → " + dest);
        }
        if (ticket.getPassenger() != null) {
            dto.setPassengerId(ticket.getPassenger().getId());
            dto.setPassengerName(ticket.getPassenger().getName());
        }
        dto.setSeatNumber(ticket.getSeatNumber());
        dto.setPrice(ticket.getPrice());
        return dto;
    }
}




