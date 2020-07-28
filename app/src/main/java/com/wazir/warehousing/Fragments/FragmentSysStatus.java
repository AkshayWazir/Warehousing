package com.wazir.warehousing.Fragments;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.wazir.warehousing.Adapters.SysStaAdapter;
import com.wazir.warehousing.FCM.SharedPrefsManager;
import com.wazir.warehousing.Interfaces.FragmentsClickEvent;
import com.wazir.warehousing.ModelObject.Compartment;
import com.wazir.warehousing.ModelObject.SensorObj;
import com.wazir.warehousing.ModelObject.SysStaObject;
import com.wazir.warehousing.R;

import java.util.ArrayList;

public class FragmentSysStatus extends Fragment {
    FragmentsClickEvent events;
    RecyclerView rcView;
    Context context;

    public FragmentSysStatus(Context context) {
        this.context = context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_sys_status, container, false);
        rcView = view.findViewById(R.id.id_container_syssta);
        rcView.setAdapter(new SysStaAdapter(getData(), context));
        rcView.setLayoutManager(new LinearLayoutManager(context));
        return view;
    }

    public void getStatData() {
        final ArrayList<SysStaObject> objects = new ArrayList<>();
        FirebaseFirestore.getInstance().collection(context.getResources().getString(R.string.rootName))
                .document(SharedPrefsManager.getInstance(context).getWarehouseId())
                .collection("SYSTEM_STAT")
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {
                        objects.clear();
                        for (QueryDocumentSnapshot snapshot : queryDocumentSnapshots) {
                            objects.add(snapshot.toObject(SysStaObject.class));
                        }
                        SysStaAdapter adapter = new SysStaAdapter(objects, context);
                        rcView.setAdapter(adapter);
                        rcView.setLayoutManager(new GridLayoutManager(context, 2));
                    }
                });
    }

    public void setEvents(FragmentsClickEvent events) {
        this.events = events;
    }

    private ArrayList<SysStaObject> getData() {
        ArrayList<SysStaObject> objects = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            SysStaObject objs = new SysStaObject();
            objs.setWarehouseId("Warehouse" + i + 1);
            objs.setCapacity("45Qt");
            objs.setCompartments(new ArrayList<Compartment>());
            for (int j = 0; j < 3; j++) {
                Compartment comp = new Compartment();
                comp.setCompId("Compar 124" + j + 1);
                comp.setCapacity("124" + i);
                comp.setSensors(new ArrayList<SensorObj>());
                for (int k = 0; k < 6; k++) {
                    SensorObj obj = new SensorObj();
                    obj.setInfo("Everything is working Fine and Up to the mark");
                    obj.setLoc("in bla bla bla compartment and Section");
                    obj.setSensorId("ABC123");
                    obj.setStatus(true);
                    comp.getSensors().add(obj);
                }
                objs.getCompartments().add(comp);
            }
            objects.add(objs);
        }
        return objects;
    }
}