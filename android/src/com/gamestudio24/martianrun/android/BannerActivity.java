package com.gamestudio24.martianrun.android;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.appsgeyser.sdk.AppsgeyserSDK;
import com.appsgeyser.sdk.ads.fastTrack.adapters.FastTrackBaseAdapter;
import com.appsgeyser.sdk.configuration.Constants;

/**
 * Created by roma on 20.02.2018.
 */

public class BannerActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        AppsgeyserSDK.getFastTrackAdsController().setFullscreenListener(new FastTrackBaseAdapter.FullscreenListener() {
            @Override
            public void onRequest() {
                //called after fullscreen banner request
            }

            @Override
            public void onShow() {
                //called after fullscreen banner has been shown
            }

            @Override
            public void onClose() {
                finish();
            }

            @Override
            public void onFailedToShow() {
                finish();
            }
        });

        AppsgeyserSDK.getFastTrackAdsController()
                .showFullscreen(Constants.BannerLoadTags.ON_START, this);

    }

    @Override
    public void onResume() {
        super.onResume();
        AppsgeyserSDK.onResume(this);
    }

    @Override
    public void onPause() {
        super.onPause();
        AppsgeyserSDK.onPause(this);
    }
}
