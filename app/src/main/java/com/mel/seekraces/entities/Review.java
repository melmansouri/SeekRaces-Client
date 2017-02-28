package com.mel.seekraces.entities;

/**
 * Created by void on 28/02/2017.
 */

public class Review {
    private String user;
    private String userName;
    private String photo_name;
    private int event;
    private int score;
    private String comment;
    private String dateOpinion;

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

    public String getPhoto_name() {
        return photo_name;
    }

    public void setPhoto_name(String photo_name) {
        this.photo_name = photo_name;
    }

    public int getEvent() {
        return event;
    }

    public void setEvent(int event) {
        this.event = event;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getDateOpinion() {
        return dateOpinion;
    }

    public void setDateOpinion(String dateOpinion) {
        this.dateOpinion = dateOpinion;
    }
}
