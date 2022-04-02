package com.qrcodegenerator.creation.ui.creation;

import com.qrcodegenerator.creation.ui.base.MvpView;

public interface CreationMvpView extends MvpView {

    void backToMainActivity();
    void openCreatedActivity(long id);
}
