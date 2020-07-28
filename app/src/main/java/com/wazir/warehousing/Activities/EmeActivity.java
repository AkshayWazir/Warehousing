package com.wazir.warehousing.Activities;

import android.os.Bundle;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.wazir.warehousing.R;

import static com.wazir.warehousing.GloabalFunctions.Constants.FLOOD_HAZ;

public class EmeActivity extends AppCompatActivity {
    ConstraintLayout background;
    ImageView symbol;
    TextView title, location;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_eme);
        initUi();
        String situation = getIntent().getStringExtra("EME_TYPE");
        if (situation.equals(FLOOD_HAZ)) {
            background.setBackgroundColor(this.getColor(R.color.g_blue));
            symbol.setImageDrawable(this.getDrawable(R.drawable.ic_flood));
            title.setText("FLOOD HAZARD");
        }
        location.setText(getIntent().getStringExtra("EME_LOC"));
    }

    void initUi() {
        background = findViewById(R.id.haz_back);
        symbol = findViewById(R.id.imageView8);
        title = findViewById(R.id.textView30);
        location = findViewById(R.id.textView32);
    }
}