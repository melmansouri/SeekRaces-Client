package com.mel.seekraces.interfaces.fragmentUserFollowed;

import com.mel.seekraces.entities.User;

import java.util.List;

/**
 * Created by void on 22/01/2017.
 */

public interface IFragmentListUsersFollowedView {


    void showProgress();

    void hideProgress();

    void showMessage(String message);


    void showComponents();

    void hideComponents();

    void fillAdapter(List<User> followeds);

    void unFollowFromAdapter();

    String getUserFollowedSelectedToDelete();

    String getUserFollowedSelectedToSetSendNotificacion();

    void setSendNotificacionFromAdapter();
}
