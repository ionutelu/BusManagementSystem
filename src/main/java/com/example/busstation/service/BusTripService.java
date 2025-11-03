package com.example.busstation.service;

import com.example.busstation.model.BusTrip;
import com.example.busstation.repository.BusTripRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BusTripService {

    private final BusTripRepo busTripRepo;
    @Autowired
    BusTripService(BusTripRepo busTripRepo) {
        this.busTripRepo = busTripRepo;
    }

    public List<BusTrip> findAll() {
        return busTripRepo.findAll();
    }

    public BusTrip findById(String id) {
        return busTripRepo.findById(id);
    }

    public void save(BusTrip busTrip) {
        busTripRepo.save(busTrip);
    }

    public boolean deleteById(String id) {
        return busTripRepo.deleteById(id);
    }
}
