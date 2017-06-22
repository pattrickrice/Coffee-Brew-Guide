package com.brewguide.android.coffeebrewguide;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.NumberPicker;
import android.widget.TextView;
import org.joda.time.Duration;
import org.joda.time.Period;
import org.joda.time.format.PeriodFormatter;
import org.joda.time.format.PeriodFormatterBuilder;
import java.util.ArrayList;
import java.util.List;
import static com.brewguide.android.coffeebrewguide.R.string.brew_method_instructions;
import static com.brewguide.android.coffeebrewguide.R.string.serving;


/**
 * Activity that handels information for the Aeropress Activity. Intent is passed from the main
 * Activity.
 */
public class BrewMethodActivity extends AppCompatActivity implements View.OnClickListener {

    List<Integer> brewPours;
    InstructionListAdapter adapter;
    final String LOGTAG = this.getClass().getSimpleName();
    String newDirection;
    NestedScrollView mScrollView;
    Context context = this;
    public RecyclerView rvInstructions;
    public BrewMethod brewMethod;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.v(LOGTAG, "brewmethod called");
        SharedPreferences pref = getSharedPreferences("preferences", MODE_PRIVATE);
        Integer newServingSize = pref.getInt("pref_key_serving_size", 1);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_brew_method);

        //retrieves intent
        Intent i = getIntent();

        //creates brewmethod object
        brewMethod = i.getParcelableExtra("brew_method");
        String brewMethodTitle = brewMethod.getmMethodName();

        //Scrollview variable for savedInstanceState
        mScrollView = (NestedScrollView) findViewById(R.id.brew_method_NSV);

        // Custom class storing sets of data
        brewMethod = i.getParcelableExtra("brew_method");

        //changes values based on users preference
        brewPours = replacePours(brewMethodTitle, brewMethod.getmMethodBrewPours());

        ArrayList<String> templateInstructions = new ArrayList<>(brewMethod.getmMethodInstructions());
        //places numerical values into strings
        ArrayList<String> instructions = (ArrayList<String>) insertPourValues(brewPours, templateInstructions);

        //log the new strings
        for(int j = 0; j < instructions.size(); j++){
            Log.v("Value for instruction #" + j, "is: " + instructions.get(j));
        }

        //adapter displays each string as a view
        adapter = new InstructionListAdapter(this, instructions);

        //set the adapter to the view
        rvInstructions = (RecyclerView) findViewById(R.id.rvInstructions);
        rvInstructions.setAdapter(adapter);

        //create layout manager and set
        final LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        rvInstructions.setLayoutManager(layoutManager);

        //set top image
        ImageView topImage = (ImageView) findViewById(R.id.topImageIV);
        topImage.setImageResource(brewMethod.getmDetailActivityGraphicId());

        //get serving layout and set click listener
        LinearLayout servingLayout = (LinearLayout) findViewById(R.id.serving_layout);
        servingLayout.setOnClickListener(servingSizeListener);

        //set serving number
        String servingString = getResources().getString(serving);
        String serving = Integer.toString(newServingSize) + " " + servingString;
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
        PeriodFormatter minutesAndSeconds;
        if(brewMethodTitle.equals("Iced Coffee")) {
            minutesAndSeconds = new PeriodFormatterBuilder()
                    .printZeroAlways()
                    .minimumPrintedDigits(2)
                    .appendHours()
                    .appendLiteral(":")
                    .appendMinutes()
                    .appendLiteral(":")
                    .appendSeconds()
                    .toFormatter();
        }else{
            minutesAndSeconds = new PeriodFormatterBuilder()
                    .printZeroAlways()
                    .minimumPrintedDigits(2)
                    .appendMinutes()
                    .appendLiteral(":")
                    .appendSeconds()
                    .toFormatter();
        }
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
        try{
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }catch (NullPointerException e){
            Log.e(LOGTAG, "NullPointerException! Trying to setDisplayHomeAsUpEnabled!");
        }

        CollapsingToolbarLayout collapsingToolbar =
                (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
        collapsingToolbar.setTitle(brewMethodTitle);

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
                    Animation anim = AnimationUtils.loadAnimation(getBaseContext(), R.anim.slide_up);
                    clockView.startAnimation(anim);
                    clockView.setVisibility(View.GONE);
                } else {
                    clockView.setVisibility(View.VISIBLE);
                    Animation anim = AnimationUtils.loadAnimation(getBaseContext(), R.anim.slide_down);
                    clockView.startAnimation(anim);
                }
            }
        });
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    /**
     * Listens for click on the serving sizes
     * Opens a preference dialogue with a numer picker
     * Changes preference for the user
     * */
    public View.OnClickListener servingSizeListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            AlertDialog.Builder builder = new AlertDialog.Builder(context)
                    .setTitle("Set serving size")
                    .setMessage("How many cups of coffee are you making?")
                    .setIcon(R.drawable.ic_weight);

            //Access preferences
            final SharedPreferences pref = getSharedPreferences("preferences", MODE_PRIVATE);
            int userServingSizePreference = pref.getInt("pref_key_serving_size", 1);

            // create number picker in the dialog
            final NumberPicker picker = new NumberPicker(context);
            picker.setMinValue(1);
            picker.setMaxValue(2);
            picker.setValue(userServingSizePreference);

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

                    // get value from picker and set as new preference
                    int value = picker.getValue();
                    SharedPreferences.Editor editor = pref.edit();
                    editor.putInt("pref_key_serving_size", value);
                    editor.apply();

                    //changes values based on users preference
                    ArrayList<String>  instructionsToReplace, replacedInstructions;
                    brewPours = replacePours(brewMethod.getmMethodName(), brewMethod.getmMethodBrewPours());
                    instructionsToReplace = new ArrayList<>(brewMethod.getmMethodInstructions());

                    //places numerical values into strings
                    replacedInstructions =  (ArrayList<String>) insertPourValues(brewPours, instructionsToReplace);

                    //log the new strings
