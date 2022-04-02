package com.qrcodegenerator.creation.ui.splash;

import com.qrcodegenerator.creation.ui.base.MvpPresenter;

public interface SplashMvpPresenter<V extends SplashMvpView> extends MvpPresenter<V> {

    void permissionGranted();
    void permissionDenied();

}
