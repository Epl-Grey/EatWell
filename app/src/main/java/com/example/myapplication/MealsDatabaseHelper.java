package com.example.myapplication;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

public class MealsDatabaseHelper extends SQLiteOpenHelper {

    public static String DB_PATH = "/data/data/com.example.myapplication/databases/";

    public static String DB_NAME = "meal.db";
    public static final int DB_VERSION = 1;
    public static final String TB_DISHES = "dishes";
    public static final String TB_INGREDIENTS = "ingredients";

    private SQLiteDatabase myDB;
    private Context context;

    public MealsDatabaseHelper(Context context) {
        super(context, DB_NAME, null, 10);
        this.context = context;
    }


    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    @Override
    public synchronized void close(){
        if(myDB!=null){
            myDB.close();
        }
        super.close();
    }

    private boolean checkDataBase() {
        SQLiteDatabase tempDB = null;
        try {
            String myPath = DB_PATH + DB_NAME;
            tempDB = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READWRITE);
        } catch (SQLiteException e) {
            Log.e("tle99 - check", e.getMessage());
        }
        if (tempDB != null)
            tempDB.close();
        return tempDB != null ? true : false;
    }

    public void copyDataBase() throws IOException {
        try {
            InputStream myInput = context.getAssets().open(DB_NAME);
            String outputFileName = DB_PATH + DB_NAME;
            OutputStream myOutput = new FileOutputStream(outputFileName);

            byte[] buffer = new byte[1024];
            int length;

            while((length = myInput.read(buffer))>0){
                myOutput.write(buffer, 0, length);
            }

            myOutput.flush();
            myOutput.close();
            myInput.close();
        } catch (Exception e) {
            Log.e("tle99 - copyDatabase", e.getMessage());
        }
    }

    public void openDataBase() throws SQLException {
        String myPath = DB_PATH + DB_NAME;
        myDB = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READWRITE);
    }

    public void createDataBase() throws IOException {
        boolean dbExist = checkDataBase();

        if (dbExist) {

        } else {
            this.getReadableDatabase();
            try {
                copyDataBase();
            } catch (IOException e) {
                Log.e("tle99 - create", e.getMessage());
            }
        }
    }

    public List<Meal> getAllDishes(){
        List<Meal> listDishes = new ArrayList<Meal>();
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor c;

        try {
            c = db.rawQuery("SELECT * FROM " + TB_DISHES , null);
            if(c == null) return null;

            Meal meal;
            c.moveToFirst();
            do {
                meal = new Meal(
                        c.getString(1),
                        c.getString(2),
                        c.getFloat(3),
                        c.getFloat(4),
                        c.getFloat(5),
                        c.getFloat(6)
                    );
                listDishes.add(meal);
            } while (c.moveToNext());
            c.close();
        } catch (Exception e) {
            Log.e("tle99", e.getMessage());
        }

        db.close();

        return listDishes;
    }

    public List<Meal> getAllIngredients(){
        List<Meal> listDishes = new ArrayList<Meal>();
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor c;

        try {
            c = db.rawQuery("SELECT * FROM " + TB_INGREDIENTS , null);
            if(c == null) return null;

            Meal meal;
            c.moveToFirst();
            do {
                meal = new Meal(
                        c.getString(1),
                        c.getString(2),
                        c.getFloat(3),
                        c.getFloat(4),
                        c.getFloat(5),
                        c.getFloat(6)
                );
                listDishes.add(meal);
            } while (c.moveToNext());
            c.close();
        } catch (Exception e) {
            Log.e("tle99", e.getMessage());
        }

        db.close();

        return listDishes;
    }
}
