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

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.gamestudio24.martianrun.config.Config;
import com.gamestudio24.martianrun.config.ConfigBackground;
import com.gamestudio24.martianrun.config.ConfigEnemy;
import com.gamestudio24.martianrun.config.ConfigLoader;

import java.util.HashMap;
import java.util.List;

public class AssetsManager {

    private static HashMap<String, TextureRegion> texturesMap = new HashMap<String, TextureRegion>();
    private static HashMap<String, Animation> animationsMap = new HashMap<String, Animation>();
    private static BitmapFont smallFont;
    private static BitmapFont smallestFont;
    private static BitmapFont largeFont;

    private AssetsManager() {

    }

    public static void loadAssets() {

        // Background
        Config config = ConfigLoader.getConfig();

        for (ConfigBackground configBackground: config.getConfigBackgroundList()){
            texturesMap.put(configBackground.getId(),
                    new TextureRegion(new Texture(Gdx.files.internal(configBackground.getLayerPath()))));
        }
        // Ground
        Texture ground=new Texture(Gdx.files.internal(config.getGroundImage()));
        ground.setWrap(Texture.TextureWrap.Repeat, Texture.TextureWrap.Repeat);
        TextureRegion  textureRegion=new TextureRegion(ground, (int) (ground.getWidth() * Constants.GROUND_WIDTH),ground.getHeight() * 2);

        texturesMap.put(Constants.GROUND_ASSETS_ID,textureRegion);

        if(!config.getLogo().isEmpty()) {
            Texture logo = new Texture(Gdx.files.internal(config.getLogo()));
            TextureRegion logoTextureRegion = new TextureRegion(logo);
            texturesMap.put(Constants.LOGO_ASSETS_ID, logoTextureRegion);
        }

        // Runner
       /* texturesMap.put(Constants.RUNNER_JUMPING_ASSETS_ID,
                textureAtlas.findRegion(Constants.RUNNER_JUMPING_REGION_NAME));*/

        animationsMap.put(Constants.RUNNER_RUNNING_ASSETS_ID, createAnimation(config.getRunnerRunningAnimation()));
        animationsMap.put(Constants.RUNNER_JUMPING_ASSETS_ID, createAnimation(config.getRunnerJumpAnimation()));
        animationsMap.put(Constants.RUNNER_DODGING_ASSETS_ID, createAnimation(config.getRunnerSlideAnimation()));
        animationsMap.put(Constants.RUNNER_HIT_ASSETS_ID, createAnimation(config.getRunnerDeadAnimation()));

        for (ConfigEnemy configEnemy: config.getConfigEnemies()) {
            animationsMap.put(configEnemy.getId(), createAnimation(configEnemy.getAnimation()));
        }

        animationsMap.put(Constants.COIN_ASSETS_ID, createAnimation(config.getCoinAnimation()));
        // Tutorial
        texturesMap.put(Constants.TUTORIAL_BOX_NAME,
                new TextureRegion(new Texture(Gdx.files.internal("2box.png"))));

        Texture runnerSlideHint=new Texture(Gdx.files.internal(ConfigLoader.getConfig().getRunnerSlideAnimation().get(ConfigLoader.getConfig().getRunnerSlideAnimation().size() -1)));
        runnerSlideHint.setWrap(Texture.TextureWrap.ClampToEdge, Texture.TextureWrap.ClampToEdge);
        TextureRegion  runnerSlideHintTextureRegion=new TextureRegion(runnerSlideHint);
        texturesMap.put(Constants.RUNNER_DODGING_HINT, runnerSlideHintTextureRegion);

        Texture runnerJumpHint=new Texture(Gdx.files.internal(ConfigLoader.getConfig().getRunnerJumpAnimation().get(ConfigLoader.getConfig().getRunnerJumpAnimation().size() -1)));
        runnerJumpHint.setWrap(Texture.TextureWrap.ClampToEdge, Texture.TextureWrap.ClampToEdge);
        TextureRegion  runnerJumpHintTextureRegion=new TextureRegion(runnerJumpHint);
        texturesMap.put(Constants.RUNNER_JUMPING_HINT, runnerJumpHintTextureRegion);


        // Fonts
        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal(config.getFont()));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = 36;
        smallFont = generator.generateFont(parameter);
        smallFont.setColor(Color.valueOf(ConfigLoader.getConfig().getAccentColor()));

        parameter.size = 72;
        largeFont = generator.generateFont(parameter);
        largeFont.setColor(Color.valueOf(ConfigLoader.getConfig().getPrimaryColor()));

        parameter.size = 24;
        smallestFont = generator.generateFont(parameter);
        smallestFont.setColor(Color.valueOf(ConfigLoader.getConfig().getAccentColor()));

        generator.dispose();

    }

    public static TextureRegion getTextureRegion(String key) {
        return texturesMap.get(key);
    }

    public static Animation getAnimation(String key) {
        return animationsMap.get(key);
    }

    private static Animation createAnimation(List<String> texturePaths) {

        TextureRegion[] runningFrames = new TextureRegion[texturePaths.size()];

        for (int i = 0; i < texturePaths.size(); i++) {
            String path = texturePaths.get(i);
            Texture texture=new Texture(Gdx.files.internal(path));
            texture.setWrap(Texture.TextureWrap.Repeat, Texture.TextureWrap.Repeat);
            TextureRegion textureRegion=new TextureRegion(texture, texture.getWidth(), texture.getHeight());
            runningFrames[i] = textureRegion;
        }
        return new Animation(0.05f, runningFrames);

    }

    private static Animation createAnimation(TextureAtlas textureAtlas, String[] regionNames) {

        TextureRegion[] runningFrames = new TextureRegion[regionNames.length];

        for (int i = 0; i < regionNames.length; i++) {
            String path = regionNames[i];
            runningFrames[i] = textureAtlas.findRegion(path);
        }

        return new Animation(0.1f, runningFrames);

    }

    public static BitmapFont getSmallFont() {
        return smallFont;
    }

    public static BitmapFont getLargeFont() {
        return largeFont;
    }

    public static BitmapFont getSmallestFont() {
        return smallestFont;
    }

    public static void dispose() {
        smallestFont.dispose();
        smallFont.dispose();
        largeFont.dispose();
        texturesMap.clear();
        animationsMap.clear();
    }
}
