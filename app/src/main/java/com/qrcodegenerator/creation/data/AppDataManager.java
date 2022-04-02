package com.qrcodegenerator.creation.data;

import android.content.Context;

import com.qrcodegenerator.creation.data.bus.BusHelper;
import com.qrcodegenerator.creation.data.db.DbHelper;
import com.qrcodegenerator.creation.data.db.model.History;
import com.qrcodegenerator.creation.data.prefs.PreferencesHelper;
import com.qrcodegenerator.creation.di.ApplicationContext;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Completable;
import io.reactivex.Observable;
import io.reactivex.Single;

@Singleton
public class AppDataManager implements DataManager {

    private final Context mContext;
    private final DbHelper mDbHelper;
    private final PreferencesHelper mPreferencesHelper;
    private final BusHelper mBusHelper;

    @Inject
    public AppDataManager(@ApplicationContext Context context,
                          DbHelper dbHelper, PreferencesHelper preferencesHelper, BusHelper busHelper) {
        mContext = context;
        mDbHelper = dbHelper;
        mPreferencesHelper = preferencesHelper;
        mBusHelper = busHelper;
    }

    @Override
    public Single<List<History>> getHistoryList(boolean isCreated) {
        return mDbHelper.getHistoryList(isCreated);
    }

    @Override
    public Single<History> getHistoryById(long id) {
        return mDbHelper.getHistoryById(id);
    }

    @Override
    public Single<History> getLastHistory() {
        return mDbHelper.getLastHistory();
    }

    @Override
    public Single<Boolean> isScannedHistoryListEmpty() {
        return null;
    }

    @Override
    public Single<Boolean> isCreatedHistoryListEmpty() {
        return null;
    }

    @Override
    public Single<Long> addHistory(History history) {
        return mDbHelper.addHistory(history);
    }

    @Override
    public Completable deleteHistory(History history) {
        return mDbHelper.deleteHistory(history);
    }

    @Override
    public void sendScannedEvent() {
        mBusHelper.sendScannedEvent();
    }

    @Override
    public void sendCreatedEvent() {
        mBusHelper.sendCreatedEvent();
    }

    @Override
    public Observable<Object> getEvent() {
        return mBusHelper.getEvent();
    }

    @Override
    public void setScanScreenInFocus(boolean state) {
        mPreferencesHelper.setScanScreenInFocus(state);
    }

    @Override
    public boolean getScaneScreenInFocus() {
        return mPreferencesHelper.getScaneScreenInFocus();
    }

}
