package com.brewguide.android.coffeebrewguide;

import android.graphics.drawable.Drawable;
import android.media.Image;
import javax.xml.datatype.Duration;

/**
 * Class holds all information about the brew methods
 */

public class BrewMethod {
    public String mMethodName, mMethodGrindSize;
    String[] mMethodInstructions;
    int mMethodServingSize, mHomeScreenTileId, mDetailActivityGraphicId;
    org.joda.time.Duration mMethodBrewTime;


    public BrewMethod(String name, String[] instructions, int servingSize, org.joda.time.Duration brewTime, String grindSize, int tile, int graphic){
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

    public int getmHomeScreenTileId() {
        return mHomeScreenTileId;
    }

    public int getmDetailActivityGraphicId() {
        return mDetailActivityGraphicId;
    }
}
