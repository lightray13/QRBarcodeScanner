package com.qrcodegenerator.creation.ui.main.scan;

import com.qrcodegenerator.creation.R;
import com.qrcodegenerator.creation.data.DataManager;
import com.qrcodegenerator.creation.data.db.model.History;
import com.qrcodegenerator.creation.ui.base.BasePresenter;
import com.qrcodegenerator.creation.utils.rx.SchedulerProvider;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;

public class ScanPresenter<V extends ScanMvpView> extends BasePresenter<V> implements ScanMvpPresenter<V> {

    @Inject
    public ScanPresenter(DataManager dataManager,
                         SchedulerProvider schedulerProvider,
                         CompositeDisposable compositeDisposable) {
        super(dataManager, schedulerProvider, compositeDisposable);
    }

    @Override
    public void qrCodeScanned(History history) {
        if (getDataManager().getScaneScreenInFocus()) {
            getCompositeDisposable().add(getDataManager()
                    .addHistory(history)
                    .subscribeOn(getSchedulerProvider().io())
                    .observeOn(getSchedulerProvider().ui())
                    .subscribe(new Consumer<Long>() {
                        @Override
                        public void accept(Long aLong) throws Exception {
                            getMvpView().openScanningActivity(aLong);
                            getDataManager().sendScannedEvent();
                        }
                    }, new Consumer<Throwable>() {
                        @Override
                        public void accept(Throwable throwable) throws Exception {
                            getMvpView().onError(throwable.getMessage());
                        }
                    }));
        } else {
            getMvpView().resumeCamera();
        }
    }

    @Override
    public void toggleFlashlightButtonPressed() {
        getMvpView().toggleFlashlight();
    }

    @Override
    public void decodeQrCodeButtonPressed() {
        getMvpView().setupPermission();
    }

    @Override
    public void permissionGranted() {
        getMvpView().showFileChooser();
    }

    @Override
    public void permissionDenied() {
        getMvpView().showMessage(R.string.permission_denied_external_storage);
    }
}
