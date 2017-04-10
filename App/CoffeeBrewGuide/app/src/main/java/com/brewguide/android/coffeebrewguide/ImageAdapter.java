package com.brewguide.android.coffeebrewguide;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import java.util.ArrayList;

/**
 * Adapter that takes the brew method tile images and implements them in a GridView shown in Main Activity
 */

public class ImageAdapter extends BaseAdapter {
    private Context mContext;
    private ArrayList<BrewMethod> brewMethodList;

    public ImageAdapter(Context c, ArrayList<BrewMethod> brewMethods) {
        mContext = c;
        brewMethodList = brewMethods;
    }

    // returns the count of the number of images
    public int getCount() {
        return brewMethodList.size();
    }

    public Object getItem(int position) {
        return null;
    }

    public long getItemId(int position) {
        return 0;
    }

    // create a new ImageView for each item referenced by the Adapter
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) mContext
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View gridView;

        if (convertView == null) {

//            gridView = new View(mContext);

            // get layout from mobile.xml
            gridView = inflater.inflate(R.layout.grid_tile, null);

            // set image based on selected text
            ImageView imageView = (ImageView) gridView
                    .findViewById(R.id.grid_tile_image);


            imageView.setImageResource(brewMethodList.get(position).getmHomeScreenTileId());


        } else {
            gridView = convertView;
        }

        return gridView;

    }

    // references to the images shown in the Gridview
    private Integer[] mThumbIds = {
            //TODO replace with object values
            R.drawable.aeropress,R.drawable.aeropress,R.drawable.aeropress,R.drawable.aeropress,R.drawable.aeropress,R.drawable.aeropress,R.drawable.aeropress
};
}
