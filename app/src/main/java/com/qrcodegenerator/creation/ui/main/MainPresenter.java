package com.qrcodegenerator.creation.ui.main;

import com.qrcodegenerator.creation.data.DataManager;
import com.qrcodegenerator.creation.ui.base.BasePresenter;
import com.qrcodegenerator.creation.utils.rx.SchedulerProvider;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;

public class MainPresenter <V extends MainMvpView> extends BasePresenter<V> implements MainMvpPresenter<V> {

    @Inject
    public MainPresenter(DataManager dataManager,
                         SchedulerProvider schedulerProvider,
                         CompositeDisposable compositeDisposable) {
        super(dataManager, schedulerProvider, compositeDisposable);
    }

    @Override
    public void shareAppButtonPressed() {
        getMvpView().shareAppSharingIntent();
    }

    @Override
    public void rateAppButtonPressed() {
        getMvpView().rateAppSharingIntent();
    }

    @Override
    public void setScanScreenPref(boolean state) {
        getDataManager().setScanScreenInFocus(state);
    }

}
