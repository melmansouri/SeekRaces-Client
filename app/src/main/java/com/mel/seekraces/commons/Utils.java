package com.mel.seekraces.commons;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.util.Base64;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;


/**
 * Created by moha on 13/01/17.
 */

public class Utils {
    public static String convertUriImageToBase64(Context c, Uri imageUri){

        String encodedImage="";
        try {
            InputStream imageStream = c.getContentResolver().openInputStream(imageUri);
            Bitmap selectedImage = BitmapFactory.decodeStream(imageStream);
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            selectedImage.compress(Bitmap.CompressFormat.JPEG,40,baos);
            byte[] b = baos.toByteArray();
            encodedImage = Base64.encodeToString(b, Base64.DEFAULT);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return encodedImage;
    }

    public static Bitmap getBitmapFromUriImage(Context context,Uri uriImage){
        Bitmap bitmap=null;
        try {
            InputStream imageStream = context.getContentResolver().openInputStream(uriImage);
            bitmap = BitmapFactory.decodeStream(imageStream);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return bitmap;
    }

    public static String convertUriImageToBase64(Bitmap bitmap){

        String encodedImage="";
        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG,40,baos);
            byte[] b = baos.toByteArray();
            encodedImage = Base64.encodeToString(b, Base64.DEFAULT);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return encodedImage;
    }

    public static boolean isOnline(Context context) {
        ConnectivityManager cm =
                (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        return activeNetwork != null && activeNetwork.isConnected();
    }

    public static Object getSerializableObjectFromIntent(Intent intent,String name){
        Object object=null;
        try{
            object=intent.getSerializableExtra(name);
        }catch (Exception e){
            e.printStackTrace();
        }
        return object;
    }
}
