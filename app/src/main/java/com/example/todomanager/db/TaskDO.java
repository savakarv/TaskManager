package com.example.todomanager.db;

import android.provider.BaseColumns;

/**
 * Created by vishwanath.s on 14/12/15.
 */
public class TaskDO {
    public static final String DB_NAME = "com.example.todomanager.tasks";
    public static final int DB_VERSION = 2;
    public static final String TABLE = "tasks";

    public class Columns {
        public static final String TITLE = "title";
        public static final String PRIORITY = "priority";
        public static final String NOTES = "notes";
        public static final String DUEDATE = "duedate";
        public static final String DELETED = "deleted";
        public static final String _ID = BaseColumns._ID;
    }
}
