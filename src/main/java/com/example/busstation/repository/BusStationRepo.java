package com.example.busstation.repository;

import com.example.busstation.model.BusStation;
import com.example.busstation.repository.AbstractRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

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
    public BusStation findById(int id) {
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
        stationRepo.add(station);
        return station;
    }
}
