package com.mel.seekraces.tasks;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.AsyncTask;

import com.bumptech.glide.Glide;

import java.util.concurrent.ExecutionException;

/**
 * Created by void on 15/01/2017.
 */

public class GetBitmapWithGlideTask extends AsyncTask<Void,Void,Bitmap> {
    private String url;
    private Context context;
    private Bitmap bitmap;
    public GetBitmapWithGlideTask(Context context, String url){
        this.context=context;
        this.url=url;
    }


    @Override
    protected Bitmap doInBackground(Void... params) {
        Bitmap bitmap=null;
            try {
                bitmap= Glide.
                        with(context).
                        load(url).
                        asBitmap().
                        into(250, 250). // Width and height
                        get();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }

        return bitmap;
    }
}
