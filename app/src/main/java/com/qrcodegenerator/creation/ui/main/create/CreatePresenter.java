package com.qrcodegenerator.creation.ui.main.create;

import com.qrcodegenerator.creation.data.DataManager;
import com.qrcodegenerator.creation.ui.base.BasePresenter;
import com.qrcodegenerator.creation.utils.rx.SchedulerProvider;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;

public class CreatePresenter<V extends CreateMvpView> extends BasePresenter<V> implements CreateMvpPresenter<V> {

    @Inject
    public CreatePresenter(DataManager dataManager,
                           SchedulerProvider schedulerProvider,
                           CompositeDisposable compositeDisposable) {
        super(dataManager, schedulerProvider, compositeDisposable);
    }
}
