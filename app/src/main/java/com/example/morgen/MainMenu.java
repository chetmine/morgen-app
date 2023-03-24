package com.example.morgen;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import com.example.morgen.db.DBManager;

public class MainMenu extends AppCompatActivity {

    Button switchActivity;
    Button playSim;
    TextView versionText;


    @SuppressLint("StaticFieldLeak")
    public static DBManager dbManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);

        dbManager = new DBManager(this);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);

        switchActivity = findViewById(R.id.playButton);
        playSim = findViewById(R.id.playSimButton);
        versionText = findViewById(R.id.version_text);


        switchActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                runActivitySwitcher();
            }
        });

        playSim.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                runActivitySwitcher2();
            }
        });

        versionText.setText(BuildConfig.VERSION_NAME);

    }

    private void runActivitySwitcher() {
        Intent nextActivity = new Intent(this, BounceVideo.class);
        nextActivity.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        startActivity(nextActivity);
    }

    private void runActivitySwitcher2() {
        Intent nextActivity = new Intent(this, BounceSimulator.class);
        nextActivity.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        startActivity(nextActivity);
    }
}