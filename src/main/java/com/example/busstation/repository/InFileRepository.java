package com.example.busstation.repository;

import com.example.busstation.model.Identifiable;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

public abstract class InFileRepository<ID, T extends Identifiable<ID>> implements AbstractRepository<ID, T> {


    private final String filePath;
    private final ObjectMapper mapper = new ObjectMapper();
    private final ArrayList<T> storage;
    private final Class<T[]> entityArrayClass;
    //private final Class<T> entityClass;

    public InFileRepository(String filePath, Class<T[]> entityArrayClass){
        this.filePath = filePath;
        //this.entityClass = entityClass;
        File file = new File(filePath);
        this.entityArrayClass = entityArrayClass;
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
            System.out.println("Eroare la load: " + e.getMessage());
            e.printStackTrace();
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
        for (T obj : storage) {
            if (obj.getId().equals(id)) { // equals!
                return obj;
            }
        }
        return null;
    }


    @Override
    public boolean deleteById(ID id) {
        Iterator<T> iterator = storage.iterator();
        while(iterator.hasNext()) {
            T obj = iterator.next();
            if(obj.getId().equals(id)) {
                iterator.remove();
                try {
                    //System.out.println("here we save in delete!");
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
            mapper.writerWithDefaultPrettyPrinter().writeValue(new File(filePath), storage);
            System.out.println("here we save in file!");
        }
        catch (Exception e){
            System.out.println("here we dont save in file!");
        }
    }

    @Override
    public T update(T updatedEntity, ID id) {
        for (int i = 0; i < storage.size(); i++) {
            T existingEntity = storage.get(i);

            if (existingEntity.getId().equals(id)) {
                storage.set(i, updatedEntity);

                try {
                    saveToFile();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                return updatedEntity;
            }
        }
        return null;
    }

}
