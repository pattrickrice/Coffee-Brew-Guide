package com.brewguide.android.coffeebrewguide;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;

public class MainActivity extends AppCompatActivity {

    String newDirection;
    List<String> instructions;
    List<Integer> brewPours;
    BrewMethod brewMethod;
    ArrayList<BrewMethod> brewMethodList;
    final String LOGTAG = this.getClass().getSimpleName();

    private String[] mNavigationDrawerTitles;
    private DrawerLayout mDrawerLayout;
    private ListView mDrawerList;
    private ActionBarDrawerToggle mDrawerToggle;
    private CharSequence mDrawerTitle;
    private CharSequence mTitle;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        GridView gridview = (GridView) findViewById(R.id.gridview);

        brewMethodList = getBrewMethodList();
        gridview.setAdapter(new ImageAdapter(this, brewMethodList));

        gridview.setOnItemClickListener(new OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v,
                                    int position, long id) {
                // Target specific brewmethod and launch an activity
                brewMethod = brewMethodList.get(position);
                // launch new activity when Item is clicked
                Intent intent = new Intent(getBaseContext(), BrewMethodActivity.class);
                //MainActivity.this.startActivity(new Intent(MainActivity.this, BrewMethodActivity.class));
                intent.putExtra("brew_method", brewMethod);
                startActivity(intent);
                //used to indicate position of tile and verify item click functionality is working
            }
        });

        //get resources for the sliding drawer
        mNavigationDrawerTitles = getResources().getStringArray(R.array.navigation_drawer_array);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerList = (ListView) findViewById(R.id.left_drawer);
        Toolbar mToolbar = (Toolbar) findViewById(R.id.toolbar);


        // Set the adapter for the list view
        mDrawerList.setAdapter(new NavigationDrawerAdapter(
                this,
                R.layout.drawer_list_item,
                //Navigation drawer Objects
                );
        // Set the list's click listener
        mDrawerList.setOnItemClickListener(new DrawerItemClickListener());

        //mTitle = mDrawerTitle = getTitle();
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerToggle = new ActionBarDrawerToggle(
                this,
                mDrawerLayout,
                mToolbar,
                R.string.drawer_open,
                R.string.drawer_close) {

            /** Called when a drawer has settled in a completely closed state. */
            public void onDrawerClosed(View view) {
                super.onDrawerClosed(view);
                //getActionBar().setTitle(mTitle);
                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }

            /** Called when a drawer has settled in a completely open state. */
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                //getActionBar().setTitle(mDrawerTitle);
                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }
        };

        //enable arrow which triggers onOptionsItemSelected()
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        mDrawerToggle.syncState();

        // Set the drawer toggle as the DrawerListener
        mDrawerLayout.setDrawerListener(mDrawerToggle);
    }

    /**
     * Create Arraylist with containing all of the brew methods
     */
    public ArrayList<BrewMethod> getBrewMethodList() {
        brewMethodList = new ArrayList<>();
        brewMethodList.add(getAeropress());
        brewMethodList.add(getFrenchPress());
        brewMethodList.add(getChemex());
        brewMethodList.add(getHarioV60());
        brewMethodList.add(getIcedCoffee());
        return brewMethodList;
    }

    public BrewMethod getAeropress() {

        //replace units in directions
        instructions = Arrays.asList(getResources().getStringArray(R.array.instructions_aeropress_array));
        instructions = replaceUnits(instructions);
        brewPours = new ArrayList<>();
        brewPours.add(getResources().getInteger(R.integer.aeropress_pour_1));


        BrewMethod aeropress = new BrewMethod(
                getResources().getString(R.string.title_aeropress),
                new ArrayList<>(instructions),
                new ArrayList<>(brewPours),
                getServingSize(),
                16,
                org.joda.time.Duration.millis(90000),
                getResources().getString(R.string.grind_size_medium),
                R.drawable.aeropress,
                R.drawable.aeropress,
                getResources().getString(R.string.bio_aeropress)
        );
        return aeropress;
    }

    public BrewMethod getFrenchPress() {

        instructions = Arrays.asList(getResources().getStringArray(R.array.instructions_frenchpress_array));
        instructions = replaceUnits(instructions);
        brewPours = new ArrayList<>();
        brewPours.add(getResources().getInteger(R.integer.french_press_pour_1));
        brewPours.add(getResources().getInteger(R.integer.french_press_pour_2));

        BrewMethod frenchPress = new BrewMethod(
                getResources().getString(R.string.title_activity_french_press),
                new ArrayList<>(instructions),
                new ArrayList<>(brewPours),
                getServingSize(),
                25,
                org.joda.time.Duration.millis(240000),
                getResources().getString(R.string.grind_size_coarse),
                R.drawable.french_press,
                R.drawable.french_press,
                getResources().getString(R.string.bio_frenchpress)
        );

        return frenchPress;
    }

    public BrewMethod getChemex() {

        instructions = Arrays.asList(getResources().getStringArray(R.array.instructions_chemex_array));
        instructions = replaceUnits(instructions);
        brewPours = new ArrayList<>();
        brewPours.add(getResources().getInteger(R.integer.chemex_pour_1));
        brewPours.add(getResources().getInteger(R.integer.chemex_pour_2));
        brewPours.add(getResources().getInteger(R.integer.chemex_pour_3));
        brewPours.add(getResources().getInteger(R.integer.chemex_pour_4));

        BrewMethod chemex = new BrewMethod(
                getResources().getString(R.string.title_activity_chemex),
                new ArrayList<>(instructions),
                new ArrayList<>(brewPours),
                getServingSize(),
                25,
                org.joda.time.Duration.millis(240000),
                getResources().getString(R.string.grind_size_coarse),
                R.drawable.chemex,
                R.drawable.chemex,
                getResources().getString(R.string.bio_chemex)
        );

        return chemex;
    }

    public BrewMethod getHarioV60() {

        instructions = Arrays.asList(getResources().getStringArray(R.array.instructions_harioV60_array));
        instructions = replaceUnits(instructions);
        brewPours = new ArrayList<>();
        brewPours.add(getResources().getInteger(R.integer.hario_v60_pour1));
        brewPours.add(getResources().getInteger(R.integer.hario_v60_pour2));
        brewPours.add(getResources().getInteger(R.integer.hario_v60_pour3));
        brewPours.add(getResources().getInteger(R.integer.hario_v60_pour4));

        BrewMethod harioV60 = new BrewMethod(
                getResources().getString(R.string.title_activity_hario_v60),
                new ArrayList<>(instructions),
                new ArrayList<>(brewPours),
                getServingSize(),
                50,
                org.joda.time.Duration.millis(165000),
                getResources().getString(R.string.grind_size_medium),
                R.drawable.hariov60,
                R.drawable.hariov60,
                getResources().getString(R.string.bio_harioV60)
        );

        return harioV60;
    }

    public BrewMethod getIcedCoffee() {

        //replace units in directions
        instructions = Arrays.asList(getResources().getStringArray(R.array.instructions_cold_brew_array));
        instructions = replaceUnits(instructions);
        brewPours = new ArrayList<>();
        brewPours.add(getResources().getInteger(R.integer.cold_brew_pour_1));

        BrewMethod icedCoffee = new BrewMethod(
                getResources().getString(R.string.title_activity_iced_coffee),
                new ArrayList<>(instructions),
                new ArrayList<>(brewPours),
                getServingSize(),
                115,
                org.joda.time.Duration.millis(57600000),
                getResources().getString(R.string.grind_size_coarse),
                R.drawable.iced_coffee,
                R.drawable.iced_coffee,
                getResources().getString(R.string.bio_cold_brew)
        );
        return icedCoffee;
    }

    public int getServingSize() {
        // first param is name of file, second is the context
        // mode private means only this application can access this file
        SharedPreferences pref = getApplicationContext().getSharedPreferences("preferences", MODE_PRIVATE);

        //first paramn is key, second will be default value if none is found.
        return pref.getInt("pref_key_serving_size", 1);
    }

    /**
     * Takes directions as input and replaces UNITS with units.
     * TODO: choose units from preferences
     */
    public List<String> replaceUnits(List<String> instructions) {

        SharedPreferences pref = getSharedPreferences("preferences", MODE_PRIVATE);
        String userUnitPreference = pref.getString("pref_key_measurement_system", "grams");

        for (int i = 0; i < instructions.size(); i++) {
            newDirection = instructions.get(i).replaceAll("UNITS", userUnitPreference);
            instructions.set(i, newDirection);
        }
        return instructions;
    }

    private class DrawerItemClickListener implements ListView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            selectItem(position);
        }
    }

    /**
     * Swaps fragments in the main content view
     */
    private void selectItem(int position) {

        // Create a new fragment and specify the planet to show based on position
//        Fragment fragment = new PlanetFragment();
//        Bundle args = new Bundle();
//        args.putInt(PlanetFragment.ARG_PLANET_NUMBER, position);
//        fragment.setArguments(args);
//
//        // Insert the fragment by replacing any existing fragment
//        FragmentManager fragmentManager = getFragmentManager();
//        fragmentManager.beginTransaction()
//                .replace(R.id.content_frame, fragment)
//                .commit();
//
//        // Highlight the selected item, update the title, and close the drawer
//        mDrawerList.setItemChecked(position, true);
//        setTitle(mPlanetTitles[position]);
//        mDrawerLayout.closeDrawer(mDrawerList);
    }

    @Override
    public void setTitle(CharSequence title) {
//        mTitle = title;
//        getActionBar().setTitle(mTitle);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Pass the event to ActionBarDrawerToggle
        // If it returns true, then it has handled
        // the nav drawer indicator touch event
        if (mDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }

        // Handle your other action bar items...

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        mDrawerToggle.syncState();
    }

}
