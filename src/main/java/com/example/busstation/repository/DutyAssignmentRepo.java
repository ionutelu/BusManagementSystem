package com.example.busstation.repository;

import com.example.busstation.model.DriverRole;
import com.example.busstation.model.DutyAssignment;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Repository
public class DutyAssignmentRepo implements AbstractRepository<DutyAssignment> {

    private final List<DutyAssignment> assignmentRepo = new ArrayList<>();

    public DutyAssignmentRepo() {
        assignmentRepo.add(new DutyAssignment("1", "1", "1", DriverRole.PRIMARY_DRIVER));
        assignmentRepo.add(new DutyAssignment("2", "1", "2", DriverRole.RESERVE_DRIVER));
    }

    @Override
    public List<DutyAssignment> findAll() {
        return assignmentRepo;
    }

    @Override
    public DutyAssignment findById(String id) {
        for (DutyAssignment assignment : assignmentRepo) {
            if (assignment.getId().equals(id)) {
                return assignment;
            }
        }
        return null;
    }

    @Override
    public DutyAssignment save(DutyAssignment assignment) {
        Objects.requireNonNull(assignment, "assignment is required");
        DutyAssignment existing = findById(assignment.getId());
        if (existing != null) {
            assignmentRepo.remove(existing);
        }
        assignmentRepo.add(assignment);
        return assignment;
    }

    @Override
    public boolean deleteById(String id) {
        for (DutyAssignment assignment : assignmentRepo) {
            if (assignment.getId().equals(id)) {
                assignmentRepo.remove(assignment);
                return true;
            }
        }
        return false;
    }
}
