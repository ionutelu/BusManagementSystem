package com.example.busstation.repository;
import com.example.busstation.model.BusStation;
import org.springframework.stereotype.Repository;

@Repository
public class BusStationRepository extends InFileBusRepository<String, BusStation>{
    public BusStationRepository(){
        super("src/main/resources/data/BusStation.json", BusStation[].class);
    }
}
