package com.qrcodegenerator.creation.data.db;

import com.qrcodegenerator.creation.data.db.dao.HistoryDao;
import com.qrcodegenerator.creation.data.db.model.History;

import java.util.List;
import java.util.concurrent.Callable;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Completable;
import io.reactivex.Single;

@Singleton
public class AppDbHelper implements DbHelper {

    private final HistoryDao mHistoryDao;

    @Inject
    public AppDbHelper(HistoryDao historyDao) {
        mHistoryDao = historyDao;
    }

    @Override
    public Single<List<History>> getHistoryList(final boolean isCreated) {
        return Single.fromCallable(new Callable<List<History>>() {
            @Override
            public List<History> call() throws Exception {
                return mHistoryDao.getHistoryByCreate(isCreated);
            }
        });
    }

    @Override
    public Single<Long> addHistory(final History history) {
        return Single.fromCallable(new Callable<Long>() {
            @Override
            public Long call() throws Exception {
                return mHistoryDao.insert(history);
            }
        });

    }

    @Override
    public Single<History> getHistoryById(final long id) {
        return Single.fromCallable(new Callable<History>() {
            @Override
            public History call() throws Exception {
                return mHistoryDao.getHistoryById(id);
            }
        });
    }

    @Override
    public Single<History> getLastHistory() {
        return Single.fromCallable(new Callable<History>() {
            @Override
            public History call() throws Exception {
                return mHistoryDao.getLastHistory();
            }
        });
    }

    @Override
    public Completable deleteHistory(final History history) {
        return Completable.fromCallable(new Callable<Integer>() {
            @Override
            public Integer call() throws Exception {
                return mHistoryDao.delete(history);
            }
        });
    }


    @Override
    public Single<Boolean> isScannedHistoryListEmpty() {
        return Single.fromCallable(new Callable<Boolean>() {
            @Override
            public Boolean call() throws Exception {
                return !(mHistoryDao.getHistoryByCreate(false).size() > 0);
            }
        });
    }

    @Override
    public Single<Boolean> isCreatedHistoryListEmpty() {
        return Single.fromCallable(new Callable<Boolean>() {
            @Override
            public Boolean call() throws Exception {
                return !(mHistoryDao.getHistoryByCreate(true).size() > 0);
            }
        });
    }
}
