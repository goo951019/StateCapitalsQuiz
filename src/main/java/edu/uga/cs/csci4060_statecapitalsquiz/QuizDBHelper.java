package edu.uga.cs.csci4060_statecapitalsquiz;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class QuizDBHelper extends SQLiteOpenHelper {
    private static final String DEBUG_TAG = "Quiz Table";

    public static final String DATABASE_NAME = "Quiz.db";
    public static final int version = 1;

    public static final String TABLE_NAME = "Quiz";
    public static final String COL_1 = "ID";
    public static final String COL_2 = "Date";
    public static final String COL_3 = "Question";
    public static final String COL_4 = "Correct";
    public static final String COL_5 = "Answers";
    public static final String COL_6 = "Score";

    // This is a reference to the only instance for the helper.
    private static QuizDBHelper helperInstance;

    public QuizDBHelper(Context context) {
        super(context, DATABASE_NAME, null, version);
    }

    // Access method to the single instance of the class
    public static synchronized QuizDBHelper getInstance( Context context ) {
        // check if the instance already exists and if not, create the instance
        if( helperInstance == null ) {
            helperInstance = new QuizDBHelper( context.getApplicationContext() );
        }
        return helperInstance;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table " + TABLE_NAME +
                "(ID INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "Date TEXT, " +
                "Question TEXT, " +
                "Correct TEXT," +
                "Answers TEXT, " +
                "Score INTEGER)");
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

