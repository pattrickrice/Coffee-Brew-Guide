package com.brewguide.android.coffeebrewguide;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import java.util.ArrayList;
import java.util.Arrays;


/**
 * Activity that handels information for the Aeropress Activity. Intent is passed from the main
 * Activity.
 * */
public class AeropressActivity extends AppCompatActivity implements InstructionsFragment.OnInstructionsStepSelected{

    // name of activity
    final String LOGTAG = this.getClass().getSimpleName();

    String name, grindSize;
    ArrayList<String> instructions;
    int servingSize,tile, graphic;
    org.joda.time.Duration brewTime;

    InstructionsFragment.OnListFragmentInteractionListener listener;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Assign variables on creation
        name =  getResources().getString(R.string.title_aeropress);
        instructions = new ArrayList<>(Arrays.asList(getResources().getStringArray(R.array.instructions_aeropress_array)));
        servingSize = 1;

        // Set brewtime to 1:30 (mm:ss)
        brewTime = org.joda.time.Duration.millis(90000);

        tile = R.drawable.aeropress;
        //TODO: update with graphic when resource is available.
        graphic = R.drawable.aeropress;

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


//        //id the recycler view
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.list_recycler_view);
//
        //create the adapter
        BrewMethodInstructionsAdapter adapter = new BrewMethodInstructionsAdapter(instructions, listener);
//
//        //set the adapter to fill the recycler view
        //TODO: Null object reference
        recyclerView.setAdapter(adapter);


        // Auto generated
        setContentView(R.layout.activity_brew_method);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //TODO replace with action of starting a timer
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }


    @Override
    public void onArticleSelected(int position) {

    }
}
