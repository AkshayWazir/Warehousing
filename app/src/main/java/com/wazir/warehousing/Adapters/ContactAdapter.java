package com.wazir.warehousing.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.wazir.warehousing.Interfaces.ContactInteract;
import com.wazir.warehousing.ModelObject.ContactObject;
import com.wazir.warehousing.R;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class ContactAdapter extends RecyclerView.Adapter<ContactAdapter.ContactViewHolder> {

    ArrayList<ContactObject> objects;
    Context context;
    ContactInteract interact;


    public ContactAdapter(ArrayList<ContactObject> objects, Context context) {
        this.objects = objects;
        this.context = context;
    }

    public void setInteract(ContactInteract interact) {
        this.interact = interact;
    }

    @NonNull
    @Override
    public ContactViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.contact_item_view, parent, false);
        return new ContactViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ContactViewHolder holder, final int position) {
        ContactObject obj = objects.get(position);
        Glide.with(context).load(obj.getProfilePicture()).into(holder.profilePicture);
        holder.name.setText(obj.getName());
        holder.designation.setText(obj.getPost());
        holder.callCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                interact.callUser(objects.get(position).getContact());
            }
        });
        holder.alertCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                interact.alertUser("someToken");
            }
        });
    }

    @Override
    public int getItemCount() {
        return objects.size();
    }

    static class ContactViewHolder extends RecyclerView.ViewHolder {
        CircleImageView profilePicture;
        TextView name, designation;
        CardView callCard, alertCard;

        public ContactViewHolder(@NonNull View itemView) {
            super(itemView);
            profilePicture = itemView.findViewById(R.id.profile_picture);
            name = itemView.findViewById(R.id.textView5);
            designation = itemView.findViewById(R.id.textView6);
            callCard = itemView.findViewById(R.id.cardView2);
            alertCard = itemView.findViewById(R.id.cardView5);
        }
    }
}
