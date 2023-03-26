package org.example.data;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalTime;
import java.util.List;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class Store {
    private List<String> pickers;
    private LocalTime pickingStartTime;
    private LocalTime pickingEndTime;
}

