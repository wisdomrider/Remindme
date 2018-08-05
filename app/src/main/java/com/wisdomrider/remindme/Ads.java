package com.wisdomrider.remindme;

import android.content.Context;
import android.util.Log;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.reward.RewardItem;
import com.google.android.gms.ads.reward.RewardedVideoAd;
import com.google.android.gms.ads.reward.RewardedVideoAdListener;

public class Ads {
    Context context;
    private InterstitialAd mInterstitialAd;
    AdView bannerad;
    private RewardedVideoAd mRewardedVideoAd;


    public Ads(Context c) {
        this.context = c;
        initialize();
    }

    private void initialize() {
    }

    public void bannerAdLoad(AdView ad) {
        bannerad = ad;
        AdRequest adRequest = new AdRequest.Builder()
                .build();
        ad.loadAd(adRequest);
    }

    public InterstitialAd loadInterestial(String id) {
        mInterstitialAd = new InterstitialAd(context);
        mInterstitialAd.setAdUnitId(id);
        mInterstitialAd.setAdListener(new AdListener() {
            @Override
            public void onAdLoaded() {
                mInterstitialAd.show();
            }

            @Override
            public void onAdOpened() {
                Log.d("1", "Ad Opened");
            }
        });
        mInterstitialAd.loadAd(new AdRequest.Builder().build());
        return mInterstitialAd;
    }

    public RewardedVideoAd loadRewardedAds() {
        return mRewardedVideoAd;
    }


}
