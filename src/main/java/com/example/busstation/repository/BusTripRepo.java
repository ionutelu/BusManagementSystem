package com.example.busstation.repository;

import com.example.busstation.model.BusTrip;
import com.example.busstation.model.BusTripStatus;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

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
                BusTripStatus.PLANNED
        ));

        tripRepo.add(new BusTrip(
                "2",
                "Route2",
                "2", // busId
                LocalDateTime.now().plusDays(2),
                null,
                null,
                BusTripStatus.ACTIVE
        ));
    }

    @Override
    public List<BusTrip> findAll() {
        return tripRepo;
    }

    @Override
    public BusTrip findById(String id) {
        for (BusTrip trip : tripRepo) {
            if (trip.getId().equals(id)) {
                return trip;
            }
        }
        return null;
    }

    @Override
    public BusTrip save(BusTrip trip) {
        Objects.requireNonNull(trip, "trip is required");
        BusTrip existing = findById(trip.getId());
        if (existing != null) {
            tripRepo.remove(existing);
        }
        tripRepo.add(trip);
        return trip;
    }

    @Override
    public boolean deleteById(String id) {
        for (BusTrip trip : tripRepo) {
            if (trip.getId().equals(id)) {
                tripRepo.remove(trip);
                return true;
            }
        }
        return false;
    }
}
