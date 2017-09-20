package com.shmeli.reminder.dialog;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TimePicker;

import com.shmeli.reminder.R;
import com.shmeli.reminder.Utils;
import com.shmeli.reminder.model.ModelTask;

import java.util.Calendar;

/**
 * Created by Serghei Ostrovschi on 9/19/17.
 */

public class AddingTaskDialogFragment extends DialogFragment {

    private EditText etTitle;
    private EditText etDate;
    private EditText etTime;

    private Spinner spPriority;

    private Calendar calendar;

    private ModelTask task;

    private AddingTaskListener addingTaskListener;

    private String errorEmptyTitle = "";

    public interface AddingTaskListener {

        void onTaskAdded(ModelTask newTask);

        void onTaskAddingCancel();
    }

//    @Override
//    public void onAttach(Context context) {
//        super.onAttach(context);
//
//        Log.e("LOG", "onAttach()");
//
//        try {
//            addingTaskListener = (AddingTaskListener) context; // getActivity();
//        } catch(ClassCastException exc) {
//            throw new ClassCastException(getActivity().toString() + " must implement AddingTaskListener");
//        }
//    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        Log.e("LOG", "onCreateDialog()");

        try {
            addingTaskListener = (AddingTaskListener) getActivity();
        } catch(ClassCastException exc) {
            throw new ClassCastException(getActivity().toString() + " must implement AddingTaskListener");
        }

        errorEmptyTitle = getResources().getString(R.string.dialog_error_empty_title);

        final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        builder.setTitle(R.string.dialog_title);

        LayoutInflater inflater = getActivity().getLayoutInflater();

        View container = inflater.inflate(R.layout.dialog_task, null);

        final TextInputLayout tilTitle    = (TextInputLayout) container.findViewById(R.id.tilDialogTaskTitle);
        tilTitle.setHint(getResources().getString(R.string.task_title));
        etTitle                     = tilTitle.getEditText();

        TextInputLayout tilDate     = (TextInputLayout) container.findViewById(R.id.tilDialogTaskDate);
        tilDate.setHint(getResources().getString(R.string.task_date));
        etDate                      = tilDate.getEditText();
        etDate.setOnClickListener(datePickerListener);

        TextInputLayout tilTime     = (TextInputLayout) container.findViewById(R.id.tilDialogTaskTime);
        tilTime.setHint(getResources().getString(R.string.task_time));
        etTime                      = tilTime.getEditText();
        etTime.setOnClickListener(timePickerListener);


        ArrayAdapter<String> priorityAdapter = new ArrayAdapter<String>(getActivity(),
                android.R.layout.simple_spinner_dropdown_item,
                ModelTask.PRIORITY_LEVELS);

        spPriority                  = (Spinner) container.findViewById(R.id.spDialogTaskPriority);
        spPriority.setAdapter(priorityAdapter);
        spPriority.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        builder.setView(container);

        // ------------------------------------------------------------------------------------ //

        task = new ModelTask();



        calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, Calendar.HOUR_OF_DAY + 1);

        builder.setPositiveButton(R.string.dialog_ok,       okClickListener);
        builder.setNegativeButton(R.string.dialog_cancel,   cancelClickListener);

        AlertDialog alertDialog = builder.create();
        alertDialog.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface dialog) {

                final Button positiveButton = ((AlertDialog) dialog).getButton(DialogInterface.BUTTON_POSITIVE);

                if(etTitle.length() == 0) {
                    positiveButton.setEnabled(false);
                    // tilTitle.setError(getResources().getString(R.string.dialog_error_empty_title));
                    tilTitle.setError(errorEmptyTitle);
                }

                etTitle.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {

                        if(s.length() == 0) {
                            positiveButton.setEnabled(false);
                            // tilTitle.setError(getResources().getString(R.string.dialog_error_empty_title));
                            tilTitle.setError(errorEmptyTitle);
                        }
                        else {
                            positiveButton.setEnabled(true);
                            tilTitle.setErrorEnabled(false);
                        }
                    }

                    @Override
                    public void afterTextChanged(Editable s) {

                    }
                });
            }
        });


        return alertDialog; // super.onCreateDialog(savedInstanceState);
    }

    View.OnClickListener datePickerListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            if(etDate.length() == 0) {
                etDate.setText(" ");
            }

            DialogFragment datePickerFragment = new DatePickerFragment() {

                @Override
                public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {

                    //Calendar calendar = Calendar.getInstance();
                    //calendar.set(year, month, dayOfMonth);
                    calendar.set(Calendar.YEAR,         year);
                    calendar.set(Calendar.MONTH,        month);
                    calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);

                    etDate.setText(Utils.getDate(calendar.getTimeInMillis()));
                }

                @Override
                public void onCancel(DialogInterface dialog) {
                    etDate.setText(null);
                }
            };

            datePickerFragment.show(getFragmentManager(), "DatePickerFragment");
        }
    };

    View.OnClickListener timePickerListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            if(etTime.length() == 0) {
                etTime.setText(" ");
            }

            DialogFragment timePickerFragment = new TimePickerFragment() {

                @Override
                public void onTimeSet(TimePicker view, int hourOfDay, int minute) {

                    //Calendar calendar = Calendar.getInstance();
                    //calendar.set(0, 0, 0, hourOfDay, minute);
                    calendar.set(Calendar.HOUR_OF_DAY,  hourOfDay);
                    calendar.set(Calendar.MINUTE,       minute);
                    calendar.set(Calendar.SECOND,       0);

                    etTime.setText(Utils.getTime(calendar.getTimeInMillis()));
                }

                @Override
                public void onCancel(DialogInterface dialog) {
                    etTime.setText(null);
                }
            };

            timePickerFragment.show(getFragmentManager(), "TimePickerFragment");
        }
    };

    DialogInterface.OnClickListener okClickListener = new DialogInterface.OnClickListener() {
        @Override
        public void onClick(DialogInterface dialog, int which) {

            Log.e("LOG", "okClickListener: addingTaskListener is null: " +(addingTaskListener == null));

            task.setTitle(etTitle.getText().toString());

            if(etDate.length() != 0 || etTime.length() != 0) {
                task.setDate(calendar.getTimeInMillis());
            }

            addingTaskListener.onTaskAdded(task);
            dialog.dismiss();
        }
    };

    DialogInterface.OnClickListener cancelClickListener = new DialogInterface.OnClickListener() {
        @Override
        public void onClick(DialogInterface dialog, int which) {

            Log.e("LOG", "cancelClickListener: addingTaskListener is null: " +(addingTaskListener == null));

            addingTaskListener.onTaskAddingCancel();
            dialog.cancel();
        }
    };

    AdapterView.OnItemSelectedListener spinnerItemSelectListener = new AdapterView.OnItemSelectedListener() {

        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            task.setPriority(position);
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }
    };
}
