package com.mel.seekraces.interfaces.signin;

import com.mel.seekraces.entities.User;

/**
 * Created by moha on 10/01/17.
 */

public interface ISignInPresenter {
    void signIn(boolean isOnline,User user);
    void activityResult(int requestCode,int resultCode);
    void onOptionsItemSelected(int idSelected);
    void selectOptionDialogPicture(String[] options,int selected);
    void validatePasswordRepeat(String pwd,String pwdRepeat);
    void onDestroy();
}
