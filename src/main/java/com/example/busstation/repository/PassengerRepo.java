package com.example.busstation.repository;

import com.example.busstation.model.Passenger;
import com.example.busstation.repository.AbstractRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class PassengerRepo implements AbstractRepository<Passenger> {

    private final List<Passenger> passengerRepo = new ArrayList<>();

    public PassengerRepo() {
        passengerRepo.add(new Passenger("1", "Andrei Popa", "RON", null));
        passengerRepo.add(new Passenger("2", "Elena Ionescu", "EUR", null));
    }

    @Override
    public List<Passenger> findAll() {
        return passengerRepo;
    }

    @Override
    public Passenger findById(int id) {
        String strId = String.valueOf(id);
        for (Passenger passenger : passengerRepo) {
            if (passenger.getId().equals(strId)) {
                return passenger;
            }
        }
        return null;
    }

    @Override
    public Passenger save(Passenger passenger) {
        passengerRepo.add(passenger);
        return passenger;
    }
}
