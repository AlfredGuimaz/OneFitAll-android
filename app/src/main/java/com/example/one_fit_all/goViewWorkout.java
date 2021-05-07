package com.example.one_fit_all;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class goViewWorkout extends AppCompatActivity {

    private EditText muscleGroup;
    private EditText exerciseType;
    private EditText viewSets;
    private EditText viewReps;
    private EditText viewWeights;

    private Button restWorkoutBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_go_view_workout);

        muscleGroup = findViewById(R.id.muscleGroup);
        exerciseType = findViewById(R.id.exerciseType);
        viewSets = findViewById(R.id.viewSets);
        viewReps = findViewById(R.id.viewReps);
        viewWeights = findViewById(R.id.viewWeights);

        restWorkoutBtn = findViewById(R.id.restWorkoutBtn);
        restWorkoutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                muscleGroup.setText(" ");
                exerciseType.setText(" ");
                viewSets.setText(" ");
                viewReps.setText(" ");
                viewWeights.setText(" ");
            }
        });
    }
}