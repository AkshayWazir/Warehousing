package com.wazir.warehousing;

import android.content.Intent;
import android.os.Bundle;
import android.view.WindowManager;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.wazir.warehousing.FCM.SharedPrefsManager;
import com.wazir.warehousing.Management.ManagerMainActivity;
import com.wazir.warehousing.Worker.WorkerMainActivity;

import static com.wazir.warehousing.GloabalFunctions.Constants.USER_MANAGER;

public class MainActivity extends AppCompatActivity {

    // firebase stuff
    FirebaseAuth mAuth;

    // essential variables
    String fcmToken;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        getAccessToken();
        initFirebase();
    }

    void initFirebase() {
        mAuth = FirebaseAuth.getInstance();
        if (mAuth.getCurrentUser() != null) {
            String user = SharedPrefsManager.getInstance(this).returnUserType();
            if (user == null) {
                finish();
                return;
            }
            if (user.equals(USER_MANAGER)) {
                Intent intent = new Intent(this, ManagerMainActivity.class);
                startActivity(intent);
                finish();
            } else {
                Intent intent = new Intent(this, WorkerMainActivity.class);
                startActivity(intent);
                finish();
            }
        } else {
            Intent intent = new Intent(this, LoginSignupActivity.class);
            startActivity(intent);
            finish();
        }
    }

    void getAccessToken() {
        fcmToken = SharedPrefsManager.getInstance(this).getToken();
    }

}