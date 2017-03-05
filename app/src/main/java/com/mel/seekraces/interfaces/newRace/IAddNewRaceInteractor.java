package com.mel.seekraces.interfaces.newRace;

import com.mel.seekraces.entities.Race;

/**
 * Created by void on 05/02/2017.
 */

public interface IAddNewRaceInteractor {
    void addRace(Race race);
    void getAutoCompletePlaces(String url);
}
