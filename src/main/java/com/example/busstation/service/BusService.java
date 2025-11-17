package com.example.busstation.service;

import com.example.busstation.model.Bus;
import com.example.busstation.repository.Repository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BusService {

    private final Repository busRepo;

    @Autowired
    BusService(Repository busRepo){
        this.busRepo = busRepo;
    }

    public Bus save(Bus bus){
        return busRepo.save(bus);
    }

    public List<Bus> findAll(){
        return busRepo.findAll();
    }

    public Bus findById(String Id){
        return busRepo.findById(Id);
    }

    public boolean deleteById(String Id){
        return busRepo.deleteById(Id);
    }

    public Bus update(Bus updatedEntity, String id) { return busRepo.update(updatedEntity, id);}

}
