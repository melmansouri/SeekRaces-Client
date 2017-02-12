package com.mel.seekraces.entities;

import android.graphics.Bitmap;
import android.os.Parcel;
import android.os.Parcelable;

import static android.R.attr.bitmap;
import static android.R.attr.description;
import static android.R.attr.id;
import static android.R.attr.name;
import static android.R.attr.rating;

/**
 * Created by moha on 10/01/17.
 */

public class User implements Parcelable {
    private String email;
    private String pwd;
    private String pwd_repeat;
    private String username;
    private String photo_url;
    private String photoBase64;
    private Bitmap photo;
    private String country;
    private String token_push;

    public User() {
        this.email = "";
        this.pwd = "";
        this.username = "";
        this.photoBase64 = "";
        this.photo = null;
        this.country = "";
        this.token_push = "";

    }

    protected User(Parcel in) {
        email = in.readString();
        username = in.readString();
        photoBase64 = in.readString();
        photo = in.readParcelable(Bitmap.class.getClassLoader());
        country = in.readString();
    }

    public static final Creator<User> CREATOR = new Creator<User>() {
        @Override
        public User createFromParcel(Parcel in) {
            return new User(in);
        }

        @Override
        public User[] newArray(int size) {
            return new User[size];
        }
    };

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    public String getPwd_repeat() {
        return pwd_repeat;
    }

    public void setPwd_repeat(String pwd_repeat) {
        this.pwd_repeat = pwd_repeat;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPhoto_url() {
        return photo_url;
    }

    public void setPhoto_url(String photo_url) {
        this.photo_url = photo_url;
    }

    public String getPhotoBase64() {
        return photoBase64;
    }

    public void setPhotoBase64(String photoBase64) {
        this.photoBase64 = photoBase64;
    }

    public Bitmap getPhoto() {
        return photo;
    }

    public void setPhoto(Bitmap photo) {
        this.photo = photo;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getToken_push() {
        return token_push;
    }

    public void setToken_push(String token_push) {
        this.token_push = token_push;
    }

    @Override
    public String toString() {
        return "User{" +
                "email='" + email + '\'' +
                ", pwd='" + pwd + '\'' +
                ", pwd_repeat='" + pwd_repeat + '\'' +
                ", username='" + username + '\'' +
                ", photo_url='" + photo_url + '\'' +
                ", photoBase64='" + photoBase64 + '\'' +
                ", country='" + country + '\'' +
                ", token_push='" + token_push + '\'' +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(id);
        parcel.writeString(email);
        parcel.writeString(username);
        parcel.writeString(photoBase64);
        parcel.writeParcelable(photo, i);
        parcel.writeString(country);
    }

    private void readFromParcel(Parcel in){
        email = in.readString();
        username = in.readString();
        photoBase64 = in.readString();
        photo = in.readParcelable(Bitmap.class.getClassLoader());
        country = in.readString();
    }

}
