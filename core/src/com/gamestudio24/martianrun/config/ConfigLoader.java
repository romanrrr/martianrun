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
            for (JsonValue item : base.get("runnerRunningAnimation")) {
                runnerRunningAnimation.add(item.getString("path"));
            }
            config.setRunnerRunningAnimation(runnerRunningAnimation);

            List<String> runnerJumpAnimation = new ArrayList<String>();
            for (JsonValue item : base.get("runnerJumpAnimation")) {
                runnerJumpAnimation.add(item.getString("path"));
            }
            config.setRunnerJumpAnimation(runnerJumpAnimation);

            List<String> runnerSlideAnimation = new ArrayList<String>();
            for (JsonValue item : base.get("runnerSlideAnimation")) {
                runnerSlideAnimation.add(item.getString("path"));
            }
            config.setRunnerSlideAnimation(runnerSlideAnimation);

            List<String> coinAnimation = new ArrayList<String>();
            for (JsonValue item : base.get("coinAnimation")) {
                coinAnimation.add(item.getString("path"));
            }
            config.setCoinAnimation(coinAnimation);

            List<String> runnerDeadAnimation = new ArrayList<String>();
            for (JsonValue item : base.get("runnerDeadAnimation")) {
                runnerDeadAnimation.add(item.getString("path"));
            }
            config.setRunnerDeadAnimation(runnerDeadAnimation);

            List<ConfigEnemy> configEnemyList = new ArrayList<ConfigEnemy>();
            for (JsonValue item : base.get("enemies")) {
                ConfigEnemy configEnemy = new ConfigEnemy();
                configEnemy.setId(item.getString("id"));
                configEnemy.setHeight(item.getInt("height"));
                configEnemy.setWidth(item.getInt("width"));
                configEnemy.setYindex(item.getFloat("y-index") + 0.5f);

                List<String> animation = new ArrayList<String>();
                for (JsonValue animationItem : item.get("animation")) {
                    animation.add(animationItem.getString("path"));
                }
                configEnemy.setAnimation(animation);
                configEnemyList.add(configEnemy);
            }
            config.setConfigEnemies(configEnemyList);

            List<ConfigBackground> configBackgroundList = new ArrayList<ConfigBackground>();
            for (JsonValue item : base.get("backgroundLayers")) {
                ConfigBackground configBackground = new ConfigBackground();
                configBackground.setId(item.getString("id"));
                configBackground.setLayerPath(item.getString("path"));
                configBackground.setSpeedPercentage(item.getInt("speedPercentage"));
                configBackgroundList.add(configBackground);
            }
            config.setConfigBackgroundList(configBackgroundList);

            config.setCoinSound(base.getString("coinSound"));
            config.setJumpSound(base.getString("jumpSound"));
            config.setHitSound(base.getString("hitSound"));
            config.setMusic(base.getString("music"));

            config.setHintLeft(base.getString("hintLeft"));
            config.setHintRight(base.getString("hintRight"));
            config.setGroundImage(base.getString("groundImage"));
            config.setFont(base.getString("font"));


            config.setStartButton(readButton(base.get("startButton")));
            config.setMusicButton(readButton(base.get("musicButton")));
            config.setSoundButton(readButton(base.get("soundButton")));
            config.setShareButton(readButton(base.get("shareButton")));
            config.setPauseButton(readButton(base.get("pauseButton")));
            config.setAboutButton(readButton(base.get("aboutButton")));

        }
        return config;
    }

    private static ConfigButton readButton(JsonValue jsonButton){
        ConfigButton configButton = new ConfigButton();
        configButton.setImagePath(jsonButton.getString("imagePath"));
        configButton.setClickedImagePath(jsonButton.getString("clickedImagePath"));
        configButton.setX(jsonButton.getFloat("x"));
        configButton.setY(jsonButton.getFloat("y"));
        configButton.setWidth(jsonButton.getFloat("width"));
        configButton.setHeight(jsonButton.getFloat("height"));
        return configButton;
    }
}
