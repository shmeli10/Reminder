package com.shmeli.reminder.database;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

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

    public ModelTask getTask(long timeStamp) {

        ModelTask task = null;

        Cursor cursor = databse.query(  DBHelper.TASKS_TABLE,
                                        null,
                                        DBHelper.SELECTION_TIME_STAMP,
                                        new String[] {Long.toString(timeStamp)},
                                        null, null, null);

        if(cursor.moveToFirst()) {

            String title    = cursor.getString(cursor.getColumnIndex(DBHelper.TASK_TITLE_COLUMN));
            long date       = cursor.getLong(cursor.getColumnIndex(DBHelper.TASK_DATE_COLUMN));
            int priority    = cursor.getInt(cursor.getColumnIndex(DBHelper.TASK_PRIORITY_COLUMN));
            int status      = cursor.getInt(cursor.getColumnIndex(DBHelper.TASK_STATUS_COLUMN));

            task = new ModelTask(title, date, timeStamp, priority, status);
        }

        cursor.close();

        return task;
    }

    public List<ModelTask> getTaskList( String      selection,
                                        String[]    selectionArgs,
                                        String      orderBy) {

        Log.e("LOG", "DBQueryManager: getTaskList(): selection= "       +selection);

        for(String state: selectionArgs)
            Log.e("LOG", "DBQueryManager: getTaskList(): selectionArgs state: "   +state);

        Log.e("LOG", "DBQueryManager: getTaskList(): orderBy= "         +orderBy);

        List<ModelTask> taskList = new ArrayList<>();

        Cursor cursor = databse.query(  DBHelper.TASKS_TABLE,
                                        null,
                                        selection,
                                        selectionArgs,
                                        null,
                                        null,
                                        orderBy);

        Log.e("LOG", "DBQueryManager: getTaskList(): cursor is null: " +(cursor == null));

        if(cursor.moveToFirst()) {

            Log.e("LOG", "DBQueryManager: getTaskList(): cursor size= " +cursor.getCount());

            do {
                String title    = cursor.getString(cursor.getColumnIndex(DBHelper.TASK_TITLE_COLUMN));
                long date       = cursor.getLong(cursor.getColumnIndex(DBHelper.TASK_DATE_COLUMN));
                long timeStamp  = cursor.getLong(cursor.getColumnIndex(DBHelper.TASK_TIME_STAMP_COLUMN));
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
