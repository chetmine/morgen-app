package com.example.morgen.ad;

import android.content.Context;

import com.yandex.mobile.ads.common.AdRequest;
import com.yandex.mobile.ads.rewarded.RewardedAd;
import com.yandex.mobile.ads.rewarded.RewardedAdEventListener;

public class RewardedAdManger extends AdController {

    private RewardedAd rewardedAd;
    private Context context;

    public RewardedAdManger(Context context) {
        super(context);
        this.context = context;
    }

    public void loadRewardedAd(String unit_id) {
        rewardedAd = new RewardedAd(context);
        rewardedAd.setAdUnitId(unit_id);
        AdRequest adRequest = new AdRequest.Builder().build();
        rewardedAd.loadAd(adRequest);
    }

    public void showRewardedAd() {
        rewardedAd.show();
    }

    public void setRewardedAdListeners(RewardedAdEventListener listener) {
        rewardedAd.setRewardedAdEventListener(listener);
    }
}
