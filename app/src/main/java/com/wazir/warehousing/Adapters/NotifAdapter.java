package com.wazir.warehousing.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.wazir.warehousing.ModelObject.NoticeObject;
import com.wazir.warehousing.R;

import java.util.ArrayList;

import static com.wazir.warehousing.GloabalFunctions.Constants.COLOR_TACK;

public class NotifAdapter extends RecyclerView.Adapter<NotifAdapter.NotifViewHolder>{

    ArrayList<NoticeObject> notices;
    Context context;

    public NotifAdapter(ArrayList<NoticeObject> notices, Context context) {
        this.notices = notices;
        this.context = context;
    }

    @NonNull
    @Override
    public NotifViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.notif_layout, parent, false);
        return new NotifViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull NotifViewHolder holder, int position) {
        NoticeObject obj = notices.get(position);
        holder.title.setText(obj.getTitle());
        holder.date.setText(obj.getDate());
        holder.desc.setText(obj.getDesc());
        holder.label.setCardBackgroundColor(context.getResources().getColor(COLOR_TACK.get(obj.getCate())));
    }

    @Override
    public int getItemCount() {
        return notices.size();
    }

    static class NotifViewHolder extends RecyclerView.ViewHolder {
        TextView title, desc, date;
        CardView label;

        public NotifViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.textView12);
            desc = itemView.findViewById(R.id.textView13);
            date = itemView.findViewById(R.id.textView11);
            label = itemView.findViewById(R.id.cardView6);
        }
    }
}
