package com.example.busstation.service;

import com.example.busstation.model.Staff;
import com.example.busstation.repository.StaffRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StaffService {
    private final StaffRepo staffRepo;
    @Autowired
    public StaffService(StaffRepo staffRepo){
        this.staffRepo = staffRepo;
    }

    public void save(Staff staff){
        staffRepo.save(staff);
    }

    public List<Staff> findAll(){
        return staffRepo.findAll();
    }

    public Staff findById(String id){
        return staffRepo.findById(id);
    }

    public boolean  deleteById(String id){
        return staffRepo.deleteById(id);
    }
}
