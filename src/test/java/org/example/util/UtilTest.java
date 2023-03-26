package org.example.util;

import org.example.data.Picker;
import org.junit.jupiter.api.Test;

import java.time.LocalTime;
import java.util.Comparator;
import java.util.PriorityQueue;

import static org.junit.jupiter.api.Assertions.*;

class UtilTest {

    @Test
    public void testDeepCopy() {
        PriorityQueue<Picker> original = new PriorityQueue<>(Comparator.comparing(Picker::getAvailableAt));
        original.add(new Picker("1", LocalTime.of(9, 0)));
        original.add(new Picker("2", LocalTime.of(10, 0)));
        original.add(new Picker("3", LocalTime.of(11, 0)));

        PriorityQueue<Picker> copy = Util.deepCopy(original);

        assertNotSame(original, copy);

        while (!original.isEmpty()) {
            Picker originalPicker = original.poll();
            Picker copiedPicker = copy.poll();
            assertEquals(originalPicker.getPickerId(), copiedPicker.getPickerId());
            assertEquals(originalPicker.getAvailableAt(), copiedPicker.getAvailableAt());
        }
    }

    @Test
    public void testDeepCopyEmpty() {
        PriorityQueue<Picker> original = new PriorityQueue<>(Comparator.comparing(Picker::getAvailableAt));
        PriorityQueue<Picker> copy = Util.deepCopy(original);
        assertNotSame(original, copy);
        assertTrue(copy.isEmpty());
    }

    @Test
    public void testDeepCopyNull() {
        assertThrows(IllegalArgumentException.class, () -> Util.deepCopy(null));
    }

}