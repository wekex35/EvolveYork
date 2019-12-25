package com.alitersolutions.evolveyork.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.alitersolutions.evolveyork.model.SentModel;
import com.alitersolutions.evolveyork.utils.AppUtils;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static com.alitersolutions.evolveyork.database.BaseDatabase.TB_COUNTCYCLE;

public class DatabaseHelper {
    private BaseDatabase baseDatabase;
    private SQLiteDatabase database;
    private Context context;

    public DatabaseHelper(Context context) {
        this.context = context;
        baseDatabase = new BaseDatabase(context);
        database = baseDatabase.getWritableDatabase();

    }

    public long insertCycleCount(SentModel sentModel){
       ContentValues values = new ContentValues();
        values.put("EvolveItemRemarks",sentModel.getEvolveItemRemarks());
        values.put("EvolveItemMeasuringUnits",sentModel.getEvolveItemMeasuringUnits());
        values.put("EvolveLocationID",sentModel.getEvolveItemLocationID());
        values.put("UpdateDate",sentModel.getUpdatedDate());
        values.put("EvolveBatchNo",sentModel.getEvolveBatchNo());
        values.put("EvolveItemID",sentModel.getEvolveItemID());
        values.put("EvolveLocationName",sentModel.getEvolveLocationName());
        values.put("EvolveItemQty",sentModel.getEvolveItemQty());
        values.put("EvolveItemName",sentModel.getEvolveItemName());
        values.put("UploadStatus",sentModel.getUploadStatus());
        values.put("PrinterText",sentModel.getPrinterText());
        Log.d("", "insertCycleCount: "+values.toString());
        return database.insert(TB_COUNTCYCLE, null, values);

    }

    public String readCycleCount(){
       // ContentValues values = sentModel;
//        ContentValues cv = AppUtils.objectToContentValues(sentModel);
        database = baseDatabase.getReadableDatabase();

        Cursor cursor =  database.rawQuery("SELECT * FROM "+TB_COUNTCYCLE+" ORDER BY _id DESC",null);

        Gson gson = new Gson();
        ArrayList<Map> list = new ArrayList<>();
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Map hashMap = new HashMap();
            for (int i = 0; i < cursor.getColumnCount(); i++) {
                hashMap.put(cursor.getColumnName(i), cursor.getString(i));
            }
            list.add(hashMap);
            cursor.moveToNext();
        }
        cursor.close();
       return gson.toJson(list);


    }
}
