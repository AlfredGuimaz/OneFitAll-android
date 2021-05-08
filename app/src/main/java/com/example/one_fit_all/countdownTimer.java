package com.example.one_fit_all;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.concurrent.TimeUnit;

public class countdownTimer extends AppCompatActivity implements View.OnClickListener{

    private long workCountInMilliSeconds = 1 * 60000;
    //Initiating 1 min of rest time
    private long restCountInMilliSeconds = 1 * 60000;

    //Assuming this like a boolean for the timer
    private enum TimerStatus{
        STARTED,
        STOPPED
    }

    //Initiating both timers to STOP. Not running
    private TimerStatus workTimerStatus = TimerStatus.STOPPED;
    private TimerStatus restTimerStatus = TimerStatus.STOPPED;

    //First Timer (Work Timer)
    private ProgressBar progressBarWork;
    private TextView textViewTime;

    //Second Timer (Rest Timer)
    private ProgressBar progressBarRest;
    private TextView viewRestTime;

    //For Reset/Start/Pause Button
    private ImageView imageViewReset;
    private ImageView imageViewStartStop;


    private CountDownTimer workTimer;

    private int workmin;
    private int restmin;
    private int sets;
    private TextView setText;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_countdown_timer);

        //Initiate all the views here in this method
        initiateViews();

        //Get the intents of Work/Rest minutes & how many sets
        Intent intent = getIntent();
        workmin = intent.getIntExtra(goQuickWorkout.EXTRA_WORK_MIN, 0);
        restmin = intent.getIntExtra(goQuickWorkout.EXTRA_REST_MIN, 0);
        sets = intent.getIntExtra(goQuickWorkout.EXTRA_SETS, 0);
        setText = (TextView) findViewById(R.id.setText);

        setText.setText(""+sets);

        //Kinda fixes the bug
        sets = sets*3;

        //Initiate the onClickListeners for Start/Pause/Reset buttons here
        initiateListeners();
    }

    //Just initiates the Views here not much
    private void initiateViews(){
        progressBarWork = (ProgressBar) findViewById(R.id.progressBarWork);
        textViewTime = (TextView) findViewById(R.id.textViewTime);
        imageViewReset = (ImageView) findViewById(R.id.imageViewReset);
        imageViewStartStop = (ImageView) findViewById(R.id.imageViewStartStop);

        progressBarRest = (ProgressBar) findViewById(R.id.progressBarRest);
        viewRestTime = (TextView) findViewById(R.id.viewRestTime);
    }

    //Initiates the buttons for Start/Pause/Reset buttons here
    private void initiateListeners() {
        imageViewReset.setOnClickListener(this);
        imageViewStartStop.setOnClickListener(this);
    }

    //When clicked either of these start. This is the beginning
    @Override
    public void onClick(View view){
        switch(view.getId()){
            //Switched the order because the Start button would be pressed first
            case R.id.imageViewStartStop:
                startStop();
                break;
            case R.id.imageViewReset:
                reset();
                break;
        }
    }

    //
    private void startStop(){
        //First checking if workTimerStatus is stopped
        if(workTimerStatus == TimerStatus.STOPPED){
            //call to initialize the timer values
            setTimerValues();
            //call to initialize the progress bar values
            setProgressBarValues();
            // showing the reset icon MIGHT LEAVE THIS OUT.
            imageViewReset.setVisibility(View.VISIBLE);
            // changing play icon to stop icon
            imageViewStartStop.setImageResource(R.drawable.ic_pause);
            // making edit text not editable
            // changing the timer status to started
            workTimerStatus = TimerStatus.STARTED;
            // call to start the Work Timer
            startWorkTimer();
        }
        else{
            // hiding the reset icon
            imageViewReset.setVisibility(View.GONE);
            // changing stop icon to start icon
            imageViewStartStop.setImageResource(R.drawable.ic_play);
            // making edit text editable
            // changing the timer status to stopped
            workTimerStatus = TimerStatus.STOPPED;
            stopWorkTimer();
        }
    }

    //Sets values for Timers in milliseconds
    private void setTimerValues(){
        int workTime = workmin;
        int restTime = restmin;
        //Converting workmin/restmin to milliseconds
        workCountInMilliSeconds = workTime * 60 * 1000;
        restCountInMilliSeconds = restTime * 60 * 1000;
    }

    //Sets values for Progress Bars
    private void setProgressBarValues(){
        //Values for Work Progress Bar
        progressBarWork.setMax((int) workCountInMilliSeconds / 1000);
        progressBarWork.setProgress((int) workCountInMilliSeconds / 1000);

        //Values for Rest Progress Bar
        progressBarRest.setMax((int) restCountInMilliSeconds / 1000);
        progressBarRest.setProgress((int) restCountInMilliSeconds / 1000);
    }

    //
    private void startWorkTimer(){
        workTimer = new CountDownTimer(workCountInMilliSeconds, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                textViewTime.setText(timeFormatter(millisUntilFinished));
                progressBarWork.setProgress((int) (millisUntilFinished) / 1000);
            }

            @Override
            public void onFinish() {
                textViewTime.setText(timeFormatter(workCountInMilliSeconds));
                setProgressBarValues();
                /*if(sets > 1) { //Try making this into a boolean.
                    restTimerStatus = TimerStatus.STARTED;
                    startRestTimer();
                }*/
                imageViewReset.setVisibility(View.GONE);
                // changing stop icon to start icon
                imageViewStartStop.setImageResource(R.drawable.ic_play);
                // changing the timer status to stopped
                workTimerStatus = TimerStatus.STOPPED;
                if(sets > 1) { //Try making this into a boolean.
                    restTimerStatus = TimerStatus.STARTED;
                    sets--;
                    startRestTimer();
                }
            }
        }.start();
        workTimer.start();
    }

    //
    private void startRestTimer(){

        // changing stop icon to start icon
        // changing the timer status to stopped
        CountDownTimer restTimer = new CountDownTimer(restCountInMilliSeconds, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                viewRestTime.setText(timeFormatter(millisUntilFinished));
                progressBarRest.setProgress((int) (millisUntilFinished) / 1000);
            }

            @Override
            public void onFinish() {
                viewRestTime.setText(timeFormatter(restCountInMilliSeconds));
                setProgressBarValues();

                imageViewReset.setVisibility(View.GONE);
                // changing stop icon to start icon
                imageViewStartStop.setImageResource(R.drawable.ic_play);
                // changing the timer status to stopped
                restTimerStatus = TimerStatus.STOPPED;

                workTimerStatus = TimerStatus.STARTED;
                startWorkTimer();
            }
        }.start();
        restTimer.start();
    }

    //
    private void stopWorkTimer() {
        workTimer.cancel();
    }

    //
    private void reset() {
        stopWorkTimer();
        startWorkTimer();
    }

    //
    private String timeFormatter(long milliSeconds) {

        String hms = String.format("%02d:%02d:%02d",
                TimeUnit.MILLISECONDS.toHours(milliSeconds),
                TimeUnit.MILLISECONDS.toMinutes(milliSeconds) - TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(milliSeconds)),
                TimeUnit.MILLISECONDS.toSeconds(milliSeconds) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(milliSeconds)));

        return hms;
    }
}