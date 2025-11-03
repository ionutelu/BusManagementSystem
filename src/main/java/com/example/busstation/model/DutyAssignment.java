package com.example.busstation.model;

import java.util.Objects;

public class DutyAssignment implements Identifiable<String>{

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
        this.id = id;
        this.tripId = tripId;
        this.staffId = staffId;
        this.role = role;
    }
    @Override
    public String getId() {
        return id;
    }
    @Override
    public void setId(String id) {
        this.id = id;
    }

    public String getTripId() {
        return tripId;
    }

    public void setTripId(String tripId) {
        this.tripId = tripId;
    }

    public String getStaffId() {
        return staffId;
    }

    public void setStaffId(String staffId) {
        this.staffId = staffId;
    }

    public DriverRole getRole() {
        return role;
    }

    public void setRole(DriverRole role) {
        this.role = role;
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
