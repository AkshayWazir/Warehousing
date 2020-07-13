package com.wazir.warehousing;

import android.app.Application;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.media.AudioAttributes;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;

import static com.wazir.warehousing.GloabalFunctions.Constants.CHANNEL_1;
import static com.wazir.warehousing.GloabalFunctions.Constants.CHANNEL_2;

public class App extends Application {


    @Override
    public void onCreate() {
        super.onCreate();
        createNotiChannel();
    }

    private void createNotiChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            Uri uri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
            AudioAttributes att = new AudioAttributes.Builder()
                    .setUsage(AudioAttributes.USAGE_NOTIFICATION)
                    .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                    .build();
            NotificationChannel channel1 = new NotificationChannel(
                    CHANNEL_1,
                    "channel 1",
                    NotificationManager.IMPORTANCE_HIGH
            );
            channel1.setSound(uri, att);
            channel1.setDescription("ALERT_CHANNEL_1");


            NotificationChannel channel2 = new NotificationChannel(
                    CHANNEL_2,
                    "channel 2",
                    NotificationManager.IMPORTANCE_LOW
            );
            channel1.setDescription("ALERT_CHANNEL_2");

            NotificationManager manager = getSystemService(NotificationManager.class);
            manager.createNotificationChannel(channel1);
            manager.createNotificationChannel(channel2);

        }
    }
}
