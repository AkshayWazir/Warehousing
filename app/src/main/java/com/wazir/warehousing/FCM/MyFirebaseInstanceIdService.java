package com.wazir.warehousing.FCM;

import android.widget.Toast;

import com.google.firebase.messaging.FirebaseMessagingService;

public class MyFirebaseInstanceIdService extends FirebaseMessagingService {
    @Override
    public void onNewToken(String token) {
        storeToken(token);
    }

    private void storeToken(String token) {
        SharedPrefsManager.getInstance(getApplicationContext()).storeToken(token);
    }
}
