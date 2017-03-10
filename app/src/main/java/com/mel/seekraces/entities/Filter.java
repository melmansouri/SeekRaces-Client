package com.mel.seekraces.entities;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by void on 21/01/2017.
 */

public class Filter implements Parcelable {
    private String user;
    private String name;
    private String place;
    private int distanceMin;
    private int distanceMax;
    private String date_interval_init;
    private String date_interval_end;

    public Filter() {

    }

    protected Filter(Parcel in) {
        user = in.readString();
        place = in.readString();
        name = in.readString();
        distanceMin = in.readInt();
        distanceMax = in.readInt();
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public int getDistanceMin() {
        return distanceMin;
    }

    public void setDistanceMin(int distanceMin) {
        this.distanceMin = distanceMin;
    }

    public int getDistanceMax() {
        return distanceMax;
    }

    public void setDistanceMax(int distanceMax) {
        this.distanceMax = distanceMax;
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
                ", name='" + name + '\'' +
                ", place='" + place + '\'' +
                ", distanceMin=" + distanceMin +
                ", distanceMax=" + distanceMax +
                ", date_interval_init='" + date_interval_init + '\'' +
                ", date_interval_end='" + date_interval_end + '\'' +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(user);
        dest.writeString(place);
        dest.writeString(name);
        dest.writeInt(distanceMin);
        dest.writeInt(distanceMax);
        dest.writeString(date_interval_init);
        dest.writeString(date_interval_end);
    }

    private void readFromParcel(Parcel in) {
        user = in.readString();
        place = in.readString();
        name = in.readString();
        distanceMin = in.readInt();
        distanceMax = in.readInt();
        date_interval_init = in.readString();
        date_interval_end = in.readString();
    }
}
