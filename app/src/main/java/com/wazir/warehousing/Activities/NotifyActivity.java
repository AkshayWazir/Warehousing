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

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;

public class NotifyActivity extends AppCompatActivity {
    RecyclerView noticeContainer;

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
        FirebaseFirestore.getInstance().collection(this.getResources().getString(R.string.rootName))
                .document(SharedPrefsManager.getInstance(this).getWarehouseId())
                .collection("Notices")
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {
                        for (QueryDocumentSnapshot snapshot : queryDocumentSnapshots) {
                            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
                            Date date;
                            try {
                                date = sdf.parse(snapshot.getString("date"));
                                NoticeObject obj = new NoticeObject();
                                obj.setTitle(snapshot.getString("title"));
                                obj.setDescription(snapshot.getString("description"));
                                obj.setPriority(Integer.parseInt(snapshot.getString("priority")));
                                obj.setDate(date);
                                notices.add(obj);
                            } catch (ParseException ex) {
                                ex.printStackTrace();
                            }

                        }
                        Comparator<NoticeObject> comparator = new Comparator<NoticeObject>() {
                            @Override
                            public int compare(NoticeObject o1, NoticeObject o2) {
                                return o1.getPriority();
                            }
                        };
                        Collections.sort(notices, comparator);
                        NotifAdapter adapter = new NotifAdapter(notices, getBaseContext());
                        noticeContainer.setAdapter(adapter);
                        noticeContainer.setLayoutManager(new LinearLayoutManager(getBaseContext()));
                    }
                });
    }

}