package com.qrcodegenerator.creation.ui.main.scanned;

import com.qrcodegenerator.creation.data.DataManager;
import com.qrcodegenerator.creation.data.bus.model.Events;
import com.qrcodegenerator.creation.data.db.model.History;
import com.qrcodegenerator.creation.ui.base.BasePresenter;
import com.qrcodegenerator.creation.utils.rx.SchedulerProvider;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;

public class ScannedPresenter<V extends ScannedMvpView> extends BasePresenter<V> implements ScannedMvpPresenter<V> {

    @Inject
    public ScannedPresenter(DataManager dataManager, SchedulerProvider schedulerProvider, CompositeDisposable compositeDisposable) {
        super(dataManager, schedulerProvider, compositeDisposable);
    }

    @Override
    public void onViewInitialized() {
        getMvpView().showLoadingDialog();
        getCompositeDisposable().add(getDataManager()
                .getHistoryList(false)
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().ui())
                .subscribe(new Consumer<List<History>>() {
                    @Override
                    public void accept(List<History> histories) throws Exception {
                        if (histories != null) {
                            getMvpView().refreshHistoryCards(histories);
                        }
                        getMvpView().hideLoadingDialog();
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        getMvpView().hideLoadingDialog();
                        getMvpView().showMessage(throwable.getMessage());
                    }
                }));
    }

    @Override
    public void onCardExhausted() {
        getMvpView().showLoadingDialog();
        getCompositeDisposable().add(getDataManager()
                .getHistoryList(false)
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().ui())
                .subscribe(new Consumer<List<History>>() {
                    @Override
                    public void accept(List<History> histories) throws Exception {
                        if (histories != null) {
                            getMvpView().reloadHistoryCards(histories);
                        }
                        getMvpView().hideLoadingDialog();
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        getMvpView().hideLoadingDialog();
                        getMvpView().showMessage(throwable.getMessage());
                    }
                }));
    }

    @Override
    public void deleteHistoryCard(final History history) {
        getCompositeDisposable().add(getDataManager()
                .deleteHistory(history)
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().ui())
                .subscribe(new Action() {
                    @Override
                    public void run() throws Exception {
                        getMvpView().showMessage(history.type + " deleted");
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        getMvpView().showMessage(throwable.getMessage());
                    }
                }));
    }

    @Override
    public void getScannedEvent() {
        getCompositeDisposable().add(getDataManager()
                .getEvent()
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().ui())
                .subscribe(new Consumer<Object>() {
                    @Override
                    public void accept(Object object) throws Exception {
                        if (object instanceof Events.ScannedEvent) {
                            onCardExhausted();
                        }
                    }
                }));
    }
}