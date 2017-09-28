package com.example.rick.rickbakker_pset4;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

/**
 * Created by Rick on 26-9-2017.
 */

public class DBHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "ToDoDB.db";
    private static final int DATABASE_VERSION = 1;
    public static final String KEY_ID = "_id";
    public static final String KEY_TODO = "todo";
    public static final String TABLE = "todoTable";


    ArrayList<ToDo> todolist = new ArrayList<>();

    public DBHelper(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_DB = "CREATE TABLE " + TABLE + "("
                + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + KEY_TODO + " TEXT NOT NULL)";
        db.execSQL(CREATE_DB);
    }

    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE);
        onCreate(db);
    }

    public void create(ToDo todo) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_TODO, todo.getItem());
        db.insert(TABLE, null, values);
        db.close();
    }


    public ArrayList<ToDo> read() {
        SQLiteDatabase db = getReadableDatabase();

        String query = "SELECT " + KEY_ID + ", " + KEY_TODO + " FROM " + TABLE;
        Cursor cursor = db.rawQuery(query, null);

        if (cursor.moveToFirst()) {
            do {
                String todoText = cursor.getString(cursor.getColumnIndex(KEY_TODO));
                int id = cursor.getInt(cursor.getColumnIndex(KEY_ID));

                ToDo todo = new ToDo(todoText, id);
                todolist.add(todo);
            }
            while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return todolist;
    }

    public Cursor fetch() {
        SQLiteDatabase db = getReadableDatabase();
        String[] columns = new String[] {KEY_ID, KEY_TODO};
        Cursor cursor = db.query(TABLE, columns, null, null, null, null, null);
        if (cursor != null) {
            cursor.moveToFirst();
        }
        return cursor;
    }

    public void Delete(long _id){
        SQLiteDatabase db = getWritableDatabase();
        db.delete(TABLE, KEY_ID + " = " + _id, null);
    }

    public int Update(long _id, String todo) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(KEY_TODO, todo);
        int i = db.update(TABLE, contentValues, KEY_ID + " = " + _id, null);
        return i;
    }
}
