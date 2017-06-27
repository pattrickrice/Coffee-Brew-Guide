package com.brewguide.android.coffeebrewguide;
import android.app.Activity;
import android.content.Context;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

/**
 * Custom adapter to populate navigation drawer
 */

public class NavigationDrawerAdapter extends ArrayAdapter {

    private final Context context;
    private final int layoutResourceId;
    private NavigationDrawerItem data[] = null;


    public NavigationDrawerAdapter(Context context, int layoutResourceId, NavigationDrawerItem [] data){
        super(context, layoutResourceId, data);
        this.context = context;
        this.layoutResourceId = layoutResourceId;
        this.data = data;
    }

    static class ViewHolder {
        TextView text;
        ImageView image;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder mViewHolder;
        NavigationDrawerItem rowItem = data[position];

        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Activity.LAYOUT_INFLATER_SERVICE);

        if (convertView == null) {
            convertView = inflater.inflate(R.layout.drawer_list_item, null);
            mViewHolder = new ViewHolder();
            mViewHolder.image = (ImageView) convertView.findViewById(R.id.drawer_itemIV);
            mViewHolder.text = (TextView) convertView.findViewById(R.id.drawer_itemTV);
        }else{
            mViewHolder = (ViewHolder) convertView.getTag();
        }

        mViewHolder.image.setImageResource(rowItem.icon);
        mViewHolder.text.setText(rowItem.name);

        convertView.setTag(mViewHolder);

        return convertView;
    }

}
