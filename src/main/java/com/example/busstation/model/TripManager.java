package com.example.busstation.model;

import java.util.List;

public class TripManager {
    public List<DutyAssignment> assignments;
    public String employeeCode;

    public TripManager() {
    }

    public TripManager(List<DutyAssignment> assignments, String employeeCode) {
        this.assignments = assignments;
        this.employeeCode = employeeCode;
    }

    public List<DutyAssignment> getAssignments() {
        return assignments;
    }

    public void setAssignments(List<DutyAssignment> assignments) {
        this.assignments = assignments;
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
                "assignments=" + assignments +
                ", employeeCode='" + employeeCode + '\'' +
                '}';
    }
}
