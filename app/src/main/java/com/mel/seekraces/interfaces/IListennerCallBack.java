package com.mel.seekraces.interfaces;

import com.mel.seekraces.entities.Response;

/**
 * Created by moha on 10/01/17.
 */

public interface IListennerCallBack {
    void onSuccess(Response response);
    void onError(Response response);
}
