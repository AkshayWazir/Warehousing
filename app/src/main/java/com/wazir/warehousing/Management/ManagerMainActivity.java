package com.wazir.warehousing.Management;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.ismaeldivita.chipnavigation.ChipNavigationBar;
import com.wazir.warehousing.FCM.MyFirebaseInstanceIdService;
import com.wazir.warehousing.FCM.SharedPrefsManager;
import com.wazir.warehousing.LoginSignupActivity;
import com.wazir.warehousing.R;

public class ManagerMainActivity extends AppCompatActivity {
    // JAVA stuff
    private BroadcastReceiver broadcastReceiver;

    // Andro Views
    ChipNavigationBar navigationBar;


    // Firebase Stuff
    FirebaseAuth mAuth;
    private String TAG = "Manager";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manager_main);
        initUi();
    }

    void initUi() {
        mAuth = FirebaseAuth.getInstance();
        broadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                String token = SharedPrefsManager.getInstance(ManagerMainActivity.this).getToken();  // retrieved Token here
                Log.d(TAG, "onReceive: " + token);
            }
        };
        registerReceiver(broadcastReceiver, new IntentFilter(MyFirebaseInstanceIdService.TOKEN_BROADCAST));

        if (SharedPrefsManager.getInstance(ManagerMainActivity.this).getToken() != null) {
            Log.d(TAG, "initUi: " + SharedPrefsManager.getInstance(ManagerMainActivity.this).getToken());
        }
        navigationBar = findViewById(R.id.chip_nav_bar);
        navigationBar.setOnItemSelectedListener(new ChipNavigationBar.OnItemSelectedListener() {
            @Override
            public void onItemSelected(int i) {
                switch (i) {
                    case R.id.id_activity_checker:
                        // TODO: 7/8/2020 open activity checker
                        break;
                    case R.id.id_contact:
                        // TODO: 7/8/2020 open contacts
                        break;
                    case R.id.id_system_status:
                        // TODO: 7/8/2020 open system Status
                        break;
                }
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.top_navbar, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case (R.id.id_notifications):
                // TODO: 7/8/2020 do something here
                break;
            case (R.id.id_logout):
                mAuth.signOut();
                startActivity(new Intent(this, LoginSignupActivity.class));
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}