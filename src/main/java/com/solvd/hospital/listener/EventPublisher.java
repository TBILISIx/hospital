package com.solvd.hospital.listener;

import java.util.ArrayList;
import java.util.List;

public class EventPublisher {

    private final List<HospitalEventListener> listeners = new ArrayList<>();

    public void subscribe(HospitalEventListener listener) {
        listeners.add(listener);
    }

    public void publish(HospitalEvent event, String details) {
        for (HospitalEventListener listener : listeners) {
            listener.onEvent(event, details);
        }
    }

}
