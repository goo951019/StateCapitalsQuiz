package edu.uga.cs.csci4060_statecapitalsquiz;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class StateDBHelper extends SQLiteOpenHelper {
    private static final String DEBUG_TAG = "State Table";

    public static final String DATABASE_NAME = "State.db";
    public static final int version = 1;

    public static final String TABLE_NAME = "State";
    public static final String COL_1 = "ID";
    public static final String COL_2 = "State";
    public static final String COL_3 = "Capital";
    public static final String COL_4 = "SecondCity";
    public static final String COL_5 = "ThirdCity";
    public static final String COL_6 = "Statehood";
    public static final String COL_7 = "Since";
    public static final String COL_8 = "SizeRank";

    // This is a reference to the only instance for the helper.
    private static StateDBHelper helperInstance;

    public StateDBHelper(Context context) {
        super(context, DATABASE_NAME, null, version);
    }

    // Access method to the single instance of the class
    public static synchronized StateDBHelper getInstance( Context context ) {
        // check if the instance already exists and if not, create the instance
        if( helperInstance == null ) {
            helperInstance = new StateDBHelper( context.getApplicationContext() );
        }
        return helperInstance;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table " + TABLE_NAME +
                "(ID INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "State TEXT, " +
                "Capital TEXT, " +
                "SecondCity TEXT, " +
                "ThirdCity TEXT, " +
                "Statehood INTEGER, " +
                "Since INTEGER, " +
                "SizeRank INTEGER )");
        Log.d( DEBUG_TAG, "Table " + TABLE_NAME + " created" );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
        Log.d( DEBUG_TAG, "Table " + TABLE_NAME + " upgraded" );
    }

    public boolean isEmpty(){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cur = db.rawQuery("SELECT COUNT(*) FROM " + TABLE_NAME, null);
        if (cur != null && cur.moveToFirst())
            return (cur.getInt (0) == 0);
        return true;
    }
}
