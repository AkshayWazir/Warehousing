package com.wazir.warehousing.Fragments;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.wazir.warehousing.Adapters.SysStaAdapter;
import com.wazir.warehousing.Interfaces.FragmentsClickEvent;
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
        SysStaAdapter adapter = new SysStaAdapter(getStatData(), context);
        rcView.setAdapter(adapter);
        rcView.setLayoutManager(new GridLayoutManager(context, 2));
        return view;
    }

    public ArrayList<SysStaObject> getStatData() {
        ArrayList<SysStaObject> objects = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            if (i % 2 == 0) {
                objects.add(new SysStaObject("Name Of Sensor " + i, false));
            } else {
                objects.add(new SysStaObject("Name Of Sensor " + i, true));
            }
        }
        return objects;
    }

    public void setEvents(FragmentsClickEvent events) {
        this.events = events;
    }
}