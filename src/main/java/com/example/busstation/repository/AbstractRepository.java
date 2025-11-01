package com.example.busstation.repository;

import java.util.List;

public interface AbstractRepository<ID,T>{
    T save(T t);
    List<T> findAll();
    T findById(ID id);
    boolean deleteById(ID id);
}
