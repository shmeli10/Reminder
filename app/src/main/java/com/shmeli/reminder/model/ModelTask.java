package com.shmeli.reminder.model;

/**
 * Created by Serghei Ostrovschi on 9/20/17.
 */

public class ModelTask implements Item {

    private String title;

    private long date;

    public ModelTask() {}

    public ModelTask(String title,
                     long   date) {

        this.title  = title;
        this.date   = date;
    }

    @Override
    public boolean isTask() {
        return true;
    }

    // ---------------------- GETTERS -------------------------------- //

    public String getTitle() {
        return title;
    }

    public long getDate() {
        return date;
    }

    // ---------------------- SETTERS -------------------------------- //

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDate(long date) {
        this.date = date;
    }
}
