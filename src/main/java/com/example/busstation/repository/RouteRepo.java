package com.example.busstation.repository;

import com.example.busstation.model.BusStation;
import com.example.busstation.model.Route;
import com.example.busstation.model.BusTrip;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Repository
public class RouteRepo implements AbstractRepository<Route> {

    private final List<Route> routeRepo = new ArrayList<>();

    public RouteRepo() {

        BusStation central = new BusStation("1", "Central Station", "Bucharest", null);
        BusStation north = new BusStation("2", "North Station", "Cluj", null);

        BusTrip trip1 = new BusTrip("1", "Route1", "1", null, null, null, BusTrip.BusTripStatus.PLANNED);
        BusTrip trip2 = new BusTrip("2", "Route1", "2", null, null, null, BusTrip.BusTripStatus.PLANNED);

        List<BusTrip> trips = new ArrayList<>();
        trips.add(trip1);
        trips.add(trip2);

        routeRepo.add(new Route("1", central, north, 450.5f, trips));
    }

    @Override
    public List<Route> findAll() {
        return routeRepo;
    }

    @Override
    public Route findById(String id) {
        for (Route route : routeRepo) {
            if (route.getId().equals(id)) {
                return route;
            }
        }
        return null;
    }

    @Override
    public Route save(Route route) {
        Objects.requireNonNull(route, "route is required");
        Route existing = findById(route.getId());
        if (existing != null) {
            routeRepo.remove(existing);
        }
        routeRepo.add(route);
        return route;
    }

    @Override
    public boolean deleteById(String id) {
        for (Route route : routeRepo) {
            if (route.getId().equals(id)) {
                routeRepo.remove(route);
                return true;
            }
        }
        return false;
    }
}
