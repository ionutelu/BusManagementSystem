package com.example.busstation.service;

import com.example.busstation.exception.DuplicateBusStationException;
import com.example.busstation.exception.EmptyFieldBusStationException;
import com.example.busstation.exception.EmptyFieldException;
import com.example.busstation.model.BusStation;
import com.example.busstation.repository.BusStationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Sort;
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

        if (busStation.getName() == null || busStation.getName().isBlank()) {
            throw new EmptyFieldBusStationException("Name cannot be empty.");
        }

        if (busStation.getCity() == null || busStation.getCity().isBlank()) {
            throw new EmptyFieldBusStationException("City cannot be empty.");
        }

        try {
            return busStationRepo.save(busStation);
        } catch (DataIntegrityViolationException e) {
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

    public List<BusStation> findAllSorted(String sortField, String sortDirection) {

        if (sortField == null || sortField.isBlank()) {
            return busStationRepo.findAll();
        }

        Sort sort = "desc".equalsIgnoreCase(sortDirection)
                ? Sort.by(sortField).descending()
                : Sort.by(sortField).ascending();

        return busStationRepo.findAll(sort);
    }
}
