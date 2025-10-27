package com.example.busstation.repository;

import com.example.busstation.model.BusTrip;
import com.example.busstation.repository.AbstractRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Repository
public class BusTripRepo implements AbstractRepository<BusTrip> {

    private final List<BusTrip> tripRepo = new ArrayList<>();

    public BusTripRepo() {
        tripRepo.add(new BusTrip(
                "1",
                "Route1",
                "1", // busId
                LocalDateTime.now().plusDays(1),
                null,
                null,
                BusTrip.BusTripStatus.PLANNED
        ));

        tripRepo.add(new BusTrip(
                "2",
                "Route2",
                "2", // busId
                LocalDateTime.now().plusDays(2),
                null,
                null,
                BusTrip.BusTripStatus.PLANNED
        ));
    }

    @Override
    public List<BusTrip> findAll() {
        return tripRepo;
    }

    @Override
    public BusTrip findById(int id) {
        String strId = String.valueOf(id);
        for (BusTrip trip : tripRepo) {
            if (trip.getId().equals(strId)) {
                return trip;
            }
        }
        return null;
    }

    @Override
    public BusTrip save(BusTrip trip) {
        tripRepo.add(trip);
        return trip;
    }
}
