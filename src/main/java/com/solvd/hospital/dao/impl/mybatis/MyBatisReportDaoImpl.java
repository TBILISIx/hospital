package com.solvd.hospital.dao.impl.mybatis;

import com.solvd.hospital.util.MyBatisSessionHolder;
import com.solvd.hospital.dao.ReportDao;
import com.solvd.hospital.model.Patient;
import org.apache.ibatis.session.SqlSession;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

public class MyBatisReportDaoImpl implements ReportDao {

    private static final Logger LOGGER = LogManager.getLogger(MyBatisReportDaoImpl.class);

    @Override
    public List<Patient> findPatientsWithAdmissionDetails() {
        try (SqlSession session = MyBatisSessionHolder.openSession()) {
            List<Patient> result = session.getMapper(ReportDao.class).findPatientsWithAdmissionDetails();
            LOGGER.info("Patient admission report: {} patients", result.size());
            return result;
        }
    }
}