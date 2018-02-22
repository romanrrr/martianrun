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

package com.gamestudio24.martianrun.actors;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.gamestudio24.martianrun.config.ConfigLoader;
import com.gamestudio24.martianrun.enums.GameState;
import com.gamestudio24.martianrun.utils.AssetsManager;
import com.gamestudio24.martianrun.utils.GameManager;

public class Score extends Actor {

    private float score;
    private float maxScore;
    private int multiplier;
    private Rectangle bounds;
    private Rectangle multiplierBounds;
    private BitmapFont font;

    public Score(Rectangle bounds, Rectangle multiplierBounds) {
        this.bounds = bounds;
        this.multiplierBounds = multiplierBounds;
        setWidth(bounds.width);
        setHeight(bounds.height);
        score = 0;
        multiplier = 1;
        font = AssetsManager.getSmallestFont();
    }

    @Override
    public void act(float delta) {
        super.act(delta);
        if (GameManager.getInstance().getGameState() != GameState.RUNNING) {
            return;
        }
        score += multiplier * delta * 5;
        if(maxScore < score){
            maxScore = score;
        }
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
        if (getScore() == 0 || GameManager.getInstance().getGameState() == GameState.OVER) {
            return;
        }
        font.drawWrapped(batch, String.format("Max score: %d", getMaxScore()), multiplierBounds.x, multiplierBounds.y, multiplierBounds.width, BitmapFont.HAlignment.RIGHT);
        font.drawWrapped(batch, String.format("Score: %d", getScore()), bounds.x, bounds.y, bounds.width, BitmapFont.HAlignment.RIGHT);
    }

    public int getScore() {
        return (int) Math.floor(score);
    }

    public int getMaxScore() {
        return (int) Math.floor(maxScore);
    }

    public void setMaxScore(float maxScore) {
        this.maxScore = maxScore;
    }

    public void setMultiplier(int multiplier) {
        this.multiplier = multiplier;
    }

    public int getMultiplier() {
        return multiplier;
    }
}
