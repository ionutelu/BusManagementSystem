package com.example.busstation.repository;

import com.example.busstation.model.Driver;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Repository
public class DriverRepo implements AbstractRepository<Driver> {

    private final List<Driver> driverRepo = new ArrayList<>();

    public DriverRepo() {
        driverRepo.add(new Driver("1", "Ion Popescu", 5));
        driverRepo.add(new Driver("2", "Maria Ionescu", 8));
    }

    @Override
    public List<Driver> findAll() {
        return driverRepo;
    }

    @Override
    public Driver findById(String id) {
        for (Driver driver : driverRepo) {
            if (driver.getId().equals(id)) {
                return driver;
            }
        }
        return null;
    }

    @Override
    public Driver save(Driver driver) {
        Objects.requireNonNull(driver, "driver is required");
        Driver existing = findById(driver.getId());
        if (existing != null) {
            driverRepo.remove(existing);
        }
        driverRepo.add(driver);
        return driver;
    }

    @Override
    public boolean deleteById(String id) {
        for (Driver driver : driverRepo) {
            if (driver.getId().equals(id)) {
                driverRepo.remove(driver);
                return true;
            }
        }
        return false;
    }
}
