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
import com.wazir.warehousing.Adapters.ContactAdapter;
import com.wazir.warehousing.FCM.SharedPrefsManager;
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
        fetchContacts();
    }

    void fetchContacts() {
        final ArrayList<ContactObject> contacts = new ArrayList<>();
        FirebaseFirestore.getInstance().collection("ACTIVITIES")
                .document(SharedPrefsManager.getInstance(mContext).getWarehouseId())
                .collection("Contacts")
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {
                        contacts.clear();
                        for (QueryDocumentSnapshot snapshot : queryDocumentSnapshots) {
                            contacts.add(snapshot.toObject(ContactObject.class));
                        }
                        ContactAdapter adapter = new ContactAdapter(contacts, mContext);
                        adapter.setInteract(contactInteract);
                        contactsRcView.setAdapter(adapter);
                    }
                });
    }
}