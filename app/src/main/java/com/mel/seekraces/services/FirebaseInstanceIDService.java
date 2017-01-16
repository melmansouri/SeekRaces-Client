package com.mel.seekraces.services;

import android.util.Log;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;
import com.mel.seekraces.commons.Constantes;
import com.mel.seekraces.commons.Utils;

/**
 * Created by moha on 16/01/17.
 */
public class FirebaseInstanceIDService extends FirebaseInstanceIdService {
    @Override
    public void onTokenRefresh() {

        String token = FirebaseInstanceId.getInstance().getToken();
        Log.e("Token firebase",token);

        Utils.saveStringSP(getApplicationContext(), Constantes.FILE_SP,Constantes.KEY_TOKEN_PUSH,token);
    }
}
