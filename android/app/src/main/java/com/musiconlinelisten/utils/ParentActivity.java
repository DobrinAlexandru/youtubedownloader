package com.musiconlinelisten.utils;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;

/**
 * Created by alexd on 05/08/2017.
 */

public class ParentActivity extends Activity {
    private com.google.android.gms.ads.InterstitialAd ecran;
    private boolean showMain =  false;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ecran =  new com.google.android.gms.ads.InterstitialAd(this);
        AdRequest req  = new AdRequest.Builder().addTestDevice("F62F3270864A170CC9D2168FE2452C87").build();

//        ecran.setAdUnitId(Preferences.getStringValue("int"));
        ecran.setAdUnitId(Preferences.getStringValue("int"));
        ecran.loadAd(req);
        ecran.setAdListener(new AdListener() {
            @Override
            public void onAdClosed() {
                super.onAdClosed();
                dissapear();
            }

            @Override
            public void onAdFailedToLoad(int i) {
                super.onAdFailedToLoad(i);
            }

            @Override
            public void onAdLeftApplication() {
                super.onAdLeftApplication();
            }

            @Override
            public void onAdOpened() {
                super.onAdOpened();
            }

            @Override
            public void onAdLoaded() {
                super.onAdLoaded();
                appear();
            }
        });
    }

    private void dissapear(){
        BabyActivity.show(this);
    }

    private void appear(){
        new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
            @Override
            public void run() {
                if (ecran.isLoaded()) {
                    ecran.show();
                } else {
                    dissapear();
                }
            }
        },  3000);
    }
}
