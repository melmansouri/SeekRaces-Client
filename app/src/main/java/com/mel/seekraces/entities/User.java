package com.mel.seekraces.entities;

import android.graphics.Bitmap;
import android.os.Parcel;
import android.os.Parcelable;

import static android.R.attr.id;

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
    private String place;
    private String token_push;
    private String idTokenGoogle;
    private boolean followed;

    public User() {
        this.email = "";
        this.pwd = "";
        this.username = "";
        this.photoBase64 = "";
        this.photo = null;
        this.place = "";
        this.token_push = "";
        this.idTokenGoogle="";
    }

    protected User(Parcel in) {
        email = in.readString();
        username = in.readString();
        photoBase64 = in.readString();
        photo = in.readParcelable(Bitmap.class.getClassLoader());
        place = in.readString();
        followed = in.readByte() != 0;
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

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public String getToken_push() {
        return token_push;
    }

    public void setToken_push(String token_push) {
        this.token_push = token_push;
    }

    public String getIdTokenGoogle() {
        return idTokenGoogle;
    }

    public void setIdTokenGoogle(String idTokenGoogle) {
        this.idTokenGoogle = idTokenGoogle;
    }

    public boolean isFollowed() {
        return followed;
    }

    public void setFollowed(boolean followed) {
        this.followed = followed;
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
                ", photo=" + photo +
                ", place='" + place + '\'' +
                ", token_push='" + token_push + '\'' +
                ", idTokenGoogle='" + idTokenGoogle + '\'' +
                ", followed=" + followed +
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
        parcel.writeString(place);
        parcel.writeByte((byte) (followed ? 1 : 0));
    }

    private void readFromParcel(Parcel in){
        email = in.readString();
        username = in.readString();
        photoBase64 = in.readString();
        photo = in.readParcelable(Bitmap.class.getClassLoader());
        place = in.readString();
        followed = in.readByte() != 0;
    }

}
