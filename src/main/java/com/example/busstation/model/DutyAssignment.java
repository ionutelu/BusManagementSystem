package com.example.busstation.model;

public class DutyAssignment {

    public enum DriverRole{
        PRIMARY_DRIVER,
        RESERVE_DRIVER
    }
    public String id;
    public String tripId;
    public String staffId;
    public DriverRole role;

    public DutyAssignment(String id, String tripId, String staffId, DriverRole role) {
        this.id = id;
        this.tripId = tripId;
        this.staffId = staffId;
        this.role = role;
    }

    public DutyAssignment() {
    }

    public String getId() {
        return id;
    }

    public String getTripId() {
        return tripId;
    }

    public String getStaffId() {
        return staffId;
    }

    public DriverRole getRole() {
        return role;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setTripId(String tripId) {
        this.tripId = tripId;
    }

    public void setStaffId(String staffId) {
        this.staffId = staffId;
    }

    public void setRole(DriverRole role) {
        this.role = role;
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
}
