package com.example.busstation.repository;

import com.example.busstation.model.Driver;
import com.example.busstation.repository.AbstractRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

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
    public Driver findById(int id) {
        String strId = String.valueOf(id);
        for (Driver driver : driverRepo) {
            if (driver.getId().equals(strId)) {
                return driver;
            }
        }
        return null;
    }

    @Override
    public Driver save(Driver driver) {
        driverRepo.add(driver);
        return driver;
    }
}
