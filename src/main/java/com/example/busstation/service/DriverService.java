package com.example.busstation.service;

import com.example.busstation.model.BusStation;
import com.example.busstation.model.Driver;
import com.example.busstation.repository.DriverRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DriverService {

    private final DriverRepository driverRepo;

    @Autowired
    public DriverService(DriverRepository driverRepo) {
        this.driverRepo = driverRepo;
    }

    public List<Driver> findAll(){
        return driverRepo.findAll();
    }

    public void save(Driver driver){
        driverRepo.save(driver);
    }

    public void deleteById(long id){
        driverRepo.deleteById(id);
    }

    public Driver findById(long id){
        return driverRepo.findById(id).orElseThrow(() -> new RuntimeException("Driver not found: " + id));
    }


}
