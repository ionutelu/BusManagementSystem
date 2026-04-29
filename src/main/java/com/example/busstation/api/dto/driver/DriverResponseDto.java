package com.example.busstation.api.dto.driver;

public class DriverResponseDto {

    private Long id;
    private String name;
    private String email;
    private int experienceYears;

    public DriverResponseDto() {}

    public DriverResponseDto(Long id, String name, String email, int experienceYears) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.experienceYears = experienceYears;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public int getExperienceYears() { return experienceYears; }
    public void setExperienceYears(int experienceYears) { this.experienceYears = experienceYears; }
}

