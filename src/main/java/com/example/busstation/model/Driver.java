package com.example.busstation.model;

import java.util.List;

public class Driver extends Staff{

    public List<DutyAssignment> assignments;
    public int experienceYears;

    public Driver(String id, String name, int experienceYears) {
        super(id, name);
        this.experienceYears = experienceYears;
    }

    public List<DutyAssignment> getAssignments() {
        return assignments;
    }

    public void setAssignments(List<DutyAssignment> assignments) {
        this.assignments = assignments;
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

}
