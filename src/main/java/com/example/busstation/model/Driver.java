package com.example.busstation.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class Driver extends Staff {

    private List<DutyAssignment> assignments = new ArrayList<>();
    private int experienceYears;

    public Driver() {
        super();
    }

    public Driver(String id, String name, String email, int experienceYears) {
        super(id, name, email);
        this.experienceYears = experienceYears;
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

    public void removeAssignment(DutyAssignment assignment) {
        this.assignments.remove(assignment);
    }

    public int getExperienceYears() {
        return experienceYears;
    }

    public void setExperienceYears(int experienceYears) {
        this.experienceYears = experienceYears;
    }

    @Override
    public String toString() {
        return "Driver{" +
                "id='" + getId() + '\'' +
                ", name='" + getName() + '\'' +
                ", experienceYears=" + experienceYears +
                ", assignments=" + assignments +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Driver)) return false;
        Driver driver = (Driver) o;
        return Objects.equals(getId(), driver.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }
}
