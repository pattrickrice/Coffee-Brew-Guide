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
import java.util.Locale;

import android.os.Handler;

import static com.brewguide.android.coffeebrewguide.R.string.serving;

/***************************************************************************************************
 * Activity that handles information for the brew methods' Activity. Intent is passed from the main
 * Activity.
 **************************************************************************************************/
public class BrewMethodActivity extends AppCompatActivity implements View.OnClickListener {

    List<Integer> brewPours;
    InstructionListAdapter adapter;
    final String LOGTAG = this.getClass().getSimpleName();
    String newDirection;
    NestedScrollView mScrollView;
    Context context = this;
    public RecyclerView rvInstructions;
    public BrewMethod brewMethod;
    int dose;

    // required for timer
    public TextView clockView;
    long startTime = 0;
    final Handler timerHandler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
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
        brewPours = new ArrayList<>();
        brewPours = replacePours(brewMethodTitle, brewMethod.getmMethodBrewPours());

        ArrayList<String> templateInstructions = new ArrayList<>(brewMethod
                .getmMethodInstructions());

        //places numerical values into strings
        ArrayList<String> instructions = (ArrayList<String>) insertPourValues(brewPours,
                templateInstructions);

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
        if (!canMakeMoreThanOnePour(brewMethodTitle)) {
            newServingSize = 1;
        }
        String servingString = getResources().getString(serving);
        String serving = Integer.toString(newServingSize) + " " + servingString;
        TextView servingNumberTV = (TextView) findViewById(R.id.TV_servingNumber);
        servingNumberTV.setText(serving);

        //set serving Dose
        dose = replaceDose(brewMethodTitle, brewMethod.getmMethodServingSize());
        String servingDose = Integer.toString(dose) + "g";
        TextView servingSizeTV = (TextView) findViewById(R.id.TV_servingDose);
        servingSizeTV.setText(servingDose);

        //set brewtime using Joda Time
        Duration brewTimeJoda = brewMethod.getmMethodBrewTime();
        Period period = brewTimeJoda.toPeriod();

