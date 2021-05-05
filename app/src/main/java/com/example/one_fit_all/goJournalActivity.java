package com.example.one_fit_all;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.provider.Telephony;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class goJournalActivity extends AppCompatActivity {

    final String[] phrases = {"You looking good!!!", "Keep Pushing!!!", "Nice Smile!!!",
            "Your Pussy Poppin'", "Randy was here! :)"};
    private int nums = 0;


    private DBManager dbManager;
    private ListView listView;
    private SimpleCursorAdapter adapter;

    final String[] from = new String[] {DatabaseHelper._ID,
            DatabaseHelper.SUBJECT, DatabaseHelper.DESC};

    final int[] to = new int[]  {R.id.id, R.id.title, R.id.desc};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        ActionBar actionBar = getSupportActionBar();
        actionBar.show();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_go_journal);

        dbManager = new DBManager(this);
        dbManager.open();
        Cursor cursor = dbManager.fetch();
        listView = findViewById(R.id.list_view);
        listView.setEmptyView(findViewById(R.id.empty));

        adapter = new SimpleCursorAdapter(this, R.layout.activity_view_record,
                cursor, from, to, 0);


        adapter.notifyDataSetChanged();
        listView.setAdapter(adapter);


        //Bottom Navagation
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setSelectedItemId(R.id.miJournal);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()){
                    case R.id.miWorkout:
                        startActivity(new Intent(getApplicationContext(), goWorkoutActivity.class));
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.miHome:
                        startActivity(new Intent(getApplicationContext(), MainActivity.class));
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.miJournal:

                        return true;
                    case R.id.miSettings:
                        startActivity(new Intent(getApplicationContext(), goSettingsActivity.class));
                        overridePendingTransition(0,0);
                        return true;
                }
                return false;
            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long viewid) {
                TextView idTextView = view.findViewById(R.id.id);
                TextView titleTextView = view.findViewById(R.id.title);
                TextView descTextView = view.findViewById(R.id.desc);



                String id = idTextView.getText().toString();
                String title = titleTextView.getText().toString();
                String desc = descTextView.getText().toString();


                Intent modify_intent = new Intent (getApplicationContext(),
                        ModifyCountryActivity.class);
                modify_intent.putExtra("title",title);
                modify_intent.putExtra("desc",desc);
                modify_intent.putExtra("id",id);

                startActivity(modify_intent);

            }
        });

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
        Toast.makeText(goJournalActivity.this, phrases[i],
                Toast.LENGTH_SHORT).show();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id=item.getItemId();
        if(id== R.id.add_record){
            Intent add_mem = new Intent (this,AddCountryActivity.class);
            startActivity(add_mem);
        }
        return super.onOptionsItemSelected(item);
    }

}



// See If we can add the bottom navigation later


//package com.example.one_fit_all;
//
//import androidx.annotation.NonNull;
//import androidx.appcompat.app.AppCompatActivity;
//
//import android.content.Intent;
//import android.os.Bundle;
//import android.view.MenuItem;
//
//import com.google.android.material.bottomnavigation.BottomNavigationView;
//
//public class goJournalActivity extends AppCompatActivity {
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_go_journal);
//
//        //End Bottom Navagation
//        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigationView);
//        bottomNavigationView.setSelectedItemId(R.id.miJournal);
//        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
//            @Override
//            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
//                switch (menuItem.getItemId()){
//                    case R.id.miWorkout:
//                        startActivity(new Intent(getApplicationContext(), goWorkoutActivity.class));
//                        overridePendingTransition(0,0);
//                        return true;
//                    case R.id.miHome:
//                        startActivity(new Intent(getApplicationContext(), MainActivity.class));
//                        overridePendingTransition(0,0);
//                        return true;
//                    case R.id.miJournal:
//                        return true;
//                    case R.id.miSettings:
//                        startActivity(new Intent(getApplicationContext(), goSettingsActivity.class));
//                        overridePendingTransition(0,0);
//                        return true;
//                }
//                return false;
//            }
//        });
//        //Bottom Navagation
//    }
//}