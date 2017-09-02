package com.brewguide.android.coffeebrewguide;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    String newDirection;
    List<String> instructions;
    List<Integer> brewPours;
    BrewMethod brewMethod;
    ArrayList<BrewMethod> brewMethodList;
    final String LOGTAG = this.getClass().getSimpleName();
    DrawerLayout mDrawerLayout;
    ListView mDrawerList;
    private ActionBarDrawerToggle mDrawerToggle;

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
        //mNavigationDrawerTitles = getResources().getStringArray(R.array.navigation_drawer_array);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerList = (ListView) findViewById(R.id.left_drawer);
        Toolbar mToolbar = (Toolbar) findViewById(R.id.toolbar);

        NavigationDrawerItem home = new NavigationDrawerItem(R.drawable.ic_home , getString(R.string.navigation_drawer_home));
        NavigationDrawerItem recipe = new NavigationDrawerItem(R.drawable.ic_grinder , getString(R.string.navigation_drawer_recipe));
        NavigationDrawerItem settings = new NavigationDrawerItem(R.drawable.ic_settings , getString(R.string.navigation_drawer_settings));
        NavigationDrawerItem feedback = new NavigationDrawerItem(R.drawable.ic_give_love , getString(R.string.navigation_drawer_feedback));

        //create arraylist of menu items
        ArrayList<NavigationDrawerItem> menu = new ArrayList<>();
        menu.add(home);
        menu.add(recipe);
        menu.add(settings);
        menu.add(feedback);

        // Set the adapter for the list view
        mDrawerList.setAdapter(new NavigationDrawerAdapter(
                this,
                R.layout.drawer_list_item,
                menu
                ));

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
        if(getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        getSupportActionBar().setHomeButtonEnabled(true);
        mDrawerToggle.syncState();

        // Set the drawer toggle as the DrawerListener
        mDrawerLayout.addDrawerListener(mDrawerToggle);
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

        instructions = Arrays.asList(getResources().getStringArray(R.array.instructions_aeropress_array));
        instructions = replaceUnits(instructions);
        brewPours = new ArrayList<>();
        brewPours.add(getResources().getInteger(R.integer.aeropress_pour_1));

        return new BrewMethod(
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
    }

    public BrewMethod getFrenchPress() {

        instructions = Arrays.asList(getResources().getStringArray(R.array.instructions_frenchpress_array));
        instructions = replaceUnits(instructions);
        brewPours = new ArrayList<>();
        brewPours.add(getResources().getInteger(R.integer.french_press_pour_1));
        brewPours.add(getResources().getInteger(R.integer.french_press_pour_2));

        return new BrewMethod(
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
    }

    public BrewMethod getChemex() {

        instructions = Arrays.asList(getResources().getStringArray(R.array.instructions_chemex_array));
        instructions = replaceUnits(instructions);
        brewPours = new ArrayList<>();
        brewPours.add(getResources().getInteger(R.integer.chemex_pour_1));
        brewPours.add(getResources().getInteger(R.integer.chemex_pour_2));
        brewPours.add(getResources().getInteger(R.integer.chemex_pour_3));
        brewPours.add(getResources().getInteger(R.integer.chemex_pour_4));

        return new BrewMethod(
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
    }

    public BrewMethod getHarioV60() {

        instructions = Arrays.asList(getResources().getStringArray(R.array.instructions_harioV60_array));
        instructions = replaceUnits(instructions);
        brewPours = new ArrayList<>();
        brewPours.add(getResources().getInteger(R.integer.hario_v60_pour1));
        brewPours.add(getResources().getInteger(R.integer.hario_v60_pour2));
        brewPours.add(getResources().getInteger(R.integer.hario_v60_pour3));
        brewPours.add(getResources().getInteger(R.integer.hario_v60_pour4));

        return new BrewMethod(
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
    }

    public BrewMethod getIcedCoffee() {

        //replace units in directions
        instructions = Arrays.asList(getResources().getStringArray(R.array.instructions_cold_brew_array));
        instructions = replaceUnits(instructions);
        brewPours = new ArrayList<>();
        brewPours.add(getResources().getInteger(R.integer.cold_brew_pour_1));

        return new BrewMethod(
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
        final Context context = getApplicationContext();
        Intent intent = new Intent(getBaseContext(), MenuActivity.class);
        intent.putExtra("position", position);
        startActivity(intent);
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
