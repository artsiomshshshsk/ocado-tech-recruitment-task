package org.example;

import org.example.data.Picker;

import java.util.Comparator;
import java.util.PriorityQueue;

public class Util {
    public static PriorityQueue<Picker> deepCopy(PriorityQueue<Picker> original) {
        PriorityQueue<Picker> copy = new PriorityQueue<>(Comparator.comparing(Picker::getAvailableAt));
        for (Picker item : original) copy.add(new Picker(item.getPickerId(), item.getAvailableAt()));
        return copy;
    }
}
