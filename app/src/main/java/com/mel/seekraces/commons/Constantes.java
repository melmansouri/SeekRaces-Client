package com.mel.seekraces.commons;

import android.os.Environment;

/**
 * Created by void on 12/01/2017.
 */

public class Constantes {
    public static final int MIN_LENGTH_PASSWORD=1;

    //Paths
    public static final String RUTA_IMAGENES = Environment.getExternalStorageDirectory().getPath() + "/NOTIFICACIONES/IMAGENES/";
    public static final String TYPE_IMAGE_PROILE = "profile";
    public static final String TYPE_IMAGE_EVENT = "event";

    //Request Start Activities
    public static final int REQUEST_IMAGE_CAPTURE_CAMERA=100;
    public static final int REQUEST_IMAGE_CAPTURE_GALLERY=101;

    public static final int REQUEST_CODE_GENERIC_PERMISSION=0;

    public static final int REQUEST_START_SIGNIN_FOR_RESULT=200;

    //SharedPreferences
    public static final String FILE_SP="seekraces_preferences";
    public static final String KEY_TOKEN_PUSH="token_push";

}
