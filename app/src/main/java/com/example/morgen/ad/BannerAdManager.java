package com.example.morgen.ad;

import android.content.Context;
import android.util.Log;

import com.example.morgen.BounceVideo;
import com.example.morgen.MainMenu;
import com.example.morgen.ShopFragment;
import com.yandex.mobile.ads.banner.AdSize;
import com.yandex.mobile.ads.banner.BannerAdView;
import com.yandex.mobile.ads.common.AdRequest;

public class BannerAdManager extends AdController {

    public BannerAdManager(Context context) {
        super(context);
    }

    public void loadBannerAd(BannerAdView ad, AdSize size, String unit_id) {
        ad.setAdSize(size);
        ad.setAdUnitId(unit_id);

        AdRequest adRequest = new AdRequest.Builder().build();

        try {
            ad.loadAd(adRequest);
        } catch (Throwable t) {
            Log.e(BounceVideo.AD_CONTROLLER_TAG, "", t);
        }
    }
}
