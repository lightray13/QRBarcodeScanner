package com.qrcodegenerator.creation.ui.qr;

import com.qrcodegenerator.creation.ui.base.MvpPresenter;

public interface QrMvpPresenter<V extends QrMvpView> extends MvpPresenter<V> {

    void onViewInitialized(long id);
    void backButtonPressed();
    void saveButtonPressed();
    void shareButtonPressed();
    void editButtonPressed();
    void deleteButtonPressed();
}
