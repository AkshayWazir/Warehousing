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

import com.wazir.warehousing.Interfaces.CheckerInteract;
import com.wazir.warehousing.ModelObject.BodyObj;
import com.wazir.warehousing.ModelObject.TitleObj;
import com.wazir.warehousing.R;

import java.util.ArrayList;


public class AdapterActivityChecker extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    ArrayList<Object> objects;
    Context context;
    CheckerInteract interact;
    boolean worker = false;
    String TAG = "Adapter Events";

    public AdapterActivityChecker(ArrayList<Object> objects, Context context) {
        this.objects = objects;
        this.context = context;
    }

    public void setWorker(boolean worker) {
        this.worker = worker;
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
            if (worker) {
                View v = LayoutInflater.from(context).inflate(R.layout.worker_activity_checker, parent, false);
                return new WorkerBody(v);
            } else {
                View v = LayoutInflater.from(context).inflate(R.layout.acitivity_check_element_body, parent, false);
                return new BodyChecker(v);
            }
        }
    }

    @Override
    public void onBindViewHolder(@NonNull final RecyclerView.ViewHolder holder, final int position) {
        if (holder instanceof HeaderViewHolder) {
            TitleObj obj = (TitleObj) objects.get(position);
            ((HeaderViewHolder) holder).header.setText(obj.getTitle());
            ((HeaderViewHolder) holder).date.setText(obj.getDate());
        } else {
            final BodyObj obj = (BodyObj) objects.get(position);
            if (worker) {
                ((WorkerBody) holder).title.setText(obj.getTitle());
                ((WorkerBody) holder).description.setText(obj.getDescription());
            } else {
                ((BodyChecker) holder).title.setText(obj.getTitle());
                ((BodyChecker) holder).description.setText(obj.getDescription());
                if (obj.isChecked()) {
                    ((BodyChecker) holder).status.setText(context.getResources().getString(R.string.completedStr));
                    ((BodyChecker) holder).statusColor.setCardBackgroundColor(context.getResources().getColor(R.color.g_green));
                } else {
                    ((BodyChecker) holder).status.setText(context.getResources().getString(R.string.incompleteStr));
                    ((BodyChecker) holder).statusColor.setCardBackgroundColor(context.getResources().getColor(R.color.g_red));
                }
                AssigneesAdapter adapter = new AssigneesAdapter(obj.getAssignees(), context);
                ((BodyChecker) holder).assRcView.setAdapter(adapter);
                ((BodyChecker) holder).assRcView.setLayoutManager(new LinearLayoutManager(context, RecyclerView.HORIZONTAL, false));
            }
        }
    }

    @Override
    public int getItemCount() {
        return objects.size();
    }

    static class HeaderViewHolder extends RecyclerView.ViewHolder {
        TextView header, date;

        public HeaderViewHolder(@NonNull View itemView) {
            super(itemView);
            header = itemView.findViewById(R.id.textView8);
            date = itemView.findViewById(R.id.textView36);
        }
    }

    static class BodyChecker extends RecyclerView.ViewHolder {
        TextView title, description, status;
        CardView statusColor;
        RecyclerView assRcView;

        public BodyChecker(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.task_title_id);
            description = itemView.findViewById(R.id.task_descri_id);
            status = itemView.findViewById(R.id.id_status);
            assRcView = itemView.findViewById(R.id.id_assign_id);

            statusColor = itemView.findViewById(R.id.id_status_card);
        }
    }

    static class WorkerBody extends RecyclerView.ViewHolder {
        TextView title, description;

        public WorkerBody(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.task_title_id);
            description = itemView.findViewById(R.id.task_descri_id);
        }
    }
}
