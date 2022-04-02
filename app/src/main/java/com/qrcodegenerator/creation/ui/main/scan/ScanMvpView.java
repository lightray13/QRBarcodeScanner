package com.qrcodegenerator.creation.ui.main.scan;

import com.qrcodegenerator.creation.ui.base.MvpView;

public interface ScanMvpView extends MvpView {

    //    void startScanActivity(String type, Result rawResult);
//    void continueScanning();
    void toggleFlashlight();
    void showFileChooser();
    void setupPermission();
    void openScanningActivity(long id);
    //    void startCamera();
//    void stopCamera();
    void resumeCamera();
//    void requestPermission();
//    boolean checkPermission();

}
