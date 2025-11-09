package com.example.busstation.service;

import com.example.busstation.model.Route;
import com.example.busstation.repository.RouteRepository;
import org.springframework.beans.factory.annotation.Autowired;
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
        routeRepo.save(route);
    }

    public List<Route> findAll(){
        return routeRepo.findAll();
    }

    public Route findById(String id){
        return routeRepo.findById(id);
    }

    public boolean deleteById(String id){
        return routeRepo.deleteById(id);
    }
}
