package com.shmeli.reminder.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.shmeli.reminder.fragment.TaskFragment;
import com.shmeli.reminder.model.Item;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by Serghei Ostrovschi on 9/20/17.
 */

public abstract class TaskAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    List<Item>      itemList;

    TaskFragment    taskFragment;

    public TaskAdapter(TaskFragment taskFragment) {
        this.taskFragment = taskFragment;

        itemList = new ArrayList<>();
    }

    // ------------------------------- GETTERS ------------------------------------ //

    public Item getItem(int position) {
        return itemList.get(position);
    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

    public TaskFragment getTaskFragment() {
        return taskFragment;
    }

    // ------------------------------- OTHER ------------------------------------ //

    public void addItem(Item newItem) {
        itemList.add(newItem);

        notifyItemInserted(getItemCount() - 1);
    }

    public void addItem(int     location,
                        Item    newItem) {

        itemList.add(location, newItem);

        notifyItemInserted(location);
    }

    public void removeItem(int location) {

        if(location >= 0 && location <= getItemCount() - 1) {
            itemList.remove(location);

            notifyItemRemoved(location);
        }
    }

    protected class TaskViewHolder extends RecyclerView.ViewHolder {

        protected TextView          title;
        protected TextView          date;

        protected CircleImageView   priority;

        public TaskViewHolder(View              itemView,
                              TextView          title,
                              TextView          date,
                              CircleImageView   priority) {
            super(itemView);

            this.title      = title;
            this.date       = date;
            this.priority   = priority;
        }
    }
}
