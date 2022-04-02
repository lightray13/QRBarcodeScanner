package com.qrcodegenerator.creation.ui.qr;

import com.qrcodegenerator.creation.data.DataManager;
import com.qrcodegenerator.creation.data.db.model.History;
import com.qrcodegenerator.creation.ui.base.BasePresenter;
import com.qrcodegenerator.creation.utils.rx.SchedulerProvider;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;

public class QrPresenter<V extends QrMvpView> extends BasePresenter<V> implements QrMvpPresenter<V> {

    @Inject
    public QrPresenter(DataManager dataManager, SchedulerProvider schedulerProvider, CompositeDisposable compositeDisposable) {
        super(dataManager, schedulerProvider, compositeDisposable);
    }

    @Override
    public void onViewInitialized(long id) {
        getCompositeDisposable().add(getDataManager()
                .getHistoryById(id)
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().ui())
                .subscribe(new Consumer<History>() {
                    @Override
                    public void accept(History history) throws Exception {
                        if (!isViewAttached()) {
                            return;
                        }

                        getMvpView().setHeader(history.type);
                        getMvpView().setQrImage(history);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        getMvpView().showMessage(throwable.getMessage());
                    }
                }));
    }

    @Override
    public void backButtonPressed() {
        getMvpView().backToCreationActivity();
    }

    @Override
    public void saveButtonPressed() {
        getMvpView().saveQrImage();
    }

    @Override
    public void shareButtonPressed() {

    }

    @Override
    public void editButtonPressed() {

    }

    @Override
    public void deleteButtonPressed() {

    }
}
