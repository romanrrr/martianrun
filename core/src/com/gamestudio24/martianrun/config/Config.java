package com.gamestudio24.martianrun.config;

import com.badlogic.gdx.graphics.Color;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by q on 12.02.2017.
 */
public class Config {

    private String appName;
    private String logo;

    private String primaryColor;
    private String accentColor;

    private String hintLeft;
    private String hintRight;

    private String groundImage;

    private String font;

    private List<String> runnerRunningAnimation;
    private List<String> runnerJumpAnimation;
    private List<String> runnerSlideAnimation;
    private List<String> runnerDeadAnimation;

    private ConfigButton startButton;
    private ConfigButton musicButton;
    private ConfigButton soundButton;
    private ConfigButton shareButton;
    private ConfigButton pauseButton;
    private ConfigButton aboutButton;

    private List<String> coinAnimation;

    private List<ConfigEnemy> configEnemies;
    private List<ConfigBackground> configBackgroundList;

    private String coinSound;
    private String jumpSound;
    private String hitSound;
    private String music;

    public List<String> getRunnerRunningAnimation() {
        return runnerRunningAnimation;
    }

    public void setRunnerRunningAnimation(List<String> runnerRunningAnimation) {
        this.runnerRunningAnimation = runnerRunningAnimation;
    }

    public List<String> getRunnerJumpAnimation() {
        return runnerJumpAnimation;
    }

    public void setRunnerJumpAnimation(List<String> runnerJumpAnimation) {
        this.runnerJumpAnimation = runnerJumpAnimation;
    }

    public List<String> getRunnerSlideAnimation() {
        return runnerSlideAnimation;
    }

    public void setRunnerSlideAnimation(List<String> runnerSlideAnimation) {
        this.runnerSlideAnimation = runnerSlideAnimation;
    }

    public List<String> getRunnerDeadAnimation() {
        return runnerDeadAnimation;
    }

    public void setRunnerDeadAnimation(List<String> runnerDeadAnimation) {
        this.runnerDeadAnimation = runnerDeadAnimation;
    }

    public List<ConfigEnemy> getConfigEnemies() {
        return configEnemies;
    }

    public void setConfigEnemies(List<ConfigEnemy> configEnemies) {
        this.configEnemies = configEnemies;
    }

    public List<ConfigBackground> getConfigBackgroundList() {
        return configBackgroundList;
    }

    public void setConfigBackgroundList(List<ConfigBackground> configBackgroundList) {
        this.configBackgroundList = configBackgroundList;
    }

    public String getCoinSound() {
        return coinSound;
    }

    public void setCoinSound(String coinSound) {
        this.coinSound = coinSound;
    }

    public String getJumpSound() {
        return jumpSound;
    }

    public void setJumpSound(String jumpSound) {
        this.jumpSound = jumpSound;
    }

    public String getHitSound() {
        return hitSound;
    }

    public void setHitSound(String hitSound) {
        this.hitSound = hitSound;
    }

    public String getMusic() {
        return music;
    }

    public void setMusic(String music) {
        this.music = music;
    }

    public List<String> getCoinAnimation() {
        return coinAnimation;
    }

    public void setCoinAnimation(List<String> coinAnimation) {
        this.coinAnimation = coinAnimation;
    }

    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    public String getPrimaryColor() {
        return primaryColor;
    }

    public void setPrimaryColor(String primaryColor) {
        this.primaryColor = primaryColor;
    }

    public String getAccentColor() {
        return accentColor;
    }

    public void setAccentColor(String accentColor) {
        this.accentColor = accentColor;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public ConfigButton getStartButton() {
        return startButton;
    }

    public void setStartButton(ConfigButton startButton) {
        this.startButton = startButton;
    }

    public ConfigButton getMusicButton() {
        return musicButton;
    }

    public void setMusicButton(ConfigButton musicButton) {
        this.musicButton = musicButton;
    }

    public ConfigButton getSoundButton() {
        return soundButton;
    }

    public void setSoundButton(ConfigButton soundButton) {
        this.soundButton = soundButton;
    }

    public ConfigButton getShareButton() {
        return shareButton;
    }

    public void setShareButton(ConfigButton shareButton) {
        this.shareButton = shareButton;
    }

    public ConfigButton getPauseButton() {
        return pauseButton;
    }

    public void setPauseButton(ConfigButton pauseButton) {
        this.pauseButton = pauseButton;
    }

    public ConfigButton getAboutButton() {
        return aboutButton;
    }

    public void setAboutButton(ConfigButton aboutButton) {
        this.aboutButton = aboutButton;
    }

    public String getHintLeft() {
        return hintLeft;
    }

    public void setHintLeft(String hintLeft) {
        this.hintLeft = hintLeft;
    }

    public String getHintRight() {
        return hintRight;
    }

    public void setHintRight(String hintRight) {
        this.hintRight = hintRight;
    }

    public String getGroundImage() {
        return groundImage;
    }

    public void setGroundImage(String groundImage) {
        this.groundImage = groundImage;
    }

    public String getFont() {
        return font;
    }

    public void setFont(String font) {
        this.font = font;
    }
}