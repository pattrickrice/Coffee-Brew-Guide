package com.brewguide.android.coffeebrewguide;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

/**
 * Class holds all information about the brew methods
 */
class BrewMethod implements Parcelable{
    private String mMethodName, mMethodGrindSize, mDescription;
    private ArrayList<Integer> mMethodBrewPours;
    private ArrayList<String> mMethodInstructions;
    private int mMethodDose, mMethodServingSize, mHomeScreenTileId, mDetailActivityGraphicId;
    private org.joda.time.Duration mMethodBrewTime;

    /**
     * @param name is the name of the brew method.
     * @param grindSize is the size the coffee needs to be ground.
     * @param brewPours is an array filled with the pour amounts necessary. different methods will
     *                  have different amounts.
     * @param instructions is the set of instructions for how to use the brew method. Each
     *                     instruction has INT and UNITS in it that are replaced depending on the
     *                     users preference.
     * @param dose is the amount of coffee required to brew one cup. The displayed dose amount
     *             changes based on the users preference.
     * @param servingSize is the users preferred number of cups to brew.
     * @param brewTime is the total amount of time required to brew the coffee.
     * @param tile is the graphic representation of the brew method for display in the MainActivity
     * @param graphic is the graphic representation of the brew method for display in
     *                BrewMthodActivity.
     * @param description is the description of what the brew method is.
     * */
    BrewMethod(String name,
                  ArrayList<String> instructions,
                  ArrayList<Integer> brewPours,
                  int dose,
                  int servingSize,
                  org.joda.time.Duration brewTime,
                  String grindSize,
                  int tile,
                  int graphic,
                  String description){
        mMethodName = name;
        mMethodGrindSize = grindSize;
        mMethodBrewPours = brewPours;
        mMethodInstructions = instructions;
        mMethodDose = dose;
        mMethodServingSize = servingSize;
        mMethodBrewTime = brewTime;
        mHomeScreenTileId = tile;
        mDetailActivityGraphicId = graphic;
        mDescription = description;
    }

    /**
     * get methods
     */
    String getmMethodName() {
        return mMethodName;
    }

    ArrayList<String> getmMethodInstructions() {
        return mMethodInstructions;
    }

    ArrayList<Integer> getmMethodBrewPours() {
        return mMethodBrewPours;
    }

    public int getDose() {
        return mMethodDose;
    }
    String getmMethodGrindSize() {
        return mMethodGrindSize;
    }

    int getmMethodServingSize() {
        return mMethodServingSize;
    }

    org.joda.time.Duration getmMethodBrewTime() {
        return mMethodBrewTime;
    }

    int getmHomeScreenTileId() {
        return mHomeScreenTileId;
    }

    int getmDetailActivityGraphicId() {
        return mDetailActivityGraphicId;
    }

    String getmDescription() {return mDescription;}

    /**
     * implement Parcelable class
     */
    public int describeContents() {
        return this.hashCode();
    }

    public void writeToParcel(Parcel out, int flags) {
        out.writeString(mMethodName);
        out.writeSerializable(mMethodInstructions);
        out.writeSerializable(mMethodBrewPours);
        out.writeInt(mMethodDose);
        out.writeInt(mMethodServingSize);
        out.writeSerializable(mMethodBrewTime);
        out.writeString(mMethodGrindSize);
        out.writeInt(mHomeScreenTileId);
        out.writeInt(mDetailActivityGraphicId);
        out.writeString(mDescription);
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
        this.mMethodBrewPours = (ArrayList<Integer>) in.readSerializable();
        this.mMethodDose = in.readInt();
        this.mMethodServingSize = in.readInt();
        this.mMethodBrewTime = (org.joda.time.Duration) in.readSerializable();
        this.mMethodGrindSize = in.readString();
        this.mHomeScreenTileId = in.readInt();
        this.mDetailActivityGraphicId = in.readInt();
        this.mDescription = in.readString();
    }
}
