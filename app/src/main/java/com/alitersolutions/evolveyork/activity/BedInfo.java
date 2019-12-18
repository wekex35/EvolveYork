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

import static com.alitersolutions.evolveyork.utils.AppUtils.getParsedDate;
import static com.alitersolutions.evolveyork.utils.Constants.INDTENTDATA;

public class BedInfo extends BaseActivity {
   String TAG = "BedInfo";
    BedInfoModel bedInfoModel;
    String fieldName[] = {"Bed ID","Bed Code","Bed RFID","Bed Make","Size Name","Type Name","Bed Status"};
    LinearLayout bedInfoHolder;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bed_info);
        setTitle(R.string.bed_info);
        bedInfoModel = (BedInfoModel)getIntent().getSerializableExtra(INDTENTDATA);
        initView();
        Log.d(TAG, "onCreate: "+bedInfoModel.getEvolveBed_Code());
    }

    private void initView() {
        bedInfoHolder = findViewById(R.id.bedinfoholder);
        setBedData();
    }

    private void setBedData() {
        bedInfoHolder.addView(setKeyValue(fieldName[0], String.valueOf(bedInfoModel.getEvolveBed_ID())));
        bedInfoHolder.addView(setKeyValue(fieldName[1],bedInfoModel.getEvolveBed_Code()));
        bedInfoHolder.addView(setKeyValue(fieldName[2],bedInfoModel.getEvolveBed_RFID()));

        bedInfoHolder.addView(setKeyValue(fieldName[3],getParsedDate(bedInfoModel.getEvolveBed_Make().split("T")[0])));

        bedInfoHolder.addView(setKeyValue(fieldName[4],bedInfoModel.getEvolveSize_Name()));
        bedInfoHolder.addView(setKeyValue(fieldName[5],bedInfoModel.getEvolveType_Name()));

        String status = "OUT";
        if (bedInfoModel.getEvolveBed_Status()){
            status = "IN";
        }
        bedInfoHolder.addView(setKeyValue(fieldName[6], status));
    }

    private View setKeyValue(String k, String v) {
        View view =  LayoutInflater.from(this).inflate(R.layout.bed_item_list,null,false);
        TextView key = view.findViewById(R.id.key);
        TextView value = view.findViewById(R.id.value);
        key.setText(k);
        value.setText(v);
        return view;
    }


    public void showHistory(View view) {
        Intent intent = new Intent(this,BedHistory.class);
        intent.putExtra(INDTENTDATA,bedInfoModel.getEvolveBed_ID());
        intent.putExtra("bedCode",bedInfoModel.getEvolveBed_Code());
        startActivity(intent);
            }
}
