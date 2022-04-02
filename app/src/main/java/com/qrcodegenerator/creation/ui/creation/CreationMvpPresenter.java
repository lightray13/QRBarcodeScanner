package com.qrcodegenerator.creation.ui.creation;

import com.qrcodegenerator.creation.data.db.model.History;
import com.qrcodegenerator.creation.ui.base.MvpPresenter;

public interface CreationMvpPresenter<V extends CreationMvpView> extends MvpPresenter<V> {

    void backButtonPressed();
    void qrCodeCreated(History history);
}
