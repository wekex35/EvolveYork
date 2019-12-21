package com.alitersolutions.evolveyork.activity;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.alitersolutions.evolveyork.R;
import com.alitersolutions.evolveyork.adapter.CycleHistoryAdapter;
import com.alitersolutions.evolveyork.database.DatabaseHelper;
import com.alitersolutions.evolveyork.model.BedHistoryModel;
import com.alitersolutions.evolveyork.model.BedHistoryResponse;

import java.util.ArrayList;
import java.util.List;

import com.alitersolutions.evolveyork.adapter.ItemHistoryListAdapter;
import com.alitersolutions.evolveyork.model.SentModel;
import com.google.gson.reflect.TypeToken;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CycleHistory extends BaseActivity {
    RecyclerView recycler;
    int bed_id;
    DatabaseHelper databaseHelper;
    private ArrayList<SentModel> sentModel;
    CycleHistoryAdapter cycleHistoryAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cycle_history);
        initView();
        setTitle("Count Cycle History");

        databaseHelper = new  DatabaseHelper(this);
        String str = databaseHelper.readCycleCount();
        Log.d( "onCreate: ",str);
        loadItems(str);
//        bed_id = getIntent().getIntExtra(INDTENTDATA,0);
//        String bedCode = getIntent().getStringExtra("bedCode");
//        setTitle(getString(R.string.axle_history)+" ("+bedCode+")");
//        if (bed_id != 0 ){
//            refresh(recycler);
//        }

    }
    private void loadItems(String str) {
        // String myJson= inputStreamToString(this.getResources().openRawResource(R.raw.location_sapi_response));
        sentModel = gson.fromJson(str,new TypeToken<List<SentModel>>(){}.getType());

        if (sentModel.isEmpty()){
            Toast.makeText(CycleHistory.this, "Not Available", Toast.LENGTH_SHORT).show();
        }

        cycleHistoryAdapter = new CycleHistoryAdapter(CycleHistory.this,sentModel);
        recycler.setAdapter(cycleHistoryAdapter);

    }
    private void initView() {
        recycler = findViewById(R.id.recycler);
        recycler.setLayoutManager(new LinearLayoutManager(this));

    }

    public void refresh(View view){
        /*BedInfoModel BIM = new BedInfoModel();
        BIM.setBed_id(bed_id);
        RetrofitUtil.createProviderAPIV2(this).getBedHistory(0,10,bed_id,1,1,"","").enqueue(loadBedHistory());
*/    }

    private Callback<BedHistoryResponse> loadBedHistory() {
        showProgressDialog();
        return new Callback<BedHistoryResponse>() {
            @Override
            public void onResponse(Call<BedHistoryResponse> call, Response<BedHistoryResponse> response) {
                //String js = gson.toJson(response.body().get());
                final List<BedHistoryModel> BedInfo = response.body().getData();
                if (BedInfo.isEmpty()){
                    Toast.makeText(CycleHistory.this, "Not Available", Toast.LENGTH_SHORT).show();
                }
              //  ItemHistoryListAdapter bedListAdapter = new ItemHistoryListAdapter(CycleHistory.this,BedInfo);
              //  recycler.setAdapter(bedListAdapter);
                hideProgressDialog();
            }

            @Override
            public void onFailure(Call<BedHistoryResponse> call, Throwable t) {

            }
        };
    }






}
