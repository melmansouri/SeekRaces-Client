package com.mel.seekraces.entities;

import android.graphics.Bitmap;
import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by void on 18/01/2017.
 */

public class Race implements Parcelable {
    private int id;
    private String userEmail;
    private String userName;
    private String name;
    private String description;
    private Bitmap bitmap;
    private String imageBase64;
    private String imageName;
    private int distance;
    private String place;
    private String date_time_init;
    private String web;
    private boolean isFavorite;
    private boolean isFinished;
    private User user;

    public Race() {
    }

    protected Race(Parcel in) {
        id = in.readInt();
        userEmail = in.readString();
        name = in.readString();
        description = in.readString();
        bitmap = in.readParcelable(Bitmap.class.getClassLoader());
        imageBase64 = in.readString();
        imageName = in.readString();
        distance = in.readInt();
        place = in.readString();
        date_time_init = in.readString();
        web = in.readString();
        isFavorite = in.readByte() != 0;
        isFinished = in.readByte() != 0;
        user=in.readParcelable(User.class.getClassLoader());
    }

    public static final Creator<Race> CREATOR = new Creator<Race>() {
        @Override
        public Race createFromParcel(Parcel in) {
            return new Race(in);
        }

        @Override
        public Race[] newArray(int size) {
            return new Race[size];
        }
    };

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
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

    public String getImageName() {
        return imageName;
    }

    public void setImageName(String imageName) {
        this.imageName = imageName;
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


    public boolean isFavorite() {
        return isFavorite;
    }

    public void setFavorite(boolean favorite) {
        isFavorite = favorite;
    }

    public boolean isFinished() {
        return isFinished;
    }

    public void setFinished(boolean finished) {
        isFinished = finished;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(userEmail);
        dest.writeString(userName);
        dest.writeString(name);
        dest.writeString(description);
        dest.writeParcelable(bitmap, flags);
        dest.writeString(imageBase64);
        dest.writeString(imageName);
        dest.writeInt(distance);
        dest.writeString(place);
        dest.writeString(date_time_init);
        dest.writeString(web);
        dest.writeByte((byte) (isFavorite ? 1 : 0));
        dest.writeByte((byte) (isFinished ? 1 : 0));
        dest.writeParcelable(user,flags);
    }

    private void readFromParcel(Parcel in) {
        id = in.readInt();
        userEmail = in.readString();
        description = in.readString();
        bitmap = in.readParcelable(Bitmap.class.getClassLoader());
        imageBase64 = in.readString();
        imageName = in.readString();
        distance = in.readInt();
        place = in.readString();
        date_time_init = in.readString();
        web = in.readString();
        isFavorite = in.readByte() != 0;
        isFinished = in.readByte() != 0;
        user=in.readParcelable(User.class.getClassLoader());
    }
}
