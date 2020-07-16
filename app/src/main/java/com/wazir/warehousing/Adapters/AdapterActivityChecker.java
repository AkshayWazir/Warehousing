package com.wazir.warehousing.Adapters;

import android.animation.ValueAnimator;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.airbnb.lottie.LottieAnimationView;
import com.wazir.warehousing.Interfaces.CheckerInteract;
import com.wazir.warehousing.ModelObject.BodyObj;
import com.wazir.warehousing.ModelObject.TitleObj;
import com.wazir.warehousing.R;

import java.util.ArrayList;

public class AdapterActivityChecker extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    ArrayList<Object> objects;
    Context context;
    CheckerInteract interact;

    public AdapterActivityChecker(ArrayList<Object> objects, Context context) {
        this.objects = objects;
        this.context = context;
    }

    public void setInteract(CheckerInteract interact) {
        this.interact = interact;
    }

    @Override
    public int getItemViewType(int position) {
        if (objects.get(position) instanceof TitleObj) {
            return 0;
        } else {
            return 1;
        }
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == 0) {
            View v = LayoutInflater.from(context).inflate(R.layout.activity_check_element_title, parent, false);
            return new HeaderViewHolder(v);
        } else {
            View v = LayoutInflater.from(context).inflate(R.layout.acitivity_check_element_body, parent, false);
            return new BodyChecker(v);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull final RecyclerView.ViewHolder holder, final int position) {
        if (holder instanceof HeaderViewHolder) {
            TitleObj obj = (TitleObj) objects.get(position);
            ((HeaderViewHolder) holder).header.setText(obj.getTitle());
        } else {
            final BodyObj obj = (BodyObj) objects.get(position);

        }
    }

    @Override
    public int getItemCount() {
        return objects.size();
    }

    static class HeaderViewHolder extends RecyclerView.ViewHolder {
        TextView header;

        public HeaderViewHolder(@NonNull View itemView) {
            super(itemView);
            header = itemView.findViewById(R.id.textView8);
        }
    }

    static class BodyChecker extends RecyclerView.ViewHolder {
        TextView title;

        public BodyChecker(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.task_title_id);
        }
    }
}
