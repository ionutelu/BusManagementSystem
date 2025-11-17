package com.example.busstation.repository;

import java.io.IOException;
import java.util.List;

public interface AbstractRepository<ID,T>{
    T save(T t) throws IOException;
    List<T> findAll();
    T findById(ID id);
    boolean deleteById(ID id);
    T update(T t, ID id);
}
