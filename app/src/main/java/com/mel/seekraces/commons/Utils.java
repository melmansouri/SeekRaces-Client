package com.mel.seekraces.commons;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.util.Base64;

import com.google.firebase.crash.FirebaseCrash;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;


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
            //bitmap = BitmapFactory.decodeStream(imageStream);
            BitmapFactory.Options bmOptions = new BitmapFactory.Options();
            bmOptions.inJustDecodeBounds = true;
            BitmapFactory.decodeStream(imageStream,null,bmOptions);
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
            imageStream = context.getContentResolver().openInputStream(uriImage);
            bitmap= BitmapFactory.decodeStream(imageStream,null,bmOptions);
        } catch (Exception e) {
            FirebaseCrash.report(e);
            e.printStackTrace();
        }catch (OutOfMemoryError error){
            error.printStackTrace();
            FirebaseCrash.report(error);
        }
        return bitmap;
    }

    public static Bitmap getResizedBitmap(Bitmap image, int maxSize) {
        int width = image.getWidth();
        int height = image.getHeight();

        float bitmapRatio = (float)width / (float) height;
        if (bitmapRatio > 1) {
            width = maxSize;
            height = (int) (width / bitmapRatio);
        } else {
            height = maxSize;
            width = (int) (height * bitmapRatio);
        }
        return Bitmap.createScaledBitmap(image, width, height, true);
    }

    public static String convertUriImageToBase64(Bitmap bitmap){

        String encodedImage="";
        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG,50,baos);
            byte[] b = baos.toByteArray();
            encodedImage = Base64.encodeToString(b, Base64.DEFAULT);
        } catch (Exception e) {
            e.printStackTrace();
        }catch (OutOfMemoryError error){

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
        Bitmap bitmap=null;
        try{
            byte[] imageAsBytes = Base64.decode(b64.getBytes(), Base64.DEFAULT);
            bitmap= BitmapFactory.decodeByteArray(imageAsBytes, 0, imageAsBytes.length);
        }catch (Exception e){
            FirebaseCrash.report(e);
        }catch (OutOfMemoryError error){
            FirebaseCrash.report(error);
        }
        return bitmap;
    }

    public static boolean isValidEmail(String email) {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    public static String convertDateSpanishToEnglish(String dateStringSpanish){
        String dateStringEnglish=null;
        try{
            SimpleDateFormat sdfSpanish=new SimpleDateFormat("dd-MM-yyyy");
            Date dateSpanish=sdfSpanish.parse(dateStringSpanish);
            SimpleDateFormat sdfEnglish=new SimpleDateFormat("yyyy-MM-dd");
            dateStringEnglish=sdfEnglish.format(dateSpanish);
        }catch (Exception e){
            e.printStackTrace();
        }

        return dateStringEnglish;
    }

    public static String convertDateEnglishToSpanish(String dateStringEnglish) {
        String dateStringSpanish=null;
        try{
            SimpleDateFormat sdfEnglish=new SimpleDateFormat("yyyy-MM-dd");
            Date dateEnglish=sdfEnglish.parse(dateStringEnglish);
            SimpleDateFormat sdfSpanish=new SimpleDateFormat("dd-MM-yyyy");
            dateStringSpanish=sdfSpanish.format(dateEnglish);
        }catch (Exception e){
            e.printStackTrace();
        }

        return dateStringSpanish;
    }

    public static String getCurrentDateSpanishString(){
        Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month =  c.get(Calendar.MONTH);
        int day =  c.get(Calendar.DAY_OF_MONTH);

        return getCorrectFormatDateSpanish(day,month,year);
    }

    public static String getCurrentTimeString(){
        Calendar c = Calendar.getInstance();
        int hour = c.get(Calendar.HOUR_OF_DAY);
        int minute = c.get(Calendar.MINUTE);
        return getCorrectFormatTime(hour,minute);

    }

    public static String getCorrectFormatTime(int hour,int minute){
        StringBuilder horaMinute=new StringBuilder();
        String hourString = String.valueOf(hour);
        String minuteString = String.valueOf(minute);
        hourString=(hourString.length()>1) ? hourString: "0".concat(hourString);
        minuteString=(minuteString.length()>1) ? minuteString: "0".concat(minuteString);
        horaMinute.append(hourString).append(":").append(minuteString);
        return horaMinute.toString();
    }

    public static String getCorrectFormatDateSpanish(int day,int month,int year){
        StringBuilder fecha=new StringBuilder();
        String anno = String.valueOf(year);
        String mes =  String.valueOf(month+1);
        String dia =  String.valueOf(day);
        mes=(mes.length()>1) ? mes: "0".concat(mes);
        dia=(dia.length()>1) ? dia: "0".concat(dia);

        fecha.append(dia).append("-").append(mes).append("-").append(anno);

        return fecha.toString();
    }

    public static Calendar getCalendarFromDateSpanish(String dateFrom) {
        Calendar calendar=Calendar.getInstance();
        try{
            SimpleDateFormat dateFormat =new SimpleDateFormat("dd-MM-yyyy");
            calendar.setTime(dateFormat.parse(dateFrom));
        }catch (Exception e){
            e.printStackTrace();
        }

        return calendar;
    }


    public static String getPlaceAutoCompleteUrl(String input) {
        //TODO https://maps.googleapis.com/maps/api/place/autocomplete/json?input=alahuri&types=(cities)&language=es&key=AIzaSyDmZRb02-RcKK3I080i5D7nqkMpfOJLHVU
        StringBuilder urlString = new StringBuilder();
        urlString.append("https://maps.googleapis.com/maps/api/place/autocomplete/json");
        urlString.append("?input=");
        try {
            urlString.append(URLEncoder.encode(input.trim(), "utf8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        urlString.append("&types=(cities)&language=es");
        urlString.append("&key=".concat(Constantes.KEY_GOOGLE_API));
        return urlString.toString();
    }

}
