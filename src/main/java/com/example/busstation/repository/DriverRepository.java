package com.example.busstation.repository;
import com.example.busstation.model.Driver;
import org.springframework.stereotype.Repository;

@Repository
public class DriverRepository extends InMemoryRepository<String, Driver> {

}
