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

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.utils.Align;
import com.gamestudio24.martianrun.config.Config;
import com.gamestudio24.martianrun.config.ConfigLoader;
import com.gamestudio24.martianrun.utils.AssetsManager;
import com.gamestudio24.martianrun.utils.Constants;

public class GameLabel extends Actor {

    private Rectangle bounds;
    private BitmapFont font;

    public GameLabel(Rectangle bounds) {
        this.bounds = bounds;
        setWidth(bounds.width);
        setHeight(bounds.height);
        font = AssetsManager.getLargeFont();
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
        Config config = ConfigLoader.getConfig();
        if(config.getLogo().isEmpty()) {
            font.setColor(Color.valueOf(ConfigLoader.getConfig().getPrimaryColor()));
            font.draw(batch, ConfigLoader.getConfig().getAppName(), bounds.x, bounds.y, bounds.width, Align.center, true);
        }else {
            TextureRegion textureRegion = AssetsManager.getTextureRegion(Constants.LOGO_ASSETS_ID);
            batch.draw(textureRegion, 2.5f*Constants.WORLD_TO_SCREEN, 8*Constants.WORLD_TO_SCREEN,20*Constants.WORLD_TO_SCREEN,
                    5*Constants.WORLD_TO_SCREEN);
        }
    }

}
