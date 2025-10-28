package com.example.busstation.model;

import java.util.Objects;

public abstract class Staff {

    protected String id;
    protected String name;
    protected String email;

    public Staff() {
    }

    public Staff(String id, String name) {
        this.id = Objects.requireNonNull(id, "id is required");
        this.name = Objects.requireNonNull(name, "name is required");
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = Objects.requireNonNull(id, "id is required");
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = Objects.requireNonNull(name, "name is required");
    }

    @Override
    public String toString() {
        return "Staff{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Staff)) return false;
        Staff staff = (Staff) o;
        return Objects.equals(id, staff.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}

