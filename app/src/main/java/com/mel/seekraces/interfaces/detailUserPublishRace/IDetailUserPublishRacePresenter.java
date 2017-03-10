package com.mel.seekraces.interfaces.detailUserPublishRace;

import com.mel.seekraces.entities.Follow;

/**
 * Created by moha on 10/01/17.
 */

public interface IDetailUserPublishRacePresenter {
    void follow(boolean isOnline,Follow follow);
    void unFollow(boolean isOnline,String userFollowed);

    void setSentNotificacion(boolean isOnline, Follow follow);

    void getRacesPublishedByUser(boolean isOnline, String userFollowed);
    void onDestroy();
}
