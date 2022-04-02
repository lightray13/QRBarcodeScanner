package com.qrcodegenerator.creation.data.bus;

import com.qrcodegenerator.creation.data.bus.model.Events;
import com.qrcodegenerator.creation.data.bus.model.RxBus;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Observable;

@Singleton
public class AppBusHelper implements BusHelper {

    private RxBus bus;

    @Inject
    public AppBusHelper(RxBus bus) {
        this.bus = bus;
    }

    @Override
    public void sendScannedEvent() {
        bus.send(new Events.ScannedEvent());
    }

    @Override
    public void sendCreatedEvent() {
        bus.send(new Events.CreatedEvent());
    }

    @Override
    public Observable<Object> getEvent() {
        return bus.toObservable();
    }
}
