package com.mel.seekraces.activities.main;

import android.text.TextUtils;

import com.mel.seekraces.commons.Constantes;
import com.mel.seekraces.commons.RMapped;
import com.mel.seekraces.commons.SharedPreferencesSingleton;
import com.mel.seekraces.entities.Filter;
import com.mel.seekraces.entities.Response;
import com.mel.seekraces.fragments.ownRacesPublished.ListOwnRacesPublishedFragment;
import com.mel.seekraces.fragments.racesFinished.ListRacesFinishedFragment;
import com.mel.seekraces.fragments.racesPublished.ListRacesPublishedFragment;
import com.mel.seekraces.fragments.racesPublishedFavorites.ListRacesPublishedFavoritesFragment;
import com.mel.seekraces.interfaces.IListennerCallBack;
import com.mel.seekraces.interfaces.main.IMainPresenter;
import com.mel.seekraces.interfaces.main.IMainView;

/**
 * Created by moha on 18/01/17.
 */

public class MainPresenterImpl implements IMainPresenter, IListennerCallBack {

    private IMainView view;
    private SharedPreferencesSingleton sharedPreferencesSingleton;

    public MainPresenterImpl(IMainView view, SharedPreferencesSingleton sharedPreferencesSingleton) {
        this.view = view;
        this.sharedPreferencesSingleton = sharedPreferencesSingleton;
    }

    @Override
    public void fillDataHeaderView() {
        if (view != null) {
            String username = sharedPreferencesSingleton.getStringSP(Constantes.KEY_USERNAME);
            String email = sharedPreferencesSingleton.getStringSP(Constantes.KEY_USER);
            String userNamePicture = sharedPreferencesSingleton.getStringSP(Constantes.KEY_USER_NAME_PICTURE);
            view.fillNavHeaderTxtUserName(username);
            view.fillNavHeaderTxtEmail(email);
            if (!TextUtils.isEmpty(userNamePicture)) {
                view.fillNavHeaderImgProfile(userNamePicture);
            }
        }
    }

    @Override
    public void onNavigationItemSelected(int itemSelectd) {
        if (view != null) {
            if (itemSelectd == RMapped.ITEM_RACES_PUBLISHED.getValue()) {
                Filter filter = new Filter();
                filter.setUser(sharedPreferencesSingleton.getStringSP(Constantes.KEY_USER));
                view.chargeFragmentRacesPublished(filter);
            } else if (itemSelectd == RMapped.ITEM_RACES_MY_PUBLISHED.getValue()) {
                view.chargeFragmentMyRacesPublished();
            } else if (itemSelectd == RMapped.ITEM_RACES_FAVORITES.getValue()) {
                view.chargeFragmentRacesFavorites();
            } else if (itemSelectd == RMapped.ITEM_RACES_PREVIOUS.getValue()) {
                view.chargeFragmentRacesPrevious();
            } else if (itemSelectd == RMapped.ITEM_EXIT.getValue()) {
                sharedPreferencesSingleton.clearAllUserSharedPreferences();
                view.exitSession();
            }
            view.closeDrawerLayout();
        }
    }

    @Override
    public void activityResult(int requestCode, int resultCode) {
        if (view != null) {
            if (resultCode == RMapped.RESULT_OK.getValue()) {
                if (requestCode == Constantes.REQUEST_START_FILTERS_FOR_RESULT) {
                    if (view.getIntentActivityResult() != null) {
                        Filter filter = view.getIntentActivityResult().getParcelableExtra("filter");
                        view.chargeFragmentRacesPublished(filter);
                    }
                } else if (requestCode == Constantes.REQUEST_START_ADD_RACE_FOR_RESULT) {
                    if (view.getIntentActivityResult() != null) {
                        String message = view.getIntentActivityResult().getStringExtra("addRace");
                        view.showMessage(message);
                        Filter filter = new Filter();
                        filter.setUser(sharedPreferencesSingleton.getStringSP(Constantes.KEY_USER));
                        view.chargeFragmentRacesPublished(filter);
                    }
                } else if (requestCode == Constantes.REQUEST_START_EDIT_PROFILE_FOR_RESULT) {
                    if (view.getIntentActivityResult() != null) {
                        String message = view.getIntentActivityResult().getStringExtra("editProfile");
                        view.showMessage(message);
                        fillDataHeaderView();
                        Filter filter = new Filter();
                        filter.setUser(sharedPreferencesSingleton.getStringSP(Constantes.KEY_USER));
                        view.chargeFragmentRacesPublished(filter);
                    }
                } else {
                    view.setIntentActivityResultToNull();
                }
            }
        }
    }

    @Override
    public void onBackPressed() {
        if (view != null) {
            if (view.isDrawerOpen()) {
                view.closeDrawerLayout();
                return;
            }
            /*int countBackStackEntry = view.getBackStackEntryCount();
            if (countBackStackEntry > 0) {
                int id = countBackStackEntry - 1;
                    String actualName = view.getBackStackEntryNameAt(countBackStackEntry);

                    if (actualName.equals(Constantes.TAG_RACES_FAVORITES_FRAGMENT) || actualName.equals(Constantes.TAG_MY_RACES_PUBLISHED_FRAGMENT)) {
                        id = 0;
                    }
                    view.backToPreviousFragmentById(id);
                    return;
            }*/
            view.setActualFragmentActive();
            if (view.actualFragmentActiveInstanceOf(ListOwnRacesPublishedFragment.class) || view.actualFragmentActiveInstanceOf(ListRacesPublishedFavoritesFragment.class) || view.actualFragmentActiveInstanceOf(ListRacesFinishedFragment.class)){
                view.backToPreviousFragmentById(0);
            }else if (view.actualFragmentActiveInstanceOf(ListRacesPublishedFragment.class)){
                view.setResult();
                view.returnBack();
            }else{
                view.backToPreviousFragment();
            }
        }
    }

    @Override
    public void onDestroy() {
        view = null;
    }


    @Override
    public void onSuccess(Object object) {

    }

    @Override
    public void onError(Response response) {

    }
}
