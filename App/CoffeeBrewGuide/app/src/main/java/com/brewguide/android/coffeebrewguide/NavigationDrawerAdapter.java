package com.brewguide.android.coffeebrewguide;
import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Custom adapter to populate navigation drawer
 */
class NavigationDrawerAdapter extends ArrayAdapter {

    private final Context context;
    private final int layoutResourceId;
    private ArrayList<NavigationDrawerItem> data = null;

    /**
     * @param context is the context of the application
     * @param layoutResourceId is the layout for each item
     * @param data is the aray of options being presented in the navigation drawer
     * */
    NavigationDrawerAdapter(Context context, int layoutResourceId, ArrayList<NavigationDrawerItem> data){
        super(context, layoutResourceId, data);
        this.context = context;
        this.layoutResourceId = layoutResourceId;
        this.data = data;
    }

    /**
     * Used to implement vieholder pattern
     * */
    private static class ViewHolder {
        TextView text;
        ImageView image;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, @NonNull ViewGroup parent) {

        ViewHolder mViewHolder;
        NavigationDrawerItem rowItem = data.get(position);

        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Activity.LAYOUT_INFLATER_SERVICE);

        if (convertView == null) {
            convertView = inflater.inflate(layoutResourceId, null);
            mViewHolder = new ViewHolder();

            //Image View
            mViewHolder.image = (ImageView) convertView.findViewById(R.id.drawer_itemIV);

            //Text View
            mViewHolder.text = (TextView) convertView.findViewById(R.id.drawer_itemTV);
        }else{
            mViewHolder = (ViewHolder) convertView.getTag();
        }

        //assign values
        mViewHolder.image.setImageResource(rowItem.icon);
        mViewHolder.text.setText(rowItem.name);

        convertView.setTag(mViewHolder);

        return convertView;
    }

}
