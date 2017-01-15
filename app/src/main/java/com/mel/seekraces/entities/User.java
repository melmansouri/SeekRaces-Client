package com.mel.seekraces.entities;

/**
 * Created by moha on 10/01/17.
 */

public class User {
    private String email;
    private String pwd;
    private String pwd_repeat;
    private String username;
    private String photo_url;
    private String photoBase64;
    private String country;
    private String token_push;

    public User() {
        this.email="";
        this.pwd="";
        this.username="";
        this.photoBase64="";
        this.country="";
        this.token_push="";

    }

    public String getToken_push() {
        return token_push;
    }

    public void setToken_push(String token_push) {
        this.token_push = token_push;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getPhotoBase64() {
        return photoBase64;
    }

    public void setPhotoBase64(String photoBase64) {
        this.photoBase64 = photoBase64;
    }

    public String getPhoto_url() {
        return photo_url;
    }

    public void setPhoto_url(String photo_url) {
        this.photo_url = photo_url;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPwd_repeat() {
        return pwd_repeat;
    }

    public void setPwd_repeat(String pwd_repeat) {
        this.pwd_repeat = pwd_repeat;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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
}
