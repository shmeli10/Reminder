package com.shmeli.reminder.fragment;

import android.app.AlertDialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.shmeli.reminder.MainActivity;
import com.shmeli.reminder.R;
import com.shmeli.reminder.adapter.TaskAdapter;
import com.shmeli.reminder.alarm.AlarmHelper;
import com.shmeli.reminder.dialog.EditTaskDialogFragment;
import com.shmeli.reminder.model.Item;
import com.shmeli.reminder.model.ModelTask;

/**
 * Created by Serghei Ostrovschi on 9/20/17.
 */

public abstract class TaskFragment extends Fragment {

    protected RecyclerView                  recyclerView;

    protected RecyclerView.LayoutManager    rvLayoutManager;

    protected TaskAdapter                   adapter;

    public MainActivity                     activity;

    public AlarmHelper                      alarmHelper;

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        Log.e("LOG", "TaskFragment: onActivityCreated()");

        if(getActivity() != null) {
            activity = (MainActivity) getActivity();
        }

        alarmHelper = AlarmHelper.getInstance();

        addTaskFromDB();
    }

    public abstract void addTask(ModelTask  newTask,
                                 boolean    saveToDB);

    public abstract void addTaskFromDB();

    public void updateTask(ModelTask task) {
        adapter.updateTask(task);
    }

    public void removeTaskDialog(final int location) {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(getActivity());

        dialogBuilder.setMessage(R.string.dialog_removing_message);

        Item item = adapter.getItem(location);

        if(item.isTask()) {
            ModelTask removingTask = (ModelTask) item;

            final long timeStamp = removingTask.getTimeStamp();

            final boolean[] isRemoved = {false};

            dialogBuilder.setPositiveButton(R.string.dialog_ok,
                                            new DialogInterface.OnClickListener() {
                                                @Override
                                                public void onClick(DialogInterface dialog, int which) {

                                                    adapter.removeItem(location);
                                                    isRemoved[0] = true;

                                                    Snackbar snackbar = Snackbar.make(  getActivity().findViewById(R.id.coordinator),
                                                                                        R.string.removed,
                                                                                        Snackbar.LENGTH_LONG);

                                                    snackbar.setAction( R.string.dialog_cancel,
                                                                        new View.OnClickListener() {
                                                                            @Override
                                                                            public void onClick(View v) {
                                                                                addTask(activity.dbHelper.query().getTask(timeStamp),
                                                                                        false);

                                                                                isRemoved[0] = false;
                                                                            }
                                                    });

                                                    snackbar.getView().addOnAttachStateChangeListener(new View.OnAttachStateChangeListener() {
                                                        @Override
                                                        public void onViewAttachedToWindow(View v) {

                                                        }

                                                        @Override
                                                        public void onViewDetachedFromWindow(View v) {
                                                            if(isRemoved[0]) {
                                                                alarmHelper.removeAlarm(timeStamp);

                                                                activity.dbHelper.removeTask(timeStamp);
                                                            }
                                                        }
                                                    });

                                                    snackbar.show();

                                                    dialog.dismiss();
                                                }
            });

            dialogBuilder.setNegativeButton(R.string.dialog_cancel,
                                            new DialogInterface.OnClickListener() {

                                                @Override
                                                public void onClick(DialogInterface dialog, int which) {
                                                    dialog.cancel();
                                                }
            });
        }

        dialogBuilder.show();
    }

    public void showTaskEditDialog(ModelTask task) {
        DialogFragment editingTaskDialog = EditTaskDialogFragment.newInstance(task);
        editingTaskDialog.show(getActivity().getFragmentManager(), "EditTaskDialogFragment");
    }

    public abstract void findTasks(String title);

    public abstract void moveTask(ModelTask task);

    public abstract void checkAdapter();
}
