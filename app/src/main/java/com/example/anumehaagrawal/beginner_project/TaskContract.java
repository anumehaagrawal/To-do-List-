package com.example.anumehaagrawal.beginner_project;

/**
 * Created by Anumeha Agrawal on 6/27/2017.
 */
import android.provider.BaseColumns;
public class TaskContract {
    public static final String DB_NAME = "dbtodo";
    public static final int DB_VERSION = 1;

    public class TaskEntry implements BaseColumns {
        public static final String TABLE = "tasks";

        public static final String COL_TASK_TITLE = "title";
    }
}
