package com.example.morgen.ad;

import android.content.Context;
import android.util.Log;

import com.example.morgen.BounceSimulator;
import com.example.morgen.BounceVideo;
import com.yandex.mobile.ads.common.AdRequest;
import com.yandex.mobile.ads.interstitial.InterstitialAd;
import com.yandex.mobile.ads.interstitial.InterstitialAdEventListener;

public class InterstitialAdManager extends AdController {
    private final Context context;
    private InterstitialAd mInterstitialAd;

    public InterstitialAdManager(Context context) {
        super(context);
        this.context = context;
    }

    public void loadInterstitialAd(String unit_id) {
        mInterstitialAd = new InterstitialAd(context);
        mInterstitialAd.setAdUnitId(unit_id);
        AdRequest adRequest = new AdRequest.Builder().build();
        try {
            mInterstitialAd.loadAd(adRequest);
        } catch (Throwable t) {
            Log.e(BounceVideo.AD_CONTROLLER_TAG, "", t);
        }
    }

    public InterstitialAd getInterstitialAd() {
        return this.mInterstitialAd;
    }

    public void showInterstitialAd() {
        mInterstitialAd.show();
    }

    public void testInterstitialAd() {
        InterstitialAd dInterstitialAd = new InterstitialAd(context);
        dInterstitialAd.setAdUnitId(BounceVideo.INTERSTITIAL_AD_TOKEN);

        AdRequest adRequest = new AdRequest.Builder().build();
        dInterstitialAd.loadAd(adRequest);
    }

    public void setAdListeners(InterstitialAdEventListener listener) {
        mInterstitialAd.setInterstitialAdEventListener(listener);
    }

}
