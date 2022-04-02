package com.qrcodegenerator.creation.utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.text.TextUtils;

import com.qrcodegenerator.creation.R;
import com.qrcodegenerator.creation.data.db.model.History;
import com.qrcodegenerator.creation.ui.scanning.ScanResult;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public final class FieldsUtils {

    private FieldsUtils() {

    }

    private static boolean isTextEmpty(String str) {
        return !TextUtils.isEmpty(str);
    }

    @SuppressLint("SimpleDateFormat")
    private static String getDateTime(long time) {
        return new SimpleDateFormat("dd/MM/yyyy HH:mm").format(new Date(time));
    }

    public static List<ScanResult> getResultsList(Context context, History history) {
        switch (history.type) {
            case AppConstants.TEXT:
                return getTextResults(context, history);
            case AppConstants.URI:
                return getUriResults(context, history);
            case AppConstants.TEL:
                return getTelResults(context, history);
            case AppConstants.ADDRESSBOOK:
                return getAddressBookResults(context, history);
            case AppConstants.EMAIL_ADDRESS:
                return getEmailAddressResults(context, history);
            case AppConstants.SMS:
                return getSmsResults(context, history);
            case AppConstants.GEO:
                return getGeoResults(context, history);
            case AppConstants.WIFI:
                return getWifiResults(context, history);
            case AppConstants.PRODUCT:
                return getProductResults(context, history);
            case AppConstants.CALENDAR:
                return getCalendarResults(context, history);
            case AppConstants.ISBN:
                return getIsbnResults(context, history);
            default:
                return getTextResults(context, history);
        }
    }

    public static List<String> getEtFieldsList(Context context, int type) {
        switch (type) {
            case 1:
                return getTextEt(context);
            case 2:
                return getUrlEt(context);
            case 3:
                return getTelEt(context);
            case 4:
                return getAddressBookEt(context);
            case 5:
                return getEmailAddressEt(context);
            case 6:
                return getSmsEt(context);
            case 7:
                return getGeoEt(context);
            case 8:
                return getWifiEt(context);
//            case 9:
//                return getProductEt(context);
//            case 10:
//                return getCalendarEt(context);
            default:
                return getTextEt(context);
        }
    }

    private static List<ScanResult> getTextResults(Context context, History history) {
        List<ScanResult> results = new ArrayList<>();
        if (isTextEmpty(history.text)) {
            results.add(new ScanResult(context.getResources().getString(R.string.field_text), history.text));
        }
        return results;
    }

    private  static List<ScanResult> getUriResults(Context context, History history) {
        List<ScanResult> results = new ArrayList<>();
        if (isTextEmpty(history.uri)) {
            results.add(new ScanResult(context.getResources().getString(R.string.field_url), history.uri));
        }
        return results;
    }

    private static List<ScanResult> getTelResults(Context context, History history) {
        List<ScanResult> results = new ArrayList<>();
        if (isTextEmpty(history.phone)) {
            results.add(new ScanResult(context.getResources().getString(R.string.field_phone_number_one), history.phone));
        }
        return results;
    }

    private static List<ScanResult> getAddressBookResults(Context context, History history) {
        List<ScanResult> results = new ArrayList<>();
        if (isTextEmpty(history.name)) {
            results.add(new ScanResult(context.getResources().getString(R.string.field_name), history.name));
        }
        if (isTextEmpty(history.position)) {
            results.add(new ScanResult(context.getResources().getString(R.string.field_position), history.position));
        }
        if (isTextEmpty(history.company)) {
            results.add(new ScanResult(context.getResources().getString(R.string.field_company), history.company));
        }
        if (isTextEmpty(history.phone)) {
            results.add(new ScanResult(context.getResources().getString(R.string.field_phone_number_one), history.phone));
        }
        if (isTextEmpty(history.phoneTwo)) {
            results.add(new ScanResult(context.getResources().getString(R.string.field_phone_number_two), history.phoneTwo));
        }
        if (isTextEmpty(history.email)) {
            results.add(new ScanResult(context.getResources().getString(R.string.email), history.email));
        }
        if (isTextEmpty(history.address)) {
            results.add(new ScanResult(context.getResources().getString(R.string.field_address), history.address));
        }
        if (isTextEmpty(history.uri)) {
            results.add(new ScanResult(context.getResources().getString(R.string.field_url), history.uri));
        }
        return results;
    }

    private static List<ScanResult> getEmailAddressResults(Context context, History history) {
        List<ScanResult> results = new ArrayList<>();
        if (isTextEmpty(history.email)) {
            results.add(new ScanResult(context.getResources().getString(R.string.field_your_email), history.email));
        }
        if (isTextEmpty(history.name)) {
            results.add(new ScanResult(context.getResources().getString(R.string.field_subject), history.name));
        }
        if (isTextEmpty(history.message)) {
            results.add(new ScanResult(context.getResources().getString(R.string.field_message), history.message));
        }
        return results;
    }

    private static List<ScanResult> getSmsResults(Context context, History history) {
        List<ScanResult> results = new ArrayList<>();
        if (isTextEmpty(history.phone)) {
            results.add(new ScanResult(context.getResources().getString(R.string.field_phone_number_one), history.phone));
        }
        if (isTextEmpty(history.message)) {
            results.add(new ScanResult(context.getResources().getString(R.string.field_message), history.message));
        }
        return results;
    }

    private static List<ScanResult> getGeoResults(Context context, History history) {
        List<ScanResult> results = new ArrayList<>();
        try {
            String lat = String.valueOf(history.latitude);
            String lon = String.valueOf(history.longitude);
            results.add(new ScanResult(context.getResources().getString(R.string.field_latitude), lat));
            results.add(new ScanResult(context.getResources().getString(R.string.field_longitude), lon));
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
//        if (isTextEmpty(history.latitude)) {
//            results.add(new ScanResult(context.getResources().getString(R.string.field_latitude), history.latitude));
//        }
//        if (isTextEmpty(history.longitude)) {
//            results.add(new ScanResult(context.getResources().getString(R.string.field_longitude), history.longitude));
//        }
        return results;
    }

    private static List<ScanResult> getWifiResults(Context context, History history) {
        List<ScanResult> results = new ArrayList<>();
        if (isTextEmpty(history.ssid)) {
            results.add(new ScanResult(context.getResources().getString(R.string.field_ssid), history.ssid));
        }
        if (isTextEmpty(history.password)) {
            results.add(new ScanResult(context.getResources().getString(R.string.field_password), history.password));
        }
        if (isTextEmpty(history.networkEncryption)) {
            results.add(new ScanResult(context.getResources().getString(R.string.field_network_encryption), history.networkEncryption));
        }
        if (isTextEmpty(history.hidden)) {
            results.add(new ScanResult(context.getResources().getString(R.string.field_hidden), history.hidden));
        }
        return results;
    }

    private static List<ScanResult> getProductResults(Context context, History history) {
        List<ScanResult> results = new ArrayList<>();
        if (isTextEmpty(history.text)) {
            results.add(new ScanResult(context.getResources().getString(R.string.field_product_code), history.text));
        }
        return results;
    }

    private static List<ScanResult> getCalendarResults(Context context, History history) {
        List<ScanResult> results = new ArrayList<>();
        if (isTextEmpty(history.name)) {
            results.add(new ScanResult(context.getResources().getString(R.string.field_event_title), history.name));
        }
        if (isTextEmpty(history.description)) {
            results.add(new ScanResult(context.getResources().getString(R.string.field_description), history.description));
        }
        if (isTextEmpty(history.address)) {
            results.add(new ScanResult(context.getResources().getString(R.string.field_event_location), history.address));
        }
        if (history.startTime > 0L) {
            String startTimestamp = getDateTime(history.startTime);
            if (isTextEmpty(startTimestamp)) {
                results.add(new ScanResult(context.getResources().getString(R.string.field_starttime), startTimestamp));
            }
        }
        if (history.endTime > 0L) {
            String endTimestamp = getDateTime(history.endTime);
            if (isTextEmpty(endTimestamp)) {
                results.add(new ScanResult(context.getResources().getString(R.string.field_endtime), endTimestamp));
            }
        }
        if (isTextEmpty(history.organizer)) {
            results.add(new ScanResult(context.getResources().getString(R.string.field_organizer), history.organizer));
        }
        return results;
    }

    private static List<ScanResult> getIsbnResults(Context context, History history) {
        List<ScanResult> results = new ArrayList<>();
        if (isTextEmpty(history.text)) {
            results.add(new ScanResult(context.getResources().getString(R.string.field_isbn), history.text));
        }
        return results;
    }

    private static List<String> getTextEt(Context context) {
        List<String> ets = new ArrayList<>();
        ets.add(context.getResources().getString(R.string.field_text));
        return ets;
    }

    private static List<String> getUrlEt(Context context) {
        List<String> ets = new ArrayList<>();
        ets.add(context.getResources().getString(R.string.field_url));
        return ets;
    }

    private static List<String> getTelEt(Context context) {
        List<String> ets = new ArrayList<>();
        ets.add(context.getResources().getString(R.string.field_phone_number_one));
        return ets;
    }

    private static List<String> getAddressBookEt(Context context) {
        List<String> ets = new ArrayList<>();
        ets.add(context.getResources().getString(R.string.field_name));
//        ets.add(context.getResources().getString(R.string.field_position));
        ets.add(context.getResources().getString(R.string.field_company));
        ets.add(context.getResources().getString(R.string.field_phone_number_one));
        ets.add(context.getResources().getString(R.string.email));
        ets.add(context.getResources().getString(R.string.field_address));
        ets.add(context.getResources().getString(R.string.field_url));
        ets.add(context.getResources().getString(R.string.field_note));
        return ets;
    }

    private static List<String> getEmailAddressEt(Context context) {
        List<String> ets = new ArrayList<>();
        ets.add(context.getResources().getString(R.string.field_your_email));
//        ets.add(context.getResources().getString(R.string.field_subject));
//        ets.add(context.getResources().getString(R.string.field_message));
        return ets;
    }

    private static List<String> getSmsEt(Context context) {
        List<String> ets = new ArrayList<>();
        ets.add(context.getResources().getString(R.string.field_phone_number_one));
        ets.add(context.getResources().getString(R.string.field_message));
        return ets;
    }

    private static List<String> getGeoEt(Context context) {
        List<String> ets = new ArrayList<>();
        ets.add(context.getResources().getString(R.string.field_latitude));
        ets.add(context.getResources().getString(R.string.field_longitude));
//        ets.add(context.getResources().getString(R.string.field_query));
        return ets;
    }

    private static List<String> getWifiEt(Context context) {
        List<String> ets = new ArrayList<>();
        ets.add(context.getResources().getString(R.string.field_ssid));
        ets.add(context.getResources().getString(R.string.field_password));
        ets.add(context.getResources().getString(R.string.field_network_encryption));
        ets.add(context.getResources().getString(R.string.field_hidden));//?
        return ets;
    }

    private static List<String> getProductEt(Context context) {
        List<String> ets = new ArrayList<>();
        ets.add(context.getResources().getString(R.string.field_product_code));
        return ets;
    }

    private static List<String> getCalendarEt(Context context) {
        List<String> ets = new ArrayList<>();
        ets.add(context.getResources().getString(R.string.field_event_title));
        ets.add(context.getResources().getString(R.string.field_description));
        ets.add(context.getResources().getString(R.string.field_event_location));
        ets.add(context.getResources().getString(R.string.field_starttime));//?
        ets.add(context.getResources().getString(R.string.field_endtime));//?
        ets.add(context.getResources().getString(R.string.field_organizer));
        return ets;
    }
}
