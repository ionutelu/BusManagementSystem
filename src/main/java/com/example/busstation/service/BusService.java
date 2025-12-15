package com.example.busstation.service;

import com.example.busstation.exception.BusCapacityInvalid;
import com.example.busstation.exception.BusNotFoundException;
import com.example.busstation.exception.DuplicateRegistrationException;
import com.example.busstation.exception.DuplicateVinException;
import com.example.busstation.model.Bus;
import com.example.busstation.model.BusStatus;
import com.example.busstation.repository.BusRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Sort;
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

    public List<Bus> findAllSorted(String sortField, String sortDirection) {

        if (sortField == null || sortField.isBlank()) {
            return busRepo.findAll();
        }

        Sort sort = sortDirection != null && sortDirection.equalsIgnoreCase("desc")
                ? Sort.by(sortField).descending()
                : Sort.by(sortField).ascending();

        return busRepo.findAll(sort);
    }

    public List<Bus> findAllFilteredAndSorted(
            String vin,
            BusStatus status,
            Integer minCapacity,
            String sortField,
            String sortDirection
    ) {

        // fallback sort
        if (sortField == null || sortField.isBlank()) {
            sortField = "id";
        }

        Sort sort = "desc".equalsIgnoreCase(sortDirection)
                ? Sort.by(sortField).descending()
                : Sort.by(sortField).ascending();

        return busRepo.findFiltered(
                vin,
                status,
                minCapacity,
                sort
        );
    }

}
