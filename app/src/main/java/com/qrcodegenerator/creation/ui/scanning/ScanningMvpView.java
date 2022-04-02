package com.qrcodegenerator.creation.ui.scanning;

import com.qrcodegenerator.creation.data.db.model.History;
import com.qrcodegenerator.creation.ui.base.MvpView;

public interface ScanningMvpView extends MvpView {

    void setupPermission();
    void backToMainActivity();
    void setHeader(String type);
    void setButtons(History history);
    void setFields(History history);
    void setQrImage(History history);
    void openMap(double latitude, double longitude);
    void addContact(String name, String position, String company, String phone, String phoneTwo, String email, String location, String url);
    void dialPhone(String phone);
    void sendSms(String phone, String message);
    void sendEmail(String email, String title, String message);
    void openUrl(String url);
    void addEvent(String title, String description, String location, long startTime, long endTime);
    void search(String text);
    void share(String text);
    void copy(String text);
    void connectToWifi(String ssid, String password);

}
