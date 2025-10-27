package com.example.busstation.repository;

import java.util.List;

public interface AbstractRepository<T>{
    T save(T t);
    List<T> findAll();
    T findById(String id);
    boolean deleteById(String id);
}
