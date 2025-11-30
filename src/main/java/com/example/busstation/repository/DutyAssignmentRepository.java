package com.example.busstation.repository;
import com.example.busstation.model.DutyAssignment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DutyAssignmentRepository extends JpaRepository<DutyAssignment, Long> {

}
