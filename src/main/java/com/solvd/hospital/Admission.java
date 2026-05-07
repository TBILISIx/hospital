package com.solvd.hospital;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Admission {

    private Long id;
    private Patient patient;
    private Room room;
    private LocalDateTime admittedAt;
    private LocalDateTime dischargedAt;  // null if still admitted
    private String reason;

}