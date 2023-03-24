package com.example.morgen.db;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class DBManager {

    private DBHelper dbHelper;
    private final Context context;

    public DBManager(Context context) {
        this.context = context;
        dbHelper = new DBHelper(context, DBHelper.TABLE_NAME, null, 6);
    }

    public void putValues() {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put(DBHelper.COLUMN_NAME_TITLE, 0);
        contentValues.put(DBHelper.COLUMN_NAME_SUBTITLE, 1);
        contentValues.put(DBHelper.FULL_VIDEO_UNLOCK_STATUS, false);

        contentValues.put(DBHelper.FULL_VIDEO_UNLOCK_STATUS, 0);
        db.insert(DBHelper.TABLE_NAME, null, contentValues);


    }
    @SuppressLint("Recycle")
    public List<Integer> getValues() {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        Cursor cursor;

        cursor = db.query(DBHelper.TABLE_NAME, null, null, null, null, null, null);
        List<Integer> result = new ArrayList<>();

        while (cursor.moveToNext()) {
            @SuppressLint("Range") int item = cursor.getInt(cursor.getColumnIndex(DBHelper.COLUMN_NAME_TITLE));
            result.add(item);
        }

        if (result.size() == 0) {
            putValues();
            List<Integer> emptyArray = new ArrayList<>();
            emptyArray.add(-1);

            return emptyArray;
        }
        cursor.close();
        return result;
    }

    public List<Boolean> getFullVideoUnlockStatus() {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        Cursor cursor;

        cursor = db.query(DBHelper.TABLE_NAME, null, null, null, null, null, null);
        List<Boolean> result = new ArrayList<>();

        while (cursor.moveToNext()) {
            @SuppressLint("Range") int item = cursor.getInt(cursor.getColumnIndex(DBHelper.FULL_VIDEO_UNLOCK_STATUS));
            boolean item2;
            item2 = item == 1;
            result.add(item2);
        }

        if (result.size() == 0) {
            List<Boolean> emptyList = new ArrayList<>();
            emptyList.add(false);
            putValues();
            return emptyList;
        }
        cursor.close();
        return result;
    }



    public void updateValues(int balance) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(DBHelper.COLUMN_NAME_TITLE, balance);
        db.update(DBHelper.TABLE_NAME, contentValues, (DBHelper.COLUMN_NAME_SUBTITLE + "=" + 1), null);
    }

    public void setFullVideoUnlockedStatus(boolean statement) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(DBHelper.FULL_VIDEO_UNLOCK_STATUS, statement);
        db.update(DBHelper.TABLE_NAME, contentValues, (DBHelper.COLUMN_NAME_SUBTITLE + "=" + 1), null);
    }

    public void closeDB() {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        db.close();
    }
}
