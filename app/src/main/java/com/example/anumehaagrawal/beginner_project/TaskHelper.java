package com.example.anumehaagrawal.beginner_project;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Anumeha Agrawal on 6/27/2017.
 */

public class TaskHelper extends SQLiteOpenHelper{
    public TaskHelper(Context context){
        super(context,TaskContract.DB_NAME,null,TaskContract.DB_VERSION);

    }
    @Override
    public void onCreate(SQLiteDatabase db){
        String createTable = "CREATE TABLE " + TaskContract.TaskEntry.TABLE + " ( " +
                TaskContract.TaskEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                TaskContract.TaskEntry.COL_TASK_TITLE + " TEXT NOT NULL);";
        db.execSQL(createTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TaskContract.TaskEntry.TABLE);
        onCreate(db);
    }

}

