package com.brewguide.android.coffeebrewguide;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.Toast;

public class MenuActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        int position;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //retrieves intent
        Intent i = getIntent();

        //fetches menu option that was selected.
        // 0  is the default
        position = i.getIntExtra("position", 0);

        // get an instance of FragmentTransaction from your Activity
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        Log.v("menu", "position is: " + position);
        switch (position){
            case 0:
                // launch main activity when Item is clicked
                Intent intent = new Intent(getBaseContext(), MainActivity.class);
                //MainActivity.this.startActivity(new Intent(MainActivity.this, BrewMethodActivity.class));
                startActivity(intent);
                break;
            case 1:
                RecipeFragment recipeFragment = new RecipeFragment();
                fragmentTransaction.add(R.id.myfragment, recipeFragment);
                fragmentTransaction.commit();
                break;
            case 2:
                fragmentTransaction.replace(R.id.myfragment, new SettingsFragment())
                        .commit();
                break;
            case 3:
                FeedbackFragment feedbackFragment = new FeedbackFragment();
                fragmentTransaction.add(R.id.myfragment, feedbackFragment);
                fragmentTransaction.commit();
                break;
            default:
                Toast toast = Toast.makeText(this,
                        "An Error has occurred.",
                        Toast.LENGTH_SHORT);
                toast.show();
                break;
        }
        if(getSupportActionBar()!= null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

    }

}
