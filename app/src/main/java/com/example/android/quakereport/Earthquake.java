package com.example.android.quakereport;

import java.util.Date;

public class Earthquake {

    private Double mQuakeMagnitude;
    private String mQuakePlace;
    private String mQuakeDate;
    private String mQuakeTime;
    private String mURL;

    /**
    * Create new Place Object without Image
    *
    * @param quakeMagnitude is the quake magnitude
    *
    * @param quakePlace is the quake place
    *
    * @param quakeDate is the quake date
    *
    */
    public Earthquake(Double quakeMagnitude, String quakePlace, Long quakeDate, String url) {
        mQuakeMagnitude = quakeMagnitude;
        mQuakePlace = quakePlace;
        mURL = url;

        Date date = new java.util.Date(quakeDate);
        android.text.format.DateFormat df = new android.text.format.DateFormat();
        mQuakeDate = df.format("dd/MM/yyyy h:mm a", date).toString();
        mQuakeTime = df.format("h:mm a", date).toString();
    }

    public Double getQuakeMagnitude() {
        return mQuakeMagnitude;
    }

    public String getQuakePlace() {
        return mQuakePlace;
    }

    public String getQuakeDate() {
        return mQuakeDate;
    }

    public String getQuakeTime() { return mQuakeTime; }

    public String getURL() { return mURL; }
}
