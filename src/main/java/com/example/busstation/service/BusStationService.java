package com.example.busstation.service;

import com.example.busstation.model.BusStation;
import com.example.busstation.repository.BusStationRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class BusStationService {
    private final BusStationRepo busStationRepo;

    @Autowired
    BusStationService(BusStationRepo busStationRepo){
        this.busStationRepo = busStationRepo;
    }

    public BusStation save(BusStation busStation){
        return busStationRepo.save(busStation);
    }

    public List<BusStation> findAll(){
        return busStationRepo.findAll();
    }

    public BusStation findById(String Id){
        return busStationRepo.findById(Id);
    }

    public boolean deleteById(String Id){
        return busStationRepo.deleteById(Id);
    }
}
