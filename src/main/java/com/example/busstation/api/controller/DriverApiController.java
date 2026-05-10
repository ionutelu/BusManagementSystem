package com.example.busstation.api.controller;

import com.example.busstation.api.dto.driver.DriverRequestDto;
import com.example.busstation.api.dto.driver.DriverResponseDto;
import com.example.busstation.model.Driver;
import com.example.busstation.service.DriverService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/drivers")
public class DriverApiController {

    private final DriverService driverService;

    public DriverApiController(DriverService driverService) {
        this.driverService = driverService;
    }

    @GetMapping
    public Page<DriverResponseDto> list(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) Integer minExperience,
            @RequestParam(required = false, defaultValue = "id") String sortField,
            @RequestParam(required = false, defaultValue = "asc") String sortDirection,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size
    ) {
        return driverService.findFilteredAndSortedPaged(name, minExperience, sortField, sortDirection, page, size)
                .map(this::toDto);
    }

    @GetMapping("/{id}")
    public DriverResponseDto getById(@PathVariable Long id) {
        return toDto(driverService.findById(id));
    }

    @PostMapping
    public ResponseEntity<DriverResponseDto> create(@Valid @RequestBody DriverRequestDto dto) {
        Driver driver = fromDto(dto);
        driverService.save(driver);
        return ResponseEntity.status(HttpStatus.CREATED).body(toDto(driver));
    }

    @PutMapping("/{id}")
    public DriverResponseDto update(@PathVariable Long id, @Valid @RequestBody DriverRequestDto dto) {
        Driver existing = driverService.findById(id);
        existing.setName(dto.getName());
        existing.setEmail(dto.getEmail());
        existing.setExperienceYears(dto.getExperienceYears());
        driverService.save(existing);
        return toDto(existing);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        driverService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    // ── Mappers ────────────────────────────────────────────────────────────────

    private DriverResponseDto toDto(Driver driver) {
        return new DriverResponseDto(
                driver.getId(),
                driver.getName(),
                driver.getEmail(),
                driver.getExperienceYears()
        );
    }

    private Driver fromDto(DriverRequestDto dto) {
        return new Driver(dto.getName(), dto.getEmail(), dto.getExperienceYears());
    }
}

