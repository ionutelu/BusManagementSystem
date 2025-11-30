package com.example.busstation.service;

import com.example.busstation.model.Driver;
import com.example.busstation.model.DutyAssignment;
import com.example.busstation.repository.DutyAssignmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DutyAssignmentService {


    private final DutyAssignmentRepository dutyAssignmentRepo;
    @Autowired
    public DutyAssignmentService(DutyAssignmentRepository dutyAssignmentRepo) {
        this.dutyAssignmentRepo = dutyAssignmentRepo;
    }

    public void save(DutyAssignment dutyAssignment){
        dutyAssignmentRepo.save(dutyAssignment);
    }

    public List<DutyAssignment> findAll(){
        return dutyAssignmentRepo.findAll();
    }

    public DutyAssignment findById(long id){
        return dutyAssignmentRepo.findById(id).orElseThrow(() -> new RuntimeException("Duty Assignment not found: " + id));
    }

    public void deleteById(long id){
        dutyAssignmentRepo.deleteById(id);
    }
}
