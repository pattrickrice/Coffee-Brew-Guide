package com.brewguide.android.coffeebrewguide;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;



import org.joda.time.Duration;
import org.joda.time.Period;

import org.joda.time.format.PeriodFormatter;
import org.joda.time.format.PeriodFormatterBuilder;



/**
 * Activity that handels information for the Aeropress Activity. Intent is passed from the main
 * Activity.
 */
public class BrewMethodActivity extends AppCompatActivity {

    // name of activity
    final String LOGTAG = this.getClass().getSimpleName();
    NestedScrollView mScrollView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_brew_method);

        //retrieves intent
        Intent i = getIntent();
        //creates brewmethod object
        BrewMethod brewMethod = (BrewMethod) i.getParcelableExtra("brew_method");

        //Scrollview variable for savedInstanceState
        mScrollView = (NestedScrollView) findViewById(R.id.brew_method_NSV);

        //set recyclerview object for instructions list
        RecyclerView rvInsstructions = (RecyclerView) findViewById(R.id.rvInstructions);

        //create adapter for recyclerview for instructions list
        InstructionListAdapter adapter = new InstructionListAdapter(this, brewMethod.getmMethodInstructions());

        //set the adapter to the view
        rvInsstructions.setAdapter(adapter);

        //create layout manager and set
        final LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        rvInsstructions.setLayoutManager(layoutManager);

        //set top image


        ImageView topImage = (ImageView) findViewById(R.id.topImageIV);
        topImage.setImageResource(brewMethod.getmDetailActivityGraphicId());

        //set serving number
        String serving = Integer.toString(brewMethod.getServingNumber()) + " " + getResources().getString(R.string.serving);
        TextView servingNumberTV = (TextView) findViewById(R.id.TV_servingNumber);
        servingNumberTV.setText(serving);

        //set serving Dose
        //TODO dynamically change units
        String servingDose = Integer.toString(brewMethod.getmMethodServingSize()) + "g";
        TextView servingSizeTV = (TextView) findViewById(R.id.TV_servingDose);
        servingSizeTV.setText(servingDose);

        //set brewtime using Joda Time
        Duration brewTimeJoda = brewMethod.getmMethodBrewTime();
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
        String grindSetting = brewMethod.getmMethodGrindSize();
        TextView grindSettingTV = (TextView) findViewById(R.id.TV_grindSetting);
        grindSettingTV.setText(grindSetting);

        //set description
        String description = brewMethod.getmDescription();
        TextView descriptionTV = (TextView) findViewById(R.id.brew_method_description_TV);
        descriptionTV.setText(description);

        // Auto generated
        final Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        CollapsingToolbarLayout collapsingToolbar =
                (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
        collapsingToolbar.setTitle(brewMethod.getmMethodName());

        //set floating action button
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


    //save state on scroll view
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putIntArray("ARTICLE_SCROLL_POSITION",
                new int[]{ mScrollView.getScrollX(), mScrollView.getScrollY()});
    }
    //go to position in scroll view
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        final int[] position = savedInstanceState.getIntArray("ARTICLE_SCROLL_POSITION");
        if(position != null)
            mScrollView.post(new Runnable() {
                public void run() {
                    mScrollView.scrollTo(position[0], position[1]);
                }
            });
    }
}
