package com.example.busstation.repository;
import com.example.busstation.model.Driver;
import org.springframework.stereotype.Repository;

@Repository
public class DriverRepository extends InFileBusRepository<String, Driver> {
    public DriverRepository(){
        super("src/main/resources/data/Driver.json", Driver[].class);
    }
}
