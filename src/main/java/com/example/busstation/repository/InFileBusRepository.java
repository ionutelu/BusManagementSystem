package com.example.busstation.repository;

import com.example.busstation.model.Identifiable;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public abstract class InFileBusRepository <ID, T extends Identifiable<ID>> implements AbstractRepository<ID, T> {


    private final String filePath;
    private final ObjectMapper mapper = new ObjectMapper();
    private final ArrayList<T> storage;
    private final Class<T[]> entityArrayClass;
    private final Class<T> entityClass;

    public InFileBusRepository(String filePath, Class<T[]> entityClass, Class<T> entityClass1){
        this.filePath = filePath;
        this.entityClass = entityClass1;
        File file = new File(filePath);
        this.entityArrayClass = entityClass;
        this.storage = loadFromFile(file);
    }

    ArrayList<T> loadFromFile(File file){
        if (!file.exists()) {
            return new ArrayList<>();
        }
        try {
            System.out.println("here we load!");
            return new ArrayList<>(Arrays.asList(mapper.readValue(file, entityArrayClass)));
        } catch (IOException e) {
            System.out.println("here we dont load!");
            return new ArrayList<>();
        }
    }

    @Override
    public T save(T t){
        storage.add(t);
        try {
            saveToFile();
            System.out.println("here we save in save!");
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("here we dont save in save!");
        }
        return t;
    }

    @Override
    public List<T> findAll() {
        return storage;
    }

    @Override
    public T findById(ID id) {
        for (T obj : storage){
            if(obj.getId() == id){
                return obj;
            }
        }
        return null;

    }

    @Override
    public boolean deleteById(ID id) {
        for (T obj : storage){
            if(obj.getId() == id){
                storage.remove(obj);
                try {
                    System.out.println("here we save in delete!");
                    saveToFile();
                } catch (IOException e) {
                    System.out.println("here we dont save in delete!");
                    e.printStackTrace();
                }
                return true;
            }
        }
        return false;
    }

    private void saveToFile() throws IOException {
        try{
            System.out.println("here we save in file!");
            mapper.writerWithDefaultPrettyPrinter().writeValue(new File(filePath), entityClass);
        }
        catch (Exception e){
            System.out.println("here we dont save in file!");
            e.printStackTrace();
        }
    }
}
