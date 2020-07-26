package com.wazir.warehousing.Fragments;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.wazir.warehousing.Adapters.AdapterActivityChecker;
import com.wazir.warehousing.FCM.SharedPrefsManager;
import com.wazir.warehousing.Interfaces.CheckerInteract;
import com.wazir.warehousing.Interfaces.FragmentsClickEvent;
import com.wazir.warehousing.ModelObject.BodyObj;
import com.wazir.warehousing.ModelObject.TitleObj;
import com.wazir.warehousing.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class FragmentActiChecker extends Fragment {
    FragmentsClickEvent event;
    RecyclerView containerRcView;
    Context context;
    CheckerInteract interact;
    boolean worker = false;


    public FragmentActiChecker(FragmentsClickEvent event, Context context, CheckerInteract interact) {
        this.event = event;
        this.context = context;
        this.interact = interact;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_acti_checker, container, false);
        containerRcView = view.findViewById(R.id.activity_rc_cotainer);
        try {
            setRcView();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return view;
    }

    public void setWorker(boolean worker) {
        this.worker = worker;
    }

    void setRcView() throws ParseException {
        containerRcView.setLayoutManager(new LinearLayoutManager(context));
        getTasks();
    }

    void getTasks() {
        final ArrayList<Object> objects = new ArrayList<>();
        String wareId = SharedPrefsManager.getInstance(context).getWarehouseId();
        FirebaseFirestore.getInstance().collection("ACTIVITIES")
                .document(wareId)
                .collection("Activities")
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {
                        objects.clear();
                        for (QueryDocumentSnapshot snapshot : queryDocumentSnapshots) {
                            if (snapshot.contains("checked")) {
                                BodyObj obj = snapshot.toObject(BodyObj.class);
                                SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy", Locale.US);
                                try {
                                    Date date = sdf.parse(snapshot.getString("timeDate"));
                                    obj.setTimeOfTask(date);
                                } catch (ParseException ex) {
                                    ex.printStackTrace();
                                }
                                objects.add(obj);
                            } else {
                                objects.add(queryDocumentSnapshots.toObjects(TitleObj.class));
                            }
                        }
                        for (int i = 0; i < 3; i++) {

                        }
                        AdapterActivityChecker adapterActivityChecker = new AdapterActivityChecker(objects, context);
                        adapterActivityChecker.setWorker(worker);
                        adapterActivityChecker.setInteract(interact);
                        containerRcView.setAdapter(adapterActivityChecker);
                    }
                });
//        for (int i = 0; i < 10; i++) {
//            objects.add(new TitleObj("Some Title : " + i));
//            for (int j = 0; j < 3; j++) {
//                BodyObj obj = new BodyObj();
//                obj.setTitle("Some task Title");
//                obj.setDescription("Some Description Description Description DescriptionDescriptionDescription v v Descriptionv");
//                obj.setAssignees(new ArrayList<Assignees>() {
//                    {
//                        add(new Assignees("Somme Name ", "Rajesh"));
//                    }
//
//                    {
//                        add(new Assignees("Somme Name ", "Rakesh"));
//                    }
//
//                    {
//                        add(new Assignees("Somme Name ", "Ramesh"));
//                    }
//                });
//                obj.setChecked(false);
//                String string = "02/04/2020";
//                SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy", Locale.ENGLISH);
//                Date date = format.parse(string);
//                obj.setTimeOfTask(date);
//                obj.setLevel1Id("SOme Id 1");
//                obj.setLevel2Id("Some id 2");
//                objects.add(obj);
//            }
//        }
    }

    public void setEvent(FragmentsClickEvent event) {
        this.event = event;
    }
}