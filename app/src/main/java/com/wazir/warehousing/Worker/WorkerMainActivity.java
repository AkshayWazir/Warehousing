package com.wazir.warehousing.Worker;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.ismaeldivita.chipnavigation.ChipNavigationBar;
import com.wazir.warehousing.FCM.MyFirebaseInstanceIdService;
import com.wazir.warehousing.FCM.SharedPrefsManager;
import com.wazir.warehousing.Fragments.FragmentActiChecker;
import com.wazir.warehousing.Fragments.FragmentContact;
import com.wazir.warehousing.Fragments.FragmentSysStatus;
import com.wazir.warehousing.FragmentsClickEvent;
import com.wazir.warehousing.LoginSignupActivity;
import com.wazir.warehousing.R;

public class WorkerMainActivity extends AppCompatActivity implements FragmentsClickEvent {
    ChipNavigationBar navigationBar;
    // Firebase Stuff
    FirebaseAuth mAuth;
    private static final String TAG = "WorkerMainActivity";
    FrameLayout fragContainer;

    FragmentContact contactFragment;
    FragmentSysStatus systemFragment;
    FragmentActiChecker activityFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_worker_main);
        initFragments();
        initUi();
    }

    void initFragments() {
        contactFragment = new FragmentContact();
        contactFragment.setEvent(this);

        systemFragment = new FragmentSysStatus();
        systemFragment.setEvents(this);

        activityFragment = new FragmentActiChecker();
        activityFragment.setEvent(this);
    }

    void initUi() {
        mAuth = FirebaseAuth.getInstance();
        BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                String token = SharedPrefsManager.getInstance(WorkerMainActivity.this).getToken();  // retrieved Token here
                Log.d(TAG, "onReceive: " + token);
            }
        };
        registerReceiver(broadcastReceiver, new IntentFilter(MyFirebaseInstanceIdService.TOKEN_BROADCAST));

        if (SharedPrefsManager.getInstance(WorkerMainActivity.this).getToken() != null) {
            String token = SharedPrefsManager.getInstance(WorkerMainActivity.this).getToken();
            if (FirebaseAuth.getInstance().getCurrentUser() != null) {
                String userId = FirebaseAuth.getInstance().getCurrentUser().getPhoneNumber();
                FirebaseFirestore.getInstance()
                        .collection("USERS")
                        .document(userId)
                        .update("userToken", token);
            }
        }
        navigationBar = findViewById(R.id.chip_nav_bar);

        navigationBar.setOnItemSelectedListener(new ChipNavigationBar.OnItemSelectedListener() {
            @Override
            public void onItemSelected(int i) {
                switch (i) {
                    case R.id.id_activity_checker:
                        getSupportFragmentManager().beginTransaction().replace(R.id.worker_fragment_container, activityFragment).commit();
                        break;
                    case R.id.id_contact:
                        getSupportFragmentManager().beginTransaction().replace(R.id.worker_fragment_container, contactFragment).commit();
                        break;
                    case R.id.id_system_status:
                        getSupportFragmentManager().beginTransaction().replace(R.id.worker_fragment_container, systemFragment).commit();
                        break;
                }
            }
        });

        fragContainer = findViewById(R.id.worker_fragment_container);
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