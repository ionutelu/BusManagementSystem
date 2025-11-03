package com.example.busstation.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class TripManager extends Staff {

    private List<DutyAssignment> assignments = new ArrayList<>();
    private String employeeCode;

    public TripManager() {
        super();
    }

    public TripManager(String id, String name, String email, List<DutyAssignment> assignments, String employeeCode) {
        super(id, name, email);
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

