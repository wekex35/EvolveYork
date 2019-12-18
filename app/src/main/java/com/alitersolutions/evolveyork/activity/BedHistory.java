package com.alitersolutions.evolveyork.activity;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.alitersolutions.evolveyork.R;
import com.alitersolutions.evolveyork.Retrofit.RetrofitUtil;
import com.alitersolutions.evolveyork.model.BedHistoryModel;
import com.alitersolutions.evolveyork.model.BedHistoryResponse;
import com.alitersolutions.evolveyork.model.BedInfoModel;

import java.util.List;

import adapter.BedHistoryListAdapter;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.alitersolutions.evolveyork.utils.Constants.INDTENTDATA;

public class BedHistory extends BaseActivity {
    RecyclerView recycler;
    int bed_id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bed_history);
        initView();


        bed_id = getIntent().getIntExtra(INDTENTDATA,0);
        String bedCode = getIntent().getStringExtra("bedCode");
        setTitle(getString(R.string.bed_history)+" ("+bedCode+")");
        if (bed_id != 0 ){
            refresh(recycler);
        }

    }
    private void initView() {
        recycler = findViewById(R.id.recycler);
        recycler.setLayoutManager(new LinearLayoutManager(this));

    }

    public void refresh(View view){
        BedInfoModel BIM = new BedInfoModel();
        BIM.setBed_id(bed_id);
        RetrofitUtil.createProviderAPIV2(this).getBedHistory(0,10,bed_id,1,1,"","").enqueue(loadBedHistory());
    }

    private Callback<BedHistoryResponse> loadBedHistory() {
        showProgressDialog();
        return new Callback<BedHistoryResponse>() {
            @Override
            public void onResponse(Call<BedHistoryResponse> call, Response<BedHistoryResponse> response) {
                //String js = gson.toJson(response.body().get());
                final List<BedHistoryModel> BedInfo = response.body().getData();
                if (BedInfo.isEmpty()){
                    Toast.makeText(BedHistory.this, "Not Available", Toast.LENGTH_SHORT).show();
                }
                BedHistoryListAdapter bedListAdapter = new BedHistoryListAdapter(BedHistory.this,BedInfo);
                recycler.setAdapter(bedListAdapter);
                hideProgressDialog();
            }

            @Override
            public void onFailure(Call<BedHistoryResponse> call, Throwable t) {

            }
        };
    }






}
