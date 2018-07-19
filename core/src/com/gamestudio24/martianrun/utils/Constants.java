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

package com.gamestudio24.martianrun.utils;

import com.badlogic.gdx.math.Vector2;
import com.gamestudio24.martianrun.config.ConfigLoader;

public class Constants {


    public static final int APP_WIDTH = 800;
    public static final int APP_HEIGHT = 480;
    public static final float WORLD_TO_SCREEN = 32;

    public static final Vector2 WORLD_GRAVITY = new Vector2(0, -10);

    public static final float GROUND_X = 0;
    public static final float GROUND_Y = 0;
    public static final float GROUND_WIDTH = 25f;
    public static final float GROUND_HEIGHT = 2f;
    public static final float GROUND_DENSITY = 0f;

    public static final float RUNNER_X = 2;
    public static final float RUNNER_Y = GROUND_Y + GROUND_HEIGHT;
    public static final float RUNNER_WIDTH = 1f;
    public static final float RUNNER_HEIGHT = 2f;
    public static final float RUNNER_GRAVITY_SCALE = 3f;
    public static final float RUNNER_DENSITY = 0.5f;
    public static final float RUNNER_DODGE_X = 2f;
    public static final float RUNNER_DODGE_Y = 1.5f;
    public static final Vector2 RUNNER_JUMPING_LINEAR_IMPULSE = new Vector2(0, 13f);
    public static final float RUNNER_HIT_ANGULAR_IMPULSE = 10f;

    public static final float ENEMY_X = 25f;
    public static final float ENEMY_DENSITY = RUNNER_DENSITY;
    public static final float RUNNING_SHORT_ENEMY_Y = 1.5f;
    public static final float RUNNING_LONG_ENEMY_Y = 2f;
    public static final float FLYING_ENEMY_Y = 3f;
    public static final float COIN_Y = 5f;
    public static final Vector2 ENEMY_LINEAR_VELOCITY = new Vector2(-10f, 0);

    public static final String GROUND_ASSETS_ID = "ground";
    public static final String LOGO_ASSETS_ID = "logo";
    public static final String RUNNER_RUNNING_ASSETS_ID = "runner_running";
    public static final String RUNNER_DODGING_ASSETS_ID = "runner_dodging";
    public static final String RUNNER_HIT_ASSETS_ID = "runner_hit";
    public static final String RUNNER_JUMPING_ASSETS_ID = "runner_jumping";

    public static final String RUNNER_DODGING_HINT = "runner_dodging_hint";
    public static final String RUNNER_JUMPING_HINT = "runner_jumping_hint";

    public static final String COIN_ASSETS_ID = "coin_body";

    public static final String SOUND_ON_REGION_NAME = "sound_on";
    public static final String SOUND_OFF_REGION_NAME = "sound_off";
    public static final String MUSIC_ON_REGION_NAME = "music_on";
    public static final String MUSIC_OFF_REGION_NAME = "music_off";
    public static final String PAUSE_REGION_NAME = "pause";
    public static final String PLAY_REGION_NAME = "play";
    public static final String LEADERBOARD_REGION_NAME = "leaderboard";
    public static final String ABOUT_REGION_NAME = "about";
    public static final String CLOSE_REGION_NAME = "close";
    public static final String SHARE_REGION_NAME = "share";
    public static final String ACHIEVEMENTS_REGION_NAME = "star";

    public static final String TUTORIAL_BOX_NAME = "tutorial_box";
    public static final String TUTORIAL_LEFT_TEXT = "\nTap left to dodge";
    public static final String TUTORIAL_RIGHT_TEXT = "\nTap right to jump";



    public static final String ABOUT_TEXT = "Developed by: @gamestudio24\nPowered by: " +
            "@libgdx\nGraphics: @kenneywings\nMusic: @kmacleod";
    public static final String SHARE_MESSAGE_PREFIX = "Check out " + ConfigLoader.getConfig().getAppName() + " %s";
    public static final String SHARE_MESSAGE_PREFIX_SCORE = "I scored %d points in " + ConfigLoader.getConfig().getAppName() + ". Can you beat me? %s";
    public static final String SHARE_TITLE = "Share!";
    public static final String PAUSED_LABEL = "Paused";
    public static final String NEW_RECORD_LABEL = "New record!";
    public static final String RESULT_SCORES_LABEL = "Your scores: ";

}
