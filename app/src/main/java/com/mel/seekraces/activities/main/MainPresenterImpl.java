package com.mel.seekraces.activities.main;

import android.text.TextUtils;

import com.mel.seekraces.commons.Constantes;
import com.mel.seekraces.commons.RMapped;
import com.mel.seekraces.commons.SharedPreferencesSingleton;
import com.mel.seekraces.entities.Filter;
import com.mel.seekraces.entities.Response;
import com.mel.seekraces.interfaces.IListennerCallBack;
import com.mel.seekraces.interfaces.main.IMainPresenter;
import com.mel.seekraces.interfaces.main.IMainView;

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
        if (view!=null){
            String username=sharedPreferencesSingleton.getStringSP(Constantes.KEY_USERNAME);
            String userNamePicture=sharedPreferencesSingleton.getStringSP(Constantes.KEY_USER_NAME_PICTURE);
            view.fillNavHeaderTxtUserName(username);
            if (!TextUtils.isEmpty(userNamePicture)){
                view.fillNavHeaderImgProfile(userNamePicture);
            }
        }
    }

    @Override
    public void onNavigationItemSelected(int itemSelectd) {
        if (view!=null){
            if (itemSelectd == RMapped.ITEM_RACES_PUBLISHED.getValue()) {
                Filter filter=new Filter();
                filter.setUser(sharedPreferencesSingleton.getStringSP(Constantes.KEY_USER));
                view.chargeFragmentRacesPublished(filter);
            } else if (itemSelectd == RMapped.ITEM_RACES_MY_PUBLISHED.getValue()) {
                view.chargeFragmentMyRacesPublished();
            } else if (itemSelectd == RMapped.ITEM_RACES_FAVORITES.getValue()) {
                view.chargeFragmentRacesFavorites();
            } else if (itemSelectd == RMapped.ITEM_RACES_PREVIOUS.getValue()) {
                view.chargeFragmentRacesPrevious();
            }else if(itemSelectd == RMapped.ITEM_EXIT.getValue()){
                sharedPreferencesSingleton.clearAllUserSharedPreferences();
                view.returnBack();
            }
            view.closeDrawerLayout();
        }
    }

    @Override
    public void activityResult(int requestCode, int resultCode) {
        if (view!=null){
            if (resultCode == RMapped.RESULT_OK.getValue()) {
                if (requestCode==Constantes.REQUEST_START_FILTERS_FOR_RESULT){
                    if (view.getIntentActivityResult()!=null){
                        Filter filter=view.getIntentActivityResult().getParcelableExtra("filter");
                        view.chargeFragmentRacesPublished(filter);
                    }
                }else if (requestCode == Constantes.REQUEST_START_ADD_RACE_FOR_RESULT) {
                    if (view.getIntentActivityResult()!=null) {
                        String message=view.getIntentActivityResult().getStringExtra("addRace");
                        view.showMessage(message);
                        Filter filter=new Filter();
                        filter.setUser(sharedPreferencesSingleton.getStringSP(Constantes.KEY_USER));
                        view.chargeFragmentRacesPublished(filter);
                    }
                }else if (requestCode == Constantes.REQUEST_START_EDIT_PROFILE_FOR_RESULT) {
                    if (view.getIntentActivityResult()!=null) {
                        String message=view.getIntentActivityResult().getStringExtra("editProfile");
                        view.showMessage(message);
                        fillDataHeaderView();
                        Filter filter=new Filter();
                        filter.setUser(sharedPreferencesSingleton.getStringSP(Constantes.KEY_USER));
                        view.chargeFragmentRacesPublished(filter);
                    }
                }else{
                    view.setIntentActivityResultToNull();
                }
            }
        }
    }

    @Override
    public void onBackPressed() {
        if (view!=null){
            if (view.isDrawerOpen()) {
                view.closeDrawerLayout();
                return;
            }
            view.setResult();
            view.returnBack();
        }
    }

    @Override
    public void onDestroy() {
        view=null;
    }

    @Override
    public void onSuccess(Object object) {

    }

    @Override
    public void onError(Response response) {

    }
}
