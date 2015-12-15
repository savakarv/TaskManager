package com.example.todomanager.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.todomanager.Todo;

import java.util.ArrayList;

/**
 * Created by vishwanath.s on 14/12/15.
 */
public class TaskDBHelper extends SQLiteOpenHelper {
    public TaskDBHelper(Context context) {
        super(context, TaskDO.DB_NAME, null, TaskDO.DB_VERSION);
    }

    public SQLiteDatabase getDB() {
        SQLiteDatabase db = this.getWritableDatabase();
        return db;
    }

    @Override
    public void onCreate(SQLiteDatabase sqlDB) {
        String sqlQuery =
                String.format("CREATE TABLE %s (" +
                                "_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                                "%s TEXT," +
                                "%s TEXT," +
                                "%s VARCHAR(30)," +
                                "%s VARCHAR(10)," +
                                "%s INTEGER" +
                                ")",
                        TaskDO.TABLE,
                        TaskDO.Columns.TITLE,
                        TaskDO.Columns.NOTES,
                        TaskDO.Columns.DUEDATE,
                        TaskDO.Columns.PRIORITY,
                        TaskDO.Columns.DELETED);

        Log.d("TaskDBHelper", "Query to form table: " + sqlQuery);
        sqlDB.execSQL(sqlQuery);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqlDB, int i, int i2) {
        sqlDB.execSQL("DROP TABLE IF EXISTS " + TaskDO.TABLE);
        onCreate(sqlDB);
    }

    public ArrayList<Todo> getDeletedTaskList() {
        ArrayList<Todo> todoList = new ArrayList<Todo>();

        SQLiteDatabase sqlDB = this.getReadableDatabase();
        String where = "deleted = ?";
        String[] whereArgs = new String[] {
                "Y"
        };

        Cursor cursor = sqlDB.query(TaskDO.TABLE,
                new String[]{TaskDO.Columns._ID, TaskDO.Columns.TITLE, TaskDO.Columns.NOTES, TaskDO.Columns.DUEDATE, TaskDO.Columns.PRIORITY, TaskDO.Columns.DELETED},
                where, whereArgs, null, null, null);

        cursor.moveToFirst();

        todoList.clear();

        while (!cursor.isAfterLast()) {
            Todo t = new Todo();
            t.setId(cursor.getString(0));
            t.setTitle(cursor.getString(1));
            t.setNotes(cursor.getString(2));
            t.setDueDate(cursor.getString(3));
            t.setPriority(cursor.getString(4));
            t.setDeleted(cursor.getString(5));
            todoList.add(t);
            Log.i("VISHY", t.getId() + "," + t.getTitle() +","+t.getNotes()+","+t.getDueDate()+","+t.getPriority()+","+(t.isDeleted()?"Y":"N"));
            cursor.moveToNext();
        }

        cursor.close();

        return todoList;
    }

    public ArrayList<Todo> getTaskList() {
        ArrayList<Todo> todoList = new ArrayList<Todo>();

        SQLiteDatabase sqlDB = this.getReadableDatabase();
        String where = "deleted = ?";
        String[] whereArgs = new String[] {
                "N"
        };

        Cursor cursor = sqlDB.query(TaskDO.TABLE,
                new String[]{TaskDO.Columns._ID, TaskDO.Columns.TITLE, TaskDO.Columns.NOTES, TaskDO.Columns.DUEDATE, TaskDO.Columns.PRIORITY, TaskDO.Columns.DELETED},
                where, whereArgs, null, null, null);

        cursor.moveToFirst();

        todoList.clear();

        while (!cursor.isAfterLast()) {
            Todo t = new Todo();
            t.setId(cursor.getString(0));
            t.setTitle(cursor.getString(1));
            t.setNotes(cursor.getString(2));
            t.setDueDate(cursor.getString(3));
            t.setPriority(cursor.getString(4));
            t.setDeleted(cursor.getString(5));
            todoList.add(t);
            Log.i("VISHY", t.getId() + "," + t.getTitle() +","+t.getNotes()+","+t.getDueDate()+","+t.getPriority()+","+(t.isDeleted()?"Y":"N"));
            cursor.moveToNext();
        }

        cursor.close();

        return todoList;
    }

    public void deleteTask(String id) {
        SQLiteDatabase db = this.getReadableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("deleted", "Y"); //These Fields should be your String values of actual column names

        db.update(TaskDO.TABLE, cv, "_id" + "=" + id, null);
        //return db.delete(TaskDO.TABLE, "_id" + "=" + id, null) > 0;
    }
}
