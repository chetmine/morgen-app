package com.example.morgen.ad;



import android.content.Context;
import android.util.Log;

import com.example.morgen.BounceVideo;
import com.yandex.mobile.ads.common.InitializationListener;
import com.yandex.mobile.ads.common.MobileAds;

public class AdController {

    private final Context context;

    public AdController(Context context) {
        this.context = context;
    }

    public void initializeAd() {
        MobileAds.initialize(context, new InitializationListener() {
            @Override
            public void onInitializationCompleted() {
                Log.d(BounceVideo.AD_CONTROLLER_TAG, "Ad initialization completed.");
            }
        });
    }
}