//                    for(int j = 0; j < instructions.size(); j++){
//                        Log.v("Value for instruction #" + j, "is: " + instructions.get(j));
//                    }

                    //adapter displays each string as a view
                    //newAdapter = new InstructionListAdapter(getBaseContext(), instructions);
                    adapter.swap(replacedInstructions);

                    //set the adapter to the view
                    //rvInstructions.swapAdapter(newAdapter, false);

                    //set serving number
                    String servingString = getResources().getString(serving);
                    String serving = Integer.toString(value) + " " + servingString;
                    TextView servingNumberTV = (TextView) findViewById(R.id.TV_servingNumber);
                    servingNumberTV.setText(serving);

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

    public List<Integer> replacePours(String brewMethodTitle, List<Integer> waterPours) {

        List<Integer> returnedPours = new ArrayList<>();


        switch (brewMethodTitle){
            case ("Iced Coffee"):
            case ("Aeropress"):
            case ("Hario V-60"):
                //return unmodified numbers
                returnedPours = waterPours;

                //display notice of ignoring serving size preference
                LinearLayout smallDeviceTV = (LinearLayout) findViewById(R.id.small_device_notice);
                smallDeviceTV.setVisibility(View.VISIBLE);
                break;

            // if device can make more than one cup
            default:
                SharedPreferences pref = getSharedPreferences("preferences", MODE_PRIVATE);
                Integer newServingSize = pref.getInt("pref_key_serving_size", 1);

                if (newServingSize == 2) {
                    for (int i = 0; i < waterPours.size(); i++) {
                        int newPour = waterPours.get(i) * 2;
                        returnedPours.add(newPour);
                    }
                }else {
                    returnedPours = waterPours;
                }
                break;
        }
        return returnedPours;
    }

    public List<String> insertPourValues(List<Integer> waterPours, List<String> instructions) {

        int i = 0;
        for (int j = 0; j < instructions.size(); j++) {
            if (instructions.get(j).contains("INT") && i< waterPours.size()) {
                newDirection = instructions.get(j).replaceAll("INT", Integer.toString(waterPours.get(i)));
                instructions.set(j, newDirection);
                i++;
            }
        }
        return instructions;
    }

}
