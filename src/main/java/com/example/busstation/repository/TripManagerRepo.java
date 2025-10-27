package com.example.busstation.repository;

import com.example.busstation.model.TripManager;
import com.example.busstation.repository.AbstractRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

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
    public TripManager findById(int id) {
        String strId = String.valueOf(id);
        for (TripManager manager : managerRepo) {
            if (manager.getId().equals(strId)) {
                return manager;
            }
        }
        return null;
    }

    @Override
    public TripManager save(TripManager manager) {
        managerRepo.add(manager);
        return manager;
    }
}
