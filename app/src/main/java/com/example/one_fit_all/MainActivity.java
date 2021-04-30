package com.example.one_fit_all;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.content.SharedPreferences;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import com.spotify.android.appremote.api.ConnectionParams;
import com.spotify.android.appremote.api.Connector;
import com.spotify.android.appremote.api.SpotifyAppRemote;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
//import android.widget.TextView;
import android.widget.Toast;


import com.spotify.protocol.client.Subscription;
import com.spotify.protocol.types.PlayerState;
import com.spotify.protocol.types.Track;

public class MainActivity extends AppCompatActivity {

    //Button and Variables for DB
    private Button Confirm;
    private EditText Name, Weight, Feet, Inch, Age;
    SharedPreferences shp;
    SharedPreferences.Editor shpEditor;

    private static final String CLIENT_ID = "f41123809dce4bd29b99d34220e91140";
    private static final String REDIRECT_URI = "http://localhost:8888/callback";
    private SpotifyAppRemote mSpotifyAppRemote;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        shp = getSharedPreferences("myPreferences", MODE_PRIVATE);
        checkConfirm();
       /* Confirm = findViewById(R.id.Confirm);
        //ViewAll = findViewById(R.id.ViewAll);
        Name = findViewById(R.id.Name);
        Weight = findViewById(R.id.Weight);
        Feet = findViewById(R.id.Feet);
        Inch = findViewById(R.id.Inch);
        Age = findViewById(R.id.Age);
*/
      /*  Confirm.setOnClickListener(new View.OnClickListener() {
            @Override
           public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(intent);


                CustomerClass customerClass;

                try {
                    customerClass = new CustomerClass(Name.getText().toString(), Integer.parseInt(Weight.getText().toString()), Integer.parseInt(Age.getText().toString()), Integer.parseInt(Feet.getText().toString()), Integer.parseInt(Inch.getText().toString()));
                    Toast.makeText(MainActivity.this, customerClass.toString(), Toast.LENGTH_SHORT).show();
                } catch (Exception e) {
                    Toast.makeText(MainActivity.this, "Error Creating User", Toast.LENGTH_SHORT).show();
                    customerClass = new CustomerClass("error", 0, 0, 0, 0);

                }


                Database dataBase = new Database(MainActivity.this);
                boolean success = dataBase.addOne(customerClass);
                Toast.makeText(MainActivity.this, "Success = " + success, Toast.LENGTH_SHORT).show();
            }
        });
*/
        setContentView(R.layout.activity_main);

        //Bottom Navagation
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setSelectedItemId(R.id.miHome);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()){
                    case R.id.miWorkout:
                        startActivity(new Intent(getApplicationContext(), goWorkoutActivity.class));
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.miHome:
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

    public void checkConfirm() {
        if (shp == null) {
            shp = getSharedPreferences("myPreferences", MODE_PRIVATE);
        }

        String Name = shp.getString("name", "");

        if (Name != null && !Name.equals("")) {
            Toast.makeText(MainActivity.this, "Welcome", Toast.LENGTH_SHORT).show();

        } else
        {
            Intent i = new Intent(MainActivity.this, LoginActivity.class);
            startActivity(i);
            finish();
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        ConnectionParams connectionParams =
                new ConnectionParams.Builder(CLIENT_ID)
                        .setRedirectUri(REDIRECT_URI)
                        .showAuthView(true)
                        .build();

        SpotifyAppRemote.connect(this, connectionParams,
                new Connector.ConnectionListener() {

                    public void onConnected(SpotifyAppRemote spotifyAppRemote) {
                        mSpotifyAppRemote = spotifyAppRemote;
                        Log.d("MainActivity", "Connected!");

                        // Now you can start interacting with App Remote
                        connected();

                    }

                    public void onFailure(Throwable throwable) {
                        Log.e("MyActivity", throwable.getMessage(), throwable);

                        // Something went wrong when attempting to connect! Handle errors here
                    }
                });
    }

    protected void connected(){
        //plays a hardcoded playlist
        //mSpotifyAppRemote.getPlayerApi().play("spotify:track:1BpKJw4RZxaFB88NE5uxXf");

        // Subscribe to PlayerState
       /* mSpotifyAppRemote.getPlayerApi()
                .subscribeToPlayerState()
                .setEventCallback(playerState -> {
                    final Track track = playerState.track;
                    if (track != null) {
                        Log.d("MainActivity", track.name + " by " + track.artist.name);
                    }
                });*/
    }

    @Override
    protected void onStop() {
        super.onStop();
        SpotifyAppRemote.disconnect(mSpotifyAppRemote);
    }
}