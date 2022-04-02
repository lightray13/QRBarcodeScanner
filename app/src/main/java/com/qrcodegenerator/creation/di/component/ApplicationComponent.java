package com.qrcodegenerator.creation.di.component;

import android.app.Application;
import android.content.Context;

import com.qrcodegenerator.creation.MyApp;
import com.qrcodegenerator.creation.data.DataManager;
import com.qrcodegenerator.creation.di.ApplicationContext;
import com.qrcodegenerator.creation.di.module.ApplicationModule;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = ApplicationModule.class)
public interface ApplicationComponent {

    void inject(MyApp app);

    @ApplicationContext
    Context context();

    Application application();

    DataManager getDataManager();
}
