package com.example.busstation.service;

import com.example.busstation.model.Ticket;
import com.example.busstation.model.TripManager;
import com.example.busstation.repository.TripManagerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TripManagerService {
    private final TripManagerRepository tripManagerRepo;
    @Autowired
    public TripManagerService(TripManagerRepository tripManagerRepo) {
        this.tripManagerRepo = tripManagerRepo;
    }
    public List<TripManager> findAll(){
        return tripManagerRepo.findAll();
    }

    public void save(TripManager tripManager){
        tripManagerRepo.save(tripManager);
    }

    public void deleteById(long id){
        tripManagerRepo.deleteById(id);
    }

    public TripManager findById(long id){
        return tripManagerRepo.findById(id).orElseThrow(() -> new RuntimeException("Trip manager not found: " + id));
    }

    public List<TripManager> findAllSorted(String sortField, String sortDirection) {

        if (sortField == null || sortField.isBlank()) {
            return tripManagerRepo.findAll();
        }

        Sort sort = sortDirection != null && sortDirection.equalsIgnoreCase("desc")
                ? Sort.by(sortField).descending()
                : Sort.by(sortField).ascending();

        return tripManagerRepo.findAll(sort);
    }

    public List<TripManager> findFilteredAndSorted(
            String name,
            String email,
            String employeeCode,
            String sortField,
            String sortDirection
    ) {
        if (name != null && name.isBlank()) name = null;
        if (email != null && email.isBlank()) email = null;
        if (employeeCode != null && employeeCode.isBlank()) employeeCode = null;

        if (sortField == null || sortField.isBlank()) sortField = "id";

        // whitelist pentru sort
        switch (sortField) {
            case "id":
            case "name":
            case "email":
            case "employeeCode":
                break;
            default:
                sortField = "id";
        }

        Sort sort = "desc".equalsIgnoreCase(sortDirection)
                ? Sort.by(sortField).descending()
                : Sort.by(sortField).ascending();

        return tripManagerRepo.findFiltered(name, email, employeeCode, sort);
    }

}
