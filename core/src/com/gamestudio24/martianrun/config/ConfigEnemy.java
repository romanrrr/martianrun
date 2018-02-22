package com.gamestudio24.martianrun.config;

import java.util.List;

/**
 * Created by roma on 14.02.2018.
 */

public class ConfigEnemy {

    private String id;
    private int width;
    private int height;
    private float yindex;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    private List<String> animation;

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public float getYindex() {
        return yindex;
    }

    public void setYindex(float yindex) {
        this.yindex = yindex;
    }

    public List<String> getAnimation() {
        return animation;
    }

    public void setAnimation(List<String> animation) {
        this.animation = animation;
    }
}
