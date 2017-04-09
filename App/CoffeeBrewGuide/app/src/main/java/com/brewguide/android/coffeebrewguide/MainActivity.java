package com.brewguide.android.coffeebrewguide;

import android.content.Intent;
import android.os.Parcelable;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

import java.util.ArrayList;
import java.util.Arrays;

import static com.brewguide.android.coffeebrewguide.R.drawable.aeropress;

public class MainActivity extends AppCompatActivity {

    String name, grindSize;
    ArrayList<String> instructions;
    int servingNumber, servingSize, tile, graphic;
    org.joda.time.Duration brewTime;
    NestedScrollView mScrollView;

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


                BrewMethod brewMethod = getBrewMethodData();
                // launch new activity when Item is clicked
                Intent intent = new Intent(getBaseContext(), AeropressActivity.class);
                //MainActivity.this.startActivity(new Intent(MainActivity.this, AeropressActivity.class));
                intent.putExtra("brew_method", brewMethod);
                startActivity(intent);
                //used to indicate position of tile and verify item click functionality is working
                Toast.makeText(MainActivity.this, "" + position,
                        Toast.LENGTH_SHORT).show();
            }
        });


    }
    public BrewMethod getBrewMethodData() {
        //Assign variables on creation
        name = getResources().getString(R.string.title_aeropress);
        instructions = new ArrayList<>(Arrays.asList(getResources().getStringArray(R.array.instructions_aeropress_array)));
        servingNumber = 1;
        servingSize = 16;
        grindSize = getResources().getString(R.string.grind_size_medium);

        // Set brewtime to 1:30 (mm:ss)
        brewTime = org.joda.time.Duration.millis(90000);

        tile = aeropress;
        //TODO: update with graphic when resource is available.
        graphic = aeropress;

        //create BrewMethod object
        BrewMethod brewMethod = new BrewMethod(
                name,
                instructions,
                servingNumber,
                servingSize,
                brewTime,
                grindSize,
                tile,
                graphic
        );
        return brewMethod;
    }

}
