package org.example.scheduler;

import org.example.data.Order;
import org.example.data.Picker;
import org.example.data.Store;
import org.example.data.Task;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
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
    private List<Order> orderList;

    private Method isFeasible;

    private Method initializePickersQueue;

    @BeforeEach
    void setUp() throws NoSuchMethodException {
        isFeasible = Scheduler.class.getDeclaredMethod("isFeasible", LocalTime.class, Order.class);
        isFeasible.setAccessible(true);
        initializePickersQueue = Scheduler.class.getDeclaredMethod("initializePickersQueue");
        initializePickersQueue.setAccessible(true);
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
}