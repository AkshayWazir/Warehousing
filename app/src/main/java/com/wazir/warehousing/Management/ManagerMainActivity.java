package com.wazir.warehousing.Management;

import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.messaging.FirebaseMessaging;
import com.ismaeldivita.chipnavigation.ChipNavigationBar;
import com.wazir.warehousing.Activities.NotifyActivity;
import com.wazir.warehousing.Activities.SupportActivity;
import com.wazir.warehousing.FCM.MyFirebaseInstanceIdService;
import com.wazir.warehousing.FCM.SharedPrefsManager;
import com.wazir.warehousing.Fragments.FragmentActiChecker;
import com.wazir.warehousing.Fragments.FragmentContact;
import com.wazir.warehousing.Fragments.FragmentSysStatus;
import com.wazir.warehousing.Interfaces.CheckerInteract;
import com.wazir.warehousing.Interfaces.ContactInteract;
import com.wazir.warehousing.Interfaces.FragmentsClickEvent;
import com.wazir.warehousing.LoginSignupActivity;
import com.wazir.warehousing.ModelObject.UserInfoType;
import com.wazir.warehousing.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class ManagerMainActivity extends AppCompatActivity implements FragmentsClickEvent, ContactInteract, CheckerInteract {
    // JAVA stuff
    private BroadcastReceiver broadcastReceiver;

    // Andro Views
    ChipNavigationBar navigationBar;
    FragmentContact contactFragment;
    FragmentSysStatus systemFragment;
    FragmentActiChecker activityFragment;
    String URL = "https://fcm.googleapis.com/fcm/send";


    // Firebase Stuff
    FirebaseAuth mAuth;
    private String TAG = "Manager";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manager_main);
        initFragments();
        initUi();
        FirebaseMessaging.getInstance().subscribeToTopic("news");
        if (!checkPermission()) {
            ActivityCompat.requestPermissions(ManagerMainActivity.this,
                    new String[]{Manifest.permission.CALL_PHONE},
                    112);
        }
    }

    void initFragments() {
        contactFragment = new FragmentContact(this, this);
        contactFragment.setContactInteract(this);

        systemFragment = new FragmentSysStatus(this);
        systemFragment.setEvents(this);

        activityFragment = new FragmentActiChecker(this, this,this);
        activityFragment.setEvent(this);
    }

    void initUi() {
        mAuth = FirebaseAuth.getInstance();
        broadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                String token = SharedPrefsManager.getInstance(ManagerMainActivity.this).getToken();  // retrieved Token here

            }
        };
        registerReceiver(broadcastReceiver, new IntentFilter(MyFirebaseInstanceIdService.TOKEN_BROADCAST));

        if (SharedPrefsManager.getInstance(ManagerMainActivity.this).getToken() != null) {
            String token = SharedPrefsManager.getInstance(ManagerMainActivity.this).getToken();
            if (FirebaseAuth.getInstance().getCurrentUser() != null) {
                String userId = FirebaseAuth.getInstance().getCurrentUser().getPhoneNumber();
                FirebaseFirestore.getInstance()
                        .collection("USERS")
                        .document(userId)
                        .update("userToken", token);
            }
        }
        getSupportFragmentManager().beginTransaction().replace(R.id.worker_fragment_container, systemFragment).commit();
        navigationBar = findViewById(R.id.chip_nav_bar);
        navigationBar.setItemSelected(R.id.id_system_status, true);
        navigationBar.setOnItemSelectedListener(new ChipNavigationBar.OnItemSelectedListener() {
            @Override
            public void onItemSelected(int i) {
                switch (i) {
                    case R.id.id_activity_checker:
                        setTitle("Activity Checker");
                        getSupportFragmentManager().beginTransaction().replace(R.id.worker_fragment_container, activityFragment).commit();
                        break;
                    case R.id.id_contact:
                        setTitle("Contacts");
                        getSupportFragmentManager().beginTransaction().replace(R.id.worker_fragment_container, contactFragment).commit();
                        break;
                    case R.id.id_system_status:
                        setTitle("System Status");
                        getSupportFragmentManager().beginTransaction().replace(R.id.worker_fragment_container, systemFragment).commit();
                        break;
                }
            }
        });
    }

    boolean checkPermission() {
        return ContextCompat.checkSelfPermission(ManagerMainActivity.this, Manifest.permission.CALL_PHONE) == PackageManager.PERMISSION_GRANTED;
    }

    @Override
    protected void onStop() {
        super.onStop();
        unregisterReceiver(broadcastReceiver);
    }

    @Override
    protected void onResume() {
        super.onResume();
        registerReceiver(broadcastReceiver, new IntentFilter(MyFirebaseInstanceIdService.TOKEN_BROADCAST));
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
            case (R.id.id_notif_menu):
                startActivity(new Intent(this, NotifyActivity.class));
                break;
            case (R.id.id_logout):
                mAuth.signOut();
                startActivity(new Intent(this, LoginSignupActivity.class));
                finish();
                break;
            case (R.id.id_support):
                startActivity(new Intent(this, SupportActivity.class));
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void callUser(String number) {
        String uri = "tel:" + number.trim();
        Intent intent = new Intent(Intent.ACTION_CALL);
        intent.setData(Uri.parse(uri));
        startActivity(intent);
    }

    void sendRequest(String name, String token) {
        RequestQueue mRequest = Volley.newRequestQueue(this);
        JSONObject json = new JSONObject();
        try {
            json.put("to", token);
            JSONObject notificationObj = new JSONObject();
            notificationObj.put("title", "Staff Alert");
            notificationObj.put("body", name + " Require Your Attention. Kindly Contact him or See if he's around.");

            JSONObject extraData = new JSONObject();
            extraData.put("brandId", "User Attention");
            extraData.put("category", "Shoes");


            json.put("notification", notificationObj);
            json.put("data", extraData);


            JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, URL,
                    json,
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            Log.d("MUR", "onResponse: " + response.toString());
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Log.d("MUR", "onError: " + error.networkResponse);
                        }
                    }
            ) {
                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                    Map<String, String> header = new HashMap<>();
                    header.put("content-type", "application/json");
                    header.put("Authorization", "key=AAAAe6_wBew:APA91bHeK8TxWNjRsVMKbWyBLvotl5VfPUBpQH65eH9yZFQuw9cxm-qBBtJOUo-37vFAHqFJ3x5ssWv0jTaPJQMY2OTK-Gh50kAqJPpNdwUOnqodknI-aSml-R7aoOl7mVB3FjMVPnix");
                    return header;
                }
            };
            mRequest.add(request);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void alertUser(final String name, final String token, String contact) {
        FirebaseFirestore.getInstance()
                .collection("USERS")
                .document(contact)
                .get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if (task.isSuccessful() && task.getResult().exists()) {
                            UserInfoType info = task.getResult().toObject(UserInfoType.class);
                            sendRequest(SharedPrefsManager.getInstance(ManagerMainActivity.this).getUserName(), info.getUserToken());
                        }
                    }
                });
    }

    @Override
    public void updateChecker(String level1Id, String level2Id) {
        // TODO: 7/12/2020 Update Your Task Here
    }
}