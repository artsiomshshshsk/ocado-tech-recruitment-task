package org.example;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.example.data.Order;
import org.example.data.Store;
import org.example.scheduler.Scheduler;

import java.io.File;
import java.io.IOException;
import java.util.*;

public class Main {
    public static void main(String[] args) throws IOException {

//        String storeJson = args[0];
//        String ordersJson = args[1];

//        File storeFile = new File(storeJson);
//        File ordersFile = new File(ordersJson);

        File storeFile = new File("src/main/resources/self-test-data/advanced-allocation/store.json");
        File ordersFile = new File("src/main/resources/self-test-data/advanced-allocation/orders.json");

//        File ordersFile = new File("src/main/resources/self-test-data/any-order-length-is-ok/orders.json");
//        File storeFile = new File("src/main/resources/self-test-data/any-order-length-is-ok/store.json");
//
//        File ordersFile = new File("src/main/resources/self-test-data/complete-by/orders.json");
//        File storeFile = new File("src/main/resources/self-test-data/complete-by/store.json");

//        File ordersFile = new File("src/main/resources/self-test-data/isf-end-time/orders.json");
//        File storeFile = new File("src/main/resources/self-test-data/isf-end-time/store.json");
//
//        File ordersFile = new File("src/main/resources/self-test-data/optimize-order-count/orders.json");
//        File storeFile = new File("src/main/resources/self-test-data/optimize-order-count/store.json");


//        File ordersFile = new File("src/main/resources/self-test-data/logic-bomb/orders.json");
//        File storeFile = new File("src/main/resources/self-test-data/logic-bomb/store.json");

//        File storeFile = new File("src/main/resources/self-test-data/my-test-data/store.json");
//        File ordersFile = new File("src/main/resources/self-test-data/my-test-data/orders.json");

//        File storeFile = new File("src/main/resources/self-test-data/advanced-optimize-order-count/store.json");
//        File ordersFile = new File("src/main/resources/self-test-data/advanced-optimize-order-count/orders.json");



        ObjectMapper objectMapper = new ObjectMapper()
                .registerModule(new JavaTimeModule());
        try {
            Store store = objectMapper.readValue(storeFile, Store.class);
            List<Order> orders = objectMapper.readValue(ordersFile, new TypeReference<List<Order>>(){});
            Scheduler scheduler = new Scheduler(orders,store);
            List<Order> scheduledOrders = scheduler.computeSchedule();
            for(Order order : scheduledOrders){
                System.out.println(order);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }






}