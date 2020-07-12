package com.wazir.warehousing.Fragments;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.wazir.warehousing.Adapters.AdapterActivityChecker;
import com.wazir.warehousing.Interfaces.CheckerInteract;
import com.wazir.warehousing.Interfaces.FragmentsClickEvent;
import com.wazir.warehousing.ModelObject.BodyObj;
import com.wazir.warehousing.ModelObject.TitleObj;
import com.wazir.warehousing.R;

import java.util.ArrayList;

public class FragmentActiChecker extends Fragment {
    FragmentsClickEvent event;
    RecyclerView containerRcView;
    Context context;
    CheckerInteract interact;

    public FragmentActiChecker(FragmentsClickEvent event, Context context, CheckerInteract interact) {
        this.event = event;
        this.context = context;
        this.interact = interact;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_acti_checker, container, false);
        containerRcView = view.findViewById(R.id.activity_rc_cotainer);
        setRcView();
        return view;
    }

    void setRcView() {
        containerRcView.setLayoutManager(new LinearLayoutManager(context));
        AdapterActivityChecker adapterActivityChecker = new AdapterActivityChecker(getTasks(), context);
        adapterActivityChecker.setInteract(interact);
        containerRcView.setAdapter(adapterActivityChecker);
    }

    ArrayList<Object> getTasks() {
        ArrayList<Object> objects = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            objects.add(new TitleObj("Some Title : " + i));
            objects.add(new BodyObj(true, "Some Title" + 1));
            objects.add(new BodyObj(true, "Some Title" + 2));
            objects.add(new BodyObj(true, "Some Title" + 3));
            objects.add(new BodyObj(true, "Some Title" + 4));
        }
        return objects;
    }

    public void setEvent(FragmentsClickEvent event) {
        this.event = event;
    }
}