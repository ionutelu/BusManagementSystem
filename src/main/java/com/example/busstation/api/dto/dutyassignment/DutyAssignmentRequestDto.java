package com.example.busstation.api.dto.dutyassignment;

import jakarta.validation.constraints.NotNull;

public class DutyAssignmentRequestDto {

    @NotNull(message = "Bus trip ID is required")
    private Long busTripId;

    @NotNull(message = "Staff ID is required")
    private Long staffId;

    private String role;

    public Long getBusTripId() { return busTripId; }
    public void setBusTripId(Long busTripId) { this.busTripId = busTripId; }

    public Long getStaffId() { return staffId; }
    public void setStaffId(Long staffId) { this.staffId = staffId; }

    public String getRole() { return role; }
    public void setRole(String role) { this.role = role; }
}

