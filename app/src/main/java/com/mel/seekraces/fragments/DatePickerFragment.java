package com.mel.seekraces.fragments;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.text.TextUtils;

import com.mel.seekraces.commons.Utils;

import java.util.Calendar;

/**
 * Created by void on 29/01/2017.
 */

public class DatePickerFragment extends DialogFragment{
    private DatePickerDialog.OnDateSetListener onDateSetListener;
    private String dateFrom="";

    public DatePickerFragment(){
    }
    public void setOnDateSetListener(DatePickerDialog.OnDateSetListener onDateSetListener){
        this.onDateSetListener=onDateSetListener;
    }

    public void setOnDateSetListener(DatePickerDialog.OnDateSetListener onDateSetListener,String dateFrom){
        this.onDateSetListener=onDateSetListener;
        this.dateFrom=dateFrom;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Calendar c;
        long currentTimeMillis=0;
        if (TextUtils.isEmpty(dateFrom)){
            c = Calendar.getInstance();
        }else{
            c= Utils.getCalendarFromDateSpanish(dateFrom);
            currentTimeMillis=c.getTimeInMillis();
        }

        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);
        DatePickerDialog datePickerDialog=new DatePickerDialog(getActivity(), onDateSetListener, year, month, day);

        if (currentTimeMillis!=0){
            datePickerDialog.getDatePicker().setMinDate(currentTimeMillis);
        }
        return datePickerDialog;
    }
}
