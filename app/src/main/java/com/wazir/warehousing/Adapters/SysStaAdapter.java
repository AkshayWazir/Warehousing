package com.wazir.warehousing.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
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
    public void onBindViewHolder(@NonNull final SysStaViewHolder holder, final int position) {
        holder.wareId.setText(objects.get(position).getWarehouseId());
        holder.wareCap.setText(objects.get(position).getCapacity());
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (holder.compRcView.getVisibility() == View.VISIBLE) {
                    holder.compRcView.setVisibility(View.GONE);
                } else {
                    holder.compRcView.setVisibility(View.VISIBLE);
                }
            }
        });
        holder.compRcView.setAdapter(new CompAdapter(objects.get(position).getCompartments(), context));
        holder.compRcView.setLayoutManager(new LinearLayoutManager(context));
    }

    @Override
    public int getItemCount() {
        return objects.size();
    }

    static class SysStaViewHolder extends RecyclerView.ViewHolder {
        TextView wareId, wareCap;
        RecyclerView compRcView;
        CardView cardView;

        public SysStaViewHolder(@NonNull View itemView) {
            super(itemView);
            wareId = itemView.findViewById(R.id.textView16);
            wareCap = itemView.findViewById(R.id.textView10);
            compRcView = itemView.findViewById(R.id.id_cont_comp);
            cardView = itemView.findViewById(R.id.id_sys_status);
        }
    }
}
