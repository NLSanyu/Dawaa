package com.example.android.dawaa;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.provider.Settings;
import android.support.v4.app.NotificationCompat;

import com.example.android.dawaa.NotificationHelper;

/**
 * Created by Lydia on 19-Mar-18.
 */

public class AlertReceiver extends BroadcastReceiver {
    static int id;
    String notif_msg="Message";

    @Override
    public void onReceive(Context context, Intent intent) {
        NotificationHelper notificationHelper;
        id++;

        if(intent.hasExtra("med_name")){
            notif_msg = intent.getStringExtra("med_name");

            notificationHelper = new NotificationHelper(context);
            NotificationCompat.Builder nb = notificationHelper.getChannel1Notification(notif_msg); //
            notificationHelper.getManager().notify(id, nb.build());
        }
        else {

            if (intent.hasExtra("name")) {
                notif_msg = intent.getStringExtra("name");
                notificationHelper = new NotificationHelper(context);
                NotificationCompat.Builder nb = notificationHelper.getChannel2Notification(notif_msg);
                notificationHelper.getManager().notify(id, nb.build());
            }
            else{
                notificationHelper = new NotificationHelper(context);
                NotificationCompat.Builder nb = notificationHelper.getChannel2Notification(notif_msg);
                notificationHelper.getManager().notify(id, nb.build());
            }
        }

        MediaPlayer mediaPlayer = MediaPlayer.create(context, Settings.System.DEFAULT_ALARM_ALERT_URI);
        mediaPlayer.start();

    }
}
