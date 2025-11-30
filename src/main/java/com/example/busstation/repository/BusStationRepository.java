package com.example.busstation.repository;
import com.example.busstation.model.BusStation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BusStationRepository extends JpaRepository<BusStation, Long> {

}
