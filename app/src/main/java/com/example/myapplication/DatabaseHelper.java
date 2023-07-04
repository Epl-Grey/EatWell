package com.example.myapplication;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.myapplication.DishData;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "dishInfo.db"; // название бд
    private static final int SCHEMA = 1; // версия базы данных
    public static final String TABLE = "dishsettings"; // название таблицы в бд
    // названия столбцов
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_NAME = "name";
    public static final String COLUMN_DATE = "date";
    public static final String COLUMN_PROTEINS = "proteins";
    public static final String COLUMN_FATS = "fats";
    public static final String COLUMN_CARBOHYDRATES = "carbohydrates";
    public static final String COLUMN_INGREDIENTS = "ingredients";
    public static final String COLUMN_RECIPE = "recipe";
    public static final String COLUMN_TIME = "time";
    public static final String COLUMN_TIMEDAY = "timeday";


    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, 10);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL("CREATE TABLE "+TABLE+" (" + COLUMN_ID
                + " TEXT PRIMARY KEY,"
                + COLUMN_NAME + " TEXT, "
                + COLUMN_DATE + " TEXT, "
                + COLUMN_PROTEINS + " TEXT, "
                + COLUMN_FATS + " TEXT, "
                + COLUMN_CARBOHYDRATES + " TEXT, "
                + COLUMN_INGREDIENTS + " TEXT, "
                + COLUMN_RECIPE + " TEXT, "
                + COLUMN_TIME + " TEXT, "
                + COLUMN_TIMEDAY + " TEXT );");


    }



    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion,  int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+TABLE);
        onCreate(db);
    }

    public void insertPill(DishData dish){
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(COLUMN_ID, dish.getPillId());
        cv.put(COLUMN_NAME, dish.getDishName());
        cv.put(COLUMN_DATE, dish.getDishData());
        cv.put(COLUMN_PROTEINS, dish.getDishProteins());
        cv.put(COLUMN_FATS, dish.getDishFats());
        cv.put(COLUMN_CARBOHYDRATES, dish.getDishCarbohydrates());
        cv.put(COLUMN_INGREDIENTS, dish.getDishIngredients());
        cv.put(COLUMN_RECIPE, dish.getDishRecipe());
        cv.put(COLUMN_TIME, dish.getDishTime());
        cv.put(COLUMN_TIMEDAY, dish.getDishTimeDay());

        db.insert(TABLE, null, cv);
    }

    public void cleanDB(){
        SQLiteDatabase db=this.getWritableDatabase();
        db.execSQL("DELETE FROM " + TABLE);
    }


}
