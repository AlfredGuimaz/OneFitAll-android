package com.example.one_fit_all;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;


public class AddCountryActivity extends Activity implements View.OnClickListener {

    private Button addTodoBtn;
    private EditText subjectEditText;
    private EditText descEditText;
    private DBManager dbManager;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("Add Record");
        setContentView(R.layout.activity_add_country);

        subjectEditText= findViewById(R.id.subject_edittext);
        descEditText   = findViewById(R.id.description_edittext);
        addTodoBtn     = findViewById(R.id.add_record);




        dbManager = new DBManager(this);
        dbManager.open();
        addTodoBtn.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        if(subjectEditText.length()==0)
        { subjectEditText.setError("Enter title");
        }
        else if(descEditText.length()==0){
            descEditText.setError("enter description");
        }
        else {
            Toast.makeText(AddCountryActivity.this, "record added", Toast.LENGTH_SHORT).show();

            switch (v.getId()) {
                case R.id.add_record:
                    final String name = subjectEditText.getText().toString();
                    final String desc = descEditText.getText().toString();

                    dbManager.insert(name, desc);

                    Intent main = new Intent(AddCountryActivity.this,
                            goJournalActivity.class)
                            .setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(main);
                    break;
            } }

    }
}