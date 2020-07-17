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
import com.wazir.warehousing.ModelObject.Assignees;
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
        AdapterActivityChecker adapterActivityChecker = new AdapterActivityChecker(getTasks(), context);
        adapterActivityChecker.setWorker(worker);
        adapterActivityChecker.setInteract(interact);
        containerRcView.setAdapter(adapterActivityChecker);
    }

    ArrayList<Object> getTasks() throws ParseException {
        BodyObj obj = new BodyObj();
        obj.setTitle("Some task Title");
        obj.setDescription("Some Description Description Description DescriptionDescriptionDescription v v Descriptionv");
        obj.setAssignees(new ArrayList<Assignees>() {
            {
                add(new Assignees("Somme Name ", "Rajesh"));
            }

            {
                add(new Assignees("Somme Name ", "Rajesh"));
            }

            {
                add(new Assignees("Somme Name ", "Rajesh"));
            }
        });
        obj.setChecked(false);
        String string = "02/04/2020";
        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy", Locale.ENGLISH);
        Date date = format.parse(string);
        obj.setTimeOfTask(date);
        obj.setLevel1Id("SOme Id 1");
        obj.setLevel2Id("Some id 2");
        ArrayList<Object> objects = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            objects.add(new TitleObj("Some Title : " + i));
            for (int j = 0; j < 3; j++) {
                objects.add(obj);
            }
        }
        return objects;
    }

    public void setEvent(FragmentsClickEvent event) {
        this.event = event;
    }
}