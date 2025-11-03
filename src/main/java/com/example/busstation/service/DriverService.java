package com.example.busstation.service;

import com.example.busstation.model.Driver;
import com.example.busstation.repository.DriverRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DriverService {

    private final DriverRepo driverRepo;

    @Autowired
    public DriverService(DriverRepo driverRepo) {
        this.driverRepo = driverRepo;
    }

    public List<Driver> findAll(){
        return driverRepo.findAll();
    }

    public void save(Driver driver){
        driverRepo.save(driver);
    }

    public boolean deleteById(String id){
        return driverRepo.deleteById(id);
    }

    public Driver findById(String id){
        return driverRepo.findById(id);
    }
}
