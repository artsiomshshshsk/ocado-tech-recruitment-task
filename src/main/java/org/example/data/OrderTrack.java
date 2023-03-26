package org.example.data;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.PriorityQueue;

@AllArgsConstructor
@Data
public class OrderTrack {

    private double answer;
    private PriorityQueue<Picker> pickers;
    private Order order;
    private OrderTrack prev;

    public OrderTrack(int answer, PriorityQueue<Picker> pickers, Order order) {
        this.answer = answer;
        this.pickers = pickers;
        this.order = order;
    }


}
