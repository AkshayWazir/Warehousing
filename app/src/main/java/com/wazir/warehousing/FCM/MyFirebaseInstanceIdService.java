package com.wazir.warehousing.FCM;

import android.content.Intent;
import android.util.Log;

import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.wazir.warehousing.MainActivity;




public class MyFirebaseInstanceIdService extends FirebaseMessagingService {

    public static final String TOKEN_BROADCAST = "myfcmtokenbroadcast";
    private static final String TAG = "MyFCMInstance";
    MyNotificationManager myNotificationManager;
    FirebaseFirestore db;

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
    public void onMessageReceived(RemoteMessage remoteMessage) {
        Log.d(TAG, "From: " + remoteMessage.getFrom());
        notifyUser(remoteMessage);
    }

    public void notifyUser(RemoteMessage message) {
        myNotificationManager = new MyNotificationManager(getApplicationContext());
        myNotificationManager.showNotification(message, new Intent(getApplicationContext(), MainActivity.class));
    }
}
