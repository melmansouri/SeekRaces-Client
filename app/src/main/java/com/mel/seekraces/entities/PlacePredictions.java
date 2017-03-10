package com.mel.seekraces.entities;

import java.util.ArrayList;

/**
 * Created by void on 11/02/2017.
 */

public class PlacePredictions {

    private ArrayList<String> predictions;
    private String status;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public ArrayList<String> getPlaces() {
        return predictions;
    }

    public void setPlaces(ArrayList<String> places) {
        this.predictions = places;
    }
}
