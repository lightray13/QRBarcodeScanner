package com.qrcodegenerator.creation.data;

import com.qrcodegenerator.creation.data.bus.BusHelper;
import com.qrcodegenerator.creation.data.db.DbHelper;
import com.qrcodegenerator.creation.data.db.model.History;
import com.qrcodegenerator.creation.data.prefs.PreferencesHelper;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Observable;
import io.reactivex.Single;

public interface DataManager extends DbHelper, PreferencesHelper, BusHelper {

    Single<List<History>> getHistoryList(boolean isCreated);
    Single<History> getHistoryById(long id);
    Single<History> getLastHistory();
    Single<Long> addHistory(History history);
    Completable deleteHistory(History history);
    void sendScannedEvent();
    void sendCreatedEvent();
    Observable<Object> getEvent();
    void setScanScreenInFocus(boolean state);
    boolean getScaneScreenInFocus();

}
