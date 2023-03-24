package com.example.morgen;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.annotation.SuppressLint;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.Space;
import android.widget.TextView;
import android.widget.VideoView;

import java.util.Timer;
import java.util.TimerTask;

public class BounceSimulator extends AppCompatActivity {

    VideoView video;
    TextView points;
    Space bounce_button;
    ImageButton shop_button;
    FrameLayout frameLayout;
    boolean isAnimAlreadyRan = false;
    int Counter = 0;
    Timer timer1;
    Timer timer2;
    int bounce_click;
    boolean fragmentShowedStatus = false;
    final int seekToValue = 101597; //  101100
    private boolean isBounceStart = false;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bounce_simulator);


        timer2 = new Timer();
        timer1 = new Timer();

        if (MainMenu.dbManager.getValues() == null) {
            MainMenu.dbManager.putValues();
        }
        bounce_click = MainMenu.dbManager.getValues().get(0);

        points = findViewById(R.id.bounce_points);
//        bounce_button = findViewById(R.id.bounce_click);
        shop_button = findViewById(R.id.shop_button);
        frameLayout = findViewById(R.id.shopFragment);



        initializeVideo();
        video.seekTo((int) seekToValue);
        video.start();

        Fragment fragment = new ShopFragment();
        FragmentManager ft = getSupportFragmentManager();
        ft.beginTransaction()
                        .replace(R.id.shopFragment, fragment)
                        .hide(fragment)
                        .commit();

        video.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!(isBounceStart)) {
                    return;
                }
                runBounceAnim();
            }
        });

        shop_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!(fragmentShowedStatus)) {
                    ft.beginTransaction()
                            .show(fragment)
                            .commit();
                    fragmentShowedStatus = true;
                    Log.i("ButtonManager", "Fragment was show");
                } else {
                    ft.beginTransaction()
                            .hide(fragment)
                            .commit();
                    fragmentShowedStatus = false;
                    Log.i("ButtonManager", "Fragment was hide");
                }
            }
        });

        timer1.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (video.getCurrentPosition() >= 101100 && video.getCurrentPosition() <= 101120) {
                            isBounceStart = true;
                            video.pause();
                        }
                    }
                });
            }
        }, 1, 20);

    }

    @Override
    protected void onResume() {
        super.onResume();

        points.setText(String.valueOf(MainMenu.dbManager.getValues().get(0)));
    }

    private void initializeVideo() {
        video = findViewById(R.id.morgen_intro2);

        String video_path = "android.resource://" + getPackageName() + "/" + R.raw.intro2;
        Uri videoURI = Uri.parse(video_path);
        video.setVideoURI(videoURI);
    }

    private void runBounceAnim() {

        bounce_click = MainMenu.dbManager.getValues().get(0);

        bounce_click++;
        MainMenu.dbManager.updateValues(bounce_click);
        bounce_click = MainMenu.dbManager.getValues().get(0);

        points.setText(String.valueOf(bounce_click));



        if (isAnimAlreadyRan) {
            return;
        }

        isAnimAlreadyRan = true;
        Counter++;
        video.start();

        if (Counter >= 13) {
            Counter = 0;

            isAnimAlreadyRan = false;
            isBounceStart = false;
            video.seekTo((int) seekToValue);
            return;
        }

        timer2.schedule(new TimerTask() {
            @Override
            public void run() {

                isAnimAlreadyRan = false;
                video.pause();
            }
        }, 450L);


    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        MainMenu.dbManager.closeDB();
    }
}