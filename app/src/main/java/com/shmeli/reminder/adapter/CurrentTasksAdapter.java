package com.shmeli.reminder.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.shmeli.reminder.R;
import com.shmeli.reminder.Utils;
import com.shmeli.reminder.model.Item;
import com.shmeli.reminder.model.ModelTask;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Serghei Ostrovschi on 9/20/17.
 */

public class CurrentTasksAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int TYPE_TASK      = 0;
    private static final int TYPE_SEPARATOR = 1;

    List<Item> itemList = new ArrayList<>();

    public Item getItem(int position) {
        return itemList.get(position);
    }

    public void addItem(Item newItem) {
        itemList.add(newItem);

        notifyItemInserted(getItemCount() - 1);
    }

    public void addItem(int     location,
                        Item    newItem) {

        itemList.add(location, newItem);

        notifyItemInserted(location);
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        switch(viewType) {

            case TYPE_TASK:

                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.model_task, parent, false);

                TextView title  = (TextView) view.findViewById(R.id.tvTaskTitle);
                TextView date   = (TextView) view.findViewById(R.id.tvTaskDate);

                return new TaskViewHolder(view, title, date);
//            case TYPE_SEPARATOR:
//
//                break;
            default:
                return null;
        }

//        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        Item item = itemList.get(position);

        if(item.isTask()) {
            holder.itemView.setEnabled(true);

            ModelTask task = (ModelTask) item;

            TaskViewHolder taskViewHolder = (TaskViewHolder) holder;
            taskViewHolder.title.setText(task.getTitle());

            if(task.getDate() != 0) {
                taskViewHolder.date.setText(Utils.getFullDate(task.getDate()));
            }
        }
    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

    @Override
    public int getItemViewType(int position) {
        if(getItem(position).isTask()) {
            return TYPE_TASK;
        }
        else {
            return TYPE_SEPARATOR;
        }
    }

    private class TaskViewHolder extends RecyclerView.ViewHolder {

        TextView title;
        TextView date;

        public TaskViewHolder(View      itemView,
                              TextView  title,
                              TextView  date) {
            super(itemView);

            this.title  = title;
            this.date   = date;
        }
    }
}
