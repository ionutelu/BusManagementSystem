package com.example.busstation.service;

import com.example.busstation.exception.DuplicateBusStationException;
import com.example.busstation.model.BusStation;
import com.example.busstation.repository.BusStationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class BusStationService {
    private final BusStationRepository busStationRepo;

    @Autowired
    BusStationService(BusStationRepository busStationRepo){
        this.busStationRepo = busStationRepo;
    }

    public BusStation save(BusStation busStation){

        try {
            return busStationRepo.save(busStation);
        }catch (DataIntegrityViolationException e){
            throw new DuplicateBusStationException("Station with the same name already exists in this City");
        }
    }

    public List<BusStation> findAll(){
        return busStationRepo.findAll();
    }

    public BusStation findById(long id){
        return busStationRepo.findById(id).orElseThrow(() -> new RuntimeException("Bus station not found: " + id));
    }

    public void deleteById(long id){
        busStationRepo.deleteById(id);
    }

}
