package org.example.data;

import lombok.*;

import java.time.Duration;
import java.time.LocalTime;


@NoArgsConstructor
@Getter
@Setter
@AllArgsConstructor
public class Picker {
    String pickerId;
    LocalTime availableAt;

    public Picker(Picker picker) {
        if (picker == null) {
            throw new IllegalArgumentException("Picker cannot be null");
        }
        this.pickerId = picker.pickerId;
        this.availableAt = picker.availableAt;
    }

    public void giveOrderWithPickingTime(Duration pickingTime) {
        if (pickingTime == null) {
            throw new IllegalArgumentException("Picking time cannot be null");
        }
        availableAt = availableAt.plus(pickingTime);
    }


    public LocalTime getTimeAfterCompleting(Duration pickingTime) {
        if (pickingTime == null) {
            throw new IllegalArgumentException("Picking time cannot be null");
        }
        return availableAt.plus(pickingTime);
    }
}
