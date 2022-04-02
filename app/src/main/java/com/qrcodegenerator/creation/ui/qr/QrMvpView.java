package com.qrcodegenerator.creation.ui.qr;

import com.qrcodegenerator.creation.data.db.model.History;
import com.qrcodegenerator.creation.ui.base.MvpView;

public interface QrMvpView extends MvpView {

    void setHeader(String type);
    void setButtons();
    void setQrImage(History history);
    void backToCreationActivity();
    void saveQrImage();

}
