package com.example.one_fit_all;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.List;

public class goViewWorkout extends AppCompatActivity {

    private EditText muscleGroup;
    private EditText exerciseType;
    private EditText viewSets;
    private EditText viewReps;
    private EditText viewWeights;

    private Button saveWorkoutBtn;
    private Button restWorkoutBtn;

    private RecyclerView workoutGrid;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;

    DataBaseWorkoutHelper dataBaseWorkoutHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_go_view_workout);

        muscleGroup = findViewById(R.id.muscleGroup);
        exerciseType = findViewById(R.id.exerciseType);
        viewSets = findViewById(R.id.viewSets);
        viewReps = findViewById(R.id.viewReps);
        viewWeights = findViewById(R.id.viewWeights);

        workoutGrid = findViewById(R.id.workoutGrid);
        workoutGrid.setHasFixedSize(true);
        layoutManager = new GridLayoutManager(getApplicationContext(), 2);
        workoutGrid.setLayoutManager(layoutManager);

        dataBaseWorkoutHelper = new DataBaseWorkoutHelper(goViewWorkout.this);

        mAdapter = new RecycleViewAdapter(dataBaseWorkoutHelper.getEverything(), goViewWorkout.this);
        workoutGrid.setAdapter(mAdapter);

        saveWorkoutBtn = findViewById(R.id.saveWorkoutBtn);
        saveWorkoutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                WorkoutModel workoutModel;

                try {
                    workoutModel = new WorkoutModel(-1, muscleGroup.getText().toString(), exerciseType.getText().toString(),
                            Integer.parseInt(viewSets.getText().toString()), Integer.parseInt(viewReps.getText().toString()),
                            Integer.parseInt(viewWeights.getText().toString()));

                    Toast.makeText(goViewWorkout.this, workoutModel.toString(), Toast.LENGTH_SHORT).show();
                    mAdapter = new RecycleViewAdapter(dataBaseWorkoutHelper.getEverything(), goViewWorkout.this);
                    workoutGrid.setAdapter(mAdapter);

                }catch (Exception e){
                    Toast.makeText(goViewWorkout.this, "error", Toast.LENGTH_SHORT).show();
                    workoutModel = new WorkoutModel(-1, "error", "error", -1, -1, -1);


                }

                boolean success = dataBaseWorkoutHelper.addOne(workoutModel);
                Toast.makeText(goViewWorkout.this, "Success = " + success, Toast.LENGTH_SHORT).show();

                DataBaseWorkoutHelper dataBaseWorkoutHelper = new DataBaseWorkoutHelper(goViewWorkout.this);

                workoutGrid.setHasFixedSize(true);
                layoutManager = new GridLayoutManager(getApplicationContext(), 2);
                workoutGrid.setLayoutManager(layoutManager);

                mAdapter = new RecycleViewAdapter(dataBaseWorkoutHelper.getEverything(), goViewWorkout.this);
                workoutGrid.setAdapter(mAdapter);

            }
        });

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