package com.mel.seekraces.commons;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.util.Base64;

import java.io.ByteArrayOutputStream;
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

    private static Bitmap resizeImage(String imagePath){
        // Get the dimensions of the bitmap
        BitmapFactory.Options bmOptions = new BitmapFactory.Options();
        bmOptions.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(imagePath, bmOptions);
        int photoW = bmOptions.outWidth;
        int photoH = bmOptions.outHeight;

        int targetH;
        int targetW;
        if(photoH > photoW){
            targetH = 1024;
            targetW = photoW*1024 / photoH;
        }else{
            targetH = photoH*1024 / photoW;
            targetW = 1024;
        }

        // Determine how much to scale down the image
        int scaleFactor = Math.min(photoW/targetW, photoH/targetH);

        // Decode the image file into a Bitmap sized to fill the View
        bmOptions.inJustDecodeBounds = false;
        bmOptions.inSampleSize = scaleFactor;
        bmOptions.inPurgeable = true;

        return BitmapFactory.decodeFile(imagePath, bmOptions);
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

    public static boolean isValidEmail(String email) {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    public static void saveStringSP(Context context, String file, String key,String value) {
        SharedPreferences prefs = context.getSharedPreferences(file, Context.MODE_PRIVATE);
        SharedPreferences.Editor ed = prefs.edit();
        ed.putString(key, value);
        ed.commit();
    }

    public static String getStringSP(Context context, String file, String key) {
        String value="";
        SharedPreferences prefs =
                context.getSharedPreferences(file, Context.MODE_PRIVATE);
        if (prefs.contains(key)){
            value=prefs.getString(key,null);
        }
        return value;
    }

    public static void removeValueSP(Context context, String file, String key) {
        SharedPreferences prefs =
                context.getSharedPreferences(file, Context.MODE_PRIVATE);
        SharedPreferences.Editor ed = prefs.edit();
        ed.remove(key);
        ed.commit();
    }
}
