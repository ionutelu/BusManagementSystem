package com.example.busstation.repository;
import com.example.busstation.model.Bus;

@org.springframework.stereotype.Repository
public class Repository extends InFileRepository<String, Bus> {
    public Repository(){
        super("src/main/resources/data/bus.json", Bus[].class);
    }
}
