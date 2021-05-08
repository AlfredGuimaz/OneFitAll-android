package com.example.one_fit_all;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import android.database.Cursor;


import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;



public class UserInfo extends AppCompatActivity {
    Button ViewData, Update;
    EditText Name, Weight, Feet, Inch, Age;
    Database db;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Create New XML file for login page
        setContentView(R.layout.activity_user_info);
        db = new Database(this);
        Update = findViewById(R.id.Update);
        //ViewAll = findViewById(R.id.ViewAll);
        Name = findViewById(R.id.Name);
        Weight = findViewById(R.id.Weight);
        Feet = findViewById(R.id.Feet);
        Inch = findViewById(R.id.Inch);
        Age = findViewById(R.id.Age);
        //  DB = new Database(this);
        ViewData = findViewById(R.id.ViewData);

        ViewData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Cursor res = db.getData();
                if(res.getCount() == 0) {
                    Toast.makeText(UserInfo.this, "No Entry Exists", Toast.LENGTH_SHORT).show();
                    return;
                }
                StringBuffer buffer = new StringBuffer();
                while(res.moveToNext()) {
                    buffer.append("Name :" +res.getString(0) +"\n");
                    buffer.append("Weight :" +res.getString(1) +"\n");
                    buffer.append("Age :" +res.getString(2) +"\n");
                    buffer.append("Feet :" +res.getString(3) +"\n");
                    buffer.append("Inch :" +res.getString(4) +"\n");
                }
                AlertDialog.Builder builder = new AlertDialog.Builder(UserInfo.this);
                builder.setCancelable(true);
                builder.setTitle("User Entries");
                builder.setMessage(buffer.toString());
                builder.show();

            }
        });


        Update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                boolean isUpdate = db.updateDB(Name.getText().toString(), Integer.parseInt(Weight.getText().toString()), Integer.parseInt(Age.getText().toString()), Integer.parseInt(Feet.getText().toString()), Integer.parseInt(Inch.getText().toString()));
                if (isUpdate == true) {
                    Toast.makeText(UserInfo.this, "User Updated", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(UserInfo.this, "Error Updating User", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

}





