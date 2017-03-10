package com.mel.seekraces.interfaces.fragmentUserFollowed;

import com.mel.seekraces.entities.Follow;

/**
 * Created by void on 08/03/2017.
 */

public interface IListenerInteractionListUsersFollowed {
    void unFollow(String followed);
    void setSendNotificacion(Follow follow);

    void showMessage(String s);
}
