package com.qrcodegenerator.creation.di.module;

import android.content.Context;

import com.qrcodegenerator.creation.data.db.model.History;
import com.qrcodegenerator.creation.di.ActivityContext;
import com.qrcodegenerator.creation.di.PerActivity;
import com.qrcodegenerator.creation.ui.creation.CreationFieldsAdapter;
import com.qrcodegenerator.creation.ui.creation.CreationMvpPresenter;
import com.qrcodegenerator.creation.ui.creation.CreationMvpView;
import com.qrcodegenerator.creation.ui.creation.CreationPresenter;
import com.qrcodegenerator.creation.ui.main.FragmentAdapter;
import com.qrcodegenerator.creation.ui.main.MainMvpPresenter;
import com.qrcodegenerator.creation.ui.main.MainMvpView;
import com.qrcodegenerator.creation.ui.main.MainPresenter;
import com.qrcodegenerator.creation.ui.main.create.CreateMvpPresenter;
import com.qrcodegenerator.creation.ui.main.create.CreateMvpView;
import com.qrcodegenerator.creation.ui.main.create.CreatePresenter;
import com.qrcodegenerator.creation.ui.main.create.TypeCreationAdapter;
import com.qrcodegenerator.creation.ui.main.create.TypeItem;
import com.qrcodegenerator.creation.ui.main.created.CreatedMvpPresenter;
import com.qrcodegenerator.creation.ui.main.created.CreatedMvpView;
import com.qrcodegenerator.creation.ui.main.created.CreatedPresenter;
import com.qrcodegenerator.creation.ui.main.created.TypeCreatedAdapter;
import com.qrcodegenerator.creation.ui.main.scan.ScanMvpPresenter;
import com.qrcodegenerator.creation.ui.main.scan.ScanMvpView;
import com.qrcodegenerator.creation.ui.main.scan.ScanPresenter;
import com.qrcodegenerator.creation.ui.main.scanned.HistoryCardsAdapter;
import com.qrcodegenerator.creation.ui.main.scanned.ScannedMvpPresenter;
import com.qrcodegenerator.creation.ui.main.scanned.ScannedMvpView;
import com.qrcodegenerator.creation.ui.main.scanned.ScannedPresenter;
import com.qrcodegenerator.creation.ui.qr.QrMvpPresenter;
import com.qrcodegenerator.creation.ui.qr.QrMvpView;
import com.qrcodegenerator.creation.ui.qr.QrPresenter;
import com.qrcodegenerator.creation.ui.scanning.ButtonsAdapter;
import com.qrcodegenerator.creation.ui.scanning.ResultAdapter;
import com.qrcodegenerator.creation.ui.scanning.ScanResult;
import com.qrcodegenerator.creation.ui.scanning.ScanningMvpPresenter;
import com.qrcodegenerator.creation.ui.scanning.ScanningMvpView;
import com.qrcodegenerator.creation.ui.scanning.ScanningPresenter;
import com.qrcodegenerator.creation.ui.splash.SplashMvpPresenter;
import com.qrcodegenerator.creation.ui.splash.SplashMvpView;
import com.qrcodegenerator.creation.ui.splash.SplashPresenter;
import com.qrcodegenerator.creation.utils.rx.AppSchedulerProvider;
import com.qrcodegenerator.creation.utils.rx.SchedulerProvider;

import java.util.ArrayList;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import dagger.Module;
import dagger.Provides;
import io.reactivex.disposables.CompositeDisposable;

@Module
public class ActivityModule {

    private AppCompatActivity mActivity;

    public ActivityModule(AppCompatActivity mActivity) {
        this.mActivity = mActivity;
    }

    @Provides
    @ActivityContext
    Context provideContext() {
        return mActivity;
    }

    @Provides
    AppCompatActivity provideActivity() {
        return mActivity;
    }

    @Provides
    CompositeDisposable provideCompositeDisposable() {
        return new CompositeDisposable();
    }

    @Provides
    SchedulerProvider provideSchedulerProvider() {
        return new AppSchedulerProvider();
    }

    @Provides
    @PerActivity
    SplashMvpPresenter<SplashMvpView> provideSplashPresenter(SplashPresenter<SplashMvpView> presenter) {
        return presenter;
    }

    @Provides
    @PerActivity
    MainMvpPresenter<MainMvpView> provideMainPresenter(MainPresenter<MainMvpView> presenter) {
        return presenter;
    }

    @Provides
    ScanMvpPresenter<ScanMvpView> provideScanMvpPresenter(ScanPresenter<ScanMvpView> presenter) {
        return presenter;
    }

    @Provides
    CreateMvpPresenter<CreateMvpView> provideCreateMvpPresenter(CreatePresenter<CreateMvpView> presenter) {
        return presenter;
    }

    @Provides
    CreatedMvpPresenter<CreatedMvpView> provideCreatedMvpPresenter(CreatedPresenter<CreatedMvpView> presenter) {
        return presenter;
    }

    @Provides
    ScannedMvpPresenter<ScannedMvpView> provideScannedMvpPresenter(ScannedPresenter<ScannedMvpView> presenter) {
        return presenter;
    }

    @Provides
    ScanningMvpPresenter<ScanningMvpView> provideScanningMvpPresenter(ScanningPresenter<ScanningMvpView> presenter) {
        return presenter;
    }

    @Provides
    CreationMvpPresenter<CreationMvpView> provideCreationMvpPresenter(CreationPresenter<CreationMvpView> presenter) {
        return presenter;
    }

    @Provides
    QrMvpPresenter<QrMvpView> provideQrMvpPresenter(QrPresenter<QrMvpView> presenter) {
        return presenter;
    }

    @Provides
    FragmentAdapter provideFragmentAdapter(AppCompatActivity activity) {
        return new FragmentAdapter(activity.getSupportFragmentManager());
    }

    @Provides
    TypeCreationAdapter provideTypeCreationAdapter() {
        return new TypeCreationAdapter(new ArrayList<TypeItem>());
    }

    @Provides
    TypeCreatedAdapter provideTypeCreatedAdapter() {
        return new TypeCreatedAdapter(new ArrayList<TypeItem>());
    }

    @Provides
    ResultAdapter provideResultAdapter() {
        return new ResultAdapter(new ArrayList<ScanResult>());
    }

    @Provides
    ButtonsAdapter provideButtonsAdapter() {
        return new ButtonsAdapter(new ArrayList<TypeItem>());
    }

    @Provides
    HistoryCardsAdapter provideHistoryCardsAdapter() {
        return new HistoryCardsAdapter(new ArrayList<History>());
    }

    @Provides
    CreationFieldsAdapter provideCreationFieldsAdapter() {
        return new CreationFieldsAdapter(new ArrayList<String>());
    }

    @Provides
    LinearLayoutManager provideLinearLayoutManager(AppCompatActivity activity) {
        return new LinearLayoutManager(activity);
    }

    @Provides
    GridLayoutManager provideGridLayoutManager(AppCompatActivity activity) {
        return new GridLayoutManager(activity, 2);
    }
}
