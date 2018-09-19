/*
 * Copyright (c) 2014. William Mora
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.gamestudio24.martianrun.android;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.RelativeLayout;

import com.appsgeyser.sdk.AppsgeyserSDK;
import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;
import com.gamestudio24.martianrun.MartianRun;
import com.gamestudio24.martianrun.utils.Constants;
import com.gamestudio24.martianrun.utils.GameEventListener;
import com.gamestudio24.martianrun.utils.GameManager;


public class AndroidLauncher extends AndroidApplication implements
        GameEventListener {

    private static String SAVED_LEADERBOARD_REQUESTED = "SAVED_LEADERBOARD_REQUESTED";
    private static String SAVED_ACHIEVEMENTS_REQUESTED = "SAVED_ACHIEVEMENTS_REQUESTED";

    //private GameHelper gameHelper;

    RelativeLayout adView;

    private Boolean isDialogEnabled;

    private boolean mLeaderboardRequested;
    private boolean mAchievementsRequested;
    GameManager.FullscreenBannerClosedListener fullscreenBannerClosedListener;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Create the layout
        RelativeLayout layout = new RelativeLayout(this);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getWindow().clearFlags(
                WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN);

        AndroidApplicationConfiguration config = new AndroidApplicationConfiguration();

        AppsgeyserSDK.takeOff(this,
                getString(R.string.widgetID),
                getString(R.string.app_metrica_on_start_event),
                getString(R.string.template_version));


        // Game view
        View gameView = initializeForView(new MartianRun(this), config);
        adView = createAdView();

        layout.addView(gameView);
        layout.addView(adView);

        setContentView(layout);



       /* gameHelper = new GameHelper(this, GameHelper.CLIENT_GAMES);
        gameHelper.setup(this);
        gameHelper.setMaxAutoSignInAttempts(0);*/
    }




    @Override
    public void onResume() {
        super.onResume();
        AppsgeyserSDK.getFastTrackAdsController().setBannerViewContainer(adView);
        AppsgeyserSDK.onResume(this);
    }

    @Override
    public void onPause() {
        super.onPause();
        AppsgeyserSDK.onPause(this);
    }




    @Override
    protected void onStart() {
        super.onStart();
        //gameHelper.onStart(this);
       // GoogleAnalytics.getInstance(this).reportActivityStart(this);
    }

    @Override
    protected void onStop() {
        super.onStop();
       // gameHelper.onStop();
        //GoogleAnalytics.getInstance(this).reportActivityStop(this);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 999){
            fullscreenBannerClosedListener.onBannerClosed();
        }

        //gameHelper.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBoolean(SAVED_LEADERBOARD_REQUESTED, mLeaderboardRequested);
        outState.putBoolean(SAVED_ACHIEVEMENTS_REQUESTED, mAchievementsRequested);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        mLeaderboardRequested = savedInstanceState.getBoolean(SAVED_LEADERBOARD_REQUESTED, false);
        mAchievementsRequested = savedInstanceState.getBoolean(SAVED_ACHIEVEMENTS_REQUESTED, false);
    }

    private RelativeLayout createAdView() {

        adView = new RelativeLayout(this, null);
        final float scale = getResources().getDisplayMetrics().density;
        int dpHeightInPx = (int) (50 * scale);//50dp
        int dpWidthInPx = (int) (320 * scale);//50dp
        adView.setId(R.id.ad_view); // this is an arbitrary id, allows for relative
        // positioning in createGameView()
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(
                dpWidthInPx, dpHeightInPx);
        params.addRule(RelativeLayout.CENTER_HORIZONTAL);
//        params.gravity = Gravity.BOTTOM;
        params.addRule(RelativeLayout.ALIGN_PARENT_TOP);

        adView.setLayoutParams(params);
        adView.setBackgroundColor(Color.TRANSPARENT);
        return adView;
    }

    private RelativeLayout.LayoutParams getAdParams() {
        RelativeLayout.LayoutParams adParams = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.MATCH_PARENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT);

        adParams.addRule(RelativeLayout.ALIGN_PARENT_TOP);

        return adParams;
    }

    @Override
    public void submitScore(int score) {

    }

    public void isDialogEnabled(final GameManager.AboutDialogEnabledListener aboutDialogEnabledListener){
        if(isDialogEnabled == null) {
            AppsgeyserSDK.isAboutDialogEnabled(this, new AppsgeyserSDK.OnAboutDialogEnableListener() {
                @Override
                public void onDialogEnableReceived(boolean enabled) {
                    isDialogEnabled = enabled;
                    aboutDialogEnabledListener.onAboutEnabled(enabled);
                }
            });
        }else {
            aboutDialogEnabledListener.onAboutEnabled(isDialogEnabled);
        }
    }

    public void showDialog(){
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                AppsgeyserSDK.showAboutDialog(AndroidLauncher.this);
            }
        });
    }

    public void showFullscreenBanner(final GameManager.FullscreenBannerClosedListener fullscreenBannerClosedListener){
        this.fullscreenBannerClosedListener = fullscreenBannerClosedListener;

        Intent intent = new Intent(this, BannerActivity.class);
        startActivityForResult(intent, 999);
    }

    @Override
    public void displayLeaderboard() {
       /* if (gameHelper.isSignedIn()) {
            startActivityForResult(Games.Leaderboards.getLeaderboardIntent(gameHelper.getApiClient(),
                    getString(R.string.leaderboard_high_scores)), 24);
        } else {*/
            //gameHelper.beginUserInitiatedSignIn();
           // mLeaderboardRequested = true;
        //}
    }

    @Override
    public void displayAchievements() {
      /*  if (gameHelper.isSignedIn()) {
            startActivityForResult(
                    Games.Achievements.getAchievementsIntent(gameHelper.getApiClient()), 25);
        } else {
            gameHelper.beginUserInitiatedSignIn();
            mAchievementsRequested = true;
        }*/
    }

    @Override
    public void share(Integer score) {
        String url = String.format("http://play.google.com/store/apps/details?id=%s",
                getApplicationContext().getPackageName());
        String message;
        if(score == null) {
            message = String.format(com.gamestudio24.martianrun.utils.Constants.SHARE_MESSAGE_PREFIX, url);
        }else {
            message = String.format(Constants.SHARE_MESSAGE_PREFIX_SCORE, score, url);
        }
        Intent share = new Intent(Intent.ACTION_SEND);
        share.setType("text/plain");
        share.putExtra(Intent.EXTRA_TEXT, message);
        startActivity(Intent.createChooser(share, com.gamestudio24.martianrun.utils.Constants.SHARE_TITLE));
    }

    @Override
    public void unlockAchievement(String id) {
      /*  if (gameHelper.isSignedIn()) {
            Games.Achievements.unlock(gameHelper.getApiClient(), id);
            GameManager.getInstance().setAchievementUnlocked(id);
        }*/
    }

    @Override
    public void incrementAchievement(String id, int steps) {
       /* if (gameHelper.isSignedIn()) {
            Games.Achievements.increment(gameHelper.getApiClient(), id, steps);
            GameManager.getInstance().incrementAchievementCount(id, steps);
        }*/
    }



}
