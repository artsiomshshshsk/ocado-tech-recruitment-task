package org.example.data;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.Duration;
import java.time.LocalTime;

import static org.junit.jupiter.api.Assertions.*;

class OrderTest {

    private Order order;

    @BeforeEach
    void setUp() {
        order = Order.builder()
                .orderId("1")
                .orderValue(BigDecimal.valueOf(10))
                .pickingTime(Duration.ofMinutes(30))
                .completeBy(LocalTime.of(13, 0))
                .build();
    }

    @Test
    void testCompleteTimeMinusPickingTime() {
        assertEquals(LocalTime.of(12, 30), order.completeTimeMinusPickingTime());
    }
}