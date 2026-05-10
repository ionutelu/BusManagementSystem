package com.example.busstation.api.controller;

import com.example.busstation.api.dto.busstation.BusStationRequestDto;
import com.example.busstation.api.dto.busstation.BusStationResponseDto;
import com.example.busstation.model.BusStation;
import com.example.busstation.service.BusStationService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/bus-stations")
public class BusStationApiController {

    private final BusStationService busStationService;

    public BusStationApiController(BusStationService busStationService) {
        this.busStationService = busStationService;
    }

    @GetMapping
    public Page<BusStationResponseDto> list(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) Boolean damaged,
            @RequestParam(required = false, defaultValue = "id") String sortField,
            @RequestParam(required = false, defaultValue = "asc") String sortDirection,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size
    ) {
        return busStationService.findFilteredAndSortedPaged(name, damaged, sortField, sortDirection, page, size)
                .map(this::toDto);
    }

    @GetMapping("/{id}")
    public BusStationResponseDto getById(@PathVariable Long id) {
        return toDto(busStationService.findById(id));
    }

    @PostMapping
    public ResponseEntity<BusStationResponseDto> create(@Valid @RequestBody BusStationRequestDto dto) {
        BusStation saved = busStationService.save(fromDto(dto));
        return ResponseEntity.status(HttpStatus.CREATED).body(toDto(saved));
    }

    @PutMapping("/{id}")
    public BusStationResponseDto update(@PathVariable Long id, @Valid @RequestBody BusStationRequestDto dto) {
        BusStation existing = busStationService.findById(id);
        existing.setName(dto.getName());
        existing.setCity(dto.getCity());
        existing.setDamaged(dto.getIsDamaged());
        return toDto(busStationService.save(existing));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        busStationService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    // ── Mappers ────────────────────────────────────────────────────────────────

    private BusStationResponseDto toDto(BusStation station) {
        return new BusStationResponseDto(
                station.getId(),
                station.getName(),
                station.getCity(),
                station.getDamaged()
        );
    }

    private BusStation fromDto(BusStationRequestDto dto) {
        BusStation station = new BusStation();
        station.setName(dto.getName());
        station.setCity(dto.getCity());
        station.setDamaged(dto.getIsDamaged());
        return station;
    }
}

