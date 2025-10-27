package com.example.busstation.repository;

import com.example.busstation.model.DutyAssignment;
import com.example.busstation.repository.AbstractRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class DutyAssignmentRepo implements AbstractRepository<DutyAssignment> {

    private final List<DutyAssignment> assignmentRepo = new ArrayList<>();

    public DutyAssignmentRepo() {
        assignmentRepo.add(new DutyAssignment("1", "1", "1", DutyAssignment.DriverRole.PRIMARY_DRIVER));
        assignmentRepo.add(new DutyAssignment("2", "1", "2", DutyAssignment.DriverRole.RESERVE_DRIVER));
    }

    @Override
    public List<DutyAssignment> findAll() {
        return assignmentRepo;
    }

    @Override
    public DutyAssignment findById(int id) {
        String strId = String.valueOf(id);
        for (DutyAssignment assignment : assignmentRepo) {
            if (assignment.getId().equals(strId)) {
                return assignment;
            }
        }
        return null;
    }

    @Override
    public DutyAssignment save(DutyAssignment assignment) {
        assignmentRepo.add(assignment);
        return assignment;
    }
}
