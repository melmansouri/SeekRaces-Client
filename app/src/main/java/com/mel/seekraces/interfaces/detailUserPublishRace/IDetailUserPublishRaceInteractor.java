package com.mel.seekraces.interfaces.detailUserPublishRace;

import com.mel.seekraces.entities.Follow;

/**
 * Created by moha on 10/01/17.
 */

public interface IDetailUserPublishRaceInteractor {
    void follow(Follow follow);
    void unFollow(String url);

    void setSentNotificacion(Follow follow);

    void getRacesPublishedByUser(String url);
}
