package com.example.tarea2_1_pm1.configuracion;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;



public class SQLiteConexion extends SQLiteOpenHelper {
    public static final String NameDB="videosDB";
    public static final int version=3;
    public static final SQLiteDatabase.CursorFactory factory = null;

    public SQLiteConexion(@Nullable Context context) {
        super(context, NameDB, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(VideosContract.CREATE_TABLE_VIDEOS);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(VideosContract.DROP_TABLE_VIDEO);
    }
}
