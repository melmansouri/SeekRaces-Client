package com.mel.seekraces.commons;

import android.content.Context;
import android.support.v7.app.AlertDialog;

/**
 * Created by void on 12/01/2017.
 */

public class UtilsViews {

    public static AlertDialog.Builder createAlertDialog(Context c, String title){
        AlertDialog.Builder builder = new AlertDialog.Builder(c);
        builder.setTitle(title);
        return builder;
    }
}
