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

    public DutyAssignment findById(String id){
        return dutyAssignmentRepo.findById(id);
    }

    public boolean deleteById(String id){
        return dutyAssignmentRepo.deleteById(id);
    }

    public DutyAssignment update(DutyAssignment updatedEntity, String id) { return dutyAssignmentRepo.update(updatedEntity, id);}
}
