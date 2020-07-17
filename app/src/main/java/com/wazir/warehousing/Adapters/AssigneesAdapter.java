package com.wazir.warehousing.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.wazir.warehousing.ModelObject.Assignees;
import com.wazir.warehousing.R;

import java.util.ArrayList;

public class AssigneesAdapter extends RecyclerView.Adapter<AssigneesAdapter.AssignViewHolder>{
    ArrayList<Assignees> aassignees;
    Context context;

    public AssigneesAdapter(ArrayList<Assignees> aassignees, Context context) {
        this.aassignees = aassignees;
        this.context = context;
    }

    @NonNull
    @Override
    public AssignViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.worker_name_tag_ayout, parent, false);
        return new AssignViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull AssignViewHolder holder, int position) {
        holder.name.setText(aassignees.get(position).getName());
    }

    @Override
    public int getItemCount() {
        return aassignees.size();
    }

    class AssignViewHolder extends RecyclerView.ViewHolder {
        TextView name;

        public AssignViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.id_worker_name);
        }
    }
}
