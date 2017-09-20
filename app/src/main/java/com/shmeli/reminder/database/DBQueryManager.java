package com.shmeli.reminder.database;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.shmeli.reminder.model.ModelTask;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Serghei Ostrovschi on 9/20/17.
 */

public class DBQueryManager {

    private SQLiteDatabase  databse;

    DBQueryManager(SQLiteDatabase database) {
        this.databse = database;
    }

    public List<ModelTask> getTaskList( String      selection,
                                        String[]    selectionArgs,
                                        String      orderBy) {

        List<ModelTask> taskList = new ArrayList<>();

        Cursor cursor = databse.query(  DBHelper.TASKS_TABLE,
                                        null,
                                        selection,
                                        selectionArgs,
                                        null,
                                        null,
                                        orderBy);

        if(cursor.moveToFirst()) {

            do {
                String title    = cursor.getString(cursor.getColumnIndex(DBHelper.TASK_TITLE_COLUMN));
                long date       = cursor.getLong(cursor.getColumnIndex(DBHelper.TASK_DATE_COLUMN));
                long timeStamp  = cursor.getLong(cursor.getColumnIndex(DBHelper.TASK_TIME_COLUMN));
                int priority    = cursor.getInt(cursor.getColumnIndex(DBHelper.TASK_PRIORITY_COLUMN));
                int status      = cursor.getInt(cursor.getColumnIndex(DBHelper.TASK_STATUS_COLUMN));


                ModelTask task = new ModelTask(title, date, timeStamp, priority, status);

                taskList.add(task);

            } while(cursor.moveToNext());

        }

        cursor.close();

        return taskList;
    }
}
