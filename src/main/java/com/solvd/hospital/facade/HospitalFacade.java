package com.solvd.hospital.facade;

import com.solvd.hospital.decorator.LoggingPatientServiceDecorator;
import com.solvd.hospital.factory.DaoFactory;
import com.solvd.hospital.listener.AuditLogListener;
import com.solvd.hospital.listener.EventPublisher;
import com.solvd.hospital.listener.HospitalEvent;
import com.solvd.hospital.model.*;
import com.solvd.hospital.service.*;
import com.solvd.hospital.service.impl.*;
import com.solvd.hospital.strategy.PaymentStrategy;

import java.math.BigDecimal;
import java.util.List;

/**
 * Facade — hides wiring of factories, services, listeners, and strategies
 * behind a single clean API used by Main.
 */
public class HospitalFacade {

    private final PatientService     patientService;
    private final DoctorService      doctorService;
    private final AdmissionService   admissionService;
    private final PaymentService     paymentService;
    private final AppointmentService appointmentService;
    private final ReportService      reportService;

    private final EventPublisher eventPublisher;

    public HospitalFacade(DaoFactory factory) {

        this.eventPublisher = new EventPublisher();
        this.eventPublisher.subscribe(new AuditLogListener());

        this.patientService = new LoggingPatientServiceDecorator(
                new PatientServiceImpl(factory.createPatientDao())
        );
        this.doctorService      = new DoctorServiceImpl(factory.createDoctorDao());
        this.admissionService   = new AdmissionServiceImpl(factory.createAdmissionDao());
        this.paymentService     = new PaymentServiceImpl(factory.createPaymentDao());
        this.appointmentService = new AppointmentServiceImpl(factory.createAppointmentDao());
        this.reportService      = new ReportServiceImpl(factory.createReportDao());
    }

    // Patients

    public List<Patient> getAllPatients() {
        return patientService.getAllPatients();
    }

    // Doctors

    public List<Doctor> getAvailableDoctors() {
        return doctorService.getAvailableDoctors();
    }

    // Admissions

    public Admission admitPatient(Admission admission, Long patientId, Long roomId) {
        Admission result = admissionService.admitPatient(admission, patientId, roomId);
        eventPublisher.publish(HospitalEvent.PATIENT_ADMITTED,
                "patientId=" + patientId + " roomId=" + roomId);
        return result;
    }

    public void dischargePatient(Long admissionId) {
        admissionService.dischargePatient(admissionId);
        eventPublisher.publish(HospitalEvent.PATIENT_DISCHARGED,
                "admissionId=" + admissionId);
    }

    public List<Admission> getActiveAdmissions() {
        return admissionService.getActiveAdmissions();
    }

    // Payments

    public List<Payment> getUnpaidPayments() {
        return paymentService.getUnpaidPayments();
    }


    public void recordPayment(Long paymentId, BigDecimal requested, PaymentStrategy strategy) {

        Payment payment = paymentService.getUnpaidPayments()
                .stream()
                .filter(p -> p.getId().equals(paymentId))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Unpaid payment not found id=" + paymentId));

        BigDecimal amount = strategy.calculatePayment(payment, requested);
        paymentService.recordPayment(paymentId, amount);
        eventPublisher.publish(HospitalEvent.PAYMENT_RECORDED,
                "paymentId=" + paymentId + " amount=" + amount);
    }

    // Report

    public List<Patient> getPatientAdmissionReport() {
        return reportService.getPatientAdmissionReport();
    }
}
