package com.solvd.hospital.listener;

public interface HospitalEventListener {

    void onEvent(HospitalEvent event, String details);

}
