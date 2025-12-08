package com.example.busstation.service;

import com.example.busstation.exception.InvalidStationException;
import com.example.busstation.model.DutyAssignment;
import com.example.busstation.model.Route;
import com.example.busstation.repository.RouteRepository;
import jakarta.persistence.PersistenceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.InvalidDataAccessApiUsageException;
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
        }catch (PersistenceException ex){
            throw new InvalidStationException("Invalid station");
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

}
