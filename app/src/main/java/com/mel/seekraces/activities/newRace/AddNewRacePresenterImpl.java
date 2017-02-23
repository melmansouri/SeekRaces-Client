package com.mel.seekraces.activities.newRace;

import android.text.TextUtils;
import android.util.Log;

import com.mel.seekraces.commons.Constantes;
import com.mel.seekraces.commons.RMapped;
import com.mel.seekraces.commons.Utils;
import com.mel.seekraces.entities.Event;
import com.mel.seekraces.entities.PlacePredictions;
import com.mel.seekraces.entities.Response;
import com.mel.seekraces.interfaces.IListennerCallBack;
import com.mel.seekraces.interfaces.fragmentRacesPublishedFavorites.IListFragmentRacesPublishedFavoritesPresenter;
import com.mel.seekraces.interfaces.newRace.IAddNewRaceInteractor;
import com.mel.seekraces.interfaces.newRace.IAddNewRacePresenter;
import com.mel.seekraces.interfaces.newRace.IAddNewRaceView;

/**
 * Created by void on 05/02/2017.
 */

public class AddNewRacePresenterImpl implements IAddNewRacePresenter, IListennerCallBack{
    private IAddNewRaceView view;
    private IAddNewRaceInteractor interactor;

    public AddNewRacePresenterImpl(IAddNewRaceView view) {
        this.view = view;
        this.interactor=new AddNewRaceInteractorImpl(this);
    }

    @Override
    public void addRace(boolean isOnline,Event event) {
        if (view!=null){
            if (!isOnline){
                view.hideProgress();
                view.showMessage("Comprueba tu conexión");
                return;
            }
            if (!verifyDataUser(event)) {
                view.hideProgress();
                return;
            }
            interactor.addRace(event);
        }
    }

    @Override
    public void onTextChangedPlaces(String text) {
        if(text.length()>3){
            interactor.getAutoCompletePlaces(null);
            interactor.getAutoCompletePlaces(Utils.getPlaceAutoCompleteUrl(text));
        }
    }

    private boolean verifyDataUser(Event event) {
        boolean result = true;
        if (TextUtils.isEmpty(event.getPlace())){
            view.showErrorPlaces("Debe de introducir el lugar de la carrera");
            result=false;
        }
        if (TextUtils.isEmpty(event.getName())) {
            view.showErrorName("La carrera debe de tener un nombre");
            result = false;
        }
        if (event.getDistance()==0){
            view.showMessage("La distancia mínima es 1 KM");
            result = false;
        }

        return result;
    }

    @Override
    public void activityResult(int requestCode, int resultCode) {
        if (view!=null){
            if (resultCode == RMapped.RESULT_OK.getValue()) {
                if (requestCode== Constantes.REQUEST_IMAGE_CAPTURE_CAMERA){
                    view.fillImageViewFromCamera();
                }else if(requestCode==Constantes.REQUEST_IMAGE_CAPTURE_GALLERY){
                    view.fillImageViewFromGallery();
                }

            }
        }
    }

    @Override
    public boolean onOptionsItemSelected(int idSelected) {
        if (view!=null){
            if(idSelected==RMapped.ITEM_HOME_BACK.getValue()) {
                view.finishActivity();
                return true;
            }else if(idSelected==RMapped.ITEM_ADDRACE.getValue()){
                view.addRace();
                return true;
            }
            return view.retunSuperOnOptionsItemSelected();
        }
        return false;
    }

    @Override
    public void selectOptionDialogPicture(String[] options,int selected) {
        if (view!=null){
            if (options[selected].equals("Tomar foto")) {
                view.openCamera();
            } else if (options[selected].equals("Elegir de galeria")) {
                view.openGalery();
            }
        }
    }

    @Override
    public void onDestroy() {
        view=null;
        interactor.getAutoCompletePlaces(null);
        interactor.addRace(null);
    }


    @Override
    public void onSuccess(Object object) {
        if (view!=null){
            if (object instanceof Response){
                view.hideProgress();
                view.showMessage(((Response)object).getMessage());
                view.returnToMainScreen(((Response)object).getMessage());
            }else if (object instanceof PlacePredictions){
                if (((PlacePredictions)object).getStatus().equals("OK")){
                    if (view.getAdapterAutoComplete()==null){
                        view.initAdapterAutoComplete(((PlacePredictions)object));
                    }else{
                        view.resetAdapterAutoComplete(((PlacePredictions)object));
                    }
                }
            }
        }
    }

    @Override
    public void onError(Response response) {
        if (view!=null){
            view.hideProgress();
            view.showMessage(response.getMessage());
        }
    }
}
