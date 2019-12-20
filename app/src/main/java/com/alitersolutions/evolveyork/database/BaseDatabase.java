package com.alitersolutions.evolveyork.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class BaseDatabase extends SQLiteOpenHelper {

    private static final String DB_NAME= "Database";
    private static final int DB_VERSION = 1;
    public static final String  TB_COUNTCYCLE = "countcycle";

    private static final String CountCycleTable =
            "create table "+TB_COUNTCYCLE+" ( " +
            "_id integer primary key," +
            "EvolveBatchNo text not null," +
            "EvolveItemRemarks text not null," +
            "EvolveItemName text not null," +
            "EvolveItemId text not null," +
            "EvolveLocationName text not null," +
            "EvolveLocationId text not null," +
            "EvolveItemMeasuringUnits text not null," +
            "EvolveItemQty text not null," +
            "UpdateDate text not null," +
            "UploadStatus text not null" +
            ");";


    public BaseDatabase(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CountCycleTable);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
