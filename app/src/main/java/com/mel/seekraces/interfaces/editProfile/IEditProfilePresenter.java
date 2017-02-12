package com.mel.seekraces.interfaces.editProfile;

import com.mel.seekraces.entities.User;

/**
 * Created by moha on 10/01/17.
 */

public interface IEditProfilePresenter {
    void editProfile(boolean isOnline, User user);
    void activityResult(int requestCode, int resultCode);
    void onOptionsItemSelected(int idSelected);
    void selectOptionDialogPicture(String[] options, int selected);
    void onDestroy();
}
