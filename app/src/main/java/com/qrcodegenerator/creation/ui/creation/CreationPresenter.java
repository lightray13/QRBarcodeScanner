package com.qrcodegenerator.creation.ui.creation;

import com.qrcodegenerator.creation.data.DataManager;
import com.qrcodegenerator.creation.data.db.model.History;
import com.qrcodegenerator.creation.ui.base.BasePresenter;
import com.qrcodegenerator.creation.ui.creation.CreationMvpPresenter;
import com.qrcodegenerator.creation.ui.creation.CreationMvpView;
import com.qrcodegenerator.creation.utils.rx.SchedulerProvider;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;

public class CreationPresenter <V extends CreationMvpView> extends BasePresenter<V> implements CreationMvpPresenter<V> {

    @Inject
    public CreationPresenter(DataManager dataManager, SchedulerProvider schedulerProvider, CompositeDisposable compositeDisposable) {
        super(dataManager, schedulerProvider, compositeDisposable);
    }

    @Override
    public void backButtonPressed() {
        getMvpView().backToMainActivity();
    }

    @Override
    public void qrCodeCreated(History history) {
        getCompositeDisposable().add(getDataManager()
                .addHistory(history)
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().ui())
                .subscribe(new Consumer<Long>() {
                    @Override
                    public void accept(Long aLong) throws Exception {
                        getMvpView().openCreatedActivity(aLong);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        getMvpView().onError(throwable.getMessage());
                    }
                }));
    }
}
