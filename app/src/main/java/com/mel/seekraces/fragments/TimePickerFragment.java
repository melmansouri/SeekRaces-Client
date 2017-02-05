package com.mel.seekraces.fragments;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.text.TextUtils;
import android.text.format.DateFormat;
import android.widget.TextView;
import android.widget.TimePicker;

import com.mel.seekraces.R;
import com.mel.seekraces.commons.Utils;

import java.util.Calendar;

/**
 * Created by void on 29/01/2017.
 */

public class TimePickerFragment extends DialogFragment{
    private TimePickerDialog.OnTimeSetListener onTimeSetListener;

    public TimePickerFragment(){
    }


    public TimePickerFragment(TimePickerDialog.OnTimeSetListener onTimeSetListener){
        this.onTimeSetListener=onTimeSetListener;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        final Calendar c = Calendar.getInstance();
        int hour = c.get(Calendar.HOUR_OF_DAY);
        int minute = c.get(Calendar.MINUTE);

        return new TimePickerDialog(getActivity(),onTimeSetListener, hour, minute,
                DateFormat.is24HourFormat(getActivity()));
    }
}
