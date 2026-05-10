package com.example.busstation.api.controller;

import com.example.busstation.api.dto.dutyassignment.DutyAssignmentRequestDto;
import com.example.busstation.api.dto.dutyassignment.DutyAssignmentResponseDto;
import com.example.busstation.model.BusTrip;
import com.example.busstation.model.DriverRole;
import com.example.busstation.model.DutyAssignment;
import com.example.busstation.model.Staff;
import com.example.busstation.service.BusTripService;
import com.example.busstation.service.DutyAssignmentService;
import com.example.busstation.service.DriverService;
import com.example.busstation.service.TripManagerService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/assignments")
public class DutyAssignmentApiController {

    private final DutyAssignmentService dutyAssignmentService;
    private final BusTripService busTripService;
    private final DriverService driverService;
    private final TripManagerService tripManagerService;

    public DutyAssignmentApiController(DutyAssignmentService dutyAssignmentService,
                                        BusTripService busTripService,
                                        DriverService driverService,
                                        TripManagerService tripManagerService) {
        this.dutyAssignmentService = dutyAssignmentService;
        this.busTripService = busTripService;
        this.driverService = driverService;
        this.tripManagerService = tripManagerService;
    }

    @GetMapping
    public Page<DutyAssignmentResponseDto> list(
            @RequestParam(required = false) Long tripId,
            @RequestParam(required = false) String staffName,
            @RequestParam(required = false) DriverRole role,
            @RequestParam(required = false, defaultValue = "id") String sortField,
            @RequestParam(required = false, defaultValue = "asc") String sortDirection,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size
    ) {
        return dutyAssignmentService.findFilteredAndSortedPaged(tripId, staffName, role, sortField, sortDirection, page, size)
                .map(this::toDto);
    }

    @GetMapping("/{id}")
    public DutyAssignmentResponseDto getById(@PathVariable Long id) {
        return toDto(dutyAssignmentService.findById(id));
    }

    @PostMapping
    public ResponseEntity<DutyAssignmentResponseDto> create(@Valid @RequestBody DutyAssignmentRequestDto dto) {
        DutyAssignment assignment = fromDto(dto);
        dutyAssignmentService.save(assignment);
        return ResponseEntity.status(HttpStatus.CREATED).body(toDto(assignment));
    }

    @PutMapping("/{id}")
    public DutyAssignmentResponseDto update(@PathVariable Long id,
                                             @Valid @RequestBody DutyAssignmentRequestDto dto) {
        DutyAssignment existing = dutyAssignmentService.findById(id);
        existing.setTripId(busTripService.findById(dto.getBusTripId()));
        existing.setStaff(resolveStaff(dto.getStaffId()));
        if (dto.getRole() != null) {
            existing.setRole(DriverRole.valueOf(dto.getRole()));
        }
        dutyAssignmentService.save(existing);
        return toDto(existing);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        dutyAssignmentService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    // ── Mappers ────────────────────────────────────────────────────────────────

    private DutyAssignmentResponseDto toDto(DutyAssignment assignment) {
        DutyAssignmentResponseDto dto = new DutyAssignmentResponseDto();
        dto.setId(assignment.getId());

        if (assignment.getBusTrip() != null) {
            BusTrip trip = assignment.getBusTrip();
            dto.setBusTripId(trip.getId());
            String origin = (trip.getRoute() != null && trip.getRoute().getOrigin() != null)
                    ? trip.getRoute().getOrigin().getName() : "?";
            String dest = (trip.getRoute() != null && trip.getRoute().getDestination() != null)
                    ? trip.getRoute().getDestination().getName() : "?";
            dto.setBusTripSummary(origin + " → " + dest);
        }

        if (assignment.getStaff() != null) {
            dto.setStaffId(assignment.getStaff().getId());
            dto.setStaffName(assignment.getStaff().getName());
        }

        if (assignment.getRole() != null) {
            dto.setRole(assignment.getRole().name());
            dto.setRoleDescription(assignment.getRole().getDescription());
        }

        return dto;
    }

    private DutyAssignment fromDto(DutyAssignmentRequestDto dto) {
        DutyAssignment assignment = new DutyAssignment();
        assignment.setTripId(busTripService.findById(dto.getBusTripId()));
        assignment.setStaff(resolveStaff(dto.getStaffId()));
        if (dto.getRole() != null) {
            assignment.setRole(DriverRole.valueOf(dto.getRole()));
        }
        return assignment;
    }

    /**
     * Resolves a Staff member by trying Driver first, then TripManager.
     * Returns a lightweight Staff proxy if neither is found (consistent with legacy behaviour).
     */
    private Staff resolveStaff(Long staffId) {
        try {
            return driverService.findById(staffId);
        } catch (RuntimeException ignored) {}
        try {
            return tripManagerService.findById(staffId);
        } catch (RuntimeException ignored) {}
        // fallback — let the DB constraint catch an invalid ID
        Staff proxy = new Staff() {};
        proxy.setId(staffId);
        return proxy;
    }
}

