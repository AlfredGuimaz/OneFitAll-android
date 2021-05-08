package com.example.one_fit_all;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class goSettingsActivity extends AppCompatActivity {

    final String[] phrases = {"You looking good!!!", "Keep Pushing!!!", "Nice Smile!!!",
            "Your Pussy Poppin'", "Randy was here! :)"};
    private int nums = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_go_settings);



        //Bottom Navigation
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setSelectedItemId(R.id.miSettings);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.miWorkout:
                        startActivity(new Intent(getApplicationContext(), goWorkoutActivity.class));
                        overridePendingTransition(0, 0);
                        return true;
                    case R.id.miHome:
                        startActivity(new Intent(getApplicationContext(), MainActivity.class));
                        overridePendingTransition(0, 0);
                        return true;
                    case R.id.miJournal:
                        startActivity(new Intent(getApplicationContext(), goJournalActivity.class));
                        overridePendingTransition(0, 0);
                    case R.id.miSettings:
                        return true;
                }
                return false;
            }
        });
        //End Bottom Navagation
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                stayPositive(nums);
                nums++;
                if(nums == 4){
                    nums = 0;
                }
            }
        });
    }

    public void stayPositive(int i){
        Toast.makeText(goSettingsActivity.this, phrases[i],
                Toast.LENGTH_SHORT).show();
    }

   public void UserInfo(View view) {
        Intent intent = new Intent(this, UserInfo.class);
        startActivity(intent);
    }




}