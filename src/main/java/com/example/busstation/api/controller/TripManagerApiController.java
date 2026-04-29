package com.example.busstation.api.controller;

import com.example.busstation.api.dto.tripmanager.TripManagerRequestDto;
import com.example.busstation.api.dto.tripmanager.TripManagerResponseDto;
import com.example.busstation.model.TripManager;
import com.example.busstation.service.TripManagerService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/trip-managers")
public class TripManagerApiController {

    private final TripManagerService tripManagerService;

    public TripManagerApiController(TripManagerService tripManagerService) {
        this.tripManagerService = tripManagerService;
    }

    @GetMapping
    public List<TripManagerResponseDto> list(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String email,
            @RequestParam(required = false) String employeeCode,
            @RequestParam(required = false, defaultValue = "id") String sortField,
            @RequestParam(required = false, defaultValue = "asc") String sortDirection
    ) {
        return tripManagerService.findFilteredAndSorted(name, email, employeeCode, sortField, sortDirection)
                .stream().map(this::toDto).collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public TripManagerResponseDto getById(@PathVariable Long id) {
        return toDto(tripManagerService.findById(id));
    }

    @PostMapping
    public ResponseEntity<TripManagerResponseDto> create(@Valid @RequestBody TripManagerRequestDto dto) {
        TripManager tm = fromDto(dto);
        tripManagerService.save(tm);
        return ResponseEntity.status(HttpStatus.CREATED).body(toDto(tm));
    }

    @PutMapping("/{id}")
    public TripManagerResponseDto update(@PathVariable Long id, @Valid @RequestBody TripManagerRequestDto dto) {
        TripManager existing = tripManagerService.findById(id);
        existing.setName(dto.getName());
        existing.setEmail(dto.getEmail());
        existing.setEmployeeCode(dto.getEmployeeCode());
        tripManagerService.save(existing);
        return toDto(existing);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        tripManagerService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    // ── Mappers ────────────────────────────────────────────────────────────────

    private TripManagerResponseDto toDto(TripManager tm) {
        return new TripManagerResponseDto(
                tm.getId(),
                tm.getName(),
                tm.getEmail(),
                tm.getEmployeeCode()
        );
    }

    private TripManager fromDto(TripManagerRequestDto dto) {
        return new TripManager(dto.getName(), dto.getEmail(), null, dto.getEmployeeCode());
    }
}

