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
import com.wazir.warehousing.ModelObject.SensorObj;
import com.wazir.warehousing.R;

import java.util.ArrayList;

public class SensorAdapter extends RecyclerView.Adapter<SensorAdapter.SensorViewHolder> {
    ArrayList<SensorObj> objects;
    Context context;

    public SensorAdapter(ArrayList<SensorObj> objects, Context context) {
        this.objects = objects;
        this.context = context;
    }

    @NonNull
    @Override
    public SensorViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.sensor_info_layout, parent, false);
        return new SensorViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull SensorViewHolder holder, final int position) {
        holder.sensorId.setText(objects.get(position).getSensorId());
        if (objects.get(position).isStatus()) {
            holder.cardBack.setCardBackgroundColor(context.getResources().getColor(R.color.g_green));
            holder.sensorStat.setText("WORKING");
        } else {
            holder.cardBack.setCardBackgroundColor(context.getResources().getColor(R.color.g_red));
            holder.sensorStat.setText("NOT-WORKING");
        }
        holder.cardBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LoadingPopup popup = new LoadingPopup(context);
                popup.setSysParams(objects.get(position).getLoc(), objects.get(position).getInfo(), objects.get(position).getSensorId());
                popup.dialogRaise();
            }
        });
    }

    @Override
    public int getItemCount() {
        return this.objects.size();
    }

    static class SensorViewHolder extends RecyclerView.ViewHolder {

        TextView sensorId, sensorStat;
        CardView cardBack;

        public SensorViewHolder(@NonNull View itemView) {
            super(itemView);
            sensorId = itemView.findViewById(R.id.textView25);
            sensorStat = itemView.findViewById(R.id.textView29);
            cardBack = itemView.findViewById(R.id.sensor_id_back);
        }
    }
}
