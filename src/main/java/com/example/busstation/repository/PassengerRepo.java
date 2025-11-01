package com.example.busstation.repository;
import com.example.busstation.model.Passenger;
import org.springframework.stereotype.Repository;

@Repository
public class PassengerRepo extends InMemoryRepository<String, Passenger> {

}
