package com.mel.seekraces.fragments;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.widget.DatePicker;

import java.util.Calendar;
import java.util.Date;

/**
 * Created by void on 29/01/2017.
 */

public class DatePickerFragment extends DialogFragment{
    private DatePickerDialog.OnDateSetListener onDateSetListener;

    public DatePickerFragment(){
    }
    public DatePickerFragment(DatePickerDialog.OnDateSetListener onDateSetListener){
        this.onDateSetListener=onDateSetListener;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        final Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);
        DatePickerDialog datePickerDialog=new DatePickerDialog(getActivity(), onDateSetListener, year, month, day);
        datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
        return datePickerDialog;
    }
}
