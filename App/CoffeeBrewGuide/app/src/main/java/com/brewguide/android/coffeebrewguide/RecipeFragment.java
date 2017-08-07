package com.brewguide.android.coffeebrewguide;

import android.os.Bundle;

import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class RecipeFragment extends Fragment {
    final String LOGTAG = this.getClass().getSimpleName();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        Log.v(LOGTAG, "Recipe Fragment called");
        return inflater.inflate(R.layout.fragment_recipe_layout, container, false);
    }

}