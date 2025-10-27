package com.example.busstation.repository;

import com.example.busstation.model.Staff;
import com.example.busstation.model.Driver;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

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
    public Staff findById(String id) {
        for (Staff staff : staffRepo) {
            if (staff.getId().equals(id)) {
                return staff;
            }
        }
        return null;
    }

    @Override
    public Staff save(Staff staff) {
        Objects.requireNonNull(staff, "staff is required");
        Staff existing = findById(staff.getId());
        if (existing != null) {
            staffRepo.remove(existing);
        }
        staffRepo.add(staff);
        return staff;
    }

    @Override
    public boolean deleteById(String id) {
        for (Staff staff : staffRepo) {
            if (staff.getId().equals(id)) {
                staffRepo.remove(staff);
                return true;
            }
        }
        return false;
    }
}
