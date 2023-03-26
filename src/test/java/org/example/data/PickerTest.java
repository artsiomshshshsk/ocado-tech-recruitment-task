package org.example.data;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.time.LocalTime;

import static org.junit.jupiter.api.Assertions.*;

class PickerTest {

    private Picker picker;


    @BeforeEach
    void setUp() {
        picker = new Picker("1", LocalTime.of(9, 0));
    }

    @Test
    public void testGiveOrderWithPickingTime() {
        Duration pickingTime = Duration.ofMinutes(30);
        picker.giveOrderWithPickingTime(pickingTime);

        LocalTime expectedAvailableAt = LocalTime.of(9, 30);
        assertEquals(expectedAvailableAt, picker.getAvailableAt());
        assertThrows(IllegalArgumentException.class, () -> picker.giveOrderWithPickingTime(null));
    }

    @Test
    public void testGetTimeAfterCompleting() {
        Duration pickingTime = Duration.ofMinutes(30);
        LocalTime expectedAvailableAt = LocalTime.of(9, 30);
        assertEquals(expectedAvailableAt, picker.getTimeAfterCompleting(pickingTime));
        assertThrows(IllegalArgumentException.class, () -> picker.getTimeAfterCompleting(null));
    }
}