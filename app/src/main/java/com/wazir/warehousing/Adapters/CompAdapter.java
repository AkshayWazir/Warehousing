package com.wazir.warehousing.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.wazir.warehousing.ModelObject.Compartment;
import com.wazir.warehousing.R;

import java.util.ArrayList;

public class CompAdapter extends RecyclerView.Adapter<CompAdapter.CompViewHolder> {
    ArrayList<Compartment> objs;
    Context context;

    public CompAdapter(ArrayList<Compartment> objs, Context context) {
        this.objs = objs;
        this.context = context;
    }

    @NonNull
    @Override
    public CompViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.compart_layout, parent, false);
        return new CompViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull final CompViewHolder holder, int position) {
        holder.compCapacity.setText(objs.get(position).getCapacity());
        holder.compId.setText(objs.get(position).getCompId());
        holder.cardBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (holder.sensRcView.getVisibility() == View.VISIBLE) {
                    holder.sensRcView.setVisibility(View.GONE);
                } else {
                    holder.sensRcView.setVisibility(View.VISIBLE);
                }
            }
        });
        SensorAdapter adapter = new SensorAdapter(objs.get(position).getSensors(), context);
        holder.sensRcView.setLayoutManager(new GridLayoutManager(context, 3));
        holder.sensRcView.setAdapter(adapter);
    }

    @Override
    public int getItemCount() {
        return this.objs.size();
    }

    static class CompViewHolder extends RecyclerView.ViewHolder {
        TextView compId, compCapacity;
        RecyclerView sensRcView;
        CardView cardBack;

        public CompViewHolder(@NonNull View itemView) {
            super(itemView);
            compId = itemView.findViewById(R.id.textView17);
            compCapacity = itemView.findViewById(R.id.textView18);
            sensRcView = itemView.findViewById(R.id.id_cont_senso);
            cardBack = itemView.findViewById(R.id.id_card_back_comp);
        }
    }
}
