package com.brewguide.android.coffeebrewguide;

import javax.xml.datatype.Duration;

import static android.R.attr.name;

/**
 * Created by Patrick on 2/17/2017.
 */

public class BrewMethod {
    public String methodName, methodGrindSize;
    String[] methodInstructions;
    int methodServingSize;
    Duration methodBrewTime;


    public BrewMethod(String name, String[] instructions, int servingSize, Duration brewTime, String grindSize){
        methodName = name;
        methodGrindSize = grindSize;
        methodInstructions = instructions;
        methodServingSize = servingSize;
        methodBrewTime = brewTime;
    }
}
