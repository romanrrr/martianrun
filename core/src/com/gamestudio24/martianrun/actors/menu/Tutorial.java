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
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.actions.SequenceAction;
import com.badlogic.gdx.utils.Align;
import com.gamestudio24.martianrun.enums.GameState;
import com.gamestudio24.martianrun.utils.AssetsManager;
import com.gamestudio24.martianrun.utils.GameManager;

public class Tutorial extends Actor {

    private TextureRegion obstacleTextureRegion;
    private TextureRegion runnerTextureRegion;
    private Rectangle bounds;
    private BitmapFont font;
    private String text;
    private boolean jump;

    public Tutorial(Rectangle bounds, String obstacleAssetId, String playerAssetId,String text, boolean jump) {
        this.bounds = bounds;
        this.jump = jump;
        this.text = text;
        obstacleTextureRegion = AssetsManager.getTextureRegion(obstacleAssetId);
        runnerTextureRegion = AssetsManager.getTextureRegion(playerAssetId);
        SequenceAction sequenceAction = new SequenceAction();
        sequenceAction.addAction(Actions.delay(4f));
        sequenceAction.addAction(Actions.removeActor());
        addAction(sequenceAction);
        font = AssetsManager.getSmallestFont();
        setWidth(bounds.width);
        setHeight(bounds.height);
    }

    @Override
    public void act(float delta) {
        super.act(delta);
        if (GameManager.getInstance().getGameState() == GameState.OVER) {
            remove();
        }
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
        float unit = bounds.width / 4;
        if(jump) {
            batch.draw(obstacleTextureRegion, bounds.x + unit * 2, bounds.y, unit * 2, unit);
            batch.draw(runnerTextureRegion, bounds.x + unit * 1.5f, bounds.y + unit * 1.5f, unit, unit * 1.5f);
        }else {
            batch.draw(obstacleTextureRegion, bounds.x + unit, bounds.y + unit, unit * 2, unit);
            batch.draw(runnerTextureRegion, bounds.x + unit, bounds.y, unit * 2, unit);
        }
        font.draw(batch, text, bounds.x, bounds.y, bounds.width, Align.center, true);
    }
}
