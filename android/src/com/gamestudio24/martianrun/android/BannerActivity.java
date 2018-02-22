package com.gamestudio24.martianrun.android;

import android.content.Context;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.appsgeyser.sdk.AppsgeyserSDK;
import com.appsgeyser.sdk.ads.FullScreenBanner;
import com.appsgeyser.sdk.ads.IFullScreenBannerListener;
import com.appsgeyser.sdk.configuration.Constants;

/**
 * Created by roma on 20.02.2018.
 */

public class BannerActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        FullScreenBanner fullScreenBanner = AppsgeyserSDK
                .getFullScreenBanner(this);
        fullScreenBanner.setContext(this);
        fullScreenBanner.setListener(new IFullScreenBannerListener() {
            @Override
            public void onLoadStarted() {

            }

            @Override
            public void onLoadFinished(FullScreenBanner fullScreenBanner) {
                fullScreenBanner.show();
            }

            @Override
            public void onAdFailedToLoad(Context context, String s) {
                finish();
            }

            @Override
            public void onAdHided(Context context, String s) {
                finish();
            }
        });
        fullScreenBanner.load(Constants.BannerLoadTags.ON_START);
    }
}
