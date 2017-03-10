package com.mel.seekraces.fragments.editRace;

import android.text.TextUtils;

import com.mel.seekraces.commons.Constantes;
import com.mel.seekraces.commons.RMapped;
import com.mel.seekraces.commons.Utils;
import com.mel.seekraces.entities.Race;
import com.mel.seekraces.entities.PlacePredictions;
import com.mel.seekraces.entities.Response;
import com.mel.seekraces.interfaces.IListennerCallBack;
import com.mel.seekraces.interfaces.fragmentEditRace.IEditRaceInteractor;
import com.mel.seekraces.interfaces.fragmentEditRace.IEditRacePresenter;
import com.mel.seekraces.interfaces.fragmentEditRace.IEditRaceView;

/**
 * Created by void on 05/02/2017.
 */

public class EditRaceFragmentPresenterImpl implements IEditRacePresenter, IListennerCallBack{
    private IEditRaceView view;
    private IEditRaceInteractor interactor;

    public EditRaceFragmentPresenterImpl(IEditRaceView view) {
        this.view = view;
        this.interactor=new EditRaceFragmentInteractorImpl(this);
    }

    @Override
    public void editRace(boolean isOnline,Race race) {
        if (view!=null){
            if (!isOnline){
                view.hideProgress();
                view.showComponents();
                view.showMessage("Comprueba tu conexión");
                return;
            }
            if (!verifyDataUser(race)) {
                view.hideProgress();
                view.showComponents();
                return;
            }
            interactor.editRace(race);
        }
    }

    @Override
    public void onTextChangedPlaces(String text) {
        if(text.length()>1){
            interactor.getAutoCompletePlaces(null);
            interactor.getAutoCompletePlaces(Utils.getPlaceAutoCompleteUrl(text));
        }
    }

    private boolean verifyDataUser(Race race) {
        boolean result = true;
        if (TextUtils.isEmpty(race.getPlace())){
            view.showErrorPlaces("Debe de introducir el lugar de la carrera");
            result=false;
        }
        if (TextUtils.isEmpty(race.getName())) {
            view.showErrorName("La carrera debe de tener un nombre");
            result = false;
        }
        if (race.getDistance()==0){
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
    public void onOptionsItemSelected(int idSelected) {
        if (view!=null){
            if(idSelected==RMapped.ITEM_EDIT_RACE.getValue()){
                view.editRace();
            }
        }
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
        interactor.editRace(null);
        interactor=null;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, int[] grantResults) {
        boolean isGranted=true;
        if (view!=null){
            if (grantResults.length > 0) {
                for (int i=0;i<grantResults.length;i++){
                    if (grantResults[i] != RMapped.PERMISSION_GRANTED.getValue()){
                        isGranted=false;
                        break;
                    }
                }
                if (isGranted){
                    if (requestCode==Constantes.REQUEST_CODE_PERMISSION_READ_EXTERNAL_STORAGE){
                        view.openGalery();
                    }
                }
            }
        }
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
