package com.example.busstation.repository;
import com.example.busstation.model.BusStation;
import org.springframework.stereotype.Repository;

@Repository
public class StationRepository extends InFileRepository<String, BusStation> {
    public StationRepository(){
        super("src/main/resources/data/BusStation.json", BusStation[].class);
    }
}
