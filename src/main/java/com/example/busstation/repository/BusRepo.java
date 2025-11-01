package com.example.busstation.repository;
import com.example.busstation.model.Bus;
import org.springframework.stereotype.Repository;

@Repository
public class BusRepo extends InMemoryRepository<String, Bus>{

}
