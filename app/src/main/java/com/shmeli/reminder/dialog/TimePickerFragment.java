package com.shmeli.reminder.dialog;

import android.app.Dialog;
import android.app.DialogFragment;
import android.app.TimePickerDialog;

import android.os.Bundle;
import android.text.format.DateFormat;
import android.view.View;
import android.widget.TimePicker;

import java.util.Calendar;

/**
 * Created by Serghei Ostrovschi on 9/19/17.
 */

public class TimePickerFragment extends     DialogFragment
                                implements  TimePickerDialog.OnTimeSetListener {

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        Calendar calendar   = java.util.Calendar.getInstance();
        int hour            = calendar.get(Calendar.HOUR_OF_DAY);
        int minute          = calendar.get(Calendar.MINUTE);

        return new TimePickerDialog(getActivity(),
                                    this, hour, minute, DateFormat.is24HourFormat(getActivity()));
    }

    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {

    }
}
