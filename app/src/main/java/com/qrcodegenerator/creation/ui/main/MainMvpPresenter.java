package com.qrcodegenerator.creation.ui.main;

import com.qrcodegenerator.creation.ui.base.MvpPresenter;

public interface MainMvpPresenter <V extends MainMvpView> extends MvpPresenter<V> {

    void shareAppButtonPressed();
    void rateAppButtonPressed();
    void setScanScreenPref(boolean state);

}
