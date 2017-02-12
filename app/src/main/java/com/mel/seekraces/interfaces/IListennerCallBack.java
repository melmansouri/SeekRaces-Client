package com.mel.seekraces.interfaces;

import com.mel.seekraces.entities.PlacePredictions;
import com.mel.seekraces.entities.Response;

/**
 * Created by moha on 10/01/17.
 */

public interface IListennerCallBack {
    void onSuccess(Object object);
    void onError(Response response);
}
