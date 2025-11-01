package com.example.busstation.repository;
import com.example.busstation.model.BusTrip;
import org.springframework.stereotype.Repository;

@Repository
public class BusTripRepo extends InMemoryRepository<String, BusTrip>{

}
