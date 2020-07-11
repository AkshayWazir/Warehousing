package com.wazir.warehousing.Fragments;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.wazir.warehousing.Adapters.ContactAdapter;
import com.wazir.warehousing.Interfaces.ContactInteract;
import com.wazir.warehousing.Interfaces.FragmentsClickEvent;
import com.wazir.warehousing.ModelObject.ContactObject;
import com.wazir.warehousing.R;

import java.util.ArrayList;

public class FragmentContact extends Fragment {

    FragmentsClickEvent event;
    RecyclerView contactsRcView;
    Context mContext;
    ContactInteract contactInteract;

    public FragmentContact(FragmentsClickEvent event, Context mContext) {
        this.event = event;
        this.mContext = mContext;
    }

    public void setContactInteract(ContactInteract contactInteract) {
        this.contactInteract = contactInteract;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View layout = inflater.inflate(R.layout.fragment_contact, container, false);
        contactsRcView = layout.findViewById(R.id.id_contact_container);
        setupContacts();
        return layout;
    }

    public void setupContacts() {
        contactsRcView.setLayoutManager(new LinearLayoutManager(mContext));
        ContactAdapter adapter = new ContactAdapter(fetchContacts(), mContext);
        adapter.setInteract(contactInteract);
        contactsRcView.setAdapter(adapter);
    }

    ArrayList<ContactObject> fetchContacts() {
        ArrayList<ContactObject> contacts = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            ContactObject object = new ContactObject();
            object.setProfilePicture("https://firebasestorage.googleapis.com/v0/b/cwc-wms.appspot.com/o/ball_640x480.jpg?alt=media&token=09b61945-870c-494b-9ac5-9c9210da2860");
            object.setContact("8368370908");
            object.setName("Akshay Rein");
            object.setUserId("+918368370908");
            object.setPost("Manager");
            contacts.add(object);
        }
        return contacts;
    }
}