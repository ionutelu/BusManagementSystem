package com.example.busstation.repository;
import com.example.busstation.model.Route;
import org.springframework.stereotype.Repository;

@Repository
public class RouteRepository extends InFileRepository<String, Route> {
    public RouteRepository(){
        super("src/main/resources/data/Route.json", Route[].class);
    }

}
