import com.solvd.hospital.dao.impl.*;
import com.solvd.hospital.db.ConnectionPool;
import com.solvd.hospital.service.*;
import com.solvd.hospital.service.impl.*;
import com.solvd.hospital.dao.impl.PatientAdmissionReport;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.sql.DataSource;
import java.util.List;

public class Main {

    private static final Logger LOGGER = LogManager.getLogger(Main.class);

    public static void main(String[] args) {



        // 1. Build DAOs
        PatientDaoImpl patientDao = new PatientDaoImpl();
        DoctorDaoImpl doctorDao = new DoctorDaoImpl();
        AppointmentDaoImpl appointmentDao = new AppointmentDaoImpl();
        AdmissionDaoImpl admissionDao = new AdmissionDaoImpl();
        PrescriptionDaoImpl prescriptionDao = new PrescriptionDaoImpl();
        MedicalRecordDaoImpl medicalRecordDao = new MedicalRecordDaoImpl();
        PaymentDaoImpl paymentDao = new PaymentDaoImpl();
        ReportDaoImpl reportDao = new ReportDaoImpl();

        // 2. Build Services (inject DAOs)
        PatientService patientService = new PatientServiceImpl(patientDao);
        DoctorService doctorService = new DoctorServiceImpl(doctorDao);
        AppointmentService appointmentService = new AppointmentServiceImpl(appointmentDao, doctorDao);
        AdmissionService admissionService = new AdmissionServiceImpl(admissionDao);
        PrescriptionService prescriptionService = new PrescriptionServiceImpl(prescriptionDao);
        PaymentService paymentService = new PaymentServiceImpl(paymentDao);
        ReportService reportService = new ReportServiceImpl(reportDao);

        // 3. Use services – Main never touches a DAO directly

        LOGGER.info("--- All patients ---");
        patientService.getAllPatients()
                .forEach(p -> LOGGER.info("{} {}", p.getFirstName(), p.getLastName()));

        LOGGER.info("--- Available doctors ---");
        doctorService.getAvailableDoctors()
                .forEach(d -> LOGGER.info(
                        "{} {} – {}",
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

        LOGGER.info("--- Full report (5-join query) ---");
        List<PatientAdmissionReport> report = reportService.getPatientAdmissionReport();
        report.forEach(row -> LOGGER.info(row.toString()));

        // 5. Shut down pool cleanly
        ConnectionPool.getInstance().close();
    }
}

