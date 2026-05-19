import com.solvd.hospital.dao.impl.*;
import com.solvd.hospital.ConnectionPool;
import com.solvd.hospital.model.Patient;
import com.solvd.hospital.service.*;
import com.solvd.hospital.service.impl.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

public class Main {

    private static final Logger LOGGER = LogManager.getLogger(Main.class);

    public static void main(String[] args) {

        // build DAOs
        PatientDaoImpl patientDao = new PatientDaoImpl();
        DoctorDaoImpl doctorDao = new DoctorDaoImpl();
        AppointmentDaoImpl appointmentDao = new AppointmentDaoImpl();
        AdmissionDaoImpl admissionDao = new AdmissionDaoImpl();
        PrescriptionDaoImpl prescriptionDao = new PrescriptionDaoImpl();
        MedicalRecordDaoImpl medicalRecordDao = new MedicalRecordDaoImpl();
        PaymentDaoImpl paymentDao = new PaymentDaoImpl();
        ReportDaoImpl reportDao = new ReportDaoImpl();

        // build services
        PatientService patientService = new PatientServiceImpl(patientDao);
        DoctorService doctorService = new DoctorServiceImpl(doctorDao);
        PrescriptionService prescriptionService =
                new PrescriptionServiceImpl(prescriptionDao);

        AdmissionService admissionService =
                new AdmissionServiceImpl(admissionDao);

        PaymentService paymentService =
                new PaymentServiceImpl(paymentDao);

        ReportService reportService =
                new ReportServiceImpl(reportDao);

        AppointmentService appointmentService =
                new AppointmentServiceImpl(appointmentDao, doctorService);

        LOGGER.info("--- All patients ---");

        patientService.getAllPatients()
                .forEach(p ->
                        LOGGER.info("{} {}", p.getFirstName(), p.getLastName())
                );

        LOGGER.info("--- Available doctors ---");

        doctorService.getAvailableDoctors()
                .forEach(d -> LOGGER.info(
                        "{} {} - {}",
                        d.getFirstName(),
                        d.getLastName(),
                        d.getSpecialization()
                ));

        LOGGER.info("--- Active admissions ---");

        admissionService.getActiveAdmissions()
                .forEach(a -> LOGGER.info(
                        "Admission id={} reason={}",
                        a.getId(),
                        a.getReason()
                ));

        LOGGER.info("--- Unpaid payments ---");

        paymentService.getUnpaidPayments()
                .forEach(pay -> LOGGER.info(
                        "Payment id={} total={} paid={}",
                        pay.getId(),
                        pay.getTotalAmount(),
                        pay.getPaidAmount()
                ));

        LOGGER.info("--- Full report (nested Patient objects) ---");

        List<Patient> report = reportService.getPatientAdmissionReport();

        report.forEach(patient -> {

            LOGGER.info(
                    "Patient: {} {} | insured={}",
                    patient.getFirstName(),
                    patient.getLastName(),
                    patient.isInsured()
            );

            if (patient.getMedicalRecord() != null) {

                LOGGER.info(
                        "  MedicalRecord: bloodType={} notes={}",
                        patient.getMedicalRecord().getBloodType(),
                        patient.getMedicalRecord().getNotes()
                );

                if (patient.getMedicalRecord().getAllergies() != null) {

                    patient.getMedicalRecord()
                            .getAllergies()
                            .forEach(a ->
                                    LOGGER.info("    Allergy: {}", a.getName())
                            );
                }
            }

            if (patient.getAdmission() != null) {

                LOGGER.info(
                        "  Admission: id={} reason={} admitted={} discharged={}",
                        patient.getAdmission().getId(),
                        patient.getAdmission().getReason(),
                        patient.getAdmission().getAdmittedAt(),
                        patient.getAdmission().getDischargedAt() != null
                                ? patient.getAdmission().getDischargedAt()
                                : "still admitted"
                );

                if (patient.getAdmission().getPayment() != null) {

                    LOGGER.info(
                            "    Payment: total={} paid={} settled={}",
                            patient.getAdmission().getPayment().getTotalAmount(),
                            patient.getAdmission().getPayment().getPaidAmount(),
                            patient.getAdmission().getPayment().isPaid()
                    );
                }
            }
        });

        // close connection pool
        ConnectionPool.getInstance().close();
    }
}