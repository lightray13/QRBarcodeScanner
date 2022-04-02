package com.qrcodegenerator.creation.utils;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.provider.CalendarContract;
import android.provider.ContactsContract;
import android.text.TextUtils;
import android.widget.Toast;

import com.qrcodegenerator.creation.R;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Locale;

public final class ActionsUtils {

    private ActionsUtils() {
    }

    public static void openMap(Context context, double lat, double lon) {
        String uri = String.format(Locale.ENGLISH, "geo:%f,%f", lat, lon);
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
        context.startActivity(intent);
    }

    public static void search(Context context, String text) {
        String query = null;
        try {
            query = URLEncoder.encode(text, "utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        String url = "http://www.google.com/search?q=" + query;
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse(url));
        context.startActivity(intent);
    }

    public static void copy(Context context, String text) {
        ClipboardManager clipboard = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
        ClipData clip = ClipData.newPlainText("", text);
        if (clipboard != null) {
            clipboard.setPrimaryClip(clip);
            Toast.makeText(context, context.getResources().getString(R.string.text_coppied), Toast.LENGTH_SHORT).show();
        }
    }

    public static void share(Context context, String text){
        Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
        sharingIntent.setType("text/plain");
        sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, context.getResources().getString(R.string.app_name));
        sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, text);
        context.startActivity(Intent.createChooser(sharingIntent, "Share"));
    }

    public static void openUrl(Context context, String uri) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse(uri));
        context.startActivity(intent);
    }

    public static void dialNumber(Context context, String number){
        Uri phoneNumber = Uri.parse("tel:" + number);
        Intent callIntent = new Intent(Intent.ACTION_DIAL, phoneNumber);
        context.startActivity(callIntent);
    }

    public static void sendSms(Context context, String phoneNumber, String message){
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("sms:" + phoneNumber));
        if (!TextUtils.isEmpty(message)) {
            intent.putExtra("sms_body", message);
        }
        context.startActivity(intent);
    }

    public static void sendEmail(Context context, String email, String subject, String body){
        Intent i = new Intent(Intent.ACTION_SEND);
        i.setType("message/rfc822");
        i.putExtra(Intent.EXTRA_EMAIL  , new String[]{email});
        if (!TextUtils.isEmpty(subject)) {
            i.putExtra(Intent.EXTRA_SUBJECT, subject);
        }
        if (!TextUtils.isEmpty(body)) {
            i.putExtra(Intent.EXTRA_TEXT, body);
        }
        try {
            context.startActivity(Intent.createChooser(i, "Send mail"));
        } catch (android.content.ActivityNotFoundException ex) {
            Toast.makeText(context, context.getResources().getString(R.string.no_email_clients), Toast.LENGTH_SHORT).show();
        }
    }

    public static void addContact(Context context, String name, String position, String company, String phoneOne, String phoneTwo, String email,
                                  String address, String url){

        Intent intent = new Intent(Intent.ACTION_INSERT);
        intent.setType(ContactsContract.Contacts.CONTENT_TYPE);

        if (!TextUtils.isEmpty(name)) {
            intent.putExtra(ContactsContract.Intents.Insert.NAME, name);
        }
        if (!TextUtils.isEmpty(position)) {
            intent.putExtra(ContactsContract.Intents.Insert.JOB_TITLE, position);
        }
        if (!TextUtils.isEmpty(company)) {
            intent.putExtra(ContactsContract.Intents.Insert.COMPANY, company);
        }
        if (!TextUtils.isEmpty(phoneOne)) {
            intent.putExtra(ContactsContract.Intents.Insert.PHONE, phoneOne);
        }
        if (!TextUtils.isEmpty(phoneTwo)) {
            intent.putExtra(ContactsContract.Intents.Insert.SECONDARY_PHONE, phoneTwo);
        }
        if (!TextUtils.isEmpty(email)) {
            intent.putExtra(ContactsContract.Intents.Insert.EMAIL, email);
        }
        if (!TextUtils.isEmpty(address)) {
            intent.putExtra(ContactsContract.Intents.Insert.POSTAL, address);
        }
        if (!TextUtils.isEmpty(url)) {
            ArrayList<ContentValues> data = new ArrayList<ContentValues>();
            ContentValues row = new ContentValues();
            row.put(ContactsContract.Data.MIMETYPE, ContactsContract.CommonDataKinds.Website.CONTENT_ITEM_TYPE);
            row.put(ContactsContract.CommonDataKinds.Website.URL, url);
            row.put(ContactsContract.CommonDataKinds.Website.TYPE, ContactsContract.CommonDataKinds.Website.TYPE_HOME);
            data.add(row);
            intent.putExtra(ContactsContract.Intents.Insert.DATA, data);
        }

        context.startActivity(intent);
    }

    public static void addEvent(Context context, String title, String description, String location, long startTime, long endTime){
        Intent intent = new Intent(Intent.ACTION_INSERT)
                .setData(CalendarContract.Events.CONTENT_URI)
                .putExtra(CalendarContract.EXTRA_EVENT_BEGIN_TIME, startTime)
                .putExtra(CalendarContract.EXTRA_EVENT_END_TIME, endTime);
        if (!TextUtils.isEmpty(title)) {
            intent.putExtra(CalendarContract.Events.TITLE, title);
        }
        if (!TextUtils.isEmpty(description)) {
            intent.putExtra(CalendarContract.Events.DESCRIPTION, description);
        }
        if (!TextUtils.isEmpty(location)) {
            intent.putExtra(CalendarContract.Events.EVENT_LOCATION, location);
        }
        context.startActivity(intent);
    }
}
