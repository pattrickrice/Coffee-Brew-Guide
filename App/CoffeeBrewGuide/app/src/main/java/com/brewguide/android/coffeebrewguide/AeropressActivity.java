package com.brewguide.android.coffeebrewguide;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import org.joda.time.Duration;
import org.joda.time.Period;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.joda.time.format.PeriodFormatter;
import org.joda.time.format.PeriodFormatterBuilder;

import java.util.ArrayList;
import java.util.Arrays;



/**
 * Activity that handels information for the Aeropress Activity. Intent is passed from the main
 * Activity.
 * */
public class AeropressActivity extends AppCompatActivity  {

    // name of activity
    final String LOGTAG = this.getClass().getSimpleName();


    String name, grindSize;
    ArrayList<String> instructions;
    int servingNumber, servingSize,tile, graphic;
    org.joda.time.Duration brewTime;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_brew_method);

        //creates brewmethod object
        BrewMethod aeropress = getBrewMethodData();

        //set recyclerview object for instructions list
        RecyclerView rvInsstructions = (RecyclerView) findViewById(R.id.rvInstructions);

        //create adapter for recyclerview for instructions list
        InstructionListAdapter adapter = new InstructionListAdapter(this, aeropress.getmMethodInstructions());

        //set the adapter to the view
        rvInsstructions.setAdapter(adapter);

        //create layout manager and set
        final LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        rvInsstructions.setLayoutManager(layoutManager);

        //set top image
        ImageView topImage = (ImageView) findViewById(R.id.topImageIV);
        topImage.setImageResource(aeropress.getmDetailActivityGraphicId());

        //set serving number
        String serving = Integer.toString(aeropress.getServingNumber()) + " " + getResources().getString(R.string.serving);
        TextView servingNumberTV = (TextView) findViewById(R.id.TV_servingNumber);
        servingNumberTV.setText(serving);

        //set serving Dose
        //TODO dynamically change units
        String servingDose = Integer.toString(aeropress.getmMethodServingSize()) + "g";
        TextView servingSizeTV = (TextView) findViewById(R.id.TV_servingDose);
        servingSizeTV.setText(servingDose);

        //set brewtime using Joda Time
        Duration brewTimeJoda = aeropress.getmMethodBrewTime();

        Period period = brewTimeJoda.toPeriod();
        // format brewtime
        PeriodFormatter minutesAndSeconds = new PeriodFormatterBuilder()
                .printZeroAlways()
                .appendMinutes()
                .appendSeparator(":")
                .appendSeconds()
                .toFormatter();
        String result = minutesAndSeconds.print(period);
        TextView brewTimeTV = (TextView) findViewById(R.id.TV_timer);
        brewTimeTV.setText(result);

        //set grind setting
        String grindSetting = aeropress.getmMethodGrindSize();
        TextView grindSettingTV = (TextView) findViewById(R.id.TV_grindSetting);
        grindSettingTV.setText(grindSetting);

        // Auto generated
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.ic_alarm_white_48dp));

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

    public BrewMethod getBrewMethodData(){
        //Assign variables on creation
        name =  getResources().getString(R.string.title_aeropress);
        instructions = new ArrayList<>(Arrays.asList(getResources().getStringArray(R.array.instructions_aeropress_array)));
        servingNumber = 1;
        servingSize = 16;
        grindSize = getResources().getString(R.string.grind_size_medium);

        // Set brewtime to 1:30 (mm:ss)
        brewTime = org.joda.time.Duration.millis(90000);

        tile = R.drawable.aeropress;
        //TODO: update with graphic when resource is available.
        graphic = R.drawable.aeropress;

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
