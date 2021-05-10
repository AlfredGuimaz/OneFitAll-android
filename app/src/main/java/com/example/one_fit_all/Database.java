package com.example.one_fit_all;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.text.Editable;

import java.util.ArrayList;
import java.util.List;
import java.util.jar.Attributes;

import androidx.annotation.Nullable;

public class Database extends SQLiteOpenHelper {


    public static final String USER_TABLE = "USER_TABLE";
    public static final String COLUMN_USER_NAME = "USER_NAME";
    public static final String COLUMN_USER_WEIGHT = "USER_WEIGHT";
    public static final String COLUMN_USER_AGE = "USER_AGE";
    public static final String COLUMN_USER_FEET = "USER_FEET";
    public static final String COLUMN_USER_INCH = "USER_INCH";
    //public static final String COLUMN_USER_GENDER = "USER_GENDER";


    public Database(@Nullable Context context) {

        super(context, "database.db", null, 1);
    }

    //Called when database is first accessed, contains code to create new database/tables
    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTableStatement = "CREATE TABLE " + USER_TABLE + " (ID INTEGER PRIMARY KEY AUTOINCREMENT, " + COLUMN_USER_NAME + " TEXT, " + COLUMN_USER_WEIGHT + " INT, " + COLUMN_USER_AGE + " INT, " + COLUMN_USER_FEET + " INT, " + COLUMN_USER_INCH + " INT )";// + COLUMN_USER_GENDER + " TEXT )";

        db.execSQL(createTableStatement);
    }



    //called if version of database changes, prevents users apps from breaking when database changes
    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldV, int newV) {

    }


    public boolean addOne(CustomerClass customerClass) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_USER_NAME, customerClass.getName());
        contentValues.put(COLUMN_USER_WEIGHT, customerClass.getWeight());
        contentValues.put(COLUMN_USER_AGE, customerClass.getAge());
        contentValues.put(COLUMN_USER_FEET, customerClass.getFeet());
        contentValues.put(COLUMN_USER_INCH, customerClass.getInch());
        // contentValues.put(COLUMN_USER_GENDER, customerClass.getGender());

        long insert = db.insert(USER_TABLE, null, contentValues);
        if (insert == -1) {
            return false;
        }
        else {

            return true;
        }
    }

    public boolean updateDB(String name, int weight, int age, int feet, int inch) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_USER_NAME, name);
        contentValues.put(COLUMN_USER_WEIGHT, weight);
        contentValues.put(COLUMN_USER_AGE, age);
        contentValues.put(COLUMN_USER_FEET, feet);
        contentValues.put(COLUMN_USER_INCH, inch);
        // contentValues.put(COLUMN_USER_GENDER, customerClass.getGender())
        Cursor cursor = db.rawQuery("Select * From USER_TABLE where COLUMN_USER_NAME = ?", new String[] {String.valueOf(name)});
        if(cursor.getCount() > 0) {
            long update = db.update(USER_TABLE, contentValues, "name=?", new String[]{name});
            if (update == -1) {
                return false;
            } else {
                return true;
            }
        } else{
            return false;
        }
    }





    /* public Cursor getData() {
         SQLiteDatabase db = this.getReadableDatabase();
         Cursor cursor = db.rawQuery("Select * From USER_TABLE where COLUMN_USER_NAME = ?", null);
         return cursor;
     } */
    public List<CustomerClass> getEveryone() {
        List<CustomerClass> returnList = new ArrayList<>();
        String queryString = "Select * From " + USER_TABLE;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(queryString, null);

        if (cursor.moveToFirst()) {
            //loop through results
            do {
                String userName = cursor.getString(1);
                int userWeight = cursor.getInt(2);
                int userAge = cursor.getInt(3);
                int userFeet = cursor.getInt(4);
                int userInch = cursor.getInt(5);

                CustomerClass newCustomer = new CustomerClass(userName, userWeight, userAge, userFeet, userInch);
                returnList.add(newCustomer);


            }   while(cursor.moveToNext());
        }

        else {
            //Do not add anything to list
        }
        cursor.close();
        db.close();
        return returnList;

    }


}
