package com.qrcodegenerator.creation.ui.main.created;

import com.qrcodegenerator.creation.data.DataManager;
import com.qrcodegenerator.creation.ui.base.BasePresenter;
import com.qrcodegenerator.creation.utils.rx.SchedulerProvider;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;

public class CreatedPresenter<V extends CreatedMvpView> extends BasePresenter<V> implements CreatedMvpPresenter<V> {

    @Inject
    public CreatedPresenter(DataManager dataManager,
                           SchedulerProvider schedulerProvider,
                           CompositeDisposable compositeDisposable) {
        super(dataManager, schedulerProvider, compositeDisposable);
    }
}
