package com.example.busstation.repository;

import com.example.busstation.model.Identifiable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class InMemoryRepository <ID, T extends Identifiable<ID>> implements AbstractRepository<ID, T>{
    private final Map<ID, T> storage = new HashMap<>();
    public T save(T t) {
        storage.put(t.getId(), t);
        return t;
    }
    @Override
    public List<T> findAll(){
        return new ArrayList<>(storage.values());
    }
    @Override
    public T findById(ID id){
        return storage.get(id);
    }

    @Override
    public boolean deleteById(ID id) {
        return storage.remove(id) != null;
    }
    @Override
    public T update(T entity, ID id){
        return entity;//doesn't work
    }
}
