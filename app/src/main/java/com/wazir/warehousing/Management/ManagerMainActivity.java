package com.wazir.warehousing.Management;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.messaging.FirebaseMessaging;
import com.ismaeldivita.chipnavigation.ChipNavigationBar;
import com.wazir.warehousing.FCM.MyFirebaseInstanceIdService;
import com.wazir.warehousing.FCM.SharedPrefsManager;
import com.wazir.warehousing.Fragments.FragmentActiChecker;
import com.wazir.warehousing.Fragments.FragmentContact;
import com.wazir.warehousing.Fragments.FragmentSysStatus;
import com.wazir.warehousing.Interfaces.ContactInteract;
import com.wazir.warehousing.Interfaces.FragmentsClickEvent;
import com.wazir.warehousing.LoginSignupActivity;
import com.wazir.warehousing.ModelObject.ContactObject;
import com.wazir.warehousing.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ManagerMainActivity extends AppCompatActivity implements FragmentsClickEvent, ContactInteract {
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
    }

    void initFragments() {
        contactFragment = new FragmentContact(this, this);
        contactFragment.setContactInteract(this);

        systemFragment = new FragmentSysStatus();
        systemFragment.setEvents(this);

        activityFragment = new FragmentActiChecker();
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
    }

    ArrayList<ContactObject> fetchContacts() {
        ArrayList<ContactObject> contacts = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            ContactObject object = new ContactObject();
            object.setProfilePicture("https://firebasestorage.googleapis.com/v0/b/cwc-wms.appspot.com/o/ball_640x480.jpg?alt=media&token=09b61945-870c-494b-9ac5-9c9210da2860");
            object.setContact("8368370908");
            object.setName("Akshay Rein");
            object.setUserId("+918368370908");
            object.setPost("Manager");
            contacts.add(object);
        }
        return contacts;
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

    @Override
    public void callUser(String number) {
        String uri = "tel:" + number.trim();
        Intent intent = new Intent(Intent.ACTION_CALL);
        intent.setData(Uri.parse(uri));
        startActivity(intent);
    }

    @Override
    public void alertUser(String token) {
        RequestQueue mRequest = Volley.newRequestQueue(this);
        JSONObject json = new JSONObject();
        try {
            json.put("to", "/topics/" + "news");
            JSONObject notificationObj = new JSONObject();
            notificationObj.put("title", "any title");
            notificationObj.put("body", "any body");

            JSONObject extraData = new JSONObject();
            extraData.put("brandId", "puma");
            extraData.put("category", "Shoes");


            json.put("notification", notificationObj);
            json.put("data", extraData);


            JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, URL,
                    json,
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            Log.d("MUR", "onResponse: ");
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
}