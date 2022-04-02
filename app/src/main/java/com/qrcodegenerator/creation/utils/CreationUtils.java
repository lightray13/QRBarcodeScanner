package com.qrcodegenerator.creation.utils;

import android.text.TextUtils;
import android.util.Patterns;
import android.webkit.URLUtil;

import com.qrcodegenerator.creation.R;
import com.qrcodegenerator.creation.data.db.model.History;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.List;

public final class CreationUtils {

    private CreationUtils() {

    }

    private static boolean isTextEmpty(String str) {
        return !TextUtils.isEmpty(str);
    }

    private static boolean isValidEmail(String str) {
        return Patterns.EMAIL_ADDRESS.matcher(str).matches();
    }

    private static boolean isValidUrl(String str) {
        return URLUtil.isValidUrl(str);
    }

    public static History createCodeHistory(List<String> list, int position) {
        History history = new History();
        history.date = DateFormat.getDateTimeInstance().format(Calendar.getInstance().getTime());
        history.isCreate = true;
        history.format = AppConstants.QR_CODE;
        switch (position) {
            case 1:
                return createTextHistory(list, history);
            case 2:
                return createUrlHistory(list, history);
            case 3:
                return createTelHistory(list, history);
            case 4:
                return createAddressBookHistory(list, history);
            case 5:
                return createEmailAddressHistory(list, history);
            case 6:
                return createSmsHistory(list, history);
            case 7:
                return createGeoHistory(list, history);
            case 8:
                return createWifiHistory(list, history);
            default:
                return createTextHistory(list, history);

        }
    }

    private static History createTextHistory(List<String> texts, History history) {
        history.type = AppConstants.TEXT;
        if (isTextEmpty(texts.get(0))) {
            history.text = texts.get(0);
            history.qrText = texts.get(0);
        } else {
            history.number = R.string.error_empty_text;
        }
        return history;
    }

    private static History createUrlHistory(List<String> texts, History history) {
        history.type = AppConstants.URI;
        if (isTextEmpty(texts.get(0))) {
            if (isValidUrl(texts.get(0))) {
                history.text = texts.get(0);
                history.uri = texts.get(0);
                history.qrText = texts.get(0);
            } else {
                history.number = R.string.error_url_invalid;
                return history;
            }
        } else {
            history.number = R.string.error_empty_url;
            return history;
        }
        return history;
    }

    private static History createTelHistory(List<String> texts, History history) {
        history.type = AppConstants.TEL;
        if (isTextEmpty(texts.get(0))) {
            history.qrText = "tel:" + texts.get(0);
            history.text = texts.get(0);
            history.phone = texts.get(0);
        } else {
            history.number = R.string.error_empty_tel;
        }
        return history;
    }

    private static History createAddressBookHistory(List<String> texts, History history) {
        String strQr = "MECARD:";
        String str = "";
        history.type = AppConstants.ADDRESSBOOK;
        if (isTextEmpty(texts.get(0))) {
            history.name = texts.get(0);
            strQr = strQr + "N:" + texts.get(0) + ";";
            str = texts.get(0);
        } else {
            history.number = R.string.error_empty_name;
            return history;
        }
        if (isTextEmpty(texts.get(1))) {
            history.company = texts.get(1);
            strQr = strQr + "ORG:" + texts.get(1) + ";";
            str = str + "\n" + texts.get(1);
        }
        if (isTextEmpty(texts.get(2))) {
            history.phone = texts.get(2);
            strQr = strQr + "TEL:" + texts.get(2) + ";";
            str = str + "\n" + texts.get(2);
        }
        if (isTextEmpty(texts.get(3))) {
            if (isValidEmail(texts.get(3))) {
                history.email = texts.get(3);
                strQr = strQr + "EMAIL:" + texts.get(3) + ";";
                str = str + "\n" + texts.get(3);
            } else {
                history.number= R.string.error_email_invalid;
                return history;
            }
        }
        if (isTextEmpty(texts.get(4))) {
            history.address = texts.get(4);
            strQr = strQr + "ADR:" + texts.get(4) + ";";
            str = str + "\n" + texts.get(4);
        }
        if (isTextEmpty(texts.get(5))) {
            if (isValidUrl(texts.get(5))) {
                history.uri = texts.get(5);
                strQr = strQr + "URL::" + texts.get(5) + ";";
                str = str + "\n" + texts.get(5);
            } else {
                history.number = R.string.error_url_invalid;
                return history;
            }
        }
        if (isTextEmpty(texts.get(6))) {
            history.description = texts.get(6);
            strQr = strQr + "NOTE:" + texts.get(6) + ";";
            str = str + "\n" + texts.get(6);
        }
        strQr = strQr + ";";
        history.qrText = strQr;
        history.text = str;
        return history;
    }

