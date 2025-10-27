package com.example.busstation.repository;

import com.example.busstation.model.Passenger;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

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
    public Passenger findById(String id) {
        for (Passenger passenger : passengerRepo) {
            if (passenger.getId().equals(id)) {
                return passenger;
            }
        }
        return null;
    }

    @Override
    public Passenger save(Passenger passenger) {
        Objects.requireNonNull(passenger, "passenger is required");
        Passenger existing = findById(passenger.getId());
        if (existing != null) {
            passengerRepo.remove(existing);
        }
        passengerRepo.add(passenger);
        return passenger;
    }

    @Override
    public boolean deleteById(String id) {
        for (Passenger passenger : passengerRepo) {
            if (passenger.getId().equals(id)) {
                passengerRepo.remove(passenger);
                return true;
            }
        }
        return false;
    }
}
