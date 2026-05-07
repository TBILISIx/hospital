package com.solvd.hospital;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Room {
    private Long id;
    private String roomNumber;
    private String type;             // "ICU", "GENERAL", "SURGERY"
    private int capacity;
    private boolean occupied;
    private Department department;
}