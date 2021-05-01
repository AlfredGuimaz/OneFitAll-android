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
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
//import android.widget.TextView;
import android.widget.Spinner;
import android.widget.Toast;


import com.spotify.protocol.client.Subscription;
import com.spotify.protocol.types.PlayerState;
import com.spotify.protocol.types.Track;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    //Button and Variables for DB
    private Button Confirm;
    private EditText Name, Weight, Feet, Inch, Age;
    SharedPreferences shp;
    SharedPreferences.Editor shpEditor;

    private static final String CLIENT_ID = "f41123809dce4bd29b99d34220e91140";
    private static final String REDIRECT_URI = "http://localhost:8888/callback";
    private SpotifyAppRemote mSpotifyAppRemote;
    private static boolean playing;
    private String selected;
    Spinner spotifySpinner;
    List<String> playlists;

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

        playlists = new ArrayList<String>();
        spotifySpinner = (Spinner) findViewById(R.id.spinner);

        String pl1 = "Workout – 120 BPM";
        String pl2 = "Born To Run 150 BPM";
        String pl3 = "160 BPM Running Pop";
        String pl4 = "Run 'N' Bass 170 – 175 BPM";
        String pl5 = "180 bpm born to run";
        playlists.add(pl1);
        playlists.add(pl2);
        playlists.add(pl3);
        playlists.add(pl4);
        playlists.add(pl5);

        ArrayAdapter<String> myAdapter = new ArrayAdapter<String>(MainActivity.this,
                android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.playlists));
        myAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spotifySpinner.setEnabled(true);
        spotifySpinner.setClickable(true);
        spotifySpinner.setAdapter(myAdapter);

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
                        connected();
                    }

                    public void onFailure(Throwable throwable) {
                        Log.e("MyActivity", throwable.getMessage(), throwable);

                        // Something went wrong when attempting to connect! Handle errors here
                    }
                });
    }

    protected void connected(){
        playing = false;
    }

    @Override
    protected void onStop() {
        super.onStop();
        mSpotifyAppRemote.getPlayerApi().pause();
        SpotifyAppRemote.disconnect(mSpotifyAppRemote);
    }

    public void play(View v){
        selected = spotifySpinner.getSelectedItem().toString();

        if(selected.equals(playlists.get(0))){
            mSpotifyAppRemote.getPlayerApi().play("spotify:playlist:1vdkPd9esYFohPkUxcrUDa");
        }else if(selected.equals(playlists.get(1))){
            mSpotifyAppRemote.getPlayerApi().play("spotify:playlist:37i9dQZF1DX0hWmn8d5pRe");
        }else if(selected.equals(playlists.get(2))){
            mSpotifyAppRemote.getPlayerApi().play("spotify:playlist:6Lzq35kGAEGCupFkxbZVAQ");
        }else if(selected.equals(playlists.get(3))){
            mSpotifyAppRemote.getPlayerApi().play("spotify:playlist:37i9dQZF1DX8jnAPF7Iiqp");
        }else if(selected.equals(playlists.get(4))){
            mSpotifyAppRemote.getPlayerApi().play("spotify:playlist:48vCjagB5NXob0LeRz4uUa");
        }
    }
    /*
    LIMITATIONS: user can only skip 6 songs per hour because of the free version
    and they must listen to playlists and albums on shuffle
    and they cannot skip ads.
     */
    public void skipSong(View v){
        mSpotifyAppRemote.getPlayerApi().skipNext();
    }

    public void pauseOrResumeSong(View v){
        if(playing == true){
            mSpotifyAppRemote.getPlayerApi().pause();
            playing = false;
        }else{
            mSpotifyAppRemote.getPlayerApi().resume();
            playing = true;
        }
    }

    public void previousSong(View v){
        mSpotifyAppRemote.getPlayerApi().skipPrevious();
    }
}