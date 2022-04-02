package com.qrcodegenerator.creation.ui.splash;

import com.qrcodegenerator.creation.ui.base.MvpView;

public interface SplashMvpView extends MvpView {

    void setupPermission();
    //    void requestPermission();
//    boolean checkPermission();
    void showNeedAccessDialog();
    void openMainActivity();
}
