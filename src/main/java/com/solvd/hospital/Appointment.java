package com.solvd.hospital;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Appointment {
    private Long id;
    private Patient patient;
    private Doctor doctor;
    private LocalDateTime scheduledAt;
    private AppointmentStatus status;
    private String notes;

    public enum AppointmentStatus {
        SCHEDULED, DONE, CANCELLED
    }
}
