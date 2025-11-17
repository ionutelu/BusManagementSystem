package com.example.busstation.repository;
import com.example.busstation.model.Passenger;
import org.springframework.stereotype.Repository;

@Repository
public class PassengerRepository extends InFileRepository<String, Passenger> {
    PassengerRepository(){
        super("src/main/resources/data/Passenger.json", Passenger[].class);
    }

}
