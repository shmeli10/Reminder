package com.shmeli.reminder.fragment;


import android.app.Activity;
import android.os.Bundle;

import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.shmeli.reminder.R;
import com.shmeli.reminder.adapter.CurrentTasksAdapter;
import com.shmeli.reminder.database.DBHelper;
import com.shmeli.reminder.model.Item;
import com.shmeli.reminder.model.ModelSeparator;
import com.shmeli.reminder.model.ModelTask;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class CurrentTaskFragment extends TaskFragment {

    // private RecyclerView                rvCurrentTasks;
//    private RecyclerView                recyclerView;
//
//    private RecyclerView.LayoutManager  rvLayoutManager;
//
//    private CurrentTasksAdapter         adapter;

    OnTaskDoneListener  onTaskDoneListener;

    public interface OnTaskDoneListener {
        void onTaskDone(ModelTask newTask);
    }

    public CurrentTaskFragment() {
        // Required empty public constructor
    }


    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        try {
            onTaskDoneListener = (OnTaskDoneListener) activity;
        } catch(ClassCastException exc) {
            throw new ClassCastException(activity.toString() + " must implement OnTaskDoneListener");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup      container,
                             Bundle         savedInstanceState) {


        // Inflate the layout for this fragment
        View rootView = inflater.inflate(   R.layout.fragment_current_task,
                                            container,
                                            false);

        rvLayoutManager = new LinearLayoutManager(getActivity());

        adapter         = new CurrentTasksAdapter(this);

//        rvCurrentTasks = (RecyclerView) rootView.findViewById(R.id.rvCurrentTasks);
//        rvCurrentTasks.setLayoutManager(rvLayoutManager);
//        rvCurrentTasks.setAdapter(adapter);

        recyclerView = (RecyclerView) rootView.findViewById(R.id.rvCurrentTasks);
        recyclerView.setLayoutManager(rvLayoutManager);
        recyclerView.setAdapter(adapter);

        return rootView;
    }

    @Override
    public void findTasks(String title) {
        adapter.removeAllItems();

        List<ModelTask> taskList = new ArrayList<>();

        taskList.addAll(activity.dbHelper.query().getTaskList(  DBHelper.SELECTION_LIKE_TITLE + " AND " + DBHelper.SELECTION_STATUS + " OR " + DBHelper.SELECTION_STATUS,
                                                                new String[] {"%" +title+ "%", Integer.toString(ModelTask.STATUS_CURRENT), Integer.toString(ModelTask.STATUS_OVERDUE)},
                                                                DBHelper.TASK_DATE_COLUMN));
        for(int i=0; i<taskList.size(); i++) {
            addTask(taskList.get(i), false);
        }
    }

    @Override
    public void addTask(ModelTask   newTask,
                        boolean     saveToDB) {

        int position = -1;

        ModelSeparator separator = null;

        for(int i=0; i<adapter.getItemCount(); i++) {

            Item item = adapter.getItem(i);

            if(item.isTask()) {

                ModelTask task = (ModelTask) item;

                if(newTask.getDate() < task.getDate()) {

                    position = i;
                    break;
                }
            }
        }

//        int dayOfYear = Calendar.getInstance().get(Calendar.DAY_OF_YEAR);
//        int nextDayOfYear = (Calendar.getInstance().get(Calendar.DAY_OF_YEAR) + 1);

        if(newTask.getDate() != 0) {
            Calendar calendar = Calendar.getInstance();
            calendar.setTimeInMillis(newTask.getDate());

            if(calendar.get(Calendar.DAY_OF_YEAR) < Calendar.getInstance().get(Calendar.DAY_OF_YEAR)) {
                newTask.setDateStatus(ModelSeparator.TYPE_OVERDUE);

                if(!adapter.containsSeparatorOverdue) {
                    adapter.containsSeparatorOverdue = true;

                    separator = new ModelSeparator(ModelSeparator.TYPE_OVERDUE);
                }
            }
            else if(calendar.get(Calendar.DAY_OF_YEAR) == Calendar.getInstance().get(Calendar.DAY_OF_YEAR)) {
                newTask.setDateStatus(ModelSeparator.TYPE_TODAY);

                if(!adapter.containsSeparatorToday) {
                    adapter.containsSeparatorToday = true;

                    separator = new ModelSeparator(ModelSeparator.TYPE_TODAY);
                }
            }
            else if(calendar.get(Calendar.DAY_OF_YEAR) == (Calendar.getInstance().get(Calendar.DAY_OF_YEAR) + 1)) {
                newTask.setDateStatus(ModelSeparator.TYPE_TOMORROW);

                if(!adapter.containsSeparatorTomorrow) {
                    adapter.containsSeparatorTomorrow = true;

                    separator = new ModelSeparator(ModelSeparator.TYPE_TOMORROW);
                }
            }
            else if(calendar.get(Calendar.DAY_OF_YEAR) > (Calendar.getInstance().get(Calendar.DAY_OF_YEAR) + 1)) {
                newTask.setDateStatus(ModelSeparator.TYPE_FUTURE);

                if(!adapter.containsSeparatorFuture) {
                    adapter.containsSeparatorFuture = true;

                    separator = new ModelSeparator(ModelSeparator.TYPE_FUTURE);
                }
            }
        }

        if(position != -1) {

            if(!adapter.getItem(position - 1).isTask()) {

                if(position - 2 >= 0) {

                    Item item = adapter.getItem(position - 2);

                    if(item.isTask()) {
                        ModelTask task = (ModelTask) item;

                        if(task.getDateStatus() == newTask.getDateStatus()) {
                            position -= 1;
                        }
                    }

//                    if(position - 2 >= 0 && item.isTask()) {
//                        ModelTask task = (ModelTask) item;
//
//                        if(task.getDateStatus() == newTask.getDateStatus()) {
//                            position -= 1;
//                        }
//                    }
//                    else if(position - 2 < 0 && newTask.getDate() == 0) {
//                        position -= 1;
//                    }

                }
                else {
                    if(newTask.getDate() == 0) {
                        position -= 1;
                    }
                }
            }

            if(separator != null) {
                adapter.addItem(position - 1,
                                separator);
            }

            adapter.addItem(position, newTask);
        }
        else {

            if(separator != null) {
                adapter.addItem(separator);
            }

            adapter.addItem(newTask);
        }

        if(saveToDB) {
            activity.dbHelper.saveTask(newTask);
        }
    }

    @Override
    public void addTaskFromDB() {
        Log.e("LOG", "CurrentTaskFragment: addTaskFromDB()");

        adapter.removeAllItems();

        List<ModelTask> taskList = new ArrayList<>();

        taskList.addAll(activity.dbHelper.query().getTaskList(  DBHelper.SELECTION_STATUS + " OR " + DBHelper.SELECTION_STATUS,
                                                                new String[] {Integer.toString(ModelTask.STATUS_CURRENT), Integer.toString(ModelTask.STATUS_OVERDUE)},
                                                                DBHelper.TASK_DATE_COLUMN));

        Log.e("LOG", "CurrentTaskFragment: addTaskFromDB(): taskList.size= " +taskList.size());

        for(int i=0; i<taskList.size(); i++) {
            addTask(taskList.get(i), false);
        }
    }

    @Override
    public void moveTask(ModelTask task) {
        alarmHelper.removeAlarm(task.getTimeStamp());

        onTaskDoneListener.onTaskDone(task);
    }

    @Override
    public void checkAdapter() {
        if(adapter == null) {
            adapter = new CurrentTasksAdapter(this);

            addTaskFromDB();
        }
    }

//    public void addTask(ModelTask newTask) {
//
//        int position = -1;
//
//        for(int i=0; i<adapter.getItemCount(); i++) {
//
//            Item item = adapter.getItem(i);
//
//            if(item.isTask()) {
//
//                ModelTask task = (ModelTask) item;
//
//                if(newTask.getDate() < task.getDate()) {
//
//                    position = i;
//                    break;
//                }
//            }
//        }
//
//        if(position != -1) {
//            adapter.addItem(position, newTask);
//        }
//        else {
//            adapter.addItem(newTask);
//        }
//    }
}
