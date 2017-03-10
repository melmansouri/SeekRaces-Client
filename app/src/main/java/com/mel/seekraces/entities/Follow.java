package com.mel.seekraces.entities;

/**
 * Created by void on 08/03/2017.
 */

public class Follow {
    private String userFollower;
    private String userFollowed;
    private int sentNotificacion;

    public String getUserFollower() {
        return userFollower;
    }

    public void setUserFollower(String userFollower) {
        this.userFollower = userFollower;
    }

    public String getUserFollowed() {
        return userFollowed;
    }

    public void setUserFollowed(String userFollowed) {
        this.userFollowed = userFollowed;
    }

    public int getSentNotificacion() {
        return sentNotificacion;
    }

    public void setSentNotificacion(int sentNotificacion) {
        this.sentNotificacion = sentNotificacion;
    }
}
