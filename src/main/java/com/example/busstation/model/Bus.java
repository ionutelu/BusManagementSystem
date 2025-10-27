package com.example.busstation.model;

public class Bus {
    public enum BusStatus{
        ACTIVE,
        DOWN
    }
    public String id;
    public String registrationNumber;
    public int capacity;
    public BusStatus status;

    public Bus(String id, String registrationNumber, int capacity, BusStatus status){
        this.id = id;
        this.registrationNumber = registrationNumber;
        this.capacity = capacity;
        this.status = status;
    }

    public String getId() {return id;}
    public void setId(String id){this.id = id;}

    public String getRegistrationNumber(){return registrationNumber;}
    public void setRegistrationNumber(String registrationNumber){this.registrationNumber = registrationNumber;}

    public int getCapacity(){return capacity;}
    public void setCapacity(int capacity){this.capacity = capacity;}

    public BusStatus getStatus(){return status;}
    public void setStatus(BusStatus status){this.status = status;}
}
