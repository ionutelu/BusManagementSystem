package com.example.busstation.model;

public class Staff {
    public String id;
    String name;

    public Staff(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public Staff() {
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }
}
