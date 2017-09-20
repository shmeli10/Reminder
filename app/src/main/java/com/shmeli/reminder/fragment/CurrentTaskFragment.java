package com.shmeli.reminder.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.shmeli.reminder.R;
import com.shmeli.reminder.adapter.CurrentTasksAdapter;
import com.shmeli.reminder.model.Item;
import com.shmeli.reminder.model.ModelTask;


/**
 * A simple {@link Fragment} subclass.
 */
public class CurrentTaskFragment extends Fragment {

    private RecyclerView                rvCurrentTasks;

    private RecyclerView.LayoutManager  rvLayoutManager;

    private CurrentTasksAdapter         adapter;

    public CurrentTaskFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View rootView = inflater.inflate(   R.layout.fragment_current_task,
                                            container,
                                            false);

        rvLayoutManager = new LinearLayoutManager(getActivity());

        adapter         = new CurrentTasksAdapter();

        rvCurrentTasks = (RecyclerView) rootView.findViewById(R.id.rvCurrentTasks);
        rvCurrentTasks.setLayoutManager(rvLayoutManager);
        rvCurrentTasks.setAdapter(adapter);

        return rootView;
    }

    public void addTask(ModelTask newTask) {

        int position = -1;

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

        if(position != -1) {
            adapter.addItem(position, newTask);
        }
        else {
            adapter.addItem(newTask);
        }
    }
}
