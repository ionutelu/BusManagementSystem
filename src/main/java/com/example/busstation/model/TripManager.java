package com.example.busstation.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
@Entity
@Table(name = "trip_managers")
@PrimaryKeyJoinColumn(name = "staff_id")
public class TripManager extends Staff {

    @OneToMany(mappedBy = "staff", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<DutyAssignment> assignments = new ArrayList<>();

    @NotBlank(message = "Code is required.")
    private String employeeCode;

    public TripManager() {
        super();
    }

    public TripManager(String name, String email, List<DutyAssignment> assignments, String employeeCode) {
        super(name, email);
        this.assignments = (assignments != null) ? new ArrayList<>(assignments) : new ArrayList<>();
        this.employeeCode = employeeCode;
    }

    public List<DutyAssignment> getAssignments() {
        return Collections.unmodifiableList(assignments);
    }

    public void setAssignments(List<DutyAssignment> assignments) {
        this.assignments = (assignments != null) ? new ArrayList<>(assignments) : new ArrayList<>();
    }

    public void addAssignment(DutyAssignment assignment) {
        this.assignments.add(assignment);
    }

    public boolean removeAssignment(DutyAssignment assignment) {
        return this.assignments.remove(assignment);
    }

    public String getEmployeeCode() {
        return employeeCode;
    }

    public void setEmployeeCode(String employeeCode) {
        this.employeeCode = employeeCode;
    }

    @Override
    public String toString() {
        return "TripManager{" +
                "id='" + getId() + '\'' +
                ", name='" + getName() + '\'' +
                ", assignments=" + assignments +
                ", employeeCode='" + employeeCode + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof TripManager)) return false;
        TripManager that = (TripManager) o;
        return Objects.equals(getId(), that.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }
}

