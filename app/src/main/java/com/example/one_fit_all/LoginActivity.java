package com.example.one_fit_all;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity {
    SharedPreferences shp;
    SharedPreferences.Editor shpEditor;
    Button Confirm;
    EditText Name, Weight, Feet, Inch, Age;
    //Database DB;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Create New XML file for login page
        setContentView(R.layout.activity_go_login_page);

        Confirm = findViewById(R.id.Confirm);
        //ViewAll = findViewById(R.id.ViewAll);
        Name = findViewById(R.id.Name);
        Weight = findViewById(R.id.Weight);
        Feet = findViewById(R.id.Feet);
        Inch = findViewById(R.id.Inch);
        Age = findViewById(R.id.Age);
        //  DB = new Database(this);
        shp = getSharedPreferences("myPreferences", MODE_PRIVATE);
        checkConfirm();
        Confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                CustomerClass customerClass;

                try {
                    customerClass = new CustomerClass(Name.getText().toString(), Integer.parseInt(Weight.getText().toString()), Integer.parseInt(Age.getText().toString()), Integer.parseInt(Feet.getText().toString()), Integer.parseInt(Inch.getText().toString()));
                    Toast.makeText(LoginActivity.this, customerClass.toString(), Toast.LENGTH_SHORT).show();
                } catch (Exception e) {
                    Toast.makeText(LoginActivity.this, "Error Creating User", Toast.LENGTH_SHORT).show();
                    customerClass = new CustomerClass("error", 0, 0, 0, 0);

                }

                Database dataBase = new Database(LoginActivity.this);
                boolean success = dataBase.addOne(customerClass);
                //DEBUG Success
                Toast.makeText(LoginActivity.this, "Success = " + success, Toast.LENGTH_SHORT).show();
                //intent = new Intent(getApplicationContext(), MainActivity.class);
                if(Name.getText().toString().equals("")){
                    Toast.makeText(LoginActivity.this, "Need to input a name ",Toast.LENGTH_SHORT).show();
                }else{
                    DoLogin(Name.getText().toString());
                }


            }
        });


    }

           public void checkConfirm() {
                if (shp == null)
                    shp = getSharedPreferences("myPreferences", MODE_PRIVATE);

                String Name = shp.getString("name", "");

                if (Name != null && !Name.equals("")) {
                    Intent i = new Intent(LoginActivity.this, MainActivity.class);
                    startActivity(i);
                    finish();
                }
            }


    public void DoLogin(String Name) {
        try {
                if (shp == null)
                    shp = getSharedPreferences("myPreferences", MODE_PRIVATE);

                shpEditor = shp.edit();
                shpEditor.putString("name", Name);
                shpEditor.commit();

                Intent i = new Intent(LoginActivity.this, MainActivity.class);
                startActivity(i);
                finish();
        } catch (Exception ex) {
            Toast.makeText(LoginActivity.this, "Invalid", Toast.LENGTH_SHORT).show();
        }
    }





}




