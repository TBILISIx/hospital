package com.solvd.hospital.dao.impl.mybatis;

import com.solvd.hospital.MyBatisSessionHolder;
import com.solvd.hospital.dao.ReportDao;
import com.solvd.hospital.model.Patient;
import org.apache.ibatis.session.SqlSession;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

public class MyBatisReportDaoImpl implements ReportDao {

    private static final Logger LOGGER = LogManager.getLogger(MyBatisReportDaoImpl.class);
    private static final String NAMESPACE = "com.solvd.hospital.dao.mybatis.MyBatisReportDao.";

    @Override
    public List<Patient> findPatientsWithAdmissionDetails() {
        try (SqlSession session = MyBatisSessionHolder.openSession()) {
            List<Patient> result = session.selectList(NAMESPACE + "findPatientsWithAdmissionDetails");
            LOGGER.info("Patient admission report: {} patients", result.size());
            return result;
        }
    }
}