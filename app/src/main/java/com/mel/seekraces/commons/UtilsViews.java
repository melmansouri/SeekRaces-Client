package com.mel.seekraces.commons;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.provider.MediaStore;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.app.DialogFragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;

import com.mel.seekraces.fragments.DatePickerFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by void on 12/01/2017.
 */

public class UtilsViews {

    public static ArrayAdapter<String> getSpinnerDistanceAdapter(Context context,int resource){
        ArrayAdapter<String> arrayAdapter;
        List<String> distancias=new ArrayList<>();
        for (int i=1;i<=100;i++){
            distancias.add(i+"KM");
        }
        arrayAdapter=new ArrayAdapter<>(context,resource,distancias);

        return arrayAdapter;
    }

    public void showDateDialogPicker(Activity activity,DatePickerDialog.OnDateSetListener onDateSetListener){
        /*DialogFragment datePickerFragment= new DatePickerFragment(onDateSetListener);
        datePickerFragment.show(activity.getSupportFragmentManager(), "datePicker");*/
    }

    public static AlertDialog.Builder createAlertDialog(Context c, String title) {
        AlertDialog.Builder builder = new AlertDialog.Builder(c);
        if (title !=null) {
            builder.setTitle(title);
        }
        return builder;
    }

    public static ProgressDialog createProgressDialog(Context context, String message){
        ProgressDialog progressDialog=new ProgressDialog(context);
        progressDialog.setMessage(message);
        progressDialog.setIndeterminate(true);

        return progressDialog;
    }

    public static void showSnackBar(CoordinatorLayout root,String message){
        Snackbar snackbar= Snackbar.make(root,message,Snackbar.LENGTH_LONG);
        snackbar.show();
    }

    public static void openCamera(Activity activity) {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        activity.startActivityForResult(takePictureIntent, Constantes.REQUEST_IMAGE_CAPTURE_CAMERA);
    }

    public static void openGallery(Activity activity){
        Intent intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        intent.setType("image/*");
        activity.startActivityForResult(intent.createChooser(intent, "Selecciona app de imagen"), Constantes.REQUEST_IMAGE_CAPTURE_GALLERY);
    }

    @TargetApi(Build.VERSION_CODES.M)
    public static boolean PermisosValidos(Activity activity,int requestCode) {

        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP) {

            int camera = ContextCompat.checkSelfPermission(activity,Manifest.permission.CAMERA);
            int acceso_tarjeta_memoria = ContextCompat.checkSelfPermission(activity,Manifest.permission.READ_EXTERNAL_STORAGE);
            int escritura_tarjeta_memoria = ContextCompat.checkSelfPermission(activity,Manifest.permission.WRITE_EXTERNAL_STORAGE);
            if (camera != PackageManager.PERMISSION_GRANTED
                    || acceso_tarjeta_memoria != PackageManager.PERMISSION_GRANTED
                    || escritura_tarjeta_memoria != PackageManager.PERMISSION_GRANTED) {
                activity.requestPermissions(new String[]{Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE}, requestCode);
                return false;
            }
        }
        return true;
    }

    public static void closeKeyBoard(Activity activity){
        View view = activity.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager)activity.getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    public static void disableScreen(Activity activity){
        activity.getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
                WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
    }

    public static void enableSreen(Activity activity){
        activity.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
    }

}
