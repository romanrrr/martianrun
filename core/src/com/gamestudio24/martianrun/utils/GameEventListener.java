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

/**
 * Game events that are platform specific (i.e. submitting a score or displaying an ad inn an
 * Android app is different than doing the same in a desktop app).
 */
public interface GameEventListener {


    /**
     * Submits a given score. Used every time the game is over
     *
     * @param score
     */
    public void submitScore(int score);

    /**
     * Displays the scores leaderboard
     */
    public void displayLeaderboard();

    /**
     * Displays the game's achievements
     */
    public void displayAchievements();

    /**
     * Shares the game's website
     */
    public void share(Integer score);

    /**
     * Unlocks an achievement with the given ID
     *
     * @param id achievement ID
     * @see <a href="https://developers.google.com/games/services/">Google Play Game Services</a>
     */
    public void unlockAchievement(String id);

    /**
     * Increments an achievement with the given ID
     *
     * @param id    achievement ID
     * @param steps incremental steps
     * @see <a href="https://developers.google.com/games/services/">Google Play Game Services</a>
     */
    public void incrementAchievement(String id, int steps);

    /**
     * The following are getters for specific achievement IDs used in this game
     */

    void showFullscreenBanner(GameManager.FullscreenBannerClosedListener fullscreenBannerClosedListener);

    void isDialogEnabled(final GameManager.AboutDialogEnabledListener aboutDialogEnabledListener);

    void showDialog();
}
