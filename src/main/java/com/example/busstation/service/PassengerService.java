package com.example.busstation.service;

import com.example.busstation.exception.PassengerNotFoundException;
import com.example.busstation.model.Bus;
import com.example.busstation.model.Passenger;
import com.example.busstation.repository.PassengerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PassengerService {

    private final PassengerRepository passengerRepo;

    @Autowired
    public PassengerService(PassengerRepository passengerRepo) {
        this.passengerRepo = passengerRepo;
    }

    public List<Passenger> findAll(){
        return passengerRepo.findAll();
    }

    public List<Passenger> findAllSorted(String sortField, String sortDirection) {

        if (sortField == null || sortField.isBlank()) {
            return passengerRepo.findAll();
        }

        Sort sort = sortDirection != null && sortDirection.equalsIgnoreCase("desc")
                ? Sort.by(sortField).descending()
                : Sort.by(sortField).ascending();

        return passengerRepo.findAll(sort);
    }

    public Passenger findById(long id) {
        return passengerRepo.findById(id).orElseThrow(() -> new PassengerNotFoundException("Passenger not found!"));
    }

    public Passenger save(Passenger passenger) {

        return passengerRepo.save(passenger);

    }

    public void deleteById(long id) {
        passengerRepo.deleteById(id);
    }

}
