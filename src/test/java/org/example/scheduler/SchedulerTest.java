package org.example.scheduler;

import org.example.data.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.time.Duration;
import java.time.LocalTime;
import java.util.List;
import java.util.PriorityQueue;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
class SchedulerTest {

    @InjectMocks
    private Scheduler scheduler = new Scheduler();
    @Mock
    private Store store;
    @Mock
    private List<Order> orders;

    private Method isFeasible;

    private Method initializePickersQueue;

    private Method initialize;

    @BeforeEach
    void setUp() throws NoSuchMethodException {
        isFeasible = Scheduler.class.getDeclaredMethod("isFeasible", LocalTime.class, Order.class);
        isFeasible.setAccessible(true);
        initializePickersQueue = Scheduler.class.getDeclaredMethod("initializePickersQueue");
        initializePickersQueue.setAccessible(true);
        initialize = Scheduler.class.getDeclaredMethod("initialize");
        initialize.setAccessible(true);
    }

    @Test
    void isFeasible_orderIsFeasible_returnTrue() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        // given
        Order order = Order.builder()
                .completeBy(LocalTime.of(10, 0))
                .build();

        when(store.getPickingEndTime()).thenReturn(LocalTime.of(11, 0));
        LocalTime timeAfterCompleting = LocalTime.of(9,30);
        boolean expected = true;
        // when
        boolean result = (boolean) isFeasible.invoke(scheduler,timeAfterCompleting,order);
        // then
        assertEquals(expected, result);
    }

    @Test
    void isFeasible_orderIsNotFeasible_returnFalse() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        // given
        Order order = Order.builder()
                .completeBy(LocalTime.of(10, 0))
                .build();

        when(store.getPickingEndTime()).thenReturn(LocalTime.of(11, 0));
        LocalTime timeAfterCompleting = LocalTime.of(10,20);
        boolean expected = false;
        // when
        boolean result = (boolean) isFeasible.invoke(scheduler,timeAfterCompleting,order);
        // then
        assertEquals(expected, result);
    }

    @Test
    void initializePickersQueue_givenStoreWithTwoPickers_returnQueueWithTwoPickers() throws InvocationTargetException, IllegalAccessException {
        // given
        when(store.getPickers()).thenReturn(List.of("P1", "P2"));
        when(store.getPickingStartTime()).thenReturn(LocalTime.of(10,0));
        // when
        PriorityQueue<Picker> result = (PriorityQueue<Picker>) initializePickersQueue.invoke(scheduler);
        // then
        assertEquals(2, result.size());
        for (Picker picker : result) {
            assertEquals(LocalTime.of(10,0), picker.getAvailableAt());
        }
    }

    @Test
    void initializeFIRST_givenOrders_returnOrderTrackList() throws InvocationTargetException, IllegalAccessException {
        //given
        scheduler.setTask(Task.FIRST);
        when(orders.get(0)).thenReturn(Order.builder()
                .orderId("order-1")
                .completeBy(LocalTime.of(10, 0))
                .pickingTime(Duration.ofMinutes(45))
                .build());
        when(orders.get(1)).thenReturn(Order.builder()
                .orderId("order-2")
                .completeBy(LocalTime.of(9, 30))
                .pickingTime(Duration.ofMinutes(30))
                .build());
        when(orders.size()).thenReturn(2);


        when(store.getPickers()).thenReturn(List.of("P1"));
        when(store.getPickingStartTime()).thenReturn(LocalTime.of(9,0));
        //when
        OrderTrack[] initial = (OrderTrack[]) initialize.invoke(scheduler);
        //then
        assertEquals(2, initial.length);
        for(int i = 0; i< initial.length; i++){
            assertEquals(BigDecimal.ONE,initial[i].getAnswer());
            assertEquals(store.getPickingStartTime().plus(orders.get(i).getPickingTime()),initial[i].getPickers().peek().getAvailableAt());
        }
    }


    @Test
    void initializeSECOND_givenOrders_returnOrderTrackList() throws InvocationTargetException, IllegalAccessException {
        //given
        scheduler.setTask(Task.SECOND);
        when(orders.get(0)).thenReturn(Order.builder()
                .orderId("order-1")
                .orderValue(BigDecimal.valueOf(40))
                .pickingTime(Duration.ofMinutes(60))
                .completeBy(LocalTime.of(10, 0))
                .build());

        when(orders.get(1)).thenReturn(Order.builder()
                .orderId("order-2")
                .orderValue(BigDecimal.valueOf(5))
                .pickingTime(Duration.ofMinutes(15))
                .completeBy(LocalTime.of(9, 15))
                .build());

        when(orders.get(2)).thenReturn(Order.builder()
                .orderId("order-3")
                .orderValue(BigDecimal.valueOf(5))
                .pickingTime(Duration.ofMinutes(15))
                .completeBy(LocalTime.of(9, 30))
                .build());

        when(orders.size()).thenReturn(3);
        when(store.getPickers()).thenReturn(List.of("P1"));
        when(store.getPickingStartTime()).thenReturn(LocalTime.of(9,0));
        //when
        OrderTrack[] initial = (OrderTrack[]) initialize.invoke(scheduler);
        //then
        assertEquals(3, initial.length);
        for(int i = 0; i< initial.length; i++){
            assertEquals(orders.get(i).getOrderValue(),initial[i].getAnswer());
            assertEquals(store.getPickingStartTime().plus(orders.get(i).getPickingTime()),initial[i].getPickers().peek().getAvailableAt());
        }

    }
}