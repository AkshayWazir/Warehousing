package com.wazir.warehousing.FCM;

import android.content.Intent;

import com.google.firebase.messaging.FirebaseMessagingService;

public class MyFirebaseInstanceIdService extends FirebaseMessagingService {

    public static final String TOKEN_BROADCAST = "myfcmtokenbroadcast";

    @Override
    public void onNewToken(String token) {
        getApplicationContext().sendBroadcast(new Intent(TOKEN_BROADCAST));
        storeToken(token);
    }

    private void storeToken(String token) {
        SharedPrefsManager.getInstance(getApplicationContext()).storeToken(token);
    }
}
