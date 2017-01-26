package com.mel.seekraces.activities.main;

import android.net.Uri;
import android.text.TextUtils;

import com.google.gson.Gson;
import com.mel.seekraces.commons.Constantes;
import com.mel.seekraces.commons.RMapped;
import com.mel.seekraces.commons.SharedPreferencesSingleton;
import com.mel.seekraces.entities.Filter;
import com.mel.seekraces.entities.Response;
import com.mel.seekraces.entities.User;
import com.mel.seekraces.interfaces.IListennerCallBack;
import com.mel.seekraces.interfaces.main.IMainPresenter;
import com.mel.seekraces.interfaces.main.IMainView;

import java.io.File;

/**
 * Created by moha on 18/01/17.
 */

public class MainPresenterImpl implements IMainPresenter, IListennerCallBack{

    private IMainView view;
    private SharedPreferencesSingleton sharedPreferencesSingleton;

    public MainPresenterImpl(IMainView view, SharedPreferencesSingleton sharedPreferencesSingleton) {
        this.view = view;
        this.sharedPreferencesSingleton = sharedPreferencesSingleton;
    }

    @Override
    public void fillDataHeaderView() {
        String userJson = sharedPreferencesSingleton.getStringSP(Constantes.KEY_USER);
        if (!TextUtils.isEmpty(userJson)) {
            final String user_picture_name=sharedPreferencesSingleton.getStringSP(Constantes.KEY_USER_NAME_PICTURE);
            String userName=sharedPreferencesSingleton.getStringSP(Constantes.KEY_USERNAME);
            view.fillNavHeaderTxtUserName(userName);
            new Thread(new Runnable() {
                @Override
                public void run() {
                    File file = new File(Constantes.RUTA_IMAGENES + user_picture_name);
                    if (file.exists()) {
                        Uri uri = Uri.fromFile(file);
                        if (uri != null) {
                            view.fillNavHeaderImgProfile(uri);
                        }
                    }
                }
            }).start();
        }
    }

    @Override
    public void onNavigationItemSelected(int itemSelectd) {

        if (itemSelectd == RMapped.ITEM_RACES_PUBLISHED.getValue()) {
            Filter filter=new Filter();
            filter.setUser(sharedPreferencesSingleton.getStringSP(Constantes.KEY_USER));
            filter.setCountry("spain");
            view.chargeFragmentRacesPublished(filter);
        } else if (itemSelectd == RMapped.ITEM_RACES_MY_PUBLISHED.getValue()) {
            view.chargeFragmentMyRacesPublished();
        } else if (itemSelectd == RMapped.ITEM_RACES_FAVORITES.getValue()) {
            view.chargeFragmentRacesFavorites();
        } else if (itemSelectd == RMapped.ITEM_RACES_PREVIOUS.getValue()) {
            view.chargeFragmentRacesPrevious();
        }
        view.closeDrawerLayout();
    }

    @Override
    public void onBackPressed() {
        if (view.isDrawerOpen()) {
            view.closeDrawerLayout();
            return;
        }
        view.returnBack();
    }

    @Override
    public void onSuccess(Response response) {

    }

    @Override
    public void onError(Response response) {

    }
}
