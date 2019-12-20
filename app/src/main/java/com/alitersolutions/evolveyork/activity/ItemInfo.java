package com.alitersolutions.evolveyork.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alitersolutions.evolveyork.R;
import com.alitersolutions.evolveyork.model.BedInfoModel;
import com.alitersolutions.evolveyork.model.MasterItems;

import static com.alitersolutions.evolveyork.utils.AppUtils.getParsedDate;
import static com.alitersolutions.evolveyork.utils.Constants.INDTENTDATA;

public class ItemInfo extends BaseActivity {
   String TAG = "ItemInfo";
    MasterItems masterItems;
    String fieldName[] = {"Evolve Item ID","Evolve Item Site","Evolve Item Loc","Evolve Item Part",
            "Evolve Item Ref","Evolve Item Qty","Evolve Item CreatedDate",
            "Evolve Item UpdatedDate","Evolve Item UOM",
            "Evolve Item Type","Evolve Item Status",
            "Evolve Item Description",
            "Evolve Item Lot"};
    LinearLayout bedInfoHolder;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_info);
        setTitle(R.string.axle_info);
        masterItems = (MasterItems) getIntent().getSerializableExtra(INDTENTDATA);
        initView();
        Log.d(TAG, "onCreate: "+masterItems.getEvolveItemPart());
    }

    private void initView() {
        bedInfoHolder = findViewById(R.id.bedinfoholder);
        setBedData();
    }

    private void setBedData() {
        bedInfoHolder.addView(setKeyValue(fieldName[0], String.valueOf(masterItems.getEvolveItemID())));
        bedInfoHolder.addView(setKeyValue(fieldName[3],masterItems.getEvolveItemPart()));
        bedInfoHolder.addView(setKeyValue(fieldName[1],masterItems.getEvolveItemSite()));
        bedInfoHolder.addView(setKeyValue(fieldName[2],masterItems.getEvolveItemLoc()));
        bedInfoHolder.addView(setKeyValue(fieldName[5],masterItems.getEvolveItemQty()));
        bedInfoHolder.addView(setKeyValue(fieldName[12],masterItems.getEvolveItemDescription()));

       /* String status = "OUT";
        if (bedInfoModel.getEvolveBed_Status()){
            status = "IN";
        }
        bedInfoHolder.addView(setKeyValue(fieldName[6], status));*/
    }

    private View setKeyValue(String k, String v) {
        View view =  LayoutInflater.from(this).inflate(R.layout.bed_item_list,null,false);
        TextView key = view.findViewById(R.id.key);
        TextView value = view.findViewById(R.id.value);
        key.setText(k);
        value.setText(v);
        return view;
    }

/*
    public void showHistory(View view) {
        Intent intent = new Intent(this, CycleHistory.class);
        intent.putExtra(INDTENTDATA,bedInfoModel.getEvolveBed_ID());
        intent.putExtra("bedCode",bedInfoModel.getEvolveBed_Code());
        startActivity(intent);
            }*/
}
