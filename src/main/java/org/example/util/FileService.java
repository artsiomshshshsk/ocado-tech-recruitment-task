package org.example.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.example.data.Order;
import org.example.data.Store;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class FileService {

    private final ObjectMapper objectMapper;

    public FileService(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    public Store readStore(String fileName) {
        File file = readFile(fileName);
        try {
            return objectMapper.readValue(file, Store.class);
        } catch (IOException e) {
            throw new RuntimeException("Could not read store from file " + file);
        }
    }

    public List<Order> readOrders(String filename) {
        File file = readFile(filename);
        try {
            return objectMapper.readValue(file, objectMapper.getTypeFactory().constructCollectionType(List.class, Order.class));
        } catch (IOException e) {
            throw new RuntimeException("Could not read orders from file " + file);
        }
    }

    public File readFile(String fileName){
        if(fileName == null){
            throw new IllegalArgumentException("Filename cannot be null");
        }
        File file = new File(fileName);
        if(!file.exists()){
            throw new IllegalArgumentException("File " + fileName + " does not exist");
        }
        return file;
    }


    public FileService() {
        this(new ObjectMapper().registerModule(new JavaTimeModule()));
    }
}
