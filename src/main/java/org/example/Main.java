package org.example;

import org.example.data.Order;
import org.example.data.Store;
import org.example.data.Task;
import org.example.scheduler.Scheduler;
import org.example.util.FileService;
import org.example.util.Util;

import java.io.IOException;
import java.util.*;

public class Main {
    public static void main(String[] args) throws IOException {
        if(args.length < 2) {
            throw new IllegalArgumentException("Please provide the path to the store and order files");
        }
        FileService fileService = new FileService();
        Task task = args.length > 2 &&
                args[2] != null &&
                args[2].equals("SECOND") ?
                Task.SECOND : Task.FIRST;

        Store store = fileService.readStore(args[0]);
        List<Order> orders = fileService.readOrders(args[1]);

        Scheduler scheduler = new Scheduler(orders, store, task);
        List<Order> scheduledOrders = scheduler.computeSchedule();

        Util.printAnswer(scheduledOrders);
    }
}
