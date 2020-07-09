package com.wazir.warehousing.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.wazir.warehousing.FragmentsClickEvent;
import com.wazir.warehousing.R;

public class FragmentContact extends Fragment {

    FragmentsClickEvent event;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_contact, container, false);
    }

    public void setEvent(FragmentsClickEvent event) {
        this.event = event;
    }
}