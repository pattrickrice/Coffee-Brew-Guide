package com.brewguide.android.coffeebrewguide;

import android.app.Activity;
import android.widget.ArrayAdapter;
import java.util.ArrayList;


/**
 * Adapter to supply BrewMethodActivity with appropriate data.
 */

public class BrewMethodAdapter extends ArrayAdapter<BrewMethod> {
    /**
    * @param context is the current context (i.e. Activity) that the adapter is being created in.
    * @param brewMethods is the list of {@link BrewMethod}s to be displayed.
     */
    public BrewMethodAdapter(Activity context, ArrayList<BrewMethod> brewMethods) {
        super(context,0, brewMethods);
    }

}
