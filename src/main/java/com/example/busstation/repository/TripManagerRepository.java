package com.example.busstation.repository;
import com.example.busstation.model.TripManager;
import org.springframework.stereotype.Repository;

@Repository
public class TripManagerRepository extends InMemoryRepository<String, TripManager> {

}
