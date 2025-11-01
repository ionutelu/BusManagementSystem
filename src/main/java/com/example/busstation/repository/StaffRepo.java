package com.example.busstation.repository;
import com.example.busstation.model.Staff;
import org.springframework.stereotype.Repository;

@Repository
public class StaffRepo extends InMemoryRepository<String, Staff> {

}
