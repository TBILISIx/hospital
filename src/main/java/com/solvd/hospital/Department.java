package com.solvd.hospital;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Department {
    private Long id;
    private String name;             // "Cardiology", "Emergency", etc.
    private String location;         // "Building A, Floor 2"
    private List<Doctor> doctors = new ArrayList<>();
    private List<Room> rooms = new ArrayList<>();
}