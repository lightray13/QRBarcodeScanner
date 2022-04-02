package com.qrcodegenerator.creation.ui.splash;

import com.qrcodegenerator.creation.data.DataManager;
import com.qrcodegenerator.creation.ui.base.BasePresenter;
import com.qrcodegenerator.creation.ui.splash.SplashMvpPresenter;
import com.qrcodegenerator.creation.ui.splash.SplashMvpView;
import com.qrcodegenerator.creation.utils.rx.SchedulerProvider;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;

public class SplashPresenter<V extends SplashMvpView> extends BasePresenter<V> implements SplashMvpPresenter<V> {

    @Inject
    public SplashPresenter(DataManager dataManager,
                           SchedulerProvider schedulerProvider,
                           CompositeDisposable compositeDisposable) {
        super(dataManager, schedulerProvider, compositeDisposable);
    }

    @Override
    public void permissionGranted() {
        getMvpView().openMainActivity();
    }

    @Override
    public void permissionDenied() {
        getMvpView().showNeedAccessDialog();
    }
}
