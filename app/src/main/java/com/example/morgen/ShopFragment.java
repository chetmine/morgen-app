package com.example.morgen;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.example.morgen.ad.RewardedAdManger;
import com.example.morgen.db.DBManager;
import com.example.morgen.design.Design;
import com.yandex.mobile.ads.common.AdRequestError;
import com.yandex.mobile.ads.common.ImpressionData;
import com.yandex.mobile.ads.rewarded.Reward;
import com.yandex.mobile.ads.rewarded.RewardedAdEventListener;


public class ShopFragment extends Fragment {
    ImageButton longVideoStatusButton;
    ImageButton shortVideoStatusButton;
    ImageView longVideoView;
    ImageView shortVideoView;
    ImageButton showAdButton;
    RewardedAdManger rewardedAdManger;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        rewardedAdManger = new RewardedAdManger(getActivity());

        shortVideoView = getView().findViewById(R.id.shortvid);
        longVideoView = getView().findViewById(R.id.longvid);
        showAdButton = getView().findViewById(R.id.watch_ad_button);
        longVideoStatusButton = getView().findViewById(R.id.long_video_status_button);
        shortVideoStatusButton = getView().findViewById(R.id.short_video_status_button);

        Design long_video_design = new Design(longVideoView, longVideoStatusButton);
        Design short_video_design = new Design(shortVideoView, shortVideoStatusButton);

        if ((!MainMenu.dbManager.getFullVideoUnlockStatus().get(0))) {
            long_video_design.setLockedDesign();
        } else {
            long_video_design.setUnlockedDesign();
        }

        if (!(BounceVideo.isLongVideo)) {
            short_video_design.setAppliedDesign();
            if ((!MainMenu.dbManager.getFullVideoUnlockStatus().get(0))) {
                long_video_design.setLockedDesign();
            } else {
                long_video_design.setUnlockedDesign();
            }
        } else {
            short_video_design.setUnlockedDesign();
            if ((!MainMenu.dbManager.getFullVideoUnlockStatus().get(0))) {
                long_video_design.setLockedDesign();
            } else {
                long_video_design.setAppliedDesign();
            }
        }

        longVideoStatusButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int balance = MainMenu.dbManager.getValues().get(0);
                boolean status = MainMenu.dbManager.getFullVideoUnlockStatus().get(0);

                if ((!status)) {
                    if (balance >= 100000) {
                        balance = balance - 100000;
                        MainMenu.dbManager.updateValues(balance);
                        MainMenu.dbManager.setFullVideoUnlockedStatus(true);
                        long_video_design.setUnlockedDesign();
                    }
                } else {
                    BounceVideo.isLongVideo = true;
                    short_video_design.setUnlockedDesign();
                    if ((!MainMenu.dbManager.getFullVideoUnlockStatus().get(0))) {
                        long_video_design.setLockedDesign();
                    } else {
                        long_video_design.setAppliedDesign();
                    }
                }
            }
        });

        shortVideoStatusButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                BounceVideo.isLongVideo = false;
                short_video_design.setAppliedDesign();
                if ((!MainMenu.dbManager.getFullVideoUnlockStatus().get(0))) {
                    long_video_design.setLockedDesign();
                } else {
                    long_video_design.setUnlockedDesign();
                }
            }
        });
        showAdButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                rewardedAdManger.loadRewardedAd(BounceVideo.REWARDED_AD_TOKEN);
//                rewardedAdManger.setRewardedAdListeners(createRewardedAdListeners());
            }
        });

    }
    private RewardedAdEventListener createRewardedAdListeners() {
        RewardedAdEventListener listener = new RewardedAdEventListener() {
            @Override
            public void onAdLoaded() {
//                rewardedAdManger.showRewardedAd();
            }

            @Override
            public void onAdFailedToLoad(@NonNull AdRequestError adRequestError) {
                showAdNotLoadedError(getActivity());
            }

            @Override
            public void onAdShown() {

            }

            @Override
            public void onAdDismissed() {

            }

            @Override
            public void onRewarded(@NonNull Reward reward) {
                DBManager dbManager = MainMenu.dbManager;
                int currentBounce = dbManager.getValues().get(0);
                currentBounce = currentBounce + 45000;
                dbManager.updateValues(currentBounce);
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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_shop, container, false);
    }

    private void showAdNotLoadedError(Context context) {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(context);
        alertDialog.setTitle("Ошибка")
                .setIcon(R.drawable.app_logo)
                .setMessage("Не удалось отобразить рекламу")
                .setPositiveButton("ОК", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.cancel();
                    }
                });
        alertDialog.create();
        alertDialog.show();
    }
}