package com.brewguide.android.coffeebrewguide;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        GridView gridview = (GridView) findViewById(R.id.gridview);
        gridview.setAdapter(new ImageAdapter(this));

        gridview.setOnItemClickListener(new OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v,
                                    int position, long id) {

                //TODO attach ids to grid tiles and associate their activities with them
                //TODO pass that information to launch activity below
                //Class targetActivity = getTargetActivityForPosition(position);

                // launch new activity when Item is clicked
                MainActivity.this.startActivity(new Intent(MainActivity.this, AeropressActivity.class));

                //used to indicate position of tile and verify item click functionality is working
                Toast.makeText(MainActivity.this, "" + position,
                        Toast.LENGTH_SHORT).show();
            }
        });

//        yourGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l){
//                Class targetActivity = getTargetActivityForPosition(position);
//                YourActivity.this.startActivity(new Intent(YourActivity.this, TargetActivity.class));
//            }
    }

}
