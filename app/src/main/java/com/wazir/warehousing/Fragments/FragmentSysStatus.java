package com.wazir.warehousing.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.wazir.warehousing.Interfaces.FragmentsClickEvent;
import com.wazir.warehousing.R;

public class FragmentSysStatus extends Fragment {
    FragmentsClickEvent events;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_sys_status, container, false);
    }

    public void setEvents(FragmentsClickEvent events) {
        this.events = events;
    }
}