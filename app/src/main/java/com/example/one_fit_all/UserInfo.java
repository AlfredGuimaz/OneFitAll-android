package com.example.one_fit_all;


import androidx.appcompat.app.AppCompatActivity;


import android.os.Bundle;



import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.List;


public class UserInfo extends AppCompatActivity {
    Button ViewData, Update;
    EditText Name2, Weight2, Feet2, Inch2, Age2, Id;
    Database db;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Create New XML file for login page
        setContentView(R.layout.activity_user_info);

        db = new Database(this);
        Update = findViewById(R.id.Update);
        //ViewAll = findViewById(R.id.ViewAll);
        Name2 = findViewById(R.id.Name2);
        Weight2 = findViewById(R.id.Weight2);
        Feet2 = findViewById(R.id.Feet2);
        Inch2 = findViewById(R.id.Inch2);
        Age2 = findViewById(R.id.Age2);
        //  DB = new Database(this);
        ViewData = findViewById(R.id.ViewData);

        ViewData.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Database database = new Database(UserInfo.this);
                List<CustomerClass> everyone = database.getEveryone();
                Toast.makeText(UserInfo.this, everyone.toString(), Toast.LENGTH_SHORT).show();

            }
});

          /*  public void onClick(View view) {
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
        }); */


        Update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //String newID = Id.getText().toString();

                boolean isUpdate = db.updateDB(Name2.getText().toString(), Integer.parseInt(Weight2.getText().toString()), Integer.parseInt(Age2.getText().toString()),Integer.parseInt(Feet2.getText().toString()), Integer.parseInt(Inch2.getText().toString()));
                if (isUpdate == true) {
                    Toast.makeText(UserInfo.this, "User Updated", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(UserInfo.this, "Error Updating User", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

}





