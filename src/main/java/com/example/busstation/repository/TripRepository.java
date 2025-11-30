package com.example.busstation.repository;
import com.example.busstation.model.BusTrip;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TripRepository extends JpaRepository<BusTrip, Long> {

}
