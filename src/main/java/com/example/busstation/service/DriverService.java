package com.example.busstation.service;

import com.example.busstation.model.BusStation;
import com.example.busstation.model.Driver;
import com.example.busstation.repository.DriverRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
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

    public List<Driver> findAllSorted(String sortField, String sortDirection) {

        if (sortField == null || sortField.isBlank()) {
            return driverRepo.findAll();
        }

        Sort sort = sortDirection != null && sortDirection.equalsIgnoreCase("desc")
                ? Sort.by(sortField).descending()
                : Sort.by(sortField).ascending();

        return driverRepo.findAll(sort);
    }


    public List<Driver> findFilteredAndSorted(
            String name,
            Integer minExperience,
            String sortField,
            String sortDirection
    ) {

        if (sortField == null || sortField.isBlank()) {
            sortField = "id";
        }

        Sort sort = "desc".equalsIgnoreCase(sortDirection)
                ? Sort.by(sortField).descending()
                : Sort.by(sortField).ascending();

        return driverRepo.findFiltered(name, minExperience, sort);
    }

}
