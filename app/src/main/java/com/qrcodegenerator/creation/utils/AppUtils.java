package com.qrcodegenerator.creation.utils;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;

import com.qrcodegenerator.creation.R;

public final class AppUtils {

    private AppUtils() {
    }

    public static void shareApp(Context context) {
        final String appPackageName = context.getPackageName();
        Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
        sharingIntent.setType("text/plain");
        String shareBody = context.getString(R.string.app_name)
                + " app download now. "
                + context.getString(R.string.app_google_play_link)
                + appPackageName;
        sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, context.getString(R.string.share_app));
        sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, shareBody);
        context.startActivity(Intent.createChooser(sharingIntent, context.getString(R.string.share)));
    }

    public static void rateApp(Context context) {
        final String appPackageName = context.getPackageName();
        Uri uri = Uri.parse(context.getString(R.string.app_google_play_prelink)
                + appPackageName);
        Intent goToMarket = new Intent(Intent.ACTION_VIEW, uri);
        goToMarket.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY |
                Intent.FLAG_ACTIVITY_NEW_DOCUMENT |
                Intent.FLAG_ACTIVITY_MULTIPLE_TASK);
        try {
            context.startActivity(goToMarket);
        } catch (ActivityNotFoundException e) {
            context.startActivity(new Intent(Intent.ACTION_VIEW,
                    Uri.parse(context.getString(R.string.app_google_play_link)
                            + appPackageName)));
        }
    }
}
