package com.example.busstation.service;

import com.example.busstation.model.Ticket;
import com.example.busstation.model.TripManager;
import com.example.busstation.repository.TripManagerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TripManagerService {
    private final TripManagerRepository tripManagerRepo;
    @Autowired
    public TripManagerService(TripManagerRepository tripManagerRepo) {
        this.tripManagerRepo = tripManagerRepo;
    }
    public List<TripManager> findAll(){
        return tripManagerRepo.findAll();
    }

    public void save(TripManager tripManager){
        tripManagerRepo.save(tripManager);
    }

    public void deleteById(long id){
        tripManagerRepo.deleteById(id);
    }

    public TripManager findById(long id){
        return tripManagerRepo.findById(id).orElseThrow(() -> new RuntimeException("Trip manager not found: " + id));
    }

}
