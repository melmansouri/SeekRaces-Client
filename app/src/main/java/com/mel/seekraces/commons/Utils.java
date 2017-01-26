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
import java.io.File;
import java.io.FileOutputStream;
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

    public static boolean mkdir(String directory) {
        boolean result=true;
        File folder = new File(directory);
        if (!folder.exists()) {
            result=folder.mkdirs();
        }
        return result;
    }
    public static void saveImage(String base64, String pathImagen) {

        try {
            byte[] decodedString = Base64.decode(base64, Base64.DEFAULT);
            Bitmap bmp = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
            if (bmp != null) {
                File imageFile = new File(pathImagen);
                FileOutputStream out = new FileOutputStream(imageFile);
                bmp.compress(Bitmap.CompressFormat.JPEG, 60, out);
                out.flush();
                out.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static Bitmap base64ToBitmap(String b64) {
        byte[] imageAsBytes = Base64.decode(b64.getBytes(), Base64.DEFAULT);
        return BitmapFactory.decodeByteArray(imageAsBytes, 0, imageAsBytes.length);
    }

    public static boolean isValidEmail(String email) {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }
}
