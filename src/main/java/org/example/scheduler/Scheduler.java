package org.example.scheduler;

import lombok.NoArgsConstructor;
import lombok.Setter;
import org.example.util.Util;
import org.example.data.*;

import java.math.BigDecimal;
import java.time.LocalTime;
import java.util.*;

@NoArgsConstructor
@Setter
public class Scheduler {

    private List<Order> orders;
    private Store store;
    private Task task;

    public Scheduler(List<Order> orders, Store store, Task task) {
        if (orders == null || store == null || task == null) {
            throw new IllegalArgumentException("Orders, task and store cannot be null");
        }
        this.orders = new ArrayList<>(orders.stream().map(Order::new).toList());
        this.store = store;
        this.task = task;
    }

    public Scheduler(List<Order> orders, Store store) {
        this(orders, store, Task.FIRST);
    }

    public List<Order> computeSchedule() {
        if (task == Task.FIRST) {
            orders.sort(Comparator.comparing(Order::completeTimeMinusPickingTime));
        } else {
            orders.sort(Comparator.comparing(Order::completeTimeMinusPickingTime).thenComparing(Order::getOrderValue));
        }
        OrderTrack[] dp = initialize();

        for (int i = 1; i < orders.size(); i++) {
            for (int j = 0; j < i; j++) {
                OrderTrack current = dp[i];
                OrderTrack prev = dp[j];
                Order order = orders.get(i);

                Picker picker = prev.getPickers().poll();
                Picker pickerCopy = new Picker(picker);
                LocalTime timeAfterCompleting = pickerCopy.getTimeAfterCompleting(order.getPickingTime());

                if (isFeasible(timeAfterCompleting, order)) {
                    BigDecimal improvement;
                    if (task == Task.FIRST) {
                        improvement = prev.getAnswer().add(BigDecimal.ONE);
                    } else {
                        improvement = prev.getAnswer().add(order.getOrderValue());
                    }
                    if (improvement.compareTo(current.getAnswer()) >= 0) {
                        updateCurrentOrderTrack(current, prev, improvement);
                        assignOrderToPicker(order, pickerCopy, timeAfterCompleting);
                        updatePickersQueue(current, prev, pickerCopy);
                    }
                }
                prev.getPickers().offer(picker);
            }
        }
        return computeResults(dp);
    }

    private void updateCurrentOrderTrack(OrderTrack current, OrderTrack prev, BigDecimal improvement) {
        current.setPrev(prev);
        current.setAnswer(improvement);
    }

    private void assignOrderToPicker(Order order, Picker picker, LocalTime timeAfterCompleting) {
        order.setPickupTime(picker.getAvailableAt());
        order.setAssignedPicker(picker.getPickerId());
        picker.setAvailableAt(timeAfterCompleting);
    }

    private void updatePickersQueue(OrderTrack current, OrderTrack prev, Picker pickerCopy) {
        PriorityQueue<Picker> queue = Util.deepCopy(prev.getPickers());
        queue.offer(pickerCopy);
        current.setPickers(queue);
    }

    private boolean isFeasible(LocalTime timeAfterCompleting, Order order) {
        if (timeAfterCompleting == null || order == null) {
            throw new IllegalArgumentException("Time and order cannot be null");
        }
        return !timeAfterCompleting.isAfter(store.getPickingEndTime()) && !timeAfterCompleting.isAfter(order.getCompleteBy());
    }


    private List<Order> computeResults(OrderTrack[] dp) {
        Optional<OrderTrack> maxPair = Arrays.stream(dp).max(Comparator.comparing(OrderTrack::getAnswer));

        if (maxPair.map(orderTrack -> orderTrack.getAnswer().equals(BigDecimal.ZERO)).orElse(true)) {
            return Collections.emptyList();
        }

        return maxPair.map(track -> {
            List<Order> result = new LinkedList<>();
            OrderTrack cur = track;
            while (cur != null) {
                result.add(0, cur.getOrder());
                cur = cur.getPrev();
            }
            return result;
        }).orElse(Collections.emptyList());
    }

    private OrderTrack[] initialize() {
        OrderTrack[] dp = new OrderTrack[orders.size()];
        for (int i = 0; i < orders.size(); i++) {
            PriorityQueue<Picker> queue = initializePickersQueue();
            if (!queue.isEmpty()) {
                Picker picker = queue.poll();
                Order order = orders.get(i);
                picker.giveOrderWithPickingTime(order.getPickingTime());
                queue.offer(picker);
                if(isFeasible(picker.getAvailableAt(), order)){
                    if (task == Task.FIRST) {
                        dp[i] = new OrderTrack(BigDecimal.ONE, queue, order);
                    } else {
                        dp[i] = new OrderTrack(order.getOrderValue(), queue, order);
                    }
                }else{
                    dp[i] = new OrderTrack(BigDecimal.ZERO, queue, order);
                }

                order.setPickupTime(store.getPickingStartTime());
                order.setAssignedPicker(picker.getPickerId());
            } else {
                dp[i] = new OrderTrack(BigDecimal.ZERO, queue, orders.get(i));
            }
        }
        return dp;
    }

    private PriorityQueue<Picker> initializePickersQueue() {
        PriorityQueue<Picker> queue = new PriorityQueue<>(Comparator.comparing(Picker::getAvailableAt));
        store.getPickers().forEach(pickerId -> queue.offer(new Picker(pickerId, store.getPickingStartTime())));
        return queue;
    }
}
