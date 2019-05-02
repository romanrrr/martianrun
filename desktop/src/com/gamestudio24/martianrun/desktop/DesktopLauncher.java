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

package com.gamestudio24.martianrun.desktop;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.gamestudio24.martianrun.MartianRun;
import com.gamestudio24.martianrun.utils.Constants;
import com.gamestudio24.martianrun.utils.GameEventListener;
import com.gamestudio24.martianrun.utils.GameManager;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
        config.width = Constants.APP_WIDTH;
        config.height = Constants.APP_HEIGHT;
		new LwjglApplication(new MartianRun(new GameEventListener() {

            @Override
            public void submitScore(int score) {
                Gdx.app.log(GameEventListener.class.getSimpleName(), "submitScore");
            }

            @Override
            public void displayLeaderboard() {
                Gdx.app.log(GameEventListener.class.getSimpleName(), "displayLeaderboard");
            }

            @Override
            public void displayAchievements() {
                Gdx.app.log(GameEventListener.class.getSimpleName(), "displayAchievements");
            }

            @Override
            public void share(Integer score) {

            }

            @Override
            public void unlockAchievement(String id) {

            }

            @Override
            public void incrementAchievement(String id, int steps) {

            }

            @Override
            public void showFullscreenBanner(GameManager.FullscreenBannerClosedListener fullscreenBannerClosedListener) {

            }

            @Override
            public void isDialogEnabled(GameManager.AboutDialogEnabledListener aboutDialogEnabledListener) {

            }

            @Override
            public void showDialog() {

            }

        }), config);
	}
}
