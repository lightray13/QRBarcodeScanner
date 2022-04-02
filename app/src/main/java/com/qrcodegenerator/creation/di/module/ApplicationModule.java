package com.qrcodegenerator.creation.di.module;

import android.app.Application;
import android.content.Context;

import com.qrcodegenerator.creation.R;
import com.qrcodegenerator.creation.data.AppDataManager;
import com.qrcodegenerator.creation.data.DataManager;
import com.qrcodegenerator.creation.data.bus.AppBusHelper;
import com.qrcodegenerator.creation.data.bus.BusHelper;
import com.qrcodegenerator.creation.data.bus.model.RxBus;
import com.qrcodegenerator.creation.data.db.AppDatabase;
import com.qrcodegenerator.creation.data.db.AppDbHelper;
import com.qrcodegenerator.creation.data.db.DbHelper;
import com.qrcodegenerator.creation.data.db.dao.HistoryDao;
import com.qrcodegenerator.creation.data.prefs.AppPreferencesHelper;
import com.qrcodegenerator.creation.data.prefs.PreferencesHelper;
import com.qrcodegenerator.creation.di.ApplicationContext;
import com.qrcodegenerator.creation.di.PreferenceInfo;
import com.qrcodegenerator.creation.utils.AppConstants;

import javax.inject.Singleton;

import androidx.room.Room;
import dagger.Module;
import dagger.Provides;
import uk.co.chrisjenx.calligraphy.CalligraphyConfig;

@Module
public class ApplicationModule {

    private final Application mApplication;

    public ApplicationModule(Application mApplication) {
        this.mApplication = mApplication;
    }

    @Provides
    @ApplicationContext
    Context provideContext() {
        return mApplication;
    }

    @Provides
    Application provideApplication() {
        return mApplication;
    }

    @Provides
    @Singleton
    DataManager provideDataManager(AppDataManager appDataManager) {
        return appDataManager;
    }

    @Provides
    @Singleton
    CalligraphyConfig provideCalligraphyDefaultConfig() {
        return new CalligraphyConfig.Builder()
                .setDefaultFontPath("fonts/MuliRegular.ttf")
                .setFontAttrId(R.attr.fontPath)
                .build();
    }

    @Provides
    @PreferenceInfo
    String providePreferenceName() {
        return AppConstants.PREF_NAME;
    }

    @Provides
    @Singleton
    AppDatabase provideDatabase(@ApplicationContext Context context) {
        return Room.databaseBuilder(context, AppDatabase.class, "database")
                .allowMainThreadQueries()
                .build();
    }

    @Provides
    @Singleton
    HistoryDao provideHistoryDao(AppDatabase appDatabase) {
        return appDatabase.historyDao();
    }

    @Provides
    @Singleton
    DbHelper provideDbHelper(AppDbHelper appDbHelper) {
        return appDbHelper;
    }

    @Provides
    @Singleton
    PreferencesHelper providePreferencesHelper(AppPreferencesHelper appPreferencesHelper) {
        return appPreferencesHelper;
    }

    @Provides
    @Singleton
    BusHelper provideBusHelper(AppBusHelper appBusHelper) {
        return appBusHelper;
    }

    @Provides
    @Singleton
    RxBus provideRxBus() {
        return new RxBus();
    }

}
