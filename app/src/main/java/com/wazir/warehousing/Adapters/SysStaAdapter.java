package com.wazir.warehousing.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.wazir.warehousing.ModelObject.SysStaObject;
import com.wazir.warehousing.R;

import java.util.ArrayList;

public class SysStaAdapter extends RecyclerView.Adapter<SysStaAdapter.SysStaViewHolder> {
    ArrayList<SysStaObject> objects;
    Context context;

    public SysStaAdapter(ArrayList<SysStaObject> objects, Context context) {
        this.objects = objects;
        this.context = context;
    }

    @NonNull
    @Override
    public SysStaViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.status_layout, parent, false);
        return new SysStaViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull SysStaViewHolder holder, int position) {
        holder.sensName.setText(objects.get(position).getSensName());
        if (objects.get(position).isSensValue()) {
            holder.staBack.setCardBackgroundColor(context.getResources().getColor(R.color.g_green));
        } else {
            holder.staBack.setCardBackgroundColor(context.getResources().getColor(R.color.g_red));
        }
    }

    @Override
    public int getItemCount() {
        return objects.size();
    }

    static class SysStaViewHolder extends RecyclerView.ViewHolder {
        CardView staBack;
        TextView sensName;

        public SysStaViewHolder(@NonNull View itemView) {
            super(itemView);
            sensName = itemView.findViewById(R.id.textView10);
            staBack = itemView.findViewById(R.id.id_sys_status);
        }
    }
}
