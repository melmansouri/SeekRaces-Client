package com.mel.seekraces.activities.editProfile;

import android.text.TextUtils;

import com.google.gson.Gson;
import com.mel.seekraces.commons.Constantes;
import com.mel.seekraces.commons.RMapped;
import com.mel.seekraces.commons.SharedPreferencesSingleton;
import com.mel.seekraces.entities.Response;
import com.mel.seekraces.entities.User;
import com.mel.seekraces.interfaces.IListennerCallBack;
import com.mel.seekraces.interfaces.editProfile.IEditProfileInteractor;
import com.mel.seekraces.interfaces.editProfile.IEditProfilePresenter;
import com.mel.seekraces.interfaces.editProfile.IEditProfileView;

/**
 * Created by void on 12/02/2017.
 */

public class EditProfilePresenterImpl implements IEditProfilePresenter, IListennerCallBack{

    private IEditProfileView view;
    private IEditProfileInteractor interactor;
    private SharedPreferencesSingleton sharedPreferencesSingleton;

    public EditProfilePresenterImpl(IEditProfileView view, SharedPreferencesSingleton sharedPreferencesSingleton){
        this.view=view;
        interactor=new EditProfileInteractorImpl(this);
        this.sharedPreferencesSingleton=sharedPreferencesSingleton;
    }

    @Override
    public void editProfile(boolean isOnline, User user) {
        if(view!=null){
            if (!isOnline){
                view.hideProgress();
                view.showComponents();
                view.showMessage("Comprueba tu conexión");
                return;
            }
            if (!verifyDataUser(user)) {
                view.hideProgress();
                view.showComponents();
                return;
            }
            if (TextUtils.isEmpty(user.getPhotoBase64())){
                user.setPhoto_url("");
            }
            interactor.editProfile(user);
        }
    }

    private boolean verifyDataUser(User user) {
        boolean result = true;
        if (user.getUsername().isEmpty()) {
            view.showErrorUserName("El nombre de usuario no puede estar vacío");
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
            if(idSelected== RMapped.ITEM_HOME_BACK.getValue()) {
                view.finishActivity();
                return true;
            }else if(idSelected==RMapped.ITEM_EDIT_PROFILE.getValue()){
                view.editProfile();
                return true;
            }
            return view.retunSuperOnOptionsItemSelected();
        }
        return false;
    }

    @Override
    public void selectOptionDialogPicture(String[] options, int selected) {
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
        interactor.editProfile(null);
        interactor=null;
        sharedPreferencesSingleton=null;
    }

    @Override
    public void onSuccess(Object object) {
        if(view!=null){
            view.hideProgress();
            User user=new Gson().fromJson(((Response)object).getContent(),User.class);
            sharedPreferencesSingleton.saveStringSP(Constantes.KEY_USERNAME,user.getUsername());
            String photoName=user.getPhoto_url();
            if (!TextUtils.isEmpty(photoName)){
                sharedPreferencesSingleton.saveStringSP(Constantes.KEY_USER_NAME_PICTURE,photoName.concat(".png"));
            }
            view.returnToMainScreen(((Response)object).getMessage());
        }
    }

    @Override
    public void onError(Response response) {
        if (view!=null){
            view.hideProgress();
            view.showComponents();
            view.showMessage(response.getMessage());
        }
    }
}
