package com.example.morgen.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DBHelper extends SQLiteOpenHelper {
    public static final String TABLE_NAME = "user_data";
    public static final String COLUMN_NAME_TITLE = "bounce_balance";
    public static final String COLUMN_NAME_SUBTITLE = "ID";
    public static final String FULL_VIDEO_UNLOCK_STATUS = "full_video_active";
;
    public DBHelper(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }



    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE IF NOT EXISTS user_data (" +
                "ID INTEGER," +
                "bounce_balance INTEGER," +
                "ad_active BOOL," +
                "full_video_active BOOL)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS user_data");
        onCreate(db);
    }
}
