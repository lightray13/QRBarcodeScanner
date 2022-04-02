package com.qrcodegenerator.creation.di.component;

import com.qrcodegenerator.creation.di.PerActivity;
import com.qrcodegenerator.creation.di.module.ActivityModule;
import com.qrcodegenerator.creation.ui.creation.CreationActivity;
import com.qrcodegenerator.creation.ui.main.MainActivity;
import com.qrcodegenerator.creation.ui.main.create.CreateFragment;
import com.qrcodegenerator.creation.ui.main.created.CreatedFragment;
import com.qrcodegenerator.creation.ui.main.scan.ScanFragment;
import com.qrcodegenerator.creation.ui.main.scanned.ScannedFragment;
import com.qrcodegenerator.creation.ui.qr.QrActivity;
import com.qrcodegenerator.creation.ui.scanning.ScanningActivity;
import com.qrcodegenerator.creation.ui.splash.SplashActivity;

import dagger.Component;

@PerActivity
@Component(dependencies = ApplicationComponent.class, modules = ActivityModule.class)
public interface ActivityComponent {

    void inject(SplashActivity activity);

    void inject(MainActivity activity);

    void inject(ScanFragment fragment);

    void inject(CreateFragment fragment);

    void inject(ScannedFragment fragment);

    void inject(CreatedFragment fragment);

    void inject(ScanningActivity activity);

    void inject(CreationActivity activity);

    void inject(QrActivity activity);
}
