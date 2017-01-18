package com.mel.seekraces.entities;

import android.graphics.Bitmap;

import java.util.Date;

/**
 * Created by void on 18/01/2017.
 */

public class Event {
    private int id;
    private String user;
    private String name;
    private String description;
    private Bitmap bitmap;
    private String imageBase64;
    private int distance;
    private String country;
    private String city;
    private Date date_time_init;
    private String web;
    private int num_reviews;
    private int total_scores;
    private int rating;

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

    public Date getDate_time_init() {
        return date_time_init;
    }

    public void setDate_time_init(Date date_time_init) {
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

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }
}
