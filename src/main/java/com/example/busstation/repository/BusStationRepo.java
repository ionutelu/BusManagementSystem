package com.example.busstation.repository;

import com.example.busstation.model.BusStation;
import com.example.busstation.repository.AbstractRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Repository
public class BusStationRepo implements AbstractRepository<BusStation> {

    private final List<BusStation> stationRepo = new ArrayList<>();

    public BusStationRepo() {
        stationRepo.add(new BusStation("1", "Central Station", "Bucharest", null));
        stationRepo.add(new BusStation("2", "North Station", "Cluj", null));
    }

    @Override
    public List<BusStation> findAll() {
        return stationRepo;
    }

    @Override
    public BusStation findById(String id) {
        String strId = String.valueOf(id);
        for (BusStation station : stationRepo) {
            if (station.getId().equals(strId)) {
                return station;
            }
        }
        return null;
    }

    @Override
    public BusStation save(BusStation station) {
        Objects.requireNonNull(station, "bus is required");
        BusStation existing = findById(station.getId());
        if (existing != null) {
            stationRepo.remove(existing);
        }
        stationRepo.add(station);
        return station;
    }

    @Override
    public boolean deleteById(String id) {
        for (BusStation b : stationRepo) {
            if (b.getId().equals(id)) {
                stationRepo.remove(b);
                return true;
            }
        }
        return false;
    }
}
