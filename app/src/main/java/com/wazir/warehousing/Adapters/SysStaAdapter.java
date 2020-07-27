package com.wazir.warehousing.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.wazir.warehousing.GloabalFunctions.LoadingPopup;
import com.wazir.warehousing.ModelObject.SysStaObject;
import com.wazir.warehousing.R;

import java.util.ArrayList;

public class SysStaAdapter extends RecyclerView.Adapter<SysStaAdapter.SysStaViewHolder> {
    ArrayList<SysStaObject> objects;
    Context context;
    LoadingPopup popup;

    public SysStaAdapter(ArrayList<SysStaObject> objects, Context context) {
        this.objects = objects;
        this.context = context;
        this.popup = new LoadingPopup(context);
    }

    @NonNull
    @Override
    public SysStaViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.status_layout, parent, false);
        return new SysStaViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull SysStaViewHolder holder, final int position) {
        holder.sensName.setText(objects.get(position).getSensorId());
        holder.compId.setText(objects.get(position).getCompartId());
        if (objects.get(position).isSensValue()) {
            holder.staBack.setCardBackgroundColor(context.getResources().getColor(R.color.g_green));
            holder.sensStatus.setText("WORKING");
        } else {
            holder.staBack.setCardBackgroundColor(context.getResources().getColor(R.color.g_red));
            holder.sensStatus.setText("No Response");
        }
        holder.staBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popup.setSysParams(
                        objects.get(position).getLocation(),
                        objects.get(position).getInfo(),
                        objects.get(position).getSensorId()
                );
                popup.dialogRaise();
            }
        });
    }

    @Override
    public int getItemCount() {
        return objects.size();
    }

    static class SysStaViewHolder extends RecyclerView.ViewHolder {
        CardView staBack;
        TextView sensName, sensStatus, compId;

        public SysStaViewHolder(@NonNull View itemView) {
            super(itemView);
            sensName = itemView.findViewById(R.id.textView10);
            staBack = itemView.findViewById(R.id.id_sys_status);
            sensStatus = itemView.findViewById(R.id.textView18);
            compId = itemView.findViewById(R.id.textView25);
        }
    }
}
