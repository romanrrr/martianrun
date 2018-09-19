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

package com.gamestudio24.martianrun.actors.menu;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.utils.Align;
import com.gamestudio24.martianrun.actors.Score;
import com.gamestudio24.martianrun.enums.GameState;
import com.gamestudio24.martianrun.utils.AssetsManager;
import com.gamestudio24.martianrun.utils.Constants;
import com.gamestudio24.martianrun.utils.GameManager;

public class ResultLabel extends Actor {

    private Rectangle bounds;
    private BitmapFont font;
    private Score score;
    private boolean newRecord;

    public ResultLabel(Rectangle bounds) {
        this.bounds = bounds;
        setWidth(bounds.width);
        setHeight(bounds.height);
        font = AssetsManager.getSmallFont();
    }

    public void setScore(Score score, boolean newRecord) {
        this.score = score;
        this.newRecord = newRecord;
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
        if (score != null && GameManager.getInstance().getGameState() == GameState.OVER) {

            StringBuilder stringBuilder = new StringBuilder();
            if(newRecord){
                stringBuilder.append(Constants.NEW_RECORD_LABEL).append("\n");
            }
            stringBuilder.append(Constants.RESULT_SCORES_LABEL).append(score.getScore()).append("\n");
            stringBuilder.append(Constants.RESULT_MAX_SCORES_LABEL).append(score.getMaxScore()).append("\n");

            font.draw(batch,stringBuilder , bounds.x, bounds.y, bounds.width, Align.center, true);
        }
    }

}
