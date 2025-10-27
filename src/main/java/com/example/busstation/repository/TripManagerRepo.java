package com.example.busstation.repository;

import com.example.busstation.model.TripManager;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Repository
public class TripManagerRepo implements AbstractRepository<TripManager> {

    private final List<TripManager> managerRepo = new ArrayList<>();

    public TripManagerRepo() {
        managerRepo.add(new TripManager("1", "Alex Marinescu", null, "EMP001"));
        managerRepo.add(new TripManager("2", "Irina Popescu", null, "EMP002"));
    }

    @Override
    public List<TripManager> findAll() {
        return managerRepo;
    }

    @Override
    public TripManager findById(String id) {
        for (TripManager manager : managerRepo) {
            if (manager.getId().equals(id)) {
                return manager;
            }
        }
        return null;
    }

    @Override
    public TripManager save(TripManager manager) {
        Objects.requireNonNull(manager, "manager is required");
        TripManager existing = findById(manager.getId());
        if (existing != null) {
            managerRepo.remove(existing); // update
        }
        managerRepo.add(manager);
        return manager;
    }

    @Override
    public boolean deleteById(String id) {
        for (TripManager manager : managerRepo) {
            if (manager.getId().equals(id)) {
                managerRepo.remove(manager);
                return true;
            }
        }
        return false;
    }
}
