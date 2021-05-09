package com.example.one_fit_all;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.example.one_fit_all.WorkoutModel;

import java.util.ArrayList;
import java.util.List;

public class DataBaseWorkoutHelper extends SQLiteOpenHelper {
    public static final String WORKOUT_TABLE = "WORKOUT_TABLE";
    public static final String COLUMN_WORKOUT_GROUP = "WORKOUT_GROUP";
    public static final String COLUMN_WORKOUT_TYPE = "WORKOUT_TYPE";
    public static final String COLUMN_WORKOUT_SETS = "WORKOUT_SETS";
    public static final String COLUMN_WORKOUT_REPS = "WORKOUT_REPS";
    public static final String COLUMN_WORKOUT_WEIGHTS = "WORKOUT_WEIGHTS";
    public static final String COLUMN_ID = "ID";

    public DataBaseWorkoutHelper(@Nullable Context context) {
        super(context, "workout.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String createTableStatement = "CREATE TABLE " + WORKOUT_TABLE + " (" + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + COLUMN_WORKOUT_GROUP + " TEXT, " + COLUMN_WORKOUT_TYPE + " TEXT, " + COLUMN_WORKOUT_SETS + " INT, " + COLUMN_WORKOUT_REPS + " INT, " + COLUMN_WORKOUT_WEIGHTS + " INT)";

        sqLiteDatabase.execSQL(createTableStatement);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    public boolean addOne(WorkoutModel workoutModel){
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(COLUMN_WORKOUT_GROUP, workoutModel.getmGroup());
        cv.put(COLUMN_WORKOUT_TYPE, workoutModel.geteType());
        cv.put(COLUMN_WORKOUT_SETS, workoutModel.getSetAmount());
        cv.put(COLUMN_WORKOUT_REPS, workoutModel.getRepAmount());
        cv.put(COLUMN_WORKOUT_WEIGHTS, workoutModel.getwAmount());

        long insert = sqLiteDatabase.insert(WORKOUT_TABLE, null, cv);
        if(insert == -1){
            return false;
        }
        else{
            return true;
        }
    }

    public boolean deleteOne(WorkoutModel workoutModel){
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        String queryString = "DELETE FROM " + WORKOUT_TABLE + " WHERE " + COLUMN_ID + " = " + workoutModel.getId();

        Cursor cursor = sqLiteDatabase.rawQuery(queryString, null);

        if(cursor.moveToFirst()){
            return true;
        }
        else{
            return false;
        }


    }

    public List<WorkoutModel> getEverything(){
        List<WorkoutModel> returnList = new ArrayList<>();

        String queryString = "SELECT * FROM " + WORKOUT_TABLE;
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery(queryString, null);
        if(cursor.moveToFirst()){
            do{
                int workoutID = cursor.getInt(0);
                String workoutGroup = cursor.getString(1);
                String workoutType = cursor.getString(2);
                int workoutSets = cursor.getInt(3);
                int workoutReps = cursor.getInt(4);
                int workoutWeights = cursor.getInt(5);

                WorkoutModel newWorkout = new WorkoutModel(workoutID, workoutGroup, workoutType, workoutSets, workoutReps, workoutWeights);
                returnList.add(newWorkout);

            }while(cursor.moveToNext());
        }
        else{

        }

        cursor.close();
        sqLiteDatabase.close();
        return returnList;
    }
}
