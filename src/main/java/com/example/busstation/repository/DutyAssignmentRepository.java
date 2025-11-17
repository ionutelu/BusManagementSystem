package com.example.busstation.repository;
import com.example.busstation.model.DutyAssignment;
import org.springframework.stereotype.Repository;

@Repository
public class DutyAssignmentRepository extends InFileRepository<String, DutyAssignment> {
    DutyAssignmentRepository(){
        super("src/main/resources/data/DutyAssignment.json", DutyAssignment[].class);
    }
}
