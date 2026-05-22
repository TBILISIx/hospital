import com.solvd.hospital.dao.impl.jdbc.*;
import com.solvd.hospital.dao.impl.mybatis.*;
import com.solvd.hospital.model.Patient;
import com.solvd.hospital.service.*;
import com.solvd.hospital.service.impl.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

public class Main {

    private static final Logger LOGGER = LogManager.getLogger(Main.class);

    private static final PatientService patientService = new PatientServiceImpl(new MyBatisPatientDaoImpl());
    private static final DoctorService doctorService = new DoctorServiceImpl(new MyBatisDoctorDaoImpl());
    private static final PrescriptionService prescriptionService = new PrescriptionServiceImpl(new MyBatisPrescriptionDaoImpl());

    private static final AdmissionService admissionService = new AdmissionServiceImpl(new MyBatisAdmissionDaoImpl());

    private static final PaymentService paymentService = new PaymentServiceImpl(new MyBatisPaymentDaoImpl());

    private static final ReportService reportService = new ReportServiceImpl(new MyBatisReportDaoImpl());


    // in this service i am providing sql code directly into servicelayerimplementation and this is better practice rather than feeding class in main.

    private static final AppointmentService appointmentService = new AppointmentServiceImpl();

    public static void main(String[] args) {

        // build DAOs with JDBC
//        JDBCPatientDaoImpl patientDao = new JDBCPatientDaoImpl();
//        JDBCDoctorDaoImpl doctorDao = new JDBCDoctorDaoImpl();
//        JDBCAppointmentDaoImpl appointmentDao = new JDBCAppointmentDaoImpl();
//        JDBCAdmissionDaoImpl admissionDao = new JDBCAdmissionDaoImpl();
//        JDBCPrescriptionDaoImpl prescriptionDao = new JDBCPrescriptionDaoImpl();
//        JDBCMedicalRecordDaoImpl medicalRecordDao = new JDBCMedicalRecordDaoImpl();
//        JDBCPaymentDaoImpl paymentDao = new JDBCPaymentDaoImpl();
//        JDBCReportDaoImpl reportDao = new JDBCReportDaoImpl();


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

        // in case of MyBatis this is not needed its done in mybatis-config
//        ConnectionPool.getInstance().close();

    }
}