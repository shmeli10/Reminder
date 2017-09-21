package com.shmeli.reminder.fragment;


import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.shmeli.reminder.R;
import com.shmeli.reminder.adapter.DoneTasksAdapter;
import com.shmeli.reminder.database.DBHelper;
import com.shmeli.reminder.model.ModelTask;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class DoneTaskFragment extends TaskFragment {

//    private RecyclerView                rvDoneTasks;
//
//    private RecyclerView.LayoutManager  rvLayoutManager;

    OnTaskRestoreListener onTaskRestoreListener;

    public interface OnTaskRestoreListener {
        void onTaskRestore(ModelTask newTask);
    }

    public DoneTaskFragment() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        try {
            onTaskRestoreListener = (OnTaskRestoreListener) activity;
        } catch(ClassCastException exc) {
            throw new ClassCastException(activity.toString() + " must implement OnTaskRestoreListener");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(   R.layout.fragment_done_task,
                                            container,
                                            false);

        rvLayoutManager = new LinearLayoutManager(getActivity());

        adapter = new DoneTasksAdapter(this);

        recyclerView = (RecyclerView) rootView.findViewById(R.id.rvDoneTasks);
        recyclerView.setLayoutManager(rvLayoutManager);
        recyclerView.setAdapter(adapter);

        return rootView;
    }

    @Override
    public void findTasks(String title) {
        adapter.removeAllItems();

        List<ModelTask> taskList = new ArrayList<>();

        taskList.addAll(activity.dbHelper.query().getTaskList( DBHelper.SELECTION_LIKE_TITLE + " AND " + DBHelper.SELECTION_STATUS,
                                                                new String[] {"%" +title+ "%", Integer.toString(ModelTask.STATUS_DONE)},
                                                                DBHelper.TASK_DATE_COLUMN));

        for(int i=0; i<taskList.size(); i++) {
            addTask(taskList.get(i), false);
        }
    }

    @Override
    public void addTaskFromDB() {
        adapter.removeAllItems();

        List<ModelTask> taskList = new ArrayList<>();

        taskList.addAll(activity.dbHelper.query().getTaskList(  DBHelper.SELECTION_STATUS,
                                                                new String[] {Integer.toString(ModelTask.STATUS_DONE)},
                                                                DBHelper.TASK_DATE_COLUMN));

        for(int i=0; i<taskList.size(); i++) {
            addTask(taskList.get(i), false);
        }
    }

    @Override
    public void moveTask(ModelTask task) {
        onTaskRestoreListener.onTaskRestore(task);
    }
}
