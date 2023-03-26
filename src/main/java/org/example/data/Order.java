package org.example.data;

import lombok.*;

import java.math.BigDecimal;
import java.time.Duration;
import java.time.LocalTime;


@NoArgsConstructor
@Getter
@Setter
public class Order{
     private String orderId;
     private BigDecimal orderValue;
     private Duration pickingTime;
     private LocalTime completeBy;
     private String assignedPicker;
     private LocalTime pickupTime;


    public Order(Order order) {
        if(order == null){
            throw new IllegalArgumentException("Order cannot be null");
        }
        this.orderId = order.orderId;
        this.orderValue = order.orderValue;
        this.pickingTime = order.pickingTime;
        this.completeBy = order.completeBy;
        this.assignedPicker = order.assignedPicker;
        this.pickupTime = order.pickupTime;
    }

    public LocalTime differenceBetweenCompleteAndPickingTime(){
        return completeBy.minus(pickingTime);
    }

    @Override
    public String toString() {
        return assignedPicker + " " + orderId + " " + pickupTime;
    }
}
