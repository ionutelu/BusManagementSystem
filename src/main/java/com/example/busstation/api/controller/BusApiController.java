package com.example.busstation.api.controller;

import com.example.busstation.api.dto.bus.BusRequestDto;
import com.example.busstation.api.dto.bus.BusResponseDto;
import com.example.busstation.model.Bus;
import com.example.busstation.model.BusStatus;
import com.example.busstation.service.BusService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/buses")
public class BusApiController {

    private final BusService busService;

    public BusApiController(BusService busService) {
        this.busService = busService;
    }

    @GetMapping
    public Page<BusResponseDto> list(
            @RequestParam(required = false) String vin,
            @RequestParam(required = false) BusStatus status,
            @RequestParam(required = false) Integer minCapacity,
            @RequestParam(required = false, defaultValue = "id") String sortField,
            @RequestParam(required = false, defaultValue = "asc") String sortDirection,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size
    ) {
        return busService.findAllFilteredAndSortedPaged(vin, status, minCapacity, sortField, sortDirection, page, size)
                .map(this::toDto);
    }

    @GetMapping("/{id}")
    public BusResponseDto getById(@PathVariable Long id) {
        return toDto(busService.findById(id));
    }

    @PostMapping
    public ResponseEntity<BusResponseDto> create(@Valid @RequestBody BusRequestDto dto) {
        Bus bus = fromDto(dto);
        Bus saved = busService.save(bus);
        return ResponseEntity.status(HttpStatus.CREATED).body(toDto(saved));
    }

    @PutMapping("/{id}")
    public BusResponseDto update(@PathVariable Long id, @Valid @RequestBody BusRequestDto dto) {
        Bus existing = busService.findById(id);
        existing.setVin(dto.getVin());
        existing.setRegistrationNumber(dto.getRegistrationNumber());
        existing.setCapacity(dto.getCapacity());
        existing.setStatus(BusStatus.fromString(dto.getStatus()));
        return toDto(busService.save(existing));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        busService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    // ── Mappers ────────────────────────────────────────────────────────────────

    private BusResponseDto toDto(Bus bus) {
        return new BusResponseDto(
                bus.getId(),
                bus.getVin(),
                bus.getRegistrationNumber(),
                bus.getCapacity(),
                bus.getStatus() != null ? bus.getStatus().name() : null
        );
    }

    private Bus fromDto(BusRequestDto dto) {
        Bus bus = new Bus();
        bus.setVin(dto.getVin());
        bus.setRegistrationNumber(dto.getRegistrationNumber());
        bus.setCapacity(dto.getCapacity());
        bus.setStatus(BusStatus.fromString(dto.getStatus()));
        return bus;
    }
}

