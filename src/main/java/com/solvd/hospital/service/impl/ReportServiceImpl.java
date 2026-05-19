package com.solvd.hospital.service.impl;

import com.solvd.hospital.dao.ReportDao;
import com.solvd.hospital.model.Patient;
import com.solvd.hospital.service.ReportService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

public class ReportServiceImpl implements ReportService {

    private static final Logger LOGGER = LogManager.getLogger(ReportServiceImpl.class);

    private final ReportDao reportDao;

    public ReportServiceImpl(ReportDao reportDao) {
        this.reportDao = reportDao;
    }

    @Override
    public List<Patient> getPatientAdmissionReport() {

        List<Patient> report = reportDao.findPatientsWithAdmissionDetails();

        LOGGER.info("Patient admission report: {} patients", report.size());

        return report;
    }
}

