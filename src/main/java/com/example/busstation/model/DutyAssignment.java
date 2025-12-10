package com.example.busstation.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;

import java.util.Objects;

@Entity
@Table(name = "duty_assignments")
@JsonIgnoreProperties(ignoreUnknown = true)
public class DutyAssignment{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "trip_id", nullable = false)
    private BusTrip busTrip;

    @ManyToOne
    @JoinColumn(name = "staff_id", nullable = false)
    private Staff staff;

    private DriverRole role;

    public DutyAssignment() {
        this.role = DriverRole.PRIMARY_DRIVER;
    }

    public DutyAssignment(BusTrip busTrip, Staff staff) {
        this(busTrip, staff, DriverRole.PRIMARY_DRIVER);
    }

    public DutyAssignment(BusTrip busTrip, Staff staff, DriverRole role) {
        this.busTrip = busTrip;
        this.staff = staff;
        this.role = role;
    }

    public Long getId() {
        return id;
    }

    void setId(Long id){ this.id = id; }

    public BusTrip getBusTrip() {
        return busTrip;
    }

    public void setTripId(BusTrip busTrip) {
        this.busTrip = busTrip;
    }

    public Staff getStaff() {
        return staff;
    }

    public void setStaff(Staff staff) {
        this.staff = staff;
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
                ", tripId='" + busTrip.getId() + '\'' +
                ", staffId='" + staff.getId() + '\'' +
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
