package com.mel.seekraces.interfaces.signin;

import com.mel.seekraces.pojos.User;

/**
 * Created by moha on 10/01/17.
 */

public interface ISignInPresenter {
    void signIn(User user);
    void activityResult(int resultCode,int type_result);
    void selectOptionDialogPicture(String[] options,int selected);
}
