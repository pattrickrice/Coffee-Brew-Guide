package com.brewguide.android.coffeebrewguide;

import android.media.Image;
import javax.xml.datatype.Duration;

/**
 * Class holds all information about the brew methods
 */

public class BrewMethod {
    public String mMethodName, mMethodGrindSize;
    String[] mMethodInstructions;
    int mMethodServingSize;
    org.joda.time.Duration mMethodBrewTime;
    Image mHomeScreenTileId, mDetailActivityGraphicId;

    public BrewMethod(String name, String[] instructions, int servingSize, org.joda.time.Duration brewTime, String grindSize, Image tile, Image graphic){
        mMethodName = name;
        mMethodGrindSize = grindSize;
        mMethodInstructions = instructions;
        mMethodServingSize = servingSize;
        mMethodBrewTime = brewTime;
        mHomeScreenTileId = tile;
        mDetailActivityGraphicId = graphic;
    }
    /**
     * get methods
     *
     */
    public String getmMethodName() {
        return mMethodName;
    }

    public String[] getmMethodInstructions() {
        return mMethodInstructions;
    }

    public String getmMethodGrindSize() {
        return mMethodGrindSize;
    }

    public int getmMethodServingSize() {
        return mMethodServingSize;
    }

    public org.joda.time.Duration getmMethodBrewTime() {
        return mMethodBrewTime;
    }

    public Image getmHomeScreenTileId() {
        return mHomeScreenTileId;
    }

    public Image getmDetailActivityGraphicId() {
        return mDetailActivityGraphicId;
    }
}
