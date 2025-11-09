package com.example.busstation.service;

import com.example.busstation.model.Passenger;
import com.example.busstation.repository.PassengerRepository;
import org.springframework.beans.factory.annotation.Autowired;
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

    public Passenger findById(String id)
    {
        return passengerRepo.findById(id);
    }

    public void save(Passenger passenger)
    {
        passengerRepo.save(passenger);
    }

    public boolean deleteById(String id)
    {
        return passengerRepo.deleteById(id);
    }
}
