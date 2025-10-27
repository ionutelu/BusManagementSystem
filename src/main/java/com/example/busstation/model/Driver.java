package com.example.busstation.model;

import java.util.List;

public class Driver extends Staff{

    public List<DutyAssignment> assignments;
    private int experienceYears;
    public Driver(String id, String name, int experienceYears) {
        super(id, name);
        this.experienceYears = experienceYears;
    }

    public int getExperienceYears() { return experienceYears; }

    public void setExperienceYears(int experienceYears) { this.experienceYears = experienceYears; }

}
