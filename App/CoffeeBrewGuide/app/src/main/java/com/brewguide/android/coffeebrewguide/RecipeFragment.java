package com.brewguide.android.coffeebrewguide;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class RecipeFragment extends Fragment {
    final String LOGTAG = this.getClass().getSimpleName();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Context context = getContext();
        // Inflate the layout for this fragment
        Log.v(LOGTAG, "Recipe Fragment called");

        //This line is required for the status bar color to stay consistent. Not sure why.
        getActivity().getWindow().setStatusBarColor(ContextCompat
                .getColor(context, R.color.colorPrimaryDark));
        return inflater.inflate(R.layout.fragment_recipe_layout, container, false);

    }

}