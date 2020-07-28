package com.wazir.warehousing.FCM;

import android.content.Intent;
import android.os.Handler;
import android.util.Log;

import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.wazir.warehousing.Activities.EmeActivity;
import com.wazir.warehousing.MainActivity;

import static com.wazir.warehousing.GloabalFunctions.Constants.FIRE_HAZ;
import static com.wazir.warehousing.GloabalFunctions.Constants.FLOOD_HAZ;


public class MyFirebaseInstanceIdService extends FirebaseMessagingService {

    public static final String TOKEN_BROADCAST = "myfcmtokenbroadcast";
    private static final String TAG = "mFCM";
    MyNotificationManager myNotificationManager;
    FirebaseFirestore db;
    Handler mHandler;

    @Override
    public void onNewToken(String token) {
        getApplicationContext().sendBroadcast(new Intent(TOKEN_BROADCAST));
        storeToken(token);
    }

    private void storeToken(String token) {
        SharedPrefsManager.getInstance(getApplicationContext()).storeToken(token);
        db = FirebaseFirestore.getInstance();
        String userId = "";
    }

    @Override
    public void onMessageReceived(final RemoteMessage remoteMessage) {
        Log.d(TAG, "From: " + remoteMessage.getData().get("category"));
        if (remoteMessage.getData().get("category").equals(FLOOD_HAZ) || remoteMessage.getData().get("category").equals(FIRE_HAZ)) {
            myNotificationManager = new MyNotificationManager(getApplicationContext());
            myNotificationManager.emeNotification(remoteMessage, new Intent(getApplicationContext(), EmeActivity.class));
        } else {
            notifyUser(remoteMessage);
        }
    }

    public void notifyUser(RemoteMessage message) {
        myNotificationManager = new MyNotificationManager(getApplicationContext());
        myNotificationManager.showNotification(message, new Intent(getApplicationContext(), MainActivity.class));
    }

}
