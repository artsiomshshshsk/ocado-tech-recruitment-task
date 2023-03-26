package org.example.data;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;
import java.util.PriorityQueue;

@AllArgsConstructor
@Data
public class OrderTrack {

    private BigDecimal answer;
    private PriorityQueue<Picker> pickers;
    private Order order;
    private OrderTrack prev;

    public OrderTrack(BigDecimal answer, PriorityQueue<Picker> pickers, Order order) {
        this.answer = answer;
        this.pickers = pickers;
        this.order = order;
    }


}
