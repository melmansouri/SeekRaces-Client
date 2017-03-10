package com.mel.seekraces.interfaces;

import com.mel.seekraces.entities.Response;

/**
 * Interfaz para conectar la clase que implemente el interactor con el presenter
 * Created by moha on 10/01/17.
 */

public interface IListennerCallBack {
    void onSuccess(Object object);
    void onError(Response response);
}
