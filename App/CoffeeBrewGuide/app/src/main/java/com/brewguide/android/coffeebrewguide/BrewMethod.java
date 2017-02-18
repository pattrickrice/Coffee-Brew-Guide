package com.brewguide.android.coffeebrewguide;

import android.media.Image;
import javax.xml.datatype.Duration;

/**
 * Class holds all information about the brew methods
 */

public class BrewMethod {
    public String methodName, methodGrindSize;
    String[] methodInstructions;
    int methodServingSize;
    Duration methodBrewTime;
    Image homeScreenTile, detailActivityGraphic;

    public BrewMethod(String name, String[] instructions, int servingSize, Duration brewTime, String grindSize, Image tile, Image graphic){
        methodName = name;
        methodGrindSize = grindSize;
        methodInstructions = instructions;
        methodServingSize = servingSize;
        methodBrewTime = brewTime;
        homeScreenTile = tile;
        detailActivityGraphic = graphic;
    }
    /**
     * get methods
     *
     */
    public String getMethodName() {
        return methodName;
    }

    public String[] getMethodInstructions() {
        return methodInstructions;
    }

    public String getMethodGrindSize() {
        return methodGrindSize;
    }

    public int getMethodServingSize() {
        return methodServingSize;
    }

    public Duration getMethodBrewTime() {
        return methodBrewTime;
    }

    public Image getHomeScreenTile() {
        return homeScreenTile;
    }

    public Image getDetailActivityGraphic() {
        return detailActivityGraphic;
    }
}
