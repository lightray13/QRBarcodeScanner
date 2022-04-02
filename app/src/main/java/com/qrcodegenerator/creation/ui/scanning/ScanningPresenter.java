package com.qrcodegenerator.creation.ui.scanning;

import com.qrcodegenerator.creation.R;
import com.qrcodegenerator.creation.data.DataManager;
import com.qrcodegenerator.creation.data.db.model.History;
import com.qrcodegenerator.creation.ui.base.BasePresenter;
import com.qrcodegenerator.creation.ui.scanning.ScanningMvpPresenter;
import com.qrcodegenerator.creation.ui.scanning.ScanningMvpView;
import com.qrcodegenerator.creation.utils.rx.SchedulerProvider;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;

public class ScanningPresenter <V extends ScanningMvpView> extends BasePresenter<V> implements ScanningMvpPresenter<V> {

    @Inject
    public ScanningPresenter(DataManager dataManager,
                             SchedulerProvider schedulerProvider,
                             CompositeDisposable compositeDisposable) {
        super(dataManager, schedulerProvider, compositeDisposable);
    }

    @Override
    public void onViewInitialized(long id) {
        getCompositeDisposable().add(getDataManager()
                .getHistoryById(id)
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().ui())
                .subscribe(new Consumer<History>() {
                    @Override
                    public void accept(History history) throws Exception {
                        if (!isViewAttached()) {
                            return;
                        }

                        getMvpView().setHeader(history.type);
                        getMvpView().setFields(history);
                        getMvpView().setButtons(history);
                        getMvpView().setQrImage(history);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        getMvpView().showMessage(throwable.getMessage());
                    }
                }));
    }

    @Override
    public void backButtonPressed() {
        getMvpView().backToMainActivity();
    }

    @Override
    public void actionButtonPressed(final int title) {
        getCompositeDisposable().add(getDataManager()
                .getLastHistory()
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().ui())
                .subscribe(new Consumer<History>() {
                    @Override
                    public void accept(History history) throws Exception {
                        if (!isViewAttached()) {
                            return;
                        }

                        switch (title) {
                            case R.string.btn_title_open_map:
                                getMvpView().openMap(history.latitude, history.longitude);
                                break;
                            case R.string.btn_title_add_contact:
                                getMvpView().addContact(history.name, history.position, history.company, history.phone, history.phoneTwo,
                                        history.email, history.address, history.uri);
                                break;
                            case R.string.btn_title_dial_phone:
                                getMvpView().dialPhone(history.phone);
                                break;
                            case R.string.btn_title_send_sms:
                                getMvpView().sendSms(history.phone, history.message);
                                break;
                            case R.string.btn_title_send_email:
                                getMvpView().sendEmail(history.email, history.name, history.message);
                                break;
                            case R.string.btn_title_open_url:
                                getMvpView().openUrl(history.uri);
                                break;
                            case R.string.btn_title_add_event:
                                getMvpView().addEvent(history.name, history.description, history.address, history.startTime, history.endTime);
                                break;
                            case R.string.btn_title_search:
                                getMvpView().search(history.text);
                                break;
                            case R.string.btn_title_share:
                                getMvpView().share(history.text);
                                break;
                            case R.string.btn_title_copy:
                                getMvpView().copy(history.text);
                                break;
                            case R.string.btn_title_connect_wifi:
                                getMvpView().connectToWifi(history.ssid, history.password);
                                break;

                        }
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        getMvpView().showMessage(throwable.getMessage());
                    }
                }));
    }

    @Override
    public void permissionGranted() {
        actionButtonPressed(R.string.btn_title_connect_wifi);
    }

    @Override
    public void permissionDenied() {
        getMvpView().showMessage(R.string.permission_denied_location);
    }
}

