package com.mel.seekraces.entities;

import android.graphics.Bitmap;
import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by void on 18/01/2017.
 */

public class Event implements Parcelable{
    private int id;
    private String user;
    private String userName;
    private String name;
    private String description;
    private Bitmap bitmap;
    private String imageBase64;
    private int distance;
    private String place;
    private String date_time_init;
    private String web;
    private int num_reviews;
    private int total_scores;
    private double rating;
    private boolean isFavorite;

    public Event(){
    }

    protected Event(Parcel in) {
        id = in.readInt();
        user = in.readString();
        name = in.readString();
        description = in.readString();
        bitmap = in.readParcelable(Bitmap.class.getClassLoader());
        imageBase64 = in.readString();
        distance = in.readInt();
        place = in.readString();
        date_time_init=in.readString();
        web = in.readString();
        num_reviews = in.readInt();
        total_scores = in.readInt();
        rating = in.readDouble();
        isFavorite=in.readByte() != 0;
    }

    public static final Creator<Event> CREATOR = new Creator<Event>() {
        @Override
        public Event createFromParcel(Parcel in) {
            return new Event(in);
        }

        @Override
        public Event[] newArray(int size) {
            return new Event[size];
        }
    };

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Bitmap getBitmap() {
        return bitmap;
    }

    public void setBitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
    }

    public String getImageBase64() {
        return imageBase64;
    }

    public void setImageBase64(String imageBase64) {
        this.imageBase64 = imageBase64;
    }

    public int getDistance() {
        return distance;
    }

    public void setDistance(int distance) {
        this.distance = distance;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public String getDate_time_init() {
        return date_time_init;
    }

    public void setDate_time_init(String date_time_init) {
        this.date_time_init = date_time_init;
    }

    public String getWeb() {
        return web;
    }

    public void setWeb(String web) {
        this.web = web;
    }

    public int getNum_reviews() {
        return num_reviews;
    }

    public void setNum_reviews(int num_reviews) {
        this.num_reviews = num_reviews;
    }

    public int getTotal_scores() {
        return total_scores;
    }

    public void setTotal_scores(int total_scores) {
        this.total_scores = total_scores;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    public boolean isFavorite() {
        return isFavorite;
    }

    public void setFavorite(boolean favorite) {
        isFavorite = favorite;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(user);
        dest.writeString(userName);
        dest.writeString(name);
        dest.writeString(description);
        dest.writeParcelable(bitmap, flags);
        dest.writeString(imageBase64);
        dest.writeInt(distance);
        dest.writeString(place);
        dest.writeString(date_time_init);
        dest.writeString(web);
        dest.writeInt(num_reviews);
        dest.writeInt(total_scores);
        dest.writeDouble(rating);
        dest.writeByte((byte) (isFavorite ? 1 : 0));
    }

    private void readFromParcel(Parcel in) {
        id = in.readInt();
        user = in.readString();
        description = in.readString();
        bitmap = in.readParcelable(Bitmap.class.getClassLoader());
        imageBase64 = in.readString();
        distance = in.readInt();
        place = in.readString();
        date_time_init = in.readString();
        web = in.readString();
        num_reviews = in.readInt();
        total_scores = in.readInt();
        rating = in.readDouble();
        isFavorite = in.readByte() != 0;
    }
}
