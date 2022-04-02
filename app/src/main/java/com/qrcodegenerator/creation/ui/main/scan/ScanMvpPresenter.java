package com.qrcodegenerator.creation.ui.main.scan;

import com.qrcodegenerator.creation.data.db.model.History;
import com.qrcodegenerator.creation.ui.base.MvpPresenter;

public interface ScanMvpPresenter<V extends ScanMvpView> extends MvpPresenter<V> {

    void qrCodeScanned(History history);
    void toggleFlashlightButtonPressed();
    void decodeQrCodeButtonPressed();
    void permissionGranted();
    void permissionDenied();
}
