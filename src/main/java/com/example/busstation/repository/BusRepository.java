package com.example.busstation.repository;
import com.example.busstation.model.Bus;
import org.springframework.stereotype.Repository;

@Repository
public class BusRepository extends InFileBusRepository<String, Bus>{
    public BusRepository(){
        super("src/main/resources/data/Bus.json", Bus[].class, Bus.class);
    }
}
