package com.alitersolutions.evolveyork.activity;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.alitersolutions.evolveyork.R;
import com.alitersolutions.evolveyork.Retrofit.RetrofitUtil;
import com.alitersolutions.evolveyork.model.BedInfoModel;
import com.alitersolutions.evolveyork.model.ResponseModel;
import com.google.gson.reflect.TypeToken;

import java.util.List;

import adapter.BedListAdapter;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.alitersolutions.evolveyork.utils.Constants.FAILURE;

public class BedList extends BaseActivity {

    RecyclerView recycler;
    String TAG = "BedList";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bed_list);
        setTitle(R.string.bed_list);
        initView();
        getbedList();
    }

    private void initView() {
        recycler = findViewById(R.id.recycler);
        recycler.setLayoutManager(new LinearLayoutManager(this));

    }

    private void getbedList() {
        RetrofitUtil.createProviderAPIV2(this).getBedList().enqueue(LoadBedList());
    }

    private Callback<ResponseModel> LoadBedList() {
        showProgressDialog();
        return  new Callback<ResponseModel>() {
            @Override
            public void onResponse(Call<ResponseModel> call, Response<ResponseModel> response) {

                String js = gson.toJson(response.body().getResult());
                final List<BedInfoModel> BedInfo = gson.fromJson(js,new TypeToken<List<BedInfoModel>>(){}.getType());
                if (BedInfo.isEmpty()){
                    Toast.makeText(BedList.this, "Not Available", Toast.LENGTH_SHORT).show();
                }
                BedListAdapter bedListAdapter = new BedListAdapter(BedList.this,BedInfo);
                recycler.setAdapter(bedListAdapter);
                hideProgressDialog();

            }

            @Override
            public void onFailure(Call<ResponseModel> call, Throwable t) {
                hideProgressDialog();
                logError(TAG,FAILURE);
            }
        };
    }

    public void refresh(View view){
        getbedList();
    }

}
