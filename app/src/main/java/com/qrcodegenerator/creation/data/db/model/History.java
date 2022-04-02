package com.qrcodegenerator.creation.data.db.model;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class History {

    @PrimaryKey(autoGenerate = true)
    public long id;

    public int number;
    public String date;
    public String type;
    public String format;
    public String text;
    public String uri;
    public String phone;
    public String phoneTwo;
    public String email;
    public String message;
    public String name;
    public String position;
    public String company;
    public String address;
    public String description;
    public String ssid;
    public String password;
    public long startTime;
    public long endTime;
    public String organizer;
    public double latitude;
    public double longitude;
    public String networkEncryption;
    public String hidden;
    public boolean isCreate;
    public String qrText;
}
