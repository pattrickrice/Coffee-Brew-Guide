package com.brewguide.android.coffeebrewguide;

import android.graphics.drawable.Drawable;
import android.media.Image;
import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

import javax.xml.datatype.Duration;

/**
 * Class holds all information about the brew methods
 */

public class BrewMethod implements Parcelable{
    public String mMethodName, mMethodGrindSize;
    ArrayList<String> mMethodInstructions;
    int mMethodServingNumber, mMethodServingSize, mHomeScreenTileId, mDetailActivityGraphicId;
    org.joda.time.Duration mMethodBrewTime;


    public BrewMethod(String name,
                      ArrayList<String> instructions,
                      int servingNumber,
                      int servingSize,
                      org.joda.time.Duration brewTime,
                      String grindSize,
                      int tile,
                      int graphic){
        mMethodName = name;
        mMethodGrindSize = grindSize;
        mMethodInstructions = instructions;
        mMethodServingNumber = servingNumber;
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

    public ArrayList<String> getmMethodInstructions() {
        return mMethodInstructions;
    }
    public int getServingNumber() {
        return mMethodServingNumber;
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

    /**
     * implement Parcelable class
     *
     */

    public int describeContents() {
        return this.hashCode();
    }

    public void writeToParcel(Parcel out, int flags) {
        out.writeString(mMethodName);
        out.writeSerializable(mMethodInstructions);
        out.writeInt(mMethodServingNumber);
        out.writeInt(mMethodServingSize);
        out.writeSerializable(mMethodBrewTime);
        out.writeString(mMethodGrindSize);
        out.writeInt(mHomeScreenTileId);
        out.writeInt(mDetailActivityGraphicId);
    }

    public static final Parcelable.Creator<BrewMethod> CREATOR
            = new Parcelable.Creator<BrewMethod>() {
        public BrewMethod createFromParcel(Parcel in) {
            return new BrewMethod(in);
        }

        public BrewMethod[] newArray(int size) {
            return new BrewMethod[size];
        }
    };

    private BrewMethod(Parcel in) {
        this.mMethodName = in.readString();
        this.mMethodInstructions = (ArrayList<String>) in.readSerializable();
        this.mMethodServingNumber = in.readInt();
        this.mMethodServingSize = in.readInt();
        this.mMethodBrewTime = (org.joda.time.Duration) in.readSerializable();
        this.mMethodGrindSize = in.readString();
        this.mHomeScreenTileId = in.readInt();
        this.mDetailActivityGraphicId = in.readInt();
    }
}
