package com.wazir.warehousing.GloabalFunctions;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatDialog;

import com.wazir.warehousing.R;

public class LoadingPopup extends AppCompatDialog {

    Context ctx;
    AlertDialog alertDialog;
    String information, location, name;

    public LoadingPopup(Context context) {
        super(context);
        ctx = context;
    }

    public void setSysParams(String loc, String inf, String name) {
        this.location = loc;
        this.information = inf;
        this.name = name;
    }

    public void dialogRaise() {
        if (ctx == null) {
            return;
        }
        final AlertDialog.Builder alert = new AlertDialog.Builder(ctx);
        final View view1 = LayoutInflater.from(ctx).inflate(R.layout.layout_alert_progress, null);
        alert.setView(view1);
        TextView viewTitle = view1.findViewById(R.id.textView19);
        TextView inform = view1.findViewById(R.id.textView23);
        TextView location = view1.findViewById(R.id.textView22);
        viewTitle.setText(name);
        inform.setText(information);
        location.setText(this.location);
        alertDialog = alert.create();
        alertDialog.setCanceledOnTouchOutside(true);
        alertDialog.show();
    }

    public void dialogDismiss() {
        if (ctx == null) {
            return;
        }
        alertDialog.dismiss();
    }
}
