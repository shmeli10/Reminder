package com.shmeli.reminder.fragment;

import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;

import com.shmeli.reminder.adapter.CurrentTasksAdapter;
import com.shmeli.reminder.model.Item;
import com.shmeli.reminder.model.ModelTask;

/**
 * Created by Serghei Ostrovschi on 9/20/17.
 */

public abstract class TaskFragment extends Fragment {

    protected RecyclerView                  recyclerView;

    protected RecyclerView.LayoutManager    rvLayoutManager;

    protected CurrentTasksAdapter           adapter;


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
