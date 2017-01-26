package com.mel.seekraces.entities;

import android.graphics.Bitmap;
import android.os.Parcel;
import android.os.Parcelable;

import java.io.File;
import java.util.Date;

/**
 * Created by void on 21/01/2017.
 */

public class Filter implements Parcelable{
    private String user;
    private String country;
    private String city;
    private String distance;
    private String date_interval_init;
    private String date_interval_end;

    public Filter(){

    }

    protected Filter(Parcel in) {
        user=in.readString();
        country = in.readString();
        city = in.readString();
        distance = in.readString();
        date_interval_init = in.readString();
        date_interval_end = in.readString();
    }

    public static final Creator<Filter> CREATOR = new Creator<Filter>() {
        @Override
        public Filter createFromParcel(Parcel in) {
            return new Filter(in);
        }

        @Override
        public Filter[] newArray(int size) {
            return new Filter[size];
        }
    };

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getDistance() {
        return distance;
    }

    public void setDistance(String distance) {
        this.distance = distance;
    }

    public String getDate_interval_init() {
        return date_interval_init;
    }

    public void setDate_interval_init(String date_interval_init) {
        this.date_interval_init = date_interval_init;
    }

    public String getDate_interval_end() {
        return date_interval_end;
    }

    public void setDate_interval_end(String date_interval_end) {
        this.date_interval_end = date_interval_end;
    }

    @Override
    public String toString() {
        return "Filter{" +
                "user='" + user + '\'' +
                "country='" + country + '\'' +
                ", city='" + city + '\'' +
                ", distance='" + distance + '\'' +
                ", date_interval_init=" + date_interval_init +
                ", date_interval_end=" + date_interval_end +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(user);
        dest.writeString(country);
        dest.writeString(city);
        dest.writeString(distance);
        dest.writeString(date_interval_init);
        dest.writeString(date_interval_end);
    }

    private void readFromParcel(Parcel in) {
        user = in.readString();
        country = in.readString();
        city = in.readString();
        distance = in.readString();
        date_interval_init = in.readString();
        date_interval_end = in.readString();
    }
}
