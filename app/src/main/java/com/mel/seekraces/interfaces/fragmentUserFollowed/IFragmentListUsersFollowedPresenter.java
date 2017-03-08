package com.mel.seekraces.interfaces.fragmentUserFollowed;

import com.mel.seekraces.entities.Follow;

/**
 * Created by void on 22/01/2017.
 */

public interface IFragmentListUsersFollowedPresenter {
    void unFollow(boolean isOnline,String userFollowed);

    void setSentNotificacion(boolean isOnline, Follow follow);

    void getUsersFollowed(boolean isOnline);

    void onDestroy();
}
