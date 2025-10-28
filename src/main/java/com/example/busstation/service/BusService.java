package com.example.busstation.service;

import com.example.busstation.model.Bus;
import com.example.busstation.model.Bus.BusStatus;
import com.example.busstation.repository.BusRepo;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class BusService {

    private final BusRepo repo;

    public BusService(BusRepo repo) {
        this.repo = repo;
    }

    public Bus add(Bus bus) {
        validateBus(bus);
        if (bus.getId() == null || bus.getId().isEmpty()) {
            bus.setId(String.valueOf(repo.findAll().size() + 1));
        }
        if (bus.getStatusEnum() == null) {
            bus.setStatus(BusStatus.DOWN);
        }
        return repo.save(bus);
    }

    public Bus add(String registrationNumber, int capacity, BusStatus status) {
        Bus bus = new Bus(String.valueOf(repo.findAll().size() + 1), registrationNumber, capacity);
        bus.setStatus(status != null ? status : BusStatus.DOWN);
        return add(bus);
    }

    public List<Bus> listAll() {
        return repo.findAll();
    }

    public Bus findById(String id) {
        for (Bus b : repo.findAll()) {
            if (b.getId().equals(id)) {
                return b;
            }
        }
        return null;
    }

    public Bus findByRegistrationNumber(String registrationNumber) {
        for (Bus b : repo.findAll()) {
            if (b.getRegistrationNumber().equalsIgnoreCase(registrationNumber)) {
                return b;
            }
        }
        return null;
    }

    public List<Bus> findByStatus(BusStatus status) {
        List<Bus> result = new ArrayList<>();
        for (Bus b : repo.findAll()) {
            if (b.getStatusEnum() == status) {
                result.add(b);
            }
        }
        return result;
    }

    public List<Bus> findByCapacityAtLeast(int minCapacity) {
        List<Bus> result = new ArrayList<>();
        for (Bus b : repo.findAll()) {
            if (b.getCapacity() >= minCapacity) {
                result.add(b);
            }
        }
        return result;
    }

    public Bus update(Bus bus) {
        validateBus(bus);
        return repo.save(bus);
    }

    public boolean deleteById(String id) {
        return repo.deleteById(id);
    }

    public int deleteAll() {
        List<Bus> all = new ArrayList<>(repo.findAll());
        int removed = 0;
        for (Bus b : all) {
            if (repo.deleteById(b.getId())) {
                removed++;
            }
        }
        return removed;
    }

    private void validateBus(Bus bus) {
        if (bus == null) throw new IllegalArgumentException("bus must not be null");
        if (bus.getRegistrationNumber() == null || bus.getRegistrationNumber().isEmpty()) {
            throw new IllegalArgumentException("registrationNumber must be provided");
        }
        if (bus.getCapacity() <= 0) {
            throw new IllegalArgumentException("capacity must be > 0");
        }
    }
}
