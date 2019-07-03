package com.jnanatech.mochwo.utils;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import androidx.core.app.NotificationBuilderWithBuilderAccessor;

import com.jnanatech.mochwo.R;
import com.jnanatech.mochwo.main.view.MainActivity;

public class AlarmReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {

        int notificationId = intent.getIntExtra("notificationid",0);
        String message = intent.getStringExtra("notificationDetail");

        Intent mainIntent = new Intent(context, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(context,0,mainIntent,0);

        NotificationManager myNotificationManager = (NotificationManager)context.getSystemService(Context.NOTIFICATION_SERVICE);

        Notification.Builder builder= new Notification.Builder(context);
        builder.setSmallIcon(R.mipmap.ic_launcher_round)
                   .setContentTitle("Reminder Of Notification.")
                .setContentText(message)
        .setWhen(System.currentTimeMillis())
        .setAutoCancel(true)
        .setContentIntent(pendingIntent)
        ;

        myNotificationManager.notify(notificationId,builder.build());

    }
}
