package com.mel.seekraces.interfaces.fragmentEditRace;

import com.mel.seekraces.entities.Event;

/**
 * Created by void on 05/02/2017.
 */

public interface IEditRaceInteractor {
    void editRace(Event event);
    void getAutoCompletePlaces(String url);
}
