package com.example.busstation.service;

import com.example.busstation.exception.BusCapacityInvalid;
import com.example.busstation.exception.BusNotFoundException;
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

        // ---------- VALIDARE CAPACITY ----------
        if (bus.getCapacity() < 20 || bus.getCapacity() > 80) {
            throw new BusCapacityInvalid("Capacity must be between 20 and 80 seats");
        }

        try {
            return busRepo.save(bus);

        } catch (DataIntegrityViolationException e) {

            String message = e.getMostSpecificCause() != null
                    ? e.getMostSpecificCause().getMessage()
                    : e.getMessage();

            if (message.contains("uq_vin")) {
                throw new DuplicateVinException("VIN already exists");
            }

            if (message.contains("uq_registration_number")) {
                throw new DuplicateRegistrationException("Registration number already exists");
            }

            throw new RuntimeException("Data integrity error: " + message);
        }
    }

    public List<Bus> findAll(){
        return busRepo.findAll();
    }

    public Bus findById(Long id) {
        return busRepo.findById(id)
                .orElseThrow(() ->
                        new BusNotFoundException("Bus not found with id: " + id));
    }

    public void deleteById(Long id){
        if (!busRepo.existsById(id)) {
            throw new BusNotFoundException("Cannot delete. Bus not found with id: " + id);
        }

        busRepo.deleteById(id);
    }
}
