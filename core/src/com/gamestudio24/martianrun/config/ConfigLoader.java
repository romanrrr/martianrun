package com.gamestudio24.martianrun.config;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.utils.JsonReader;
import com.badlogic.gdx.utils.JsonValue;
import com.badlogic.gdx.utils.Logger;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by roma on 18.02.2017.
 */

public class ConfigLoader {
    public static Config config;

    public static Config getConfig() {
        if (config == null) {
            config = new Config();
            JsonReader json = new JsonReader();
            JsonValue base = json.parse(Gdx.files.internal("settings.json"));

            config.setAppName(base.getString("name"));
            config.setLogo(base.getString("logo"));

            JsonValue theme = base.get("themeColors");
            config.setPrimaryColor(theme.getString("colorPrimary").substring(1));
            config.setAccentColor(theme.getString("colorAccent").substring(1));

            List<String> runnerRunningAnimation = new ArrayList<String>();
            for (JsonValue wrap : base.get("runnerRunningAnimationImage")) {
                for (JsonValue item : wrap.get("runnerRunningAnimation")) {
                    runnerRunningAnimation.add(item.getString("path"));
                }
            }
            config.setRunnerRunningAnimation(runnerRunningAnimation);

            List<String> runnerJumpAnimation = new ArrayList<String>();
            for (JsonValue wrap : base.get("runnerJumpAnimationImage")) {
                for (JsonValue item : wrap.get("runnerJumpAnimation")) {
                    runnerJumpAnimation.add(item.getString("path"));
                }
            }
            config.setRunnerJumpAnimation(runnerJumpAnimation);

            List<String> runnerSlideAnimation = new ArrayList<String>();
            for (JsonValue wrap : base.get("runnerSlideAnimationImage")) {
                for (JsonValue item : wrap.get("runnerSlideAnimation")) {
                    runnerSlideAnimation.add(item.getString("path"));
                }
            }
            config.setRunnerSlideAnimation(runnerSlideAnimation);

            List<String> coinAnimation = new ArrayList<String>();
            for (JsonValue wrap : base.get("coinAnimationImage")) {
                for (JsonValue item : wrap.get("coinAnimation")) {
                    coinAnimation.add(item.getString("path"));
                }
            }
            config.setCoinAnimation(coinAnimation);

            List<String> runnerDeadAnimation = new ArrayList<String>();
            for (JsonValue wrap : base.get("runnerDeadAnimationImage")) {
                for (JsonValue item : wrap.get("runnerDeadAnimation")) {
                    runnerDeadAnimation.add(item.getString("path"));
                }
            }
            config.setRunnerDeadAnimation(runnerDeadAnimation);

            List<ConfigEnemy> configEnemyList = new ArrayList<ConfigEnemy>();
            int id = 0;
            for (JsonValue item : base.get("enemies")) {
                ConfigEnemy configEnemy = new ConfigEnemy();
                configEnemy.setId("enemy" + id++);
                configEnemy.setHeight(item.getInt("enemyHeight"));
                configEnemy.setWidth(item.getInt("enemyWidth"));
                configEnemy.setYindex(item.getFloat("enemyYIndex") + 0.5f);

                List<String> animation = new ArrayList<String>();
                for (JsonValue animationItem : item.get("animation")) {
                    animation.add(animationItem.getString("path"));
                }
                configEnemy.setAnimation(animation);
                configEnemyList.add(configEnemy);
            }
            config.setConfigEnemies(configEnemyList);

            List<ConfigBackground> configBackgroundList = new ArrayList<ConfigBackground>();
            id = 0;
            for (JsonValue item : base.get("backgroundLayers")) {
                ConfigBackground configBackground = new ConfigBackground();
                configBackground.setId("background" + id++);
                configBackground.setLayerPath(item.getString("layerImage"));
                configBackground.setSpeedPercentage(item.getInt("speedPercentage"));
                configBackgroundList.add(configBackground);
            }
            config.setConfigBackgroundList(configBackgroundList);

            if (!base.getString("coinSound").isEmpty()) {
                config.setCoinSound(base.getString("coinSound"));
            }
            if (!base.getString("jumpSound").isEmpty()) {
                config.setJumpSound(base.getString("jumpSound"));
            }
            if (!base.getString("hitSound").isEmpty()) {
                config.setHitSound(base.getString("hitSound"));
            }
            if (!base.getString("music").isEmpty()) {
                config.setMusic(base.getString("music"));
            }

            config.setGroundImage(base.getString("groundImage"));
            if (!base.getString("font").isEmpty()) {
                config.setFont(base.getString("font"));
            }


            JsonValue defaultValues = base.get("default");
            ConfigButton startButton = new ConfigButton();
            startButton.setX(12.5f);
            startButton.setY(4f);
            startButton.setWidth(5);
            startButton.setHeight(5);
            startButton.setImagePath(defaultValues.getString("startButtonImage"));
            config.setStartButton(startButton);

            ConfigButton shareButton = new ConfigButton();
            shareButton.setX(17f);
            shareButton.setY(4f);
            shareButton.setWidth(3f);
            shareButton.setHeight(3f);
            shareButton.setImagePath(defaultValues.getString("shareButtonImage"));
            config.setShareButton(shareButton);


            ConfigButton exitButton = new ConfigButton();
            exitButton.setX(8f);
            exitButton.setY(4f);
            exitButton.setWidth(3f);
            exitButton.setHeight(3f);
            exitButton.setImagePath(defaultValues.getString("exitButtonImage"));
            config.setExitButton(exitButton);

            ConfigButton musicButton = new ConfigButton();
            musicButton.setX(1f);
            musicButton.setY(11.5f);
            musicButton.setWidth(1.5f);
            musicButton.setHeight(1.5f);
            musicButton.setImagePath(defaultValues.getString("musicButtonImage"));
            config.setMusicButton(musicButton);

            ConfigButton soundButton = new ConfigButton();
            soundButton.setX(1f);
            soundButton.setY(9.5f);
            soundButton.setWidth(1.5f);
            soundButton.setHeight(1.5f);
            soundButton.setImagePath(defaultValues.getString("soundButtonImage"));
            soundButton.setClickedImagePath(defaultValues.getString("soundOffButtonImage"));
            config.setSoundButton(soundButton);




            ConfigButton pauseButton = new ConfigButton();
            pauseButton.setX(1f);
            pauseButton.setY(7.5f);
            pauseButton.setWidth(1.5f);
            pauseButton.setHeight(1.5f);
            pauseButton.setImagePath(defaultValues.getString("playButtonImage"));
            pauseButton.setClickedImagePath(defaultValues.getString("pauseButtonImage"));
            config.setPauseButton(pauseButton);


            ConfigButton aboutButton = new ConfigButton();
            aboutButton.setX(1f);
            aboutButton.setY(13.5f);
            aboutButton.setWidth(1.5f);
            aboutButton.setHeight(1.5f);
            aboutButton.setImagePath(defaultValues.getString("aboutButtonImage"));
            config.setAboutButton(aboutButton);

        }
        return config;
    }
}
