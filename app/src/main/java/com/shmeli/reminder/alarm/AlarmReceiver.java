package com.shmeli.reminder.alarm;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;

import com.shmeli.reminder.MainActivity;
import com.shmeli.reminder.MyApplication;
import com.shmeli.reminder.R;

import static android.support.v4.app.NotificationCompat.DEFAULT_ALL;
import static android.support.v4.app.NotificationCompat.FLAG_AUTO_CANCEL;


/**
 * Created by Serghei Ostrovschi on 9/21/17.
 */

public class AlarmReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {

        String title    = intent.getStringExtra("title");
        long timeStamp  = intent.getLongExtra("time_stamp", 0);
        int color       = intent.getIntExtra("color",       0);

        Intent resultIntent = new Intent(   context,
                                            MainActivity.class);

        if(MyApplication.isActivityVisible()) {
            resultIntent = intent;
        }

        resultIntent.setFlags(intent.FLAG_ACTIVITY_CLEAR_TOP | intent.FLAG_ACTIVITY_SINGLE_TOP);

        PendingIntent pendingIntent = PendingIntent.getActivity(context,
                                                                (int) timeStamp,
                                                                resultIntent,
                                                                PendingIntent.FLAG_UPDATE_CURRENT);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(context);
        builder.setContentTitle("Reminder");
        builder.setContentText(title);
        builder.setColor(context.getResources().getColor(color));
        builder.setSmallIcon(R.drawable.ic_check_white_48dp);

        builder.setDefaults(DEFAULT_ALL);
        builder.setContentIntent(pendingIntent);

        Notification notification = builder.build();
        notification.flags |= FLAG_AUTO_CANCEL;

        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify((int) timeStamp,
                                    notification);
    }
}
