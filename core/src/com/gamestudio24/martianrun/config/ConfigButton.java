package com.gamestudio24.martianrun.config;

/**
 * Created by roma on 19.02.2018.
 */

public class ConfigButton {

    private String id;
    private String imagePath;
    private String clickedImagePath;
    private float x;
    private float y;
    private float width;
    private float height;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public String getClickedImagePath() {
        return clickedImagePath;
    }

    public void setClickedImagePath(String clickedImagePath) {
        this.clickedImagePath = clickedImagePath;
    }

    public float getX() {
        return x;
    }

    public void setX(float x) {
        this.x = x;
    }

    public float getY() {
        return y;
    }

    public void setY(float y) {
        this.y = y;
    }

    public float getWidth() {
        return width;
    }

    public void setWidth(float width) {
        this.width = width;
    }

    public float getHeight() {
        return height;
    }

    public void setHeight(float height) {
        this.height = height;
    }
}
