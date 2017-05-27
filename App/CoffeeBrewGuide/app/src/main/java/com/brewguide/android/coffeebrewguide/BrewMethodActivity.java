package com.brewguide.android.coffeebrewguide;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import android.view.Gravity;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.NumberPicker;
import android.widget.TextView;
import android.widget.Toast;


import org.joda.time.Duration;
import org.joda.time.Period;

import org.joda.time.format.PeriodFormatter;
import org.joda.time.format.PeriodFormatterBuilder;

import static android.icu.lang.UCharacter.GraphemeClusterBreak.V;
import static com.brewguide.android.coffeebrewguide.R.string.serving;


/**
 * Activity that handels information for the Aeropress Activity. Intent is passed from the main
 * Activity.
 */
public class BrewMethodActivity extends AppCompatActivity implements View.OnClickListener {

    // name of activity
    final String LOGTAG = this.getClass().getSimpleName();
    NestedScrollView mScrollView;
    Context context = this;

    SharedPreferences SP = PreferenceManager.getDefaultSharedPreferences(context);


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

        //get serving layout and set click listener
        LinearLayout servingLayout = (LinearLayout) findViewById(R.id.serving_layout);
        servingLayout.setOnClickListener(servingSizeListener);

        //set serving number
        String servingString = getResources().getString(serving);
        String serving = Integer.toString(brewMethod.getServingNumber()) + " " + servingString;
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

        // get clock view
        final TextView clockView = (TextView) findViewById(R.id.clock_tv);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //TODO replace with action of starting a timer
                if (clockView.getVisibility() == View.VISIBLE) {
                    clockView.setVisibility(View.GONE);
                } else {
                    clockView.setVisibility(View.VISIBLE);
                }
            }
        });
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    public View.OnClickListener servingSizeListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            AlertDialog.Builder builder = new AlertDialog.Builder(context)
                    .setTitle("Set serving size")
                    .setMessage("How many cups of coffee are you making?")
                    .setIcon(R.drawable.ic_weight);

            // create number picker in the dialog
            final NumberPicker picker = new NumberPicker(context);
            picker.setMinValue(1);
            picker.setMaxValue(2);

            // create layout for picker to reside in
            final FrameLayout parent = new FrameLayout(context);
            parent.addView(picker, new FrameLayout.LayoutParams(
                    FrameLayout.LayoutParams.WRAP_CONTENT,
                    FrameLayout.LayoutParams.WRAP_CONTENT,
                    Gravity.CENTER));
            builder.setView(parent);

            // if okay button is pressed
            builder.setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    // get value from picker
                    int value = picker.getValue();


                    Toast.makeText(getApplicationContext(), "The value is " + value,
                            Toast.LENGTH_LONG).show();
                }
            })

            // if cancel button is pressed
            .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    // do nothing
                }
            });
            builder.show();
        }
    };

    @Override
    public void onClick(View view) {
    }

    //save state on scroll view
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putIntArray("ARTICLE_SCROLL_POSITION",
                new int[]{mScrollView.getScrollX(), mScrollView.getScrollY()});
    }

    //go to position in scroll view
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        final int[] position = savedInstanceState.getIntArray("ARTICLE_SCROLL_POSITION");
        if (position != null)
            mScrollView.post(new Runnable() {
                public void run() {
                    mScrollView.scrollTo(position[0], position[1]);
                }
            });
    }
}
