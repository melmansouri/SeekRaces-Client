package com.mel.seekraces.entities;

/**
 * Created by void on 26/02/2017.
 */

public class Favorite {
    private String user;
    private int event;

    public Favorite(String user, int event) {
        this.user = user;
        this.event = event;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public int getEvent() {
        return event;
    }

    public void setEvent(int event) {
        this.event = event;
    }
}
