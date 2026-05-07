import com.solvd.hospital.*;
import com.solvd.hospital.enums.BloodType;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public class Main {

    private static final Logger LOGGER = LogManager.getLogger(Main.class);

    public static void main(String[] args) {

        // -- Doctors ------------------------------------------------------------
        Doctor drSandro = new Doctor(1L, "Sandro", "Kapanadze", "Cardiology", true);
        Doctor drShota = new Doctor(2L, "Shota", "Iremashvili", "Neurology", true);

        // -- Staff --------------------------------------------------------------
        Staff nurse1 = new Staff(1L, "Salome", "Gloveli", Staff.StaffRole.NURSE, LocalDate.of(2020, 3, 15), true);
        Staff receptionist = new Staff(2L, "Elene", "Butxuzi", Staff.StaffRole.RECEPTIONIST, LocalDate.of(2019, 6, 1), true);

        // -- Rooms --------------------------------------------------------------
        Room room101 = new Room(1L, "101", Room.RoomType.GENERAL, 2L, false);
        Room room202 = new Room(2L, "202", Room.RoomType.ICU, 1L, true);

        // -- Department (contains doctors, staff, rooms) ------------------------
        Department cardiology = new Department(
                1L, "Cardiology", "Building A - Floor 2",
                List.of(drSandro),
                List.of(nurse1, receptionist),
                List.of(room101, room202)
        );

        // -- Clinic (contains departments) -------------------------------------
        MedicalClinic clinic = new MedicalClinic(
                1L, "City General Hospital", "123 Main St",
                10L, 3L,
                List.of(cardiology)
        );

        LOGGER.info("Clinic created: {}", clinic.getName());
        LOGGER.info("Department '{}' has {} doctor(s), {} staff, {} room(s)",
                cardiology.getName(),
                cardiology.getDoctors().size(),
                cardiology.getStaff().size(),
                cardiology.getRooms().size());

        // -- Medical record with allergies --------------------------------------
        Allergy penicillinAllergy = new Allergy(1L, "Penicillin");
        Allergy pollenAllergy = new Allergy(2L, "Pollen");

        MedicalRecord medicalRecord = new MedicalRecord(
                1L, LocalDate.of(2022, 1, 10),
                BloodType.A_POSITIVE,
                List.of(penicillinAllergy, pollenAllergy),
                "Patient has history of hypertension"
        );

        // -- Appointment (patient sees a doctor) -------------------------------
        Appointment appointment = new Appointment(
                1L, drSandro,
                LocalDateTime.of(2024, 5, 20, 10, 30),
                Appointment.AppointmentStatus.DONE,
                "Routine checkup"
        );

        // -- Payment (belongs to admission, no patient reference needed) --------
        Payment payment = new Payment(
                1L, LocalDate.of(2024, 5, 21),
                new BigDecimal("1500.00"),
                new BigDecimal("1500.00"),
                true
        );

        // -- Admission (patient -> room -> payment)

        Admission admission = new Admission();
        admission.setId(1L);
        admission.setRoom(room101);
        admission.setAdmittedAt(LocalDateTime.of(2024, 5, 20, 9, 0));
        admission.setDischargedAt(LocalDateTime.of(2024, 5, 21, 14, 0));
        admission.setReason("Chest pain evaluation");
        admission.setPayment(payment);

        // -- Prescription with medications -------------------------------------
        PrescriptionMedication med1 = new PrescriptionMedication(1L, "Aspirin", "100mg", "Once daily");
        PrescriptionMedication med2 = new PrescriptionMedication(2L, "Lisinopril", "10mg", "Twice daily");

        Prescription prescription = new Prescription(
                1L, drSandro,
                LocalDate.of(2024, 5, 21),
                List.of(med1, med2),
                "Take with food. Avoid grapefruit."
        );

        // -- Patient (root of patient-side hierarchy) --------------------------
        Patient patient = new Patient(
                1L, "Alice", "Johnson",
                LocalDate.of(1985, 7, 23),
                "+1-555-0101", true,
                medicalRecord,
                List.of(appointment),
                List.of(admission),
                List.of(prescription)
        );

        // Set patient on admission
        admission.setPatient(patient);

        // -- Logging full hierarchy ---------------------------------------------
        LOGGER.info("=== CLINIC HIERARCHY ===");
        LOGGER.info("Clinic       : {}", clinic.getName());
        LOGGER.info("Department   : {} | Location: {}", cardiology.getName(), cardiology.getLocation());
        LOGGER.info("  Doctors    : {}", cardiology.getDoctors());
        LOGGER.info("  Staff      : {}", cardiology.getStaff());
        LOGGER.info("  Rooms      : {}", cardiology.getRooms());

        LOGGER.info("=== PATIENT HIERARCHY ===");
        LOGGER.info("Patient      : {} {}", patient.getFirstName(), patient.getLastName());
        LOGGER.info("Blood type   : {}", patient.getMedicalRecord().getBloodType());
        LOGGER.info("Allergies    : {}", patient.getMedicalRecord().getAllergies());
        LOGGER.info("Appointments : {}", patient.getAppointments());
        LOGGER.info("Admissions   : {}", patient.getAdmissions());
        LOGGER.info("  Payment    : {}", patient.getAdmissions().get(0).getPayment());
        LOGGER.info("Prescriptions: {}", patient.getPrescriptions());
        LOGGER.info("  Medications: {}", patient.getPrescriptions().get(0).getMedications());
    }
}
