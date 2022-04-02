package com.qrcodegenerator.creation.data.bus;

import io.reactivex.Observable;

public interface BusHelper {

    void sendScannedEvent();
    void sendCreatedEvent();
    Observable<Object> getEvent();

}
