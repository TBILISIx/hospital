import com.solvd.hospital.dao.impl.mybatis.*;
import com.solvd.hospital.facade.HospitalFacade;
import com.solvd.hospital.factory.DaoFactory;
import com.solvd.hospital.factory.MyBatisDaoFactory;
import com.solvd.hospital.factory.PatientBuilder;
import com.solvd.hospital.model.*;
import com.solvd.hospital.service.*;
import com.solvd.hospital.service.impl.*;
import com.solvd.hospital.strategy.FullPaymentStrategy;
import com.solvd.hospital.strategy.InstallmentPaymentStrategy;
import com.solvd.hospital.strategy.InsurancePaymentStrategy;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public class Main {

    private static final Logger LOGGER = LogManager.getLogger(Main.class);

    public static void main(String[] args) {

        //  Abstract Factory
        // Swap MyBatisDaoFactory for JdbcDaoFactory to switch the whole data layer
        DaoFactory factory = new MyBatisDaoFactory();

        //  Facade
        // Single entry point — wires all services, decorator, and listeners inside
        // Previously all services were wired manually here in Main like:

   /*
        private static final PatientService patientService = new PatientServiceImpl(new MyBatisPatientDaoImpl());
        private static final DoctorService doctorService = new DoctorServiceImpl(new MyBatisDoctorDaoImpl());
        private static final PrescriptionService prescriptionService = new PrescriptionServiceImpl(new MyBatisPrescriptionDaoImpl());

        private static final AdmissionService admissionService = new AdmissionServiceImpl(new MyBatisAdmissionDaoImpl());

        private static final PaymentService paymentService = new PaymentServiceImpl(new MyBatisPaymentDaoImpl());

        private static final ReportService reportService = new ReportServiceImpl(new MyBatisReportDaoImpl());

        // in this service i am providing sql code directly into servicelayerimplementation and this is better practice rather than feeding class in main.

//             build DAOs with JDBC
        JDBCPatientDaoImpl patientDao = new JDBCPatientDaoImpl();
        JDBCDoctorDaoImpl doctorDao = new JDBCDoctorDaoImpl();
        JDBCAppointmentDaoImpl appointmentDao = new JDBCAppointmentDaoImpl();
        JDBCAdmissionDaoImpl admissionDao = new JDBCAdmissionDaoImpl();
        JDBCPrescriptionDaoImpl prescriptionDao = new JDBCPrescriptionDaoImpl();
        JDBCMedicalRecordDaoImpl medicalRecordDao = new JDBCMedicalRecordDaoImpl();
        JDBCPaymentDaoImpl paymentDao = new JDBCPaymentDaoImpl();
        JDBCReportDaoImpl reportDao = new JDBCReportDaoImpl();
        */

        // Now the Facade handles all of that internally
        HospitalFacade hospital = new HospitalFacade(factory);

        // Builder
        // Clean way to construct a Patient without a huge 10-arg constructor call
        Patient newPatient = new PatientBuilder()
                .firstName("Ana")
                .lastName("Dolidze")
                .dateOfBirth(LocalDate.of(1995, 3, 22))
                .phoneNumber("+995 599 00 11 22")
                .insured(true)
                .build();

        LOGGER.info("Built patient with builder: {} {}", newPatient.getFirstName(), newPatient.getLastName());

        // All patients
        // Goes through LoggingPatientServiceDecorator transparently (Decorator pattern)
        LOGGER.info("--- All patients ---");

        hospital.getAllPatients()
                .forEach(p ->
                        LOGGER.info("{} {}", p.getFirstName(), p.getLastName())
                );

        // Available doctors
        LOGGER.info("--- Available doctors ---");

        hospital.getAvailableDoctors()
                .forEach(d -> LOGGER.info(
                        "{} {} - {}",
                        d.getFirstName(),
                        d.getLastName(),
                        d.getSpecialization()
                ));

        // Active admissions
        // admitPatient() inside Facade fires PATIENT_ADMITTED event to AuditLogListener (Listener pattern)
        LOGGER.info("--- Active admissions ---");

        hospital.getActiveAdmissions()
                .forEach(a -> LOGGER.info(
                        "Admission id={} reason={}",
                        a.getId(),
                        a.getReason()
                ));

        // Unpaid payments + Strategy pattern
        // Three different strategies showing how the same payment can be calculated differently
        LOGGER.info("--- Unpaid payments ---");

        List<Payment> unpaid = hospital.getUnpaidPayments();

        unpaid.forEach(pay -> LOGGER.info(
                "Payment id={} total={} paid={}",
                pay.getId(),
                pay.getTotalAmount(),
                pay.getPaidAmount()
        ));

        if (!unpaid.isEmpty()) {

            Payment first = unpaid.get(0);

            LOGGER.info("Demonstrating payment strategies on payment id={}:", first.getId());

            // Strategy 1 — pay off the entire remaining balance
            BigDecimal full = new FullPaymentStrategy()
                    .calculatePayment(first, BigDecimal.ZERO);
            LOGGER.info("  FullPaymentStrategy      would pay: {}", full);

            // Strategy 2 — insurance covers 80% of the total
            BigDecimal insured = new InsurancePaymentStrategy()
                    .calculatePayment(first, first.getTotalAmount());
            LOGGER.info("  InsurancePaymentStrategy would pay: {}", insured);

            // Strategy 3 — fixed 1000 GEL
            BigDecimal installment = new InstallmentPaymentStrategy(new BigDecimal("1000.00"))
                    .calculatePayment(first, BigDecimal.ZERO);
            LOGGER.info("  InstallmentPaymentStrategy would pay: {}", installment);
        }

        // Full report (nested Patient objects)
        LOGGER.info("--- Full report (nested Patient objects) ---");

        List<Patient> report = hospital.getPatientAdmissionReport();

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
        // in case of MyBatis this is not needed, its done in mybatis-config
        // ConnectionPool.getInstance().close();
    }
}
