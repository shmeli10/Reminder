package com.shmeli.reminder.alarm;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.shmeli.reminder.database.DBHelper;
import com.shmeli.reminder.model.ModelTask;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Serghei Ostrovschi on 9/21/17.
 */

public class AlarmSetter extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        DBHelper dbHelper = new DBHelper(context);

        AlarmHelper.getInstance().init(context);
        AlarmHelper alarmHelper = AlarmHelper.getInstance();

        List<ModelTask> taskList = new ArrayList<>();

        taskList.addAll(dbHelper.query().getTaskList(  DBHelper.SELECTION_STATUS + " OR " + DBHelper.SELECTION_STATUS,
                                                        new String[] {Integer.toString(ModelTask.STATUS_CURRENT), Integer.toString(ModelTask.STATUS_OVERDUE)},
                                                        DBHelper.TASK_DATE_COLUMN));

        for(ModelTask task: taskList) {
            if(task.getDate() != 0) {
                alarmHelper.setAlarm(task);
            }
        }
    }
}
