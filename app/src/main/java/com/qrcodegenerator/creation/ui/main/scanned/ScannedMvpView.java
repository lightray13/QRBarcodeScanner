package com.qrcodegenerator.creation.ui.main.scanned;

import android.view.View;

import com.qrcodegenerator.creation.data.db.model.History;
import com.qrcodegenerator.creation.ui.base.MvpView;

import java.util.List;

public interface ScannedMvpView extends MvpView {

    void refreshHistoryCards(List<History> histories);
    void reloadHistoryCards(List<History> histories);
    void showLoadingDialog();
    void hideLoadingDialog();
    void showChooseDialog(View view, int position);
    void openHistoryCardActivity();
}
