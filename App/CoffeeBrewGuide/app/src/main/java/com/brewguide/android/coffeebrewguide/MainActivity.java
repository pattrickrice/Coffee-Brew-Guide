package com.brewguide.android.coffeebrewguide;

import android.content.Intent;
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

    BrewMethod brewMethod;
    ArrayList<BrewMethod> brewMethodList;


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
                Toast.makeText(MainActivity.this, "" + brewMethod.getmMethodName(),
                        Toast.LENGTH_SHORT).show();
            }
        });

    }

    /**
    * Create Arraylist with containing all of the brew methods
    * */
    public ArrayList<BrewMethod> getBrewMethodList(){


        BrewMethod aeropress = new BrewMethod(
                getResources().getString(R.string.title_aeropress),
                new ArrayList<>(Arrays.asList(getResources().getStringArray(R.array.instructions_aeropress_array))),
                1,
                16,
                org.joda.time.Duration.millis(90000),
                getResources().getString(R.string.grind_size_medium),
                R.drawable.aeropress,
                R.drawable.aeropress,
                getResources().getString(R.string.bio_aeropress)
        );

        BrewMethod frenchPress = new BrewMethod(
                getResources().getString(R.string.title_activity_french_press),
                new ArrayList<>(Arrays.asList(getResources().getStringArray(R.array.instructions_frenchpress_array))),
                1,
                25,
                org.joda.time.Duration.millis(240000),
                getResources().getString(R.string.grind_size_coarse),
                R.drawable.french_press,
                R.drawable.french_press,
                getResources().getString(R.string.bio_frenchpress)
        );

        BrewMethod chemex = new BrewMethod(
                getResources().getString(R.string.title_activity_chemex),
                new ArrayList<>(Arrays.asList(getResources().getStringArray(R.array.instructions_chemex_array))),
                1,
                25,
                org.joda.time.Duration.millis(240000),
                getResources().getString(R.string.grind_size_coarse),
                R.drawable.chemex,
                R.drawable.chemex,
                getResources().getString(R.string.bio_chemex)
        );
        BrewMethod harioV60 = new BrewMethod(
                getResources().getString(R.string.title_activity_hario_v60),
                new ArrayList<>(Arrays.asList(getResources().getStringArray(R.array.instructions_harioV60_array))),
                1,
                50,
                org.joda.time.Duration.millis(14400000),
                getResources().getString(R.string.grind_size_medium),
                R.drawable.hariov60,
                R.drawable.hariov60,
                getResources().getString(R.string.bio_harioV60)
        );

        BrewMethod icedCoffee = new BrewMethod(
                getResources().getString(R.string.title_activity_iced_coffee),
                new ArrayList<>(Arrays.asList(getResources().getStringArray(R.array.instructions_cold_brew_array))),
                1,
                50,
                org.joda.time.Duration.millis(14400000),
                getResources().getString(R.string.grind_size_coarse),
                R.drawable.iced_coffee,
                R.drawable.iced_coffee,
                getResources().getString(R.string.bio_cold_brew)
        );

        brewMethodList = new ArrayList<>();
        brewMethodList.add(aeropress);
        brewMethodList.add(frenchPress);
        brewMethodList.add(chemex);
        brewMethodList.add(harioV60);
        brewMethodList.add(icedCoffee);

        return brewMethodList;
    };



}
