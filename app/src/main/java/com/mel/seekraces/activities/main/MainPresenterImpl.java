package com.mel.seekraces.activities.main;

import android.net.Uri;
import android.text.TextUtils;

import com.google.gson.Gson;
import com.mel.seekraces.commons.Constantes;
import com.mel.seekraces.commons.RMapped;
import com.mel.seekraces.commons.SharedPreferencesSingleton;
import com.mel.seekraces.entities.User;
import com.mel.seekraces.interfaces.main.IMainPresenter;
import com.mel.seekraces.interfaces.main.IMainView;

import java.io.File;

/**
 * Created by moha on 18/01/17.
 */

public class MainPresenterImpl implements IMainPresenter {

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
            User user = new Gson().fromJson(userJson, User.class);
            view.fillNavHeaderTxtUserName(user.getUsername());
            File file = new File(Constantes.RUTA_IMAGENES + user.getPhoto_url());
            if (file.exists()) {
                Uri uri = Uri.fromFile(file);
                if (uri != null) {
                    view.fillNavHeaderImgProfile(uri);
                }
            }
        }
    }

    @Override
    public void onNavigationItemSelected(int itemSelectd) {

        if (itemSelectd == RMapped.ITEM_RACES_PUBLISHED.getValue()) {
            view.chargeFragmentRacesPublished();
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
    public void fillDataRacesPublished() {

    }

    @Override
    public void onBackPressed() {
        if (view.isDrawerOpen()) {
            view.closeDrawerLayout();
            return;
        }
        view.returnBack();
    }
}
