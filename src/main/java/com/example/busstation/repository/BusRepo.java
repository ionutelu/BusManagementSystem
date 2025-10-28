package com.example.busstation.repository;

import com.example.busstation.model.Bus;
import com.example.busstation.repository.AbstractRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Repository
public class BusRepo implements AbstractRepository<Bus> {

    private final List<Bus> busRepo = new ArrayList<>();


    public BusRepo() {
        busRepo.add(new Bus("1", "B123ABC", 50, "BALALALA"));
        busRepo.add(new Bus("2", "B456DEF", 40, "ABBAABBA"));
    }

    @Override
    public List<Bus> findAll() {
        return busRepo;
    }

    @Override
    public Bus findById(String id) {
        String strId = String.valueOf(id);
        for (Bus bus : busRepo) {
            if (bus.getId().equals(strId)) {
                return bus;
            }
        }
        return null;
    }

    public Bus save(Bus bus) {
        Objects.requireNonNull(bus, "bus is required");
        Bus existing = findById(bus.getId());
        if (existing != null) {
            busRepo.remove(existing);
        }
        busRepo.add(bus);
        return bus;
    }

    public boolean deleteById(String id) {
        for (Bus b : busRepo) {
            if (b.getId().equals(id)) {
                busRepo.remove(b);
                return true;
            }
        }
        return false;
    }
}
