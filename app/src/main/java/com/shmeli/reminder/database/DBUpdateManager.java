package com.shmeli.reminder.database;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;

import com.shmeli.reminder.model.ModelTask;

/**
 * Created by Serghei Ostrovschi on 9/20/17.
 */

public class DBUpdateManager {

    SQLiteDatabase database;

    public DBUpdateManager(SQLiteDatabase database) {
        this.database = database;
    }

    private void update(String  column,
                        long    key,
                        String  value) {

        ContentValues contentValues = new ContentValues();
        contentValues.put(column, value);

        database.update(DBHelper.TASKS_TABLE,
                        contentValues,
                        DBHelper.TASK_TIME_STAMP_COLUMN + " = " + key,
                        null);
    }

    public void title(long      timeStamp,
                      String    title) {

        update( DBHelper.TASK_TITLE_COLUMN,
                timeStamp,
                title);
    }

    public void date(long   timeStamp,
                     long   date) {

        update( DBHelper.TASK_DATE_COLUMN,
                timeStamp,
                date);
    }

    public void priority(   long   timeStamp,
                            int    priority) {

        update( DBHelper.TASK_PRIORITY_COLUMN,
                timeStamp,
                priority);
    }

    public void status( long   timeStamp,
                        int    status) {

        update( DBHelper.TASK_STATUS_COLUMN,
                timeStamp,
                status);
    }

    public void task(ModelTask task) {

        title(task.getTimeStamp(),      task.getTitle());
        date(task.getTimeStamp(),       task.getDate());
        priority(task.getTimeStamp(),   task.getPriority());
        status(task.getTimeStamp(),     task.getStatus());
    }

    private void update(String  column,
                        long    key,
                        long    value) {

        ContentValues contentValues = new ContentValues();
        contentValues.put(column, value);

        database.update(DBHelper.TASKS_TABLE,
                contentValues,
                DBHelper.TASK_TIME_STAMP_COLUMN + " = " + key,
                null);
    }
}
