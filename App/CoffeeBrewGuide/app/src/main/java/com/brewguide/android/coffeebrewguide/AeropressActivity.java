package com.brewguide.android.coffeebrewguide;

import android.media.Image;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import javax.xml.datatype.Duration;

import static com.brewguide.android.coffeebrewguide.R.drawable.aeropress;

/**
 * Activity that handels information for the Aeropress Activity. Intent is passed from the main
 * Activity.
 * */
public class AeropressActivity extends AppCompatActivity {
    String name, grindSize;
    String[] instructions;
    int servingSize;
    org.joda.time.Duration brewTime;
    Image tile, graphic;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        name =  getResources().getString(R.string.title_aeropress);
        instructions = getResources().getStringArray(R.array.instructions_aeropress_array);
        servingSize = 1;

        // Set brewtime to 1:30 (mm:ss)
        brewTime = org.joda.time.Duration.millis(90000);



        //create BrewMethod object
        BrewMethod aeropress = new BrewMethod(
                name,
                instructions,
                servingSize,
                brewTime,
                grindSize,
                tile,
                graphic
        );

        // Auto generated
        setContentView(R.layout.activity_brew_method);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

}
