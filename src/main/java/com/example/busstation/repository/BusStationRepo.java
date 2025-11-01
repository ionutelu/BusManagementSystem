package com.example.busstation.repository;
import com.example.busstation.model.BusStation;
import org.springframework.stereotype.Repository;

@Repository
public class BusStationRepo extends InMemoryRepository<String, BusStation>{

}
