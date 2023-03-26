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
    List<String> pickers;
    LocalTime pickingStartTime;
    LocalTime pickingEndTime;
}