    //?
    private static History createEmailAddressHistory(List<String> texts, History history) {
        history.type = AppConstants.EMAIL_ADDRESS;
        if (isTextEmpty(texts.get(0))) {
            if (isValidEmail(texts.get(0))) {
                history.qrText = "mailto:" + texts.get(0);
                history.email = texts.get(0);
                history.text = texts.get(0);
            } else {
                history.number = R.string.error_email_invalid;
                return history;
            }
        } else {
            history.number = R.string.error_empty_email;
            return history;
        }
        return history;
    }

    private static History createSmsHistory(List<String> texts, History history) {
        String strQr = "smsto:";
        String str = "";
        history.type = AppConstants.SMS;
        if (isTextEmpty(texts.get(0))) {
            history.phone = texts.get(0);
            strQr = strQr + texts.get(0);
            str = texts.get(0);
        } else {
            history.number = R.string.error_empty_tel;
            return history;
        }
        if (isTextEmpty(texts.get(1))) {
            history.message = texts.get(1);
            strQr = strQr + ":" + texts.get(1);
            str = str + "\n" + texts.get(1);
        }
        history.qrText = strQr;
        history.text = str;
        return history;
    }

    private static History createGeoHistory(List<String> texts, History history) {
        String strQr = "geo:";
        String str = "";
        history.type = AppConstants.GEO;
        if (isTextEmpty(texts.get(0))) {
            double latitude = Double.parseDouble(texts.get(0));
            if ( latitude >= -90 && latitude <= 90 ) {
                history.latitude = Double.parseDouble(texts.get(0));
                strQr = strQr + latitude;
                str = texts.get(0);
            } else {
                history.number = R.string.error_range_latitude;
                return history;
            }
        } else {
            history.number = R.string.error_empty_latitude;
            return history;
        }
        if (isTextEmpty(texts.get(1))) {
            double longitude = Double.parseDouble(texts.get(1));
            if ( longitude >= -180 && longitude <= 180) {
                history.longitude = Double.parseDouble(texts.get(1));
                strQr = strQr + "," + longitude;
                str = str + "\n" + texts.get(1);
            } else {
                history.number = R.string.error_range_longitude;
                return history;
            }
        } else {
            history.number = R.string.error_empty_longitude;
            return history;
        }
        history.text = str;
        history.qrText = strQr;
        return history;
    }

    private static History createWifiHistory(List<String> texts, History history) {
        String str = "";
        String strQr = "WIFI:S:";
        history.type = AppConstants.WIFI;
        if (isTextEmpty(texts.get(0))) {
            history.ssid = texts.get(0);
            strQr = strQr + texts.get(0) + ";";
            str = texts.get(0);
        } else {
            history.number = R.string.error_empty_ssid;
        }
        if (isTextEmpty(texts.get(1))) {
            history.password = texts.get(1);
            strQr = strQr + "P:" + texts.get(1) + ";";
            str = str + "\n" + texts.get(1);
        }
        if (isTextEmpty(texts.get(2))) {
            history.networkEncryption = texts.get(2);
            strQr = strQr + "T:" + texts.get(2) + ";";
            str = str + "\n" + texts.get(2);
        }
        if (isTextEmpty(texts.get(3))) {
            history.hidden = texts.get(3);
            if (texts.get(3).equals("Yes")) {
                strQr = strQr + "H:true;";
                str = str + "\ntrue";
            } else {
                str = str + "\nfalse";
            }
        }
        strQr = strQr + ";";
        history.text = str;
        history.qrText = strQr;
        return history;
    }
}
