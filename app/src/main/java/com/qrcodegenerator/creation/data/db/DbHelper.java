package com.qrcodegenerator.creation.data.db;

import com.qrcodegenerator.creation.data.db.model.History;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Single;

public interface DbHelper {

    Single<List<History>> getHistoryList(boolean isCreated);
    Single<Long> addHistory(final History history);
    Completable deleteHistory(History history);
    Single<History> getHistoryById(long id);
    Single<History> getLastHistory();
    Single<Boolean> isScannedHistoryListEmpty();
    Single<Boolean> isCreatedHistoryListEmpty();

}