        // format brewtime
        PeriodFormatter minutesAndSeconds;
        if (brewMethodTitle.equals("Iced Coffee")) {
            minutesAndSeconds = new PeriodFormatterBuilder()
                    .printZeroAlways()
                    .minimumPrintedDigits(2)
                    .appendHours()
                    .appendLiteral(":")
                    .appendMinutes()
                    .appendLiteral(":")
                    .appendSeconds()
                    .toFormatter();
        } else {
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
        try {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        } catch (NullPointerException e) {
            Log.e(LOGTAG, "NullPointerException! Trying to setDisplayHomeAsUpEnabled!");
        }

        CollapsingToolbarLayout collapsingToolbar =
                (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
        collapsingToolbar.setTitle(brewMethodTitle);

        //set floating action button
        final FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setImageDrawable(ContextCompat.getDrawable(getApplicationContext(),
                R.drawable.ic_alarm_white_48dp));

        // get clock view
        clockView = (TextView) findViewById(R.id.clock_tv);
        final View startButton, stopButton, resetbutton;
        startButton = findViewById(R.id.startButtonB);
        stopButton = findViewById(R.id.stopButtonB);
        resetbutton = findViewById(R.id.resetButtonB);

        //arraylist of all clock views
        final ArrayList<View> clockViewList = new ArrayList<>();
        clockViewList.add(clockView);
        clockViewList.add(startButton);
        clockViewList.add(stopButton);
        clockViewList.add(resetbutton);

        //clock layout view
        final LinearLayout clockLayout = (LinearLayout) findViewById(R.id.clock_button_layout);

        //open clockview
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (clockView.getVisibility() == View.VISIBLE) {

                    //loads and plays the custom animation
                    Animation anim = AnimationUtils.loadAnimation(getBaseContext(),
                            R.anim.slide_up);
                    clockLayout.setAnimation(anim);
                    clockLayout.setVisibility(View.GONE);
                    for (int i = 0; i < clockViewList.size(); i++) {
                        View v = clockViewList.get(i);
                        v.startAnimation(anim);
                        v.setVisibility(View.GONE);
                    }
                } else {

                    for (int i = 0; i < clockViewList.size(); i++) {
                        View v = clockViewList.get(i);
                        v.setVisibility(View.VISIBLE);
                    }

                    clockLayout.setVisibility(View.VISIBLE);

                    Animation anim = AnimationUtils.loadAnimation(getBaseContext(),
                            R.anim.slide_down);

                    for (int i = 0; i < clockViewList.size(); i++) {
                        View v = clockViewList.get(i);
                        v.startAnimation(anim);

                    }

                    //assign onClick handlers
                    startButton.setOnClickListener(startButtonListener);
                    stopButton.setOnClickListener(stopButtonListener);
                    resetbutton.setOnClickListener(resetButtonListener);
                    clockLayout.setAnimation(anim);
                }
            }
        });
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //Make FAB disappear when scrolling down
        mScrollView.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {
            @Override
            public void onScrollChange(NestedScrollView v,
                                       int scrollX,
                                       int scrollY,
                                       int oldScrollX,
                                       int oldScrollY) {

                if (scrollY > oldScrollY) {
                    //Scrolling down
                    fab.hide();
                }
                if (scrollY < oldScrollY) {
                    //Scrolling up
                    fab.show();

                }
            }
        });
    }

    /***********************************************************************************************
     * Listens for click on the serving sizes and opens a preference dialogue with a number picker.
     * Changes preference for the user
     **********************************************************************************************/
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
                    if (!canMakeMoreThanOnePour(brewMethod.getmMethodName())) {
                        value = 1;
                    }

                    //changes values based on users preference
                    ArrayList<String> instructionsToReplace, replacedInstructions;
                    brewPours = replacePours(brewMethod.getmMethodName(),
                            brewMethod.getmMethodBrewPours());
                    instructionsToReplace = new ArrayList<>(brewMethod.getmMethodInstructions());

                    //places numerical values into strings
                    replacedInstructions = (ArrayList<String>) insertPourValues(brewPours,
                            instructionsToReplace);

                    //adapter displays each string as a view
                    //newAdapter = new InstructionListAdapter(getBaseContext(), instructions);
                    adapter.swap(replacedInstructions);

                    //set serving number
                    String servingString = getResources().getString(serving);
                    String serving = Integer.toString(value) + " " + servingString;
                    TextView servingNumberTV = (TextView) findViewById(R.id.TV_servingNumber);
                    servingNumberTV.setText(serving);

                    //set serving Dose
                    dose = replaceDose(brewMethod.getmMethodName(),
                            brewMethod.getmMethodServingSize());
                    String servingDose = Integer.toString(dose) + "g";
                    TextView servingSizeTV = (TextView) findViewById(R.id.TV_servingDose);
                    servingSizeTV.setText(servingDose);

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

    /***********************************************************************************************
     * Runnable that is used for the timer when the user hits start on the clock.
     **********************************************************************************************/
    Runnable timerRunnable = new Runnable() {

        /**
         * Required for the runnable object. Starts counting up from the Unix time that the runnable
         * was created
         * */
        @Override
        public void run() {
            long millis = System.currentTimeMillis() - startTime;
            int seconds = (int) (millis / 1000);
            int minutes = seconds / 60;
            seconds = seconds % 60;

            //Why didn't I use the formatting earlier instead of Joda.Time?
            clockView.setText(String.format("%02d:%02d", minutes, seconds));
            timerHandler.postDelayed(this, 500);
        }
    };
    /***********************************************************************************************
     * Onclick listener for the start button on the timer. When Start button is clicked, it will
     * start a new Handler.
     **********************************************************************************************/
    public View.OnClickListener startButtonListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            startTime = System.currentTimeMillis();
            timerHandler.postDelayed(timerRunnable, 0);
        }
    };

    /***********************************************************************************************
     * Onclick listener for the stop button on the timer. When stop button is clicked, it will
     * end the timer process.
     **********************************************************************************************/
    public View.OnClickListener stopButtonListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            timerHandler.removeCallbacks(timerRunnable);
        }
    };

    /***********************************************************************************************
     * Onclick listener for the reset button on the timer. When reset button is clicked, it will
     * stop the Handler and reset the value of the clock.
     **********************************************************************************************/
    public View.OnClickListener resetButtonListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            timerHandler.removeCallbacks(timerRunnable);
            int seconds = 0;
            int minutes = 0;
            seconds = seconds % 60;
            clockView.setText(String.format(Locale.US, "%02d:%02d", minutes, seconds));
        }
    };

    /***********************************************************************************************
     * Not used but required to implement onClickListener.
     **********************************************************************************************/
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

    /***********************************************************************************************
     * Change the pour amounts based on the user preferences
     **********************************************************************************************/
    public ArrayList<Integer> replacePours(String brewMethodTitle, List<Integer> waterPours) {
        SharedPreferences pref = getSharedPreferences("preferences", MODE_PRIVATE);
        Integer userServingSize = pref.getInt("pref_key_serving_size", 1);
        ArrayList<Integer> returnedPours = new ArrayList<>();
        if (canMakeMoreThanOnePour(brewMethodTitle)) {
            if (userServingSize == 2) {
                for (int i = 0; i < waterPours.size(); i++) {
                    int newPour = waterPours.get(i) * 2;
                    returnedPours.add(newPour);
                }
            } else {
                for (int i = 0; i < waterPours.size(); i++) {
                    returnedPours.add(waterPours.get(i));
                }
            }
        } else {
            //create notice if device cannot brew more than one cup
            LinearLayout smallDeviceTV = (LinearLayout) findViewById(R.id.small_device_notice);
            if (userServingSize == 2) {
                for (int i = 0; i < waterPours.size(); i++) {
                    returnedPours.add(waterPours.get(i));
                }
                //display notice of ignoring serving size preference
                smallDeviceTV.setVisibility(View.VISIBLE);
            } else {
                //if already visible make invisible
                smallDeviceTV.setVisibility(View.GONE);
                for (int i = 0; i < waterPours.size(); i++) {
                    returnedPours.add(waterPours.get(i));
                }
            }
        }
        for (int j = 0; j < returnedPours.size(); j++) {
            Log.v(LOGTAG, returnedPours.get(j).toString());
        }
        return returnedPours;
    }


    /***********************************************************************************************
     * Change the dose based on user preferences
     **********************************************************************************************/
    public int replaceDose(String brewMethodTitle, int dose) {
        SharedPreferences pref = getSharedPreferences("preferences", MODE_PRIVATE);
        Integer userServingSize = pref.getInt("pref_key_serving_size", 1);
        int newDose;
        if (canMakeMoreThanOnePour(brewMethodTitle)) {
            if (userServingSize == 2) {
                //Serving size is 2
                newDose = dose * 2;

            } else {
                //serving size is one
                newDose = dose;
            }

        } else {
            //device cannot support larger dose
            newDose = dose;
        }

        return newDose;
    }

    /***********************************************************************************************
     * This function takes the instructions and inserts the pour values into the strings as well as
     * getting the user's preferred measurement system and inserting the unit into the statement.
     * ********************************************************************************************/
    public List<String> insertPourValues(List<Integer> waterPours, List<String> instructions) {

        int i = 0;
        for (int j = 0; j < instructions.size(); j++) {
            if (instructions.get(j).contains("INT") && i < waterPours.size()) {
                newDirection = instructions.get(j).replaceAll("INT",
                        Integer.toString(waterPours.get(i)));
                instructions.set(j, newDirection);
                i++;
            }
        }
        return instructions;
    }

    /***********************************************************************************************
     * Checks whether the brew method is on the list for being able to make more than one cup.
     **********************************************************************************************/
    public Boolean canMakeMoreThanOnePour(String brewMethodTitle) {
        switch (brewMethodTitle) {
            case ("Iced Coffee"):
            case ("Aeropress"):
            case ("Hario V-60"):
                return false;
            // if device can make more than one cup
            default:
                return true;
        }
    }


}
