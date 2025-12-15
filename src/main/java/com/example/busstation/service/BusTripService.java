package com.example.busstation.service;

import com.example.busstation.exception.BusTripNotFoundException;
import com.example.busstation.exception.RouteNotFoundForTripException;
import com.example.busstation.model.BusStation;
import com.example.busstation.model.BusTrip;
import com.example.busstation.model.Route;
import com.example.busstation.repository.TripRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Comparator;
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
        return busTripRepo.findById(id).orElseThrow(() -> new BusTripNotFoundException("BusTrip not found"));
    }

    public BusTrip save(BusTrip busTrip) {

        try{
            return busTripRepo.save(busTrip);
        }catch (RuntimeException e){
            throw new RouteNotFoundForTripException("Trip not found");
        }

    }

    public void deleteById(long id) {
        busTripRepo.deleteById(id);
    }




}
