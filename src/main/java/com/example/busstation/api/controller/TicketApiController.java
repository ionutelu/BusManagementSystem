package com.example.busstation.api.controller;

import com.example.busstation.api.dto.ticket.TicketRequestDto;
import com.example.busstation.api.dto.ticket.TicketResponseDto;
import com.example.busstation.model.BusTrip;
import com.example.busstation.model.Ticket;
import com.example.busstation.service.BusTripService;
import com.example.busstation.service.PassengerService;
import com.example.busstation.service.TicketService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/tickets")
public class TicketApiController {

    private final TicketService ticketService;
    private final BusTripService busTripService;
    private final PassengerService passengerService;

    public TicketApiController(TicketService ticketService,
                                BusTripService busTripService,
                                PassengerService passengerService) {
        this.ticketService = ticketService;
        this.busTripService = busTripService;
        this.passengerService = passengerService;
    }

    @GetMapping
    public List<TicketResponseDto> list(
            @RequestParam(required = false) Long busTripId,
            @RequestParam(required = false) String passengerName,
            @RequestParam(required = false) Double maxPrice,
            @RequestParam(required = false, defaultValue = "id") String sortField,
            @RequestParam(required = false, defaultValue = "asc") String sortDirection
    ) {
        return ticketService.findFilteredAndSorted(busTripId, passengerName, maxPrice, sortField, sortDirection)
                .stream().map(this::toDto).collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public TicketResponseDto getById(@PathVariable Long id) {
        return toDto(ticketService.findById(id));
    }

    @PostMapping
    public ResponseEntity<TicketResponseDto> create(@Valid @RequestBody TicketRequestDto dto) {
        Ticket ticket = fromDto(dto);
        Ticket saved = ticketService.save(ticket);
        return ResponseEntity.status(HttpStatus.CREATED).body(toDto(saved));
    }

    @PutMapping("/{id}")
    public TicketResponseDto update(@PathVariable Long id, @Valid @RequestBody TicketRequestDto dto) {
        Ticket existing = ticketService.findById(id);
        existing.setBusTrip(busTripService.findById(dto.getBusTripId()));
        existing.setPassenger(passengerService.findById(dto.getPassengerId()));
        existing.setSeatNumber(dto.getSeatNumber());
        existing.setPrice(dto.getPrice());
        return toDto(ticketService.save(existing));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        ticketService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    // ── Mappers (package-visible so PassengerApiController can reuse) ──────────

    TicketResponseDto toDto(Ticket ticket) {
        TicketResponseDto dto = new TicketResponseDto();
        dto.setId(ticket.getId());

        if (ticket.getBusTrip() != null) {
            BusTrip trip = ticket.getBusTrip();
            dto.setBusTripId(trip.getId());
            String origin = (trip.getRoute() != null && trip.getRoute().getOrigin() != null)
                    ? trip.getRoute().getOrigin().getName() : "?";
            String dest = (trip.getRoute() != null && trip.getRoute().getDestination() != null)
                    ? trip.getRoute().getDestination().getName() : "?";
            dto.setBusTripSummary(origin + " → " + dest + " @ " +
                    (trip.getStartTime() != null ? trip.getStartTime().toString() : "?"));
        }

        if (ticket.getPassenger() != null) {
            dto.setPassengerId(ticket.getPassenger().getId());
            dto.setPassengerName(ticket.getPassenger().getName());
        }

        dto.setSeatNumber(ticket.getSeatNumber());
        dto.setPrice(ticket.getPrice());
        return dto;
    }

    private Ticket fromDto(TicketRequestDto dto) {
        Ticket ticket = new Ticket();
        ticket.setBusTrip(busTripService.findById(dto.getBusTripId()));
        ticket.setPassenger(passengerService.findById(dto.getPassengerId()));
        ticket.setSeatNumber(dto.getSeatNumber());
        ticket.setPrice(dto.getPrice());
        return ticket;
    }
}


