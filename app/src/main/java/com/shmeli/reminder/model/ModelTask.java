package com.shmeli.reminder.model;

import com.shmeli.reminder.R;

import java.util.Date;

/**
 * Created by Serghei Ostrovschi on 9/20/17.
 */

public class ModelTask implements Item {

    public static final int PRIORITY_LOW    = 0;
    public static final int PRIORITY_NORMAL = 1;
    public static final int PRIORITY_HIGH   = 2;

    public static final String[] PRIORITY_LEVELS = {"Low Priority", "Normal Priority", "High Priority"};

    public static final int STATUS_OVERDUE  = 0;
    public static final int STATUS_CURRENT  = 1;
    public static final int STATUS_DONE     = 2;

    private String title;

    private long date;
    private long timeStamp;

    private int priority;

    private int status;

    public ModelTask() {
        this.status     = -1;
        this.timeStamp  = new Date().getTime();
    }

    public ModelTask(String title,
                     long   date,
                     long   timeStamp,
                     int    priority,
                     int    status) {

        this.title      = title;
        this.date       = date;
        this.timeStamp  = timeStamp;
        this.priority   = priority;
        this.status     = status;
    }

    @Override
    public boolean isTask() {
        return true;
    }

    // ---------------------- GETTERS -------------------------------- //

    public long getDate() {

        return date;
    }

    public int getPriority() {
        return priority;
    }

    public int getStatus() {
        return status;
    }

    public String getTitle() {
        return title;
    }

    public int getPriorityColor() {

        switch(getPriority()) {

            case PRIORITY_HIGH:
                if(getStatus() == STATUS_CURRENT || getStatus() == STATUS_OVERDUE) {
                    return R.color.priority_high;
                }
                else {
                    return R.color.priority_high_selected;
                }
            case PRIORITY_NORMAL:
                if(getStatus() == STATUS_CURRENT || getStatus() == STATUS_OVERDUE) {
                    return R.color.priority_normal;
                }
                else {
                    return R.color.priority_normal_selected;
                }
            case PRIORITY_LOW:
                if(getStatus() == STATUS_CURRENT || getStatus() == STATUS_OVERDUE) {
                    return R.color.priority_low;
                }
                else {
                    return R.color.priority_low_selected;
                }

            default: return 0;
        }
    }

    public long getTimeStamp() {
        return timeStamp;
    }

    // ---------------------- SETTERS -------------------------------- //

    public void setDate(long date) {
        this.date = date;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setTimeStamp(long timeStamp) {
        this.timeStamp = timeStamp;
    }
}
