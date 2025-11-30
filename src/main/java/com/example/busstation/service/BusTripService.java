package com.example.busstation.service;

import com.example.busstation.model.BusStation;
import com.example.busstation.model.BusTrip;
import com.example.busstation.repository.TripRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BusTripService {

    private final TripRepository busTripRepo;
    @Autowired
    BusTripService(TripRepository busTripRepo) {
        this.busTripRepo = busTripRepo;
    }

    public List<BusTrip> findAll() {
        return busTripRepo.findAll();
    }

    public BusTrip findById(long id) {
        return busTripRepo.findById(id).orElseThrow(() -> new RuntimeException("Bus trip not found: " + id));
    }

    public void save(BusTrip busTrip) {
        busTripRepo.save(busTrip);
    }

    public void deleteById(long id) {
        busTripRepo.deleteById(id);
    }


}
