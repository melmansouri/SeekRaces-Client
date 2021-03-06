package com.mel.seekraces.commons;

import android.os.Environment;

import java.io.File;

/**
 * Created by void on 12/01/2017.
 */

public class Constantes {
    public static final String KEY_GOOGLE_API = "AIzaSyDmZRb02-RcKK3I080i5D7nqkMpfOJLHVU";

    public static final int MIN_LENGTH_PASSWORD = 6;

    //Paths
    public static final String RUTA_IMAGENES = Environment.getExternalStorageDirectory() + File.separator + "Seekraces" + File.separator + "pictures" + File.separator;
    public static final String TYPE_IMAGE_PROILE = "PROFILE";
    public static final String TYPE_IMAGE_EVENT = "EVENTS";

    //Request Start Activities
    public static final int REQUEST_IMAGE_CAPTURE_CAMERA = 100;
    public static final int REQUEST_IMAGE_CAPTURE_GALLERY = 101;

    public static final int REQUEST_CODE_GENERIC_PERMISSION = 0;
    public static final int REQUEST_CODE_GENERIC_PERMISSION_LOGIN = 1;
    public static final int REQUEST_CODE_GENERIC_PERMISSION_SIGNIN = 2;
    public static final int REQUEST_CODE_PERMISSION_READ_EXTERNAL_STORAGE = 10;

    public static final int REQUEST_START_SIGNIN_FOR_RESULT = 200;
    public static final int REQUEST_START_MAIN_FOR_RESULT = 201;
    public static final int REQUEST_START_FILTERS_FOR_RESULT = 202;
    public static final int REQUEST_START_ADD_RACE_FOR_RESULT = 203;
    public static final int REQUEST_START_EDIT_PROFILE_FOR_RESULT = 204;
    public static final int REQUEST_START_SIGNIN_GOOGLE_FOR_RESULT = 205;

    //SharedPreferences
    public static final String FILE_SP = "seekraces_preferences";
    public static final String KEY_TOKEN_PUSH = "token_push";
    public static final String KEY_USER = "user";
    public static final String KEY_USERNAME = "username";
    public static final String KEY_USER_NAME_PICTURE = "picture";
    public static final String KEY_PLACE_USER = "place";

    //Tags fragments
    public static final String TAG_REVIEWS_FRAGMENT = "reviews";
    public static final String TAG_RACES_PUBLISHED_FRAGMENT = "racesPublished";
    public static final String TAG_MY_RACES_PUBLISHED_FRAGMENT = "myRacesPublished";
    public static final String TAG_RACES_FAVORITES_FRAGMENT = "racesFavorites";
    public static final String TAG_DETAIL_RACES_FRAGMENT = "detailRaces";


}
