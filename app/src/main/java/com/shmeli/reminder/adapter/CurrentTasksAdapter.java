package com.shmeli.reminder.adapter;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.content.res.Resources;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.shmeli.reminder.R;
import com.shmeli.reminder.Utils;
import com.shmeli.reminder.fragment.CurrentTaskFragment;
import com.shmeli.reminder.model.Item;
import com.shmeli.reminder.model.ModelTask;

import de.hdodenhof.circleimageview.CircleImageView;


/**
 * Created by Serghei Ostrovschi on 9/20/17.
 */

public class CurrentTasksAdapter extends TaskAdapter {

    private static final int TYPE_TASK      = 0;
    private static final int TYPE_SEPARATOR = 1;

    private View            itemView;
    private ModelTask       task;
    private Resources       resources;
    private TaskViewHolder  taskViewHolder;


    //List<Item> itemList = new ArrayList<>();

    public CurrentTasksAdapter(CurrentTaskFragment taskFragment) {
        super(taskFragment);
    }

//    public Item getItem(int position) {
//        return itemList.get(position);
//    }
//
//    public void addItem(Item newItem) {
//        itemList.add(newItem);
//
//        notifyItemInserted(getItemCount() - 1);
//    }
//
//    public void addItem(int     location,
//                        Item    newItem) {
//
//        itemList.add(location, newItem);
//
//        notifyItemInserted(location);
//    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        switch(viewType) {

            case TYPE_TASK:

                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.model_task, parent, false);

                TextView title  = (TextView) view.findViewById(R.id.tvTaskTitle);
                TextView date   = (TextView) view.findViewById(R.id.tvTaskDate);

                CircleImageView priority = (CircleImageView) view.findViewById(R.id.cvTaskPriority);

                return new TaskViewHolder(view, title, date, priority);
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

            task = (ModelTask) item;

            taskViewHolder = (TaskViewHolder) holder;
            taskViewHolder.title.setText(task.getTitle());

            if(task.getDate() != 0) {
                taskViewHolder.date.setText(Utils.getFullDate(task.getDate()));
            }
            else {
                taskViewHolder.date.setText(null);
            }

            itemView = taskViewHolder.itemView;
            itemView.setVisibility(View.VISIBLE);

            resources = itemView.getResources();

            itemView.setBackgroundColor(resources.getColor(R.color.gray_50));

            taskViewHolder.title.setTextColor(resources.getColor(R.color.primary_text_default_material_light));
            taskViewHolder.date.setTextColor(resources.getColor(R.color.secondary_text_default_material_light));
            taskViewHolder.priority.setColorFilter(resources.getColor(task.getPriorityColor()));
            taskViewHolder.priority.setImageResource(R.drawable.ic_circle_white_48dp);
            taskViewHolder.priority.setOnClickListener(priorityClickListener);
        }
    }

//    @Override
//    public int getItemCount() {
//        return itemList.size();
//    }

    @Override
    public int getItemViewType(int position) {
        if(getItem(position).isTask()) {
            return TYPE_TASK;
        }
        else {
            return TYPE_SEPARATOR;
        }
    }

    View.OnClickListener priorityClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            if(task != null) {
                task.setStatus(ModelTask.STATUS_DONE);

                getTaskFragment().activity.dbHelper.update().status(task.getTimeStamp(),
                                                                    ModelTask.STATUS_DONE);

                itemView.setBackgroundColor(resources.getColor(R.color.gray_200));

                taskViewHolder.title.setTextColor(resources.getColor(R.color.primary_text_disabled_material_light));
                taskViewHolder.date.setTextColor(resources.getColor(R.color.secondary_text_disabled_material_light));
                taskViewHolder.priority.setColorFilter(resources.getColor(task.getPriorityColor()));

                ObjectAnimator flipIn = ObjectAnimator.ofFloat( taskViewHolder.priority,
                                                                "rotationY",
                                                                -180f,
                                                                0f);
                flipIn.addListener(flipListener);
            }
        }
    };

    Animator.AnimatorListener flipListener = new Animator.AnimatorListener() {

        @Override
        public void onAnimationStart(Animator animation) {

        }

        @Override
        public void onAnimationEnd(Animator animation) {

        }

        @Override
        public void onAnimationCancel(Animator animation) {

        }

        @Override
        public void onAnimationRepeat(Animator animation) {

        }
    };

//    private class TaskViewHolder extends RecyclerView.ViewHolder {
//
//        TextView title;
//        TextView date;
//
//        public TaskViewHolder(View      itemView,
//                              TextView  title,
//                              TextView  date) {
//            super(itemView);
//
//            this.title  = title;
//            this.date   = date;
//        }
//    }
}
