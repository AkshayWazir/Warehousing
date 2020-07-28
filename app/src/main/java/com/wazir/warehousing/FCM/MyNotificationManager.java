package com.wazir.warehousing.FCM;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;

import androidx.core.app.NotificationCompat;

import com.google.firebase.messaging.RemoteMessage;
import com.wazir.warehousing.R;

import static com.wazir.warehousing.GloabalFunctions.Constants.CHANNEL_1;
import static com.wazir.warehousing.GloabalFunctions.Constants.FLOOD_HAZ;


public class MyNotificationManager {
    private Context ctx;
    public static final int NOTIFICATION_ID = 234;

    public MyNotificationManager(Context ctx) {
        this.ctx = ctx;
    }

    public void showNotification(RemoteMessage message, Intent intent) {
        PendingIntent pendingIntent = PendingIntent.getActivity(ctx, NOTIFICATION_ID, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        Uri uri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(ctx, CHANNEL_1);
        builder.setColor(ctx.getResources().getColor(R.color.white));
        Notification notification1 = builder
                .setSmallIcon(R.drawable.ic_contact)
                .setAutoCancel(true)
                .setSound(uri)
                .setContentIntent(pendingIntent)
                .setContentTitle(message.getNotification().getTitle())
                .setContentText(message.getNotification().getBody())
                .build();
        NotificationManager notificationManager = (NotificationManager) ctx.getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(NOTIFICATION_ID, notification1);
    }

    public void emeNotification(RemoteMessage message, Intent intent) {
        intent.putExtra("EME_TYPE", message.getData().get("category"));
        intent.putExtra("EME_LOC", message.getData().get("EME_LOC"));
        int drawabl = R.drawable.ic_fire;
        String category = "FIRE";
        if (message.getData().get("category").equals(FLOOD_HAZ)){
            drawabl = R.drawable.ic_flood;
            category = "FLOODS";
        }
        PendingIntent pendingIntent = PendingIntent.getActivity(ctx, NOTIFICATION_ID, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        Uri uri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALL);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(ctx, CHANNEL_1);
        builder.setColor(ctx.getResources().getColor(R.color.white));
        Notification notification1 = builder
                .setSmallIcon(drawabl)
                .setAutoCancel(true)
                .setSound(uri)
                .setContentIntent(pendingIntent)
                .setContentTitle(category)
                .setContentText("Incident at: " + message.getData().get("EME_LOC"))
                .build();
        NotificationManager notificationManager = (NotificationManager) ctx.getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(NOTIFICATION_ID, notification1);
    }
}
