package com.qrcodegenerator.creation.ui.scanning;

import com.qrcodegenerator.creation.ui.base.MvpPresenter;

public interface ScanningMvpPresenter<V extends ScanningMvpView> extends MvpPresenter<V> {

    void onViewInitialized(long id);
    void backButtonPressed();
    void actionButtonPressed(int title);
    void permissionGranted();
    void permissionDenied();
}
