package com.mel.seekraces.tasks;

import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;

import com.bumptech.glide.Glide;
import com.mel.seekraces.commons.Utils;

import java.util.concurrent.ExecutionException;

/**
 * Tarea asincrona para obtener el base64 de una imagen apartir del bitmap o la uri
 * Created by void on 15/01/2017.
 */

public class EncodeImageTask extends AsyncTask<Void,Void,String> {
    private Uri fileUri;
    private Context context;
    private Bitmap bitmap;
    public EncodeImageTask(Context context, Uri fileUri){
        this.fileUri=fileUri;
        this.context=context;
    }

    public EncodeImageTask(Context context, Bitmap bitmap){
        this.bitmap=bitmap;
        this.context=context;
    }


    @Override
    protected String doInBackground(Void... params) {
        //si uso la uri para obtener el base64 uso glide para obtener el bitmap
        if (fileUri!=null){
            try {
                bitmap= Glide.
                        with(context).
                        load(fileUri).
                        asBitmap().
                        into(100, 100). // Width and height
                        get();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
        }
        return Utils.convertUriImageToBase64(bitmap);
    }
}
