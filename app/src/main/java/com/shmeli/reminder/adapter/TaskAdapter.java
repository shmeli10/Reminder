package com.shmeli.reminder.adapter;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.shmeli.reminder.fragment.TaskFragment;
import com.shmeli.reminder.model.Item;
import com.shmeli.reminder.model.ModelSeparator;
import com.shmeli.reminder.model.ModelTask;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by Serghei Ostrovschi on 9/20/17.
 */

public abstract class TaskAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    List<Item>      itemList;

    TaskFragment    taskFragment;

    public boolean containsSeparatorOverdue;
    public boolean containsSeparatorToday;
    public boolean containsSeparatorTomorrow;
    public boolean containsSeparatorFuture;

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
        //Log.e("LOG", "TaskAdapter: getItemCount(): itemList.size = " +itemList.size());
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

    public void updateTask(ModelTask newTask) {

        for(int i=0; i<getItemCount(); i++) {

            Item item = getItem(i);

            if(item.isTask()) {
                ModelTask task = (ModelTask) item;

                if(newTask.getTimeStamp() == task.getTimeStamp()) {
                    removeItem(i);

                    getTaskFragment().addTask(newTask, false);
                }
            }
        }
    }

    public void removeItem(int location) {

        if(location >= 0 && location <= getItemCount() - 1) {
            itemList.remove(location);

            notifyItemRemoved(location);

            if(location - 1 >= 0 && location <= getItemCount() - 1) {
                if(!getItem(location).isTask() && !getItem(location - 1).isTask()) {
                    ModelSeparator separator = (ModelSeparator) getItem(location - 1);

                    checkSeparators(separator.getType());

                    itemList.remove(location - 1);

                    notifyItemRemoved(location - 1);
                }
            }
            else if (getItemCount() - 1 >= 0 && !getItem(getItemCount() - 1).isTask()) {
                ModelSeparator separator = (ModelSeparator) getItem(getItemCount() - 1);

                checkSeparators(separator.getType());

                int locationTemp = getItemCount() - 1;

                itemList.remove(locationTemp);

                notifyItemRemoved(locationTemp);
            }
        }
    }

    private void checkSeparators(int type) {
        switch (type) {

            case ModelSeparator.TYPE_OVERDUE:
                containsSeparatorOverdue = false;
                break;
            case ModelSeparator.TYPE_TODAY:
                containsSeparatorToday = false;
                break;
            case ModelSeparator.TYPE_TOMORROW:
                containsSeparatorTomorrow = false;
                break;
            case ModelSeparator.TYPE_FUTURE:
                containsSeparatorFuture = false;
                break;
        }
    }

    public void removeAllItems() {
        if(getItemCount() != 0) {
            itemList = new ArrayList<>();
            notifyDataSetChanged();

            containsSeparatorOverdue    = false;
            containsSeparatorToday      = false;
            containsSeparatorTomorrow   = false;
            containsSeparatorFuture     = false;
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

    protected class SeparatorViewHolder extends RecyclerView.ViewHolder {

        protected TextView type;

        public SeparatorViewHolder(View     itemView,
                                   TextView type) {
            super(itemView);

            this.type = type;
        }
    }
}
