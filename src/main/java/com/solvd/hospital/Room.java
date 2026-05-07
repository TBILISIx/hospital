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
    private RoomType type;
    private int capacity;
    private boolean occupied;
    private Department department;

    public enum RoomType {
        ICU, GENERAL, SURGERY, RECOVERY, PRIVATE
    }
}
