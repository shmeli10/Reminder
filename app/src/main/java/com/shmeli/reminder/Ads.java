package com.shmeli.reminder;

import android.app.Activity;
import android.view.View;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

/**
 * Created by Serghei Ostrovschi on 9/26/17.
 */

public class Ads {

    public static void showBanner(final Activity activity) {

        final AdView banner = (AdView) activity.findViewById(R.id.banner);

        AdRequest adRequest = new AdRequest.Builder().build();
        banner.loadAd(adRequest);

        banner.setAdListener(new AdListener() {
            @Override
            public void onAdLoaded() {
                super.onAdLoaded();
                setupContentViewPadding(activity,
                                        banner.getHeight());
            }
        });
    }

    public static void setupContentViewPadding(Activity activity,
                                               int      padding) {
        View view = activity.findViewById(R.id.coordinator);
        view.setPadding(view.getPaddingLeft(),
                        view.getPaddingTop(),
                        view.getPaddingRight(),
                        padding);
    }

//    static Activity mActivity   = null;
//    static AdRequest adRequest  = null;
//    static AdView banner        = null;
//
//    static AdListener adListener = new AdListener() {
//        @Override
//        public void onAdLoaded() {
//            super.onAdLoaded();
//
//            setupViewPadding(mActivity, banner.getHeight());
//        }
//    };
//
//    public static void showBanner(Activity activity) {
//
//        mActivity = activity;
//
//        adRequest = new AdRequest.Builder().build();
//
//        banner       = (AdView) activity.findViewById(R.id.banner);
//        banner.loadAd(adRequest);
//        banner.setAdListener(adListener);
//    }
//
//    public static void setupViewPadding(Activity    activity,
//                                        int         padding) {
//
//        View view = activity.findViewById(R.id.coordinator);
//        view.setPadding(view.getPaddingLeft(),
//                        view.getPaddingTop(),
//                        view.getPaddingRight(),
//                        padding);
//    }
}
