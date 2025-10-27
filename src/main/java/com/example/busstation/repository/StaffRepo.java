package com.example.busstation.repository;

import com.example.busstation.model.Staff;
import com.example.busstation.model.Driver;
import com.example.busstation.repository.AbstractRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class StaffRepo implements AbstractRepository<Staff> {

    private final List<Staff> staffRepo = new ArrayList<>();

    public StaffRepo() {
        staffRepo.add(new Driver("1", "Ion Popescu", 5));
        staffRepo.add(new Driver("2", "Maria Ionescu", 8));
    }

    @Override
    public List<Staff> findAll() {
        return staffRepo;
    }

    @Override
    public Staff findById(int id) {
        String strId = String.valueOf(id);
        for (Staff staff : staffRepo) {
            if (staff.getId().equals(strId)) {
                return staff;
            }
        }
        return null;
    }

    @Override
    public Staff save(Staff staff) {
        staffRepo.add(staff);
        return staff;
    }
}
