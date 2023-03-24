package com.example.morgen;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.app.NotificationManager;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.VideoView;

import com.example.morgen.ad.AdController;
import com.example.morgen.ad.BannerAdManager;
import com.example.morgen.ad.InterstitialAdManager;
import com.yandex.mobile.ads.banner.AdSize;
import com.yandex.mobile.ads.banner.BannerAdView;
import com.yandex.mobile.ads.common.AdRequestError;
import com.yandex.mobile.ads.common.ImpressionData;
import com.yandex.mobile.ads.interstitial.InterstitialAd;
import com.yandex.mobile.ads.interstitial.InterstitialAdEventListener;

import java.util.Timer;
import java.util.TimerTask;

public class BounceVideo extends AppCompatActivity {

    public static boolean isLongVideo = false;
    public static String INTERSTITIAL_AD_TOKEN = "demo-interstitial-yandex"; // R-M-2195871-2
    public static String BANNER_AD_TOKEN = "demo-banner-yandex"; // R-M-2195871-1
    public static String REWARDED_AD_TOKEN = "demo-rewarded-yandex"; // R-M-2195871-3
    public static String AD_CONTROLLER_TAG = "AdController";

    VideoView intro;
    ConstraintLayout bounce_button;
    ImageView click_help;
    Timer timer;
    Timer timer2;
    Timer timer3;
    BannerAdView banner_1;
    private int Counter = 0;
    boolean isAnimAlreadyRan;
    boolean isBounceDone = false;
    boolean isBounceStart = false;
    NotificationManager notificationManager;
    InterstitialAd mInterstitialAd;
    AdController adController;
    private InterstitialAdManager interstitialAdManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bounce_video);

        intro = findViewById(R.id.morgen_intro);
        bounce_button = findViewById(R.id.bounce_button);
        click_help = findViewById(R.id.click_help);
        banner_1 = findViewById(R.id.ad_banner_1);

        adController = new AdController(this);
//        adController.initializeAd();
        BannerAdManager bannerAdManager = new BannerAdManager(this);
//        bannerAdManager.loadBannerAd(banner_1, AdSize.BANNER_320x50, BANNER_AD_TOKEN);
        interstitialAdManager = new InterstitialAdManager(this);
//        interstitialAdManager.loadInterstitialAd(INTERSTITIAL_AD_TOKEN);
//        interstitialAdManager.setAdListeners(createInterstitialAdListeners());

        timer = new Timer();
        timer2 = new Timer();
        timer3 = new Timer();

        initializeVideo();

        click_help.setVisibility(View.INVISIBLE);
        timer3.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (intro.getCurrentPosition() >= 101100 && intro.getCurrentPosition() <= 101120) {
                            isBounceStart = true;
                            click_help.setVisibility(View.VISIBLE);
                            intro.pause();
                        }
                        if (!(isLongVideo)) {
                            if (intro.getCurrentPosition() >= 123950) {
                                runActivitySwitcher();
                                cancel();
                                intro.pause();
                            }
                        }
                    }
                });
            }
        }, 1, 20);

        bounce_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!(isBounceStart)) {
                    return;
                }

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        click_help.setVisibility(View.INVISIBLE);
                    }
                });

                runBounceAnim();
            }
        });

        intro.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mediaPlayer) {
                runActivitySwitcher();
            }
        });


    }

    private void runActivitySwitcher() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
//                interstitialAdManager.showInterstitialAd();
            }
        });
    }

    private void runBounceAnim() {
        if (isBounceDone) {
            return;
        }
        if (isAnimAlreadyRan) {
            return;
        }

        isAnimAlreadyRan = true;
        Counter++;

        intro.start();

        timer2.schedule(new TimerTask() {
            @Override
            public void run() {

                isAnimAlreadyRan = false;
                intro.pause();

                if (Counter == 13) {
                    isBounceDone = true;
                    intro.start();
                    cancel();
                }
            }
        }, 450L);


    }


    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }



    private void showContent() {
        Intent intent = new Intent(this, MainMenu.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        startActivity(intent);
    }



    private InterstitialAdEventListener createInterstitialAdListeners() {
        InterstitialAdEventListener listener = new InterstitialAdEventListener() {
            @Override
            public void onAdLoaded() {
            }

            @Override
            public void onAdFailedToLoad(@NonNull AdRequestError adRequestError) {
                showContent();
//                interstitialAdManager.loadInterstitialAd(INTERSTITIAL_AD_TOKEN);
            }

            @Override
            public void onAdShown() {
            }

            @Override
            public void onAdDismissed() {
                showContent();
//                interstitialAdManager.loadInterstitialAd(INTERSTITIAL_AD_TOKEN);
            }

            @Override
            public void onAdClicked() {

            }

            @Override
            public void onLeftApplication() {

            }

            @Override
            public void onReturnedToApplication() {
            }

            @Override
            public void onImpression(@Nullable ImpressionData impressionData) {

            }
        };

        return listener;
    }


    private void initializeVideo() {
        String video_path = "android.resource://" + getPackageName() + "/" + R.raw.intro2;
        Uri video = Uri.parse(video_path);
        intro.setVideoURI(video);

        if (!(isLongVideo)) {
            intro.seekTo(87600); // 101597
        }

        intro.start();
    }
}