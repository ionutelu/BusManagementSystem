package com.example.busstation.model;

import java.util.Objects;

public class DutyAssignment {

    private String id;
    private String tripId;
    private String staffId;
    private DriverRole role;

    public DutyAssignment() {
        this.role = DriverRole.PRIMARY_DRIVER;
    }

    public DutyAssignment(String id, String tripId, String staffId) {
        this(id, tripId, staffId, DriverRole.PRIMARY_DRIVER);
    }

    public DutyAssignment(String id, String tripId, String staffId, DriverRole role) {
        this.id = Objects.requireNonNull(id, "id is required");
        this.tripId = Objects.requireNonNull(tripId, "tripId is required");
        this.staffId = Objects.requireNonNull(staffId, "staffId is required");
        this.role = Objects.requireNonNullElse(role, DriverRole.PRIMARY_DRIVER);
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = Objects.requireNonNull(id, "id is required");
    }

    public String getTripId() {
        return tripId;
    }

    public void setTripId(String tripId) {
        this.tripId = Objects.requireNonNull(tripId, "tripId is required");
    }

    public String getStaffId() {
        return staffId;
    }

    public void setStaffId(String staffId) {
        this.staffId = Objects.requireNonNull(staffId, "staffId is required");
    }

    public DriverRole getRole() {
        return role;
    }

    public void setRole(DriverRole role) {
        this.role = Objects.requireNonNullElse(role, DriverRole.PRIMARY_DRIVER);
    }

    public String getRoleDescription() {
        return role.getDescription();
    }

    @Override
    public String toString() {
        return "DutyAssignment{" +
                "id='" + id + '\'' +
                ", tripId='" + tripId + '\'' +
                ", staffId='" + staffId + '\'' +
                ", role=" + role +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof DutyAssignment)) return false;
        DutyAssignment that = (DutyAssignment) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
