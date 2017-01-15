package com.mel.seekraces.tasks;

import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;

import com.mel.seekraces.commons.Utils;

/**
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
        return Utils.convertUriImageToBase64(bitmap);
    }
}
