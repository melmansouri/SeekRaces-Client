package com.mel.seekraces.commons;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.provider.MediaStore;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;

/**
 * Created by void on 12/01/2017.
 */

public class UtilsViews {

    public static AlertDialog.Builder createAlertDialog(Context c, String title) {
        AlertDialog.Builder builder = new AlertDialog.Builder(c);
        builder.setTitle(title);
        return builder;
    }

    public static void showSnackBar(CoordinatorLayout root,String message){
        Snackbar snackbar= Snackbar.make(root,message,Snackbar.LENGTH_SHORT);
        snackbar.show();
    }

    public static void openCamera(Activity activity) {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        activity.startActivityForResult(takePictureIntent, Constantes.REQUEST_IMAGE_CAPTURE);
    }

    public static void openGallery(Activity activity){
        Intent intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        intent.setType("image/*");
        activity.startActivityForResult(intent.createChooser(intent, "Selecciona app de imagen"), 1);
    }
}
