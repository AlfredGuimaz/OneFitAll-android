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

                String idText = "0";
                String nameTxt = Name.getText().toString();
                String weightTxt = Weight.getText().toString();
                //int finalWeightTxt = Integer.parseInt(weightTxt);
                String feetTxt = Feet.getText().toString();
                //int finalFeetTxt = Integer.parseInt(feetTxt);
                String inchTxt = Inch.getText().toString();
                //int finalInchTxt = Integer.parseInt(inchTxt);
                String ageTxt = Age.getText().toString();
                //int finalAgeTxt = Integer.parseInt(ageTxt);

                boolean isUpdate = db.updateDB(idText, nameTxt, weightTxt, ageTxt, feetTxt, inchTxt/*nameTxt, finalWeightTxt, finalAgeTxt, finalFeetTxt, finalInchTxt*/);
                if (isUpdate == true) {
                    Toast.makeText(UserInfo.this, "User Updated", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(UserInfo.this, "Error Updating User", Toast.LENGTH_SHORT).show();
                }


            }
        });
    }

}





