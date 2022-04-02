package com.qrcodegenerator.creation.data.prefs;

import android.content.Context;
import android.content.SharedPreferences;

import com.qrcodegenerator.creation.di.ApplicationContext;
import com.qrcodegenerator.creation.di.PreferenceInfo;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class AppPreferencesHelper implements PreferencesHelper {

    private static final String PREF_KEY_SCAN_SCREEN_IN_FOCUS = "PREF_KEY_SCAN_SCREEN_IN_FOCUS";

    private final SharedPreferences mPrefs;

    @Inject
    public AppPreferencesHelper(@ApplicationContext Context context,
                                @PreferenceInfo String prefFileName) {
        mPrefs = context.getSharedPreferences(prefFileName, Context.MODE_PRIVATE);
    }

    @Override
    public void setScanScreenInFocus(boolean state) {
        mPrefs.edit().putBoolean(PREF_KEY_SCAN_SCREEN_IN_FOCUS, state).apply();
    }

    @Override
    public boolean getScaneScreenInFocus() {
        return mPrefs.getBoolean(PREF_KEY_SCAN_SCREEN_IN_FOCUS, true);
    }
}
