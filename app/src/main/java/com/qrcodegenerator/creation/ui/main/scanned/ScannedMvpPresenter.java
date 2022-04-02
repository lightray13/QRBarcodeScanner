package com.qrcodegenerator.creation.ui.main.scanned;

import com.qrcodegenerator.creation.data.db.model.History;
import com.qrcodegenerator.creation.ui.base.MvpPresenter;

public interface ScannedMvpPresenter<V extends ScannedMvpView> extends MvpPresenter<V> {

    void onViewInitialized();
    void onCardExhausted();
    void deleteHistoryCard(History history);
    void getScannedEvent();
}
