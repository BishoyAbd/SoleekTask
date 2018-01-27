package com.project.bishoy.soleektask.data.local;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by bisho on 11/9/2017.
 */

public class WeddingSqlHelper extends SQLiteOpenHelper {


    private static final String DATABASE_NAME = "wedding.db";
    private static final int DATABASE_VERSION = 1;


    //table1 apartment details
    public static final String TABLE_PLAN = "plan";
    public static final String COLUMN_ID = "plan_id";
    public static final String COLUMN_LOCATION = "location";
    public static final String COLUMN_ATTENDEES = "attendees";
    public static final String COLUMN_COST = "price";

    public static final String COLUMN_DATE = "date";


    private static final String TABLE_PLAN_CREATE =
            "CREATE TABLE " + TABLE_PLAN + " (" +
                    COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    COLUMN_LOCATION + " VARCHAR2, " +
                    COLUMN_COST + " INT, " +
                    COLUMN_ATTENDEES + " INT, " +
                    COLUMN_DATE + " DATE " +
                    ")";


    public static final String QUERY_GET_ALL_PLANS = "SELECT* FROM " + TABLE_PLAN;

    public WeddingSqlHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(TABLE_PLAN_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_PLAN);
        sqLiteDatabase.execSQL(TABLE_PLAN_CREATE);

    }
}
