package com.example.busstation.service;

import com.example.busstation.exception.DuplicateRegistrationException;
import com.example.busstation.exception.DuplicateVinException;
import com.example.busstation.model.Bus;
import com.example.busstation.repository.BusRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BusService {

    private final BusRepository busRepo;

    @Autowired
    BusService(BusRepository busRepo){
        this.busRepo = busRepo;
    }

    public Bus save(Bus bus) {

        try {
            return busRepo.save(bus);

        } catch (DataIntegrityViolationException e) {

            String message = e.getMostSpecificCause().getMessage();


            if (message.contains("UKeqck0ex424pjnawvgifj2k5fm")) {
                throw new DuplicateRegistrationException("Registration number already exists");
            }

            if (message.contains("UK_vin_index_name_here")) {
                throw new DuplicateVinException("VIN already exists");
            }

            throw new RuntimeException("Data integrity error");
        }
    }

//    public Bus save(Bus bus){
//        return busRepo.save(bus);
//    }

    public List<Bus> findAll(){
        return busRepo.findAll();
    }

    public Bus findById(Long id) {
        return busRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Bus not found with id: " + id));
    }

    public void deleteById(Long Id){
         busRepo.deleteById(Id);
    }


}
