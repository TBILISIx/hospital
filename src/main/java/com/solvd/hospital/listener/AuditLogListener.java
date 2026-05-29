package com.solvd.hospital.listener;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class AuditLogListener implements HospitalEventListener {

    private static final Logger LOGGER = LogManager.getLogger(AuditLogListener.class);

    @Override
    public void onEvent(HospitalEvent event, String details) {
        LOGGER.info("[AUDIT] event={} details={}", event, details);
    }

}
