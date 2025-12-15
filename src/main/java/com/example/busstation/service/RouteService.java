package com.example.busstation.service;

import com.example.busstation.exception.DuplicateRouteException;
import com.example.busstation.exception.InvalidStationException;
import com.example.busstation.model.DutyAssignment;
import com.example.busstation.model.Route;
import com.example.busstation.repository.RouteRepository;
import jakarta.persistence.PersistenceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RouteService {
    private final RouteRepository routeRepo;

    @Autowired
    public RouteService(RouteRepository routeRepository) {
        this.routeRepo = routeRepository;
    }

    public void save(Route route){

        try {
            routeRepo.save(route);
        }catch (DataIntegrityViolationException e){
            throw new DuplicateRouteException("Route already exists!");
        }
    }

    public List<Route> findAll(){
        return routeRepo.findAll();
    }

    public Route findById(long id){
        return routeRepo.findById(id).orElseThrow(() -> new RuntimeException("Route not found: " + id));
    }

    public void deleteById(long id){
        routeRepo.deleteById(id);
    }

    public List<Route> findAllSorted(String sortField, String sortDirection) {

        if (sortField == null || sortField.isBlank()) {
            return routeRepo.findAll();
        }

        Sort sort = sortDirection != null && sortDirection.equalsIgnoreCase("desc")
                ? Sort.by(sortField).descending()
                : Sort.by(sortField).ascending();

        return routeRepo.findAll(sort);
    }

    public List<Route> findFilteredAndSorted(
            String origin,
            String destination,
            Float maxDistance,
            String sortField,
            String sortDirection
    ) {
        if (sortField == null || sortField.isBlank()) {
            sortField = "id";
        }

        if (origin != null && origin.isBlank()) origin = null;
        if (destination != null && destination.isBlank()) destination = null;

        Sort sort = "desc".equalsIgnoreCase(sortDirection)
                ? Sort.by(sortField).descending()
                : Sort.by(sortField).ascending();

        return routeRepo.findFiltered(origin, destination, maxDistance, sort);
    }

}
