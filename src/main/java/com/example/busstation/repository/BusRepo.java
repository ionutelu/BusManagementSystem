package com.example.busstation.repository;

import com.example.busstation.model.Bus;
import com.example.busstation.repository.AbstractRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class BusRepo implements AbstractRepository<Bus> {

    private final List<Bus> busRepo = new ArrayList<>();


    public BusRepo() {
        busRepo.add(new Bus("1", "B123ABC", 50));
        busRepo.add(new Bus("2", "B456DEF", 40));
    }

    @Override
    public List<Bus> findAll() {
        return busRepo;
    }

    @Override
    public Bus findById(int id) {

        String strId = String.valueOf(id);
        for (Bus bus : busRepo) {
            if (bus.getId().equals(strId)) {
                return bus;
            }
        }
        return null;
    }

    @Override
    public Bus save(Bus bus) {
        busRepo.add(bus);
        return bus;
    }
}
