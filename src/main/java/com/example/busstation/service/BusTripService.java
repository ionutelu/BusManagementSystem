package com.example.busstation.service;

import com.example.busstation.exception.BusNotFoundForTripException;
import com.example.busstation.exception.BusTripNotFoundException;
import com.example.busstation.exception.RouteNotFoundForTripException;
import com.example.busstation.model.BusStation;
import com.example.busstation.model.BusTrip;
import com.example.busstation.model.BusTripStatus;
import com.example.busstation.model.Route;
import com.example.busstation.repository.BusTripRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;

@Service
public class BusTripService {

    private final BusTripRepository busTripRepo;
    @Autowired
    BusTripService(BusTripRepository busTripRepo) {
        this.busTripRepo = busTripRepo;
    }

    public List<BusTrip> findAll() {
        return busTripRepo.findAll();
    }

    public BusTrip findById(long id) {
        return busTripRepo.findById(id).orElseThrow(() -> new BusTripNotFoundException("BusTrip not found"));
    }

    public BusTrip save(BusTrip busTrip) {
        // AC2: eager FK validation before hitting the database
        if (busTrip.getRoute() == null) {
            throw new RouteNotFoundForTripException("Route is required for a bus trip");
        }
        if (busTrip.getBus() == null) {
            throw new BusNotFoundForTripException("Bus is required for a bus trip");
        }
        try {
            return busTripRepo.save(busTrip);
        } catch (DataIntegrityViolationException e) {
            // AC3: specific catch — FK/unique constraint violation, not a catch-all
            throw new RouteNotFoundForTripException("Save failed due to data integrity violation: " + e.getMessage());
        }
        // All other RuntimeExceptions (e.g. NPE) now propagate naturally — AC1
    }

    public void deleteById(long id) {
        busTripRepo.deleteById(id);
    }

    public List<BusTrip> findAllSorted(String sortField, String sortDirection) {

        if (sortField == null || sortField.isBlank()) {
            return busTripRepo.findAll();
        }

        Sort sort = sortDirection != null && sortDirection.equalsIgnoreCase("desc")
                ? Sort.by(sortField).descending()
                : Sort.by(sortField).ascending();

        return busTripRepo.findAll(sort);
    }


    public List<BusTrip> findFilteredAndSorted(
            String route,
            BusTripStatus status,
            String sortField,
            String sortDirection
    ) {

        if (sortField == null || sortField.isBlank()) {
            sortField = "id";
        }

        Sort sort = "desc".equalsIgnoreCase(sortDirection)
                ? Sort.by(sortField).descending()
                : Sort.by(sortField).ascending();

        return busTripRepo.findFiltered(route, status, sort);
    }

    public Page<BusTrip> findFilteredAndSortedPaged(
            String route,
            BusTripStatus status,
            String sortField,
            String sortDirection,
            int page,
            int size
    ) {
        if (sortField == null || sortField.isBlank()) sortField = "id";
        Sort sort = "desc".equalsIgnoreCase(sortDirection)
                ? Sort.by(sortField).descending()
                : Sort.by(sortField).ascending();
        return busTripRepo.findFilteredPage(route, status, PageRequest.of(page, size, sort));
    }
}
