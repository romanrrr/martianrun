package com.gamestudio24.martianrun.config;

import java.util.List;

/**
 * Created by roma on 14.02.2018.
 */

public class ConfigBackground {

    private String id;
    private String layerPath;
    private int speedPercentage;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLayerPath() {
        return layerPath;
    }

    public void setLayerPath(String layerPath) {
        this.layerPath = layerPath;
    }

    public int getSpeedPercentage() {
        return speedPercentage;
    }

    public void setSpeedPercentage(int speedPercentage) {
        this.speedPercentage = speedPercentage;
    }
}
