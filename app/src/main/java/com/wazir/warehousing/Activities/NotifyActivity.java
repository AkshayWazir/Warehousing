package com.wazir.warehousing.Activities;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.wazir.warehousing.Adapters.NotifAdapter;
import com.wazir.warehousing.FCM.SharedPrefsManager;
import com.wazir.warehousing.ModelObject.NoticeObject;
import com.wazir.warehousing.R;

import java.util.ArrayList;

public class NotifyActivity extends AppCompatActivity {
    RecyclerView noticeContainer;
    String TAG = "NOTICES:";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notify);
        noticeContainer = findViewById(R.id.id_notif_container);
        setUpRecyclerView();
        setTitle("Notifications");
    }

    void setUpRecyclerView() {
        final ArrayList<NoticeObject> notices = new ArrayList<>();
        FirebaseFirestore
                .getInstance()
                .collection(this.getResources().getString(R.string.rootName))
                .document(SharedPrefsManager.getInstance(this).getWarehouseId())
                .collection("Notices")
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {
                        notices.clear();
                        if (queryDocumentSnapshots != null) {
                            for (QueryDocumentSnapshot snapshot : queryDocumentSnapshots) {
                                NoticeObject obj = new NoticeObject(
                                        (String) snapshot.getData().get("date"),
                                        (String) snapshot.getData().get("cate"),
                                        (String) snapshot.getData().get("temp"),
                                        (String) snapshot.getData().get("moist"),
                                        (String) snapshot.getData().get("light"),
                                        (String) snapshot.getData().get("loc")
                                );
                                obj.makeNotice();
                                notices.add(obj);
                            }
                        }
                        NotifAdapter adapter = new NotifAdapter(notices, getBaseContext());
                        noticeContainer.setAdapter(adapter);
                        noticeContainer.setLayoutManager(new LinearLayoutManager(getBaseContext()));
                    }
                });
    }

}