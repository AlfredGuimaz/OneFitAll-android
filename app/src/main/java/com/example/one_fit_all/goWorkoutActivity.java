package com.example.one_fit_all;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;


public class goWorkoutActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_go_workout);

        //Bottom Navagation
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setSelectedItemId(R.id.miWorkout);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()){
                    case R.id.miWorkout:
                        return true;
                    case R.id.miHome:
                        startActivity(new Intent(getApplicationContext(), MainActivity.class));
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.miJournal:
                        startActivity(new Intent(getApplicationContext(), goJournalActivity.class));
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.miSettings:
                        startActivity(new Intent(getApplicationContext(), goSettingsActivity.class));
                        overridePendingTransition(0,0);
                        return true;
                }
                return false;
            }
        });
        //End Bottom Navagation
    }
    //End of onCreate()

    public void goCaloriesBurned(View view){
        Intent intent = new Intent(this, goCaloriesBurned.class);
        startActivity(intent);
    }

    public void goCalorieCounter(View view){
        Intent intent = new Intent(this, goCalorieCounter.class);
        startActivity(intent);
    }

    public void goQuickWorkout(View view){
        Intent intent = new Intent(this, goQuickWorkout.class);
        startActivity(intent);
    }
}