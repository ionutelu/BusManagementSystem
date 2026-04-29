package com.example.busstation.api.dto.dutyassignment;

public class DutyAssignmentResponseDto {

    private Long id;
    private Long busTripId;
    private String busTripSummary;
    private Long staffId;
    private String staffName;
    private String role;
    private String roleDescription;

    public DutyAssignmentResponseDto() {}

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Long getBusTripId() { return busTripId; }
    public void setBusTripId(Long busTripId) { this.busTripId = busTripId; }

    public String getBusTripSummary() { return busTripSummary; }
    public void setBusTripSummary(String busTripSummary) { this.busTripSummary = busTripSummary; }

    public Long getStaffId() { return staffId; }
    public void setStaffId(Long staffId) { this.staffId = staffId; }

    public String getStaffName() { return staffName; }
    public void setStaffName(String staffName) { this.staffName = staffName; }

    public String getRole() { return role; }
    public void setRole(String role) { this.role = role; }

    public String getRoleDescription() { return roleDescription; }
    public void setRoleDescription(String roleDescription) { this.roleDescription = roleDescription; }
}

