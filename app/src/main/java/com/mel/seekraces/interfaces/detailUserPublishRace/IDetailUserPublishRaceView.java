package com.mel.seekraces.interfaces.detailUserPublishRace;

import com.mel.seekraces.entities.User;

/**
 * Created by moha on 10/01/17.
 */

public interface IDetailUserPublishRaceView {
    boolean isClickedbtnSetNotificacion();

    boolean isClickedbtnFollow();

    void setClickedbtnSetNotificacion(boolean clicked);

    void setClickedbtnFollow(boolean clicked);

    void changeIconButtonollow(int id);

    void changeIconButtonSendNotificacion(int id);

    void showButtonSendNotificacion(boolean show);

    User getUser();

    void showProgress();
    void hideProgress();
    void showMessage(String message);
    void showComponents();
    void hideComponents();
}
