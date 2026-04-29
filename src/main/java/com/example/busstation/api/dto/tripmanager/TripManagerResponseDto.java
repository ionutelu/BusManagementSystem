package com.example.busstation.api.dto.tripmanager;

public class TripManagerResponseDto {

    private Long id;
    private String name;
    private String email;
    private String employeeCode;

    public TripManagerResponseDto() {}

    public TripManagerResponseDto(Long id, String name, String email, String employeeCode) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.employeeCode = employeeCode;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getEmployeeCode() { return employeeCode; }
    public void setEmployeeCode(String employeeCode) { this.employeeCode = employeeCode; }
}

