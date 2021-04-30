package com.example.one_fit_all;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;

public class goQuickWorkout extends AppCompatActivity {

    public static final String EXTRA_WORK_MIN = "com.example.one_fit_all.EXTRA_WORK_MIN";
    public static final String EXTRA_REST_MIN = "com.example.one_fit_all.EXTRA_REST_MIN";
    public static final String EXTRA_SETS = "com.example.one_fit_all.EXTRA_SETS";

    private ImageButton subSets;
    private EditText setAmount;
    private ImageButton addSets;

    private ImageButton subWork;
    private EditText workMins;
    private ImageButton addWork;

    private ImageButton subRest;
    private EditText restMins;
    private ImageButton addRest;

    private Button startWorkout;
    private Button resetWorkout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_go_quick_workout);

        subSets = (ImageButton) findViewById(R.id.subSets);
        setAmount = (EditText) findViewById(R.id.setAmount);
        addSets = (ImageButton) findViewById(R.id.addSets);

        subWork = (ImageButton) findViewById(R.id.subWork);
        workMins = (EditText) findViewById(R.id.workMins);
        addWork = (ImageButton) findViewById(R.id.addWork);

        subRest = (ImageButton) findViewById(R.id.subRest);
        restMins = (EditText) findViewById(R.id.restMins);
        addRest = (ImageButton) findViewById(R.id.addRest);

        startWorkout = (Button) findViewById(R.id.startWorkout);
        resetWorkout = (Button) findViewById(R.id.resetWorkout);

        subSets.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int t = Integer.parseInt(setAmount.getText().toString());
                t--;
                if(t <= 1){
                    setAmount.setText("1");
                }
                else {
                    setAmount.setText(String.valueOf(t));
                }
            }
        });
        addSets.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int t = Integer.parseInt(setAmount.getText().toString());
                t++;
                if(t > 3){
                    setAmount.setText(String.valueOf("3"));
                }
                else{
                    setAmount.setText(String.valueOf(t));
                }
            }
        });

        subWork.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int t = Integer.parseInt(workMins.getText().toString());
                t--;
                if(t == -1){
                    workMins.setText("00");
                }
                else if(0<=t && t<10){
                    workMins.setText("0"+String.valueOf(t));
                }
                else{
                    workMins.setText(String.valueOf(t));
                }
            }
        });
        addWork.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int t = Integer.parseInt(workMins.getText().toString());
                t++;
                if(t > 99){
                    workMins.setText(String.valueOf("99"));
                }
                else if(0<=t && t<10){
                    workMins.setText("0"+String.valueOf(t));
                }
                else{
                    workMins.setText(String.valueOf(t));
                }
            }
        });

        subRest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int t = Integer.parseInt(restMins.getText().toString());
                t--;
                if(t == -1){
                    restMins.setText("00");
                }
                else if(0<=t && t<10){
                    restMins.setText("0"+String.valueOf(t));
                }
                else{
                    restMins.setText(String.valueOf(t));
                }
            }
        });
        addRest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int t = Integer.parseInt(restMins.getText().toString());
                t++;
                if(t > 99){
                    restMins.setText(String.valueOf("99"));
                }
                else if(0<=t && t<10){
                    restMins.setText("0"+String.valueOf(t));
                }
                else{
                    restMins.setText(String.valueOf(t));
                }
            }
        });

        startWorkout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int workMinutes = Integer.parseInt(workMins.getText().toString());
                int restMinutes = Integer.parseInt(restMins.getText().toString());
                int sets = Integer.parseInt(setAmount.getText().toString());
                if(sets > 3){
                    sets = 3;
                }

                Intent intent = new Intent(goQuickWorkout.this, countdownTimer.class);
                intent.putExtra(EXTRA_WORK_MIN, workMinutes);
                intent.putExtra(EXTRA_REST_MIN, restMinutes);
                intent.putExtra(EXTRA_SETS, sets);
                startActivity(intent);
            }
        });
        resetWorkout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setAmount.setText("1");
                workMins.setText("00");
                restMins.setText("00");
            }
        });
    }
}