package com.wazir.warehousing.Fragments;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
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
import com.wazir.warehousing.ModelObject.Assignees;
import com.wazir.warehousing.ModelObject.BodyObj;
import com.wazir.warehousing.ModelObject.TitleObj;
import com.wazir.warehousing.R;

import java.text.ParseException;
import java.util.ArrayList;

public class FragmentActiChecker extends Fragment {
    FragmentsClickEvent event;
    RecyclerView containerRcView;
    Context context;
    CheckerInteract interact;
    boolean worker = false;
    String TAG = "NOTICES";


    public FragmentActiChecker(FragmentsClickEvent event, Context context, CheckerInteract interact) {
        this.event = event;
        this.context = context;
        this.interact = interact;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_acti_checker, container, false);
        containerRcView = view.findViewById(R.id.activity_rc_cotainer);
        getTasks();
//        try {
//
//        } catch (ParseException e) {
//            e.printStackTrace();
//        }
        return view;
    }

    public void setWorker(boolean worker) {
        this.worker = worker;
    }

    void setRcView() throws ParseException {
        containerRcView.setLayoutManager(new LinearLayoutManager(context));
        containerRcView.setAdapter(new AdapterActivityChecker(feedTask(), context));
    }

    ArrayList<Object> feedTask() {
        ArrayList<Object> objects = new ArrayList<>();

        objects.add(new TitleObj("Warehouse Maintenance", "12 July"));

        BodyObj obj1 = new BodyObj();
        obj1.setChecked(false);
        obj1.setTimeOfTask("12 July");
        obj1.setTitle("Maintainable");
        obj1.setDescription("Some Long Description to make it very long so that it can fit and expand the bars and we know the actual size and fitting of the text");
        obj1.setAssignees(new ArrayList<Assignees>());
        obj1.getAssignees().add(new Assignees("+2211", "Rakesh Kumar"));
        obj1.getAssignees().add(new Assignees("+2211", "Suresh Kumar"));
        obj1.getAssignees().add(new Assignees("+2211", "Jamesh Roshan"));
        objects.add(obj1);

        BodyObj obj2 = new BodyObj();
        obj2.setChecked(false);
        obj2.setTimeOfTask("12 July");
        obj2.setTitle("Maintainable");
        obj2.setDescription("Some Long Description to make it very long so that it can fit and expand the bars and we know the actual size and fitting of the text");
        obj2.setAssignees(new ArrayList<Assignees>());
        obj2.getAssignees().add(new Assignees("+2211", "Rakesh Kumar"));
        obj2.getAssignees().add(new Assignees("+2211", "Suresh Kumar"));
        obj2.getAssignees().add(new Assignees("+2211", "Jamesh Roshan"));
        objects.add(obj2);

        return objects;
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
                            if (snapshot.getData().get("date") == null) {
                                BodyObj obj = snapshot.toObject(BodyObj.class);
                                objects.add(obj);
                                Log.d(TAG, "onEvent: Body");
                            } else {
                                Log.d(TAG, "onEvent: Title");
                                objects.add(new TitleObj(
                                        (String) snapshot.getData().get("title"),
                                        (String) snapshot.getData().get("date")
                                ));
                            }
                        }
                        AdapterActivityChecker adapterActivityChecker = new AdapterActivityChecker(objects, context);
                        containerRcView.setAdapter(adapterActivityChecker);
                        containerRcView.setLayoutManager(new LinearLayoutManager(context));
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