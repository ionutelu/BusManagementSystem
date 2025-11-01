package com.example.busstation.repository;
import com.example.busstation.model.Route;
import org.springframework.stereotype.Repository;

@Repository
public class RouteRepo extends InMemoryRepository<String, Route> {

}
