package com.mel.seekraces.entities;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by void on 15/01/2017.
 */

public class Response implements Parcelable {
    private String message;
    private String content;
    private String countries;
    private String cities;
    private boolean isOk;

    public Response() {

    }

    protected Response(Parcel in) {
        message = in.readString();
        content = in.readString();
        countries = in.readString();
        cities = in.readString();
        isOk = in.readByte() != 0;
    }

    public static final Creator<Response> CREATOR = new Creator<Response>() {
        @Override
        public Response createFromParcel(Parcel in) {
            return new Response(in);
        }

        @Override
        public Response[] newArray(int size) {
            return new Response[size];
        }
    };

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getCountries() {
        return countries;
    }

    public void setCountries(String countries) {
        this.countries = countries;
    }

    public String getCities() {
        return cities;
    }

    public void setCities(String cities) {
        this.cities = cities;
    }

    public boolean isOk() {
        return isOk;
    }

    public void setOk(boolean ok) {
        isOk = ok;
    }

    @Override
    public String toString() {
        return "Response{" +
                "message='" + message + '\'' +
                ", content='" + content + '\'' +
                ", countries='" + countries + '\'' +
                ", cities='" + cities + '\'' +
                ", isOk=" + isOk +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(message);
        dest.writeString(content);
        dest.writeString(countries);
        dest.writeString(cities);
        dest.writeByte((byte) (isOk ? 1 : 0));
    }

    private void readFromParcel(Parcel in) {
        message = in.readString();
        content = in.readString();
        countries = in.readString();
        cities = in.readString();
        isOk = in.readByte() != 0;
    }
}
