package com.example.one_fit_all;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class goCaloriesBurned extends AppCompatActivity {
    private Button enterBtn;
    private Button resetBtn;
    //private EditText metLevel;
    private SeekBar metSeekBar;
    private EditText weightKg;
    private EditText activityDuration;
    private TextView result;
    private int metNum;
    private boolean resetOn = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_go_calories_burned);

        result = (TextView) findViewById(R.id.result);
        enterBtn = (Button) findViewById(R.id.enterBtn);
        resetBtn = (Button) findViewById(R.id.resetBtn);
        //metLevel = (EditText) findViewById(R.id.metLevel);
        weightKg = (EditText) findViewById(R.id.weightKg);
        activityDuration = (EditText) findViewById(R.id.activityDuration);
        metSeekBar = (SeekBar) findViewById(R.id.metSeekBar);

        metSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                metNum = i;
                if(resetOn == true){
                    resetOn = false;
                }
                else {
                    Toast.makeText(goCaloriesBurned.this, "Met Level: " + i,
                            Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                Toast.makeText(goCaloriesBurned.this, "Thank you for the selection",
                        Toast.LENGTH_SHORT).show();
            }
        });

        enterBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    //String met = metLevel.getText().toString();
                    //double finalMet = Double.parseDouble(met);
                    String weight = weightKg.getText().toString();
                    double finalWeight = Double.parseDouble(weight);
                    String duration = activityDuration.getText().toString();
                    double finalDuration = Double.parseDouble(duration);
                    Toast.makeText(goCaloriesBurned.this, "GOOD JOB!!!",
                            Toast.LENGTH_LONG).show();
                    caloriesBurned(/*finalMet*/metNum, finalWeight, finalDuration);
                }
                catch (NumberFormatException e){
                    result.setText("Oop invalid input. Try Again." );
                }
            }
        });

        resetBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //metLevel.setText("");
                resetOn = true;
                metSeekBar.setProgress(8);
                weightKg.setText("");
                activityDuration.setText("");
                result.setText("Results");
            }
        });

    }

    private void caloriesBurned(int met, double weight, double duration){
        double calBurned = (met * 3.5 * weight * duration)/200;
        String calBurnedString = String.format("%.2f", calBurned);
        result.setText(calBurnedString);
        return;
    }
}