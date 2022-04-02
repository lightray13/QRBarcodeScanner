package com.qrcodegenerator.creation.utils;

import android.text.TextUtils;

import com.google.zxing.Result;
import com.google.zxing.client.result.AddressBookParsedResult;
import com.google.zxing.client.result.CalendarParsedResult;
import com.google.zxing.client.result.EmailAddressParsedResult;
import com.google.zxing.client.result.GeoParsedResult;
import com.google.zxing.client.result.ISBNParsedResult;
import com.google.zxing.client.result.ParsedResult;
import com.google.zxing.client.result.ProductParsedResult;
import com.google.zxing.client.result.ResultParser;
import com.google.zxing.client.result.SMSParsedResult;
import com.google.zxing.client.result.TelParsedResult;
import com.google.zxing.client.result.TextParsedResult;
import com.google.zxing.client.result.URIParsedResult;
import com.google.zxing.client.result.WifiParsedResult;
import com.qrcodegenerator.creation.data.db.model.History;

import java.text.DateFormat;
import java.util.Calendar;

public final class ParsedResultUtils {

    private ParsedResultUtils() {
    }

    private static ParsedResult getParsedResult(Result rawResult) {
        return ResultParser.parseResult(rawResult);
    }

    private static boolean isTextEmpty(String str) {
        return !TextUtils.isEmpty(str);
    }

    private static boolean isArrayEmpty(String[] array) {
        return array != null && array.length > 0;
    }

    public static History getResultsHistory(Result rawResult) {
        History history = new History();
        String type = ResultParser.parseResult(rawResult).getType().name();
        String format = rawResult.getBarcodeFormat().name();
        history.type = type;
        history.format = format;
        history.date = DateFormat.getDateTimeInstance().format(Calendar.getInstance().getTime());
        history.isCreate = false;
        switch (type) {
            case AppConstants.TEXT:
                return getTextHistory(history, rawResult);
            case AppConstants.URI:
                return getUriHistory(history, rawResult);
            case AppConstants.TEL:
                return getTelHistory(history, rawResult);
            case AppConstants.ADDRESSBOOK:
                return getAddressBookHistory(history, rawResult);
            case AppConstants.EMAIL_ADDRESS:
                return getEmailAddressHistory(history, rawResult);
            case AppConstants.SMS:
                return getSmsHistory(history, rawResult);
            case AppConstants.GEO:
                return getGeoHistory(history, rawResult);
            case AppConstants.WIFI:
                return getWifiHistory(history, rawResult);
            case AppConstants.PRODUCT:
                return getProductHistory(history, rawResult);
            case AppConstants.CALENDAR:
                return getCalendarHistory(history, rawResult);
            case AppConstants.ISBN:
                return getIsbnHistory(history, rawResult);
            default:
                return getTextHistory(history, rawResult);
        }
    }

    private static History getTextHistory(History history, Result rawResult) {
        TextParsedResult parsedResult = (TextParsedResult) getParsedResult(rawResult);
        String text = parsedResult.getText();
        if (isTextEmpty(text)) {
            history.text = text;
            history.qrText = text;
        }
        return history;
    }

    private  static History getUriHistory(History history, Result rawResult) {
        URIParsedResult parsedResult = (URIParsedResult) getParsedResult(rawResult);
        String uri = parsedResult.getURI();
        if (isTextEmpty(uri)) {
            history.text = uri;
            history.uri = uri;
            history.qrText = uri;
        }
        return history;
    }

    private static History getTelHistory(History history, Result rawResult) {
        TelParsedResult parsedResult = (TelParsedResult) getParsedResult(rawResult);
        String phone = parsedResult.getNumber();
        String qrText = rawResult.getText();
        if (isTextEmpty(phone)) {
            history.text = phone;
            history.phone = phone;
        }
        if (isTextEmpty(qrText)) {
            history.qrText = qrText;
        }
        return history;
    }

    private static History getAddressBookHistory(History history, Result rawResult) {
        AddressBookParsedResult parsedResult = (AddressBookParsedResult)getParsedResult(rawResult);
        String position = parsedResult.getTitle();
        String company = parsedResult.getOrg();
        String displayResult = parsedResult.getDisplayResult();
        String qrText = rawResult.getText();
        String[] names = parsedResult.getNames();
        String[] phones = parsedResult.getPhoneNumbers();
        String[] mails = parsedResult.getEmails();
        String[] addresses = parsedResult.getAddresses();
        String[] contactUries = parsedResult.getURLs();
        if (names != null && names.length > 0) {
            String name = names[0];
            if (isTextEmpty(name)) {
                history.name = name;
            }
        }
        if (isTextEmpty(position)) {
            history.position = position;
        }
        if (isTextEmpty(company)) {
            history.company = company;
        }
        if (isArrayEmpty(phones)) {
            String phoneOne = phones[0];
            if (isTextEmpty(phoneOne)) {
                history.phone = phoneOne;
            }
            if (phones.length > 1) {
                String phoneTwo = phones[1];
                if (isTextEmpty(phoneTwo)) {
                    history.phoneTwo = phoneTwo;
                }
            }
        }
        if (isArrayEmpty(mails)) {
            String mail = mails[0];
            if (isTextEmpty(mail)) {
                history.email = mail;
            }
        }
        if (isArrayEmpty(addresses)) {
            String address = addresses[0];
            if (isTextEmpty(address)) {
                history.address = address;
            }
        }
        if (isArrayEmpty(contactUries)) {
            String contactUri = contactUries[0];
            if (isTextEmpty(contactUri)) {
                history.uri = contactUri;
            }
        }
        if (isTextEmpty(displayResult)) {
            history.text = displayResult;
        }
        if (isTextEmpty(qrText)) {
            history.qrText = qrText;
        }
        return history;
    }

    private static History getEmailAddressHistory(History history, Result rawResult) {
        EmailAddressParsedResult parsedResult = (EmailAddressParsedResult) getParsedResult(rawResult);
        String email = parsedResult.getEmailAddress();
        String subject = parsedResult.getSubject();
        String body = parsedResult.getBody();
        String displayResult = parsedResult.getDisplayResult();
        String qrText = rawResult.getText();
        if (isTextEmpty(email)) {
            history.email = email;
        }
        if (isTextEmpty(subject)) {
            history.name = subject;
        }
        if (isTextEmpty(body)) {
            history.message = body;
        }
        if (isTextEmpty(displayResult)) {
            history.text = displayResult;
        }
        if (isTextEmpty(qrText)) {
            history.qrText = qrText;
        }
        return history;
    }

    private static History getSmsHistory(History history, Result rawResult) {
        SMSParsedResult parsedResult = (SMSParsedResult) getParsedResult(rawResult);
        String[] numbers = parsedResult.getNumbers();
        String smsBody = parsedResult.getBody();
        String displayResult = parsedResult.getDisplayResult();
        String qrText = rawResult.getText();
        if (isArrayEmpty(numbers)) {
            String number = numbers[0];
            if (isTextEmpty(number)) {
                history.phone = number;
            }
        }
        if (isTextEmpty(smsBody)) {
            history.message = smsBody;
        }
        if (isTextEmpty(displayResult)) {
            history.text = displayResult;
        }
        if (isTextEmpty(qrText)) {
            history.qrText = qrText;
        }
        return history;
    }

    private static History getGeoHistory(History history, Result rawResult) {
        GeoParsedResult parsedResult = (GeoParsedResult) getParsedResult(rawResult);
        double lat = parsedResult.getLatitude();
        double lon = parsedResult.getLongitude();
        history.latitude = lat;
        history.longitude = lon;
        String displayResult = parsedResult.getDisplayResult();
        String qrText = rawResult.getText();
        if (isTextEmpty(displayResult)) {
            history.text = displayResult;
        }
        if (isTextEmpty(qrText)) {
            history.qrText = qrText;
        }
        return history;
    }

    private static History getWifiHistory(History history, Result rawResult) {
        WifiParsedResult parsedResult = (WifiParsedResult) getParsedResult(rawResult);
        String ssid = parsedResult.getSsid();
        String password = parsedResult.getPassword();
        String networkEncryption = parsedResult.getNetworkEncryption();
        boolean isHidden = parsedResult.isHidden();
        String displayResult = parsedResult.getDisplayResult();
        String qrText = rawResult.getText();
        if (isTextEmpty(ssid)) {
            history.ssid = ssid;
        }
        if (isTextEmpty(password)) {
            history.password = password;
        }
        if (isTextEmpty(networkEncryption)) {
            history.networkEncryption = networkEncryption;
        }
        String hidden = "No";
        if (isHidden) {
            hidden = "Yes";
        }
        history.hidden = hidden;
        if (isTextEmpty(displayResult)) {
            history.text = displayResult;
        }
        if (isTextEmpty(qrText)) {
            history.qrText = qrText;
        }
        return history;
    }

    private static History getProductHistory(History history, Result rawResult) {
        ProductParsedResult parsedResult = (ProductParsedResult) getParsedResult(rawResult);
        String product = parsedResult.getProductID();
        String qrText = rawResult.getText();
        if (isTextEmpty(product)) {
            history.text = product;
        }
        if (isTextEmpty(qrText)) {
            history.qrText = qrText;
        }
        return history;
    }

    private static History getCalendarHistory(History history, Result rawResult) {
        CalendarParsedResult parsedResult = (CalendarParsedResult) getParsedResult(rawResult);
        String summary = parsedResult.getSummary();
        String location = parsedResult.getLocation();
        String description = parsedResult.getDescription();
        long startTimestamp = parsedResult.getStartTimestamp();
        long endTimestamp = parsedResult.getEndTimestamp();
        String organizer = parsedResult.getOrganizer();
        String qrText = rawResult.getText();
        if (isTextEmpty(summary)) {
            history.name = summary;
        }
        if (isTextEmpty(description)) {
            history.description = description;
        }
        if (isTextEmpty(location)) {
            history.address = location;
        }
        if (startTimestamp > 0L) {
            history.startTime = startTimestamp;
        }
        if (endTimestamp > 0L) {
            history.endTime = endTimestamp;
        }
        if (isTextEmpty(organizer)) {
            history.organizer = organizer;
        }
        if (isTextEmpty(qrText)) {
            history.qrText = qrText;
        }
        return history;
    }

    private static History getIsbnHistory(History history, Result rawResult) {
        ISBNParsedResult parsedResult = (ISBNParsedResult) getParsedResult(rawResult);
        String isbn = parsedResult.getISBN();
        String qrText = rawResult.getText();
        if (isTextEmpty(isbn)) {
            history.text = isbn;
        }
        if (isTextEmpty(qrText)) {
            history.qrText = qrText;
        }
        return history;
    }
}

