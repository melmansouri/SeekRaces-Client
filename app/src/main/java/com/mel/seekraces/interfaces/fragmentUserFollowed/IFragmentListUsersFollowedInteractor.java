package com.mel.seekraces.interfaces.fragmentUserFollowed;

import com.mel.seekraces.entities.Follow;

/**
 * Created by void on 22/01/2017.
 */

public interface IFragmentListUsersFollowedInteractor {
    void getUsersFollowed(String url);

    void unFollow(String url);

    void setSentNotificacion(Follow follow);
}
