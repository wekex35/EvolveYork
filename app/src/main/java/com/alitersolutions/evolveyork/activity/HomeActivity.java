package com.alitersolutions.evolveyork.activity;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import androidx.recyclerview.widget.RecyclerView;

import com.alitersolutions.evolveyork.R;
import com.alitersolutions.evolveyork.authenticate.LoginActivity;
import com.google.gson.Gson;

import static com.alitersolutions.evolveyork.utils.AppUtils.saveServerInfo;
import static com.alitersolutions.evolveyork.utils.Constants.BASEURL;
import static com.alitersolutions.evolveyork.utils.Constants.TOKEN;
import static com.alitersolutions.evolveyork.utils.SharedPreferenceUtil.getStringValue;
import static com.alitersolutions.evolveyork.utils.SharedPreferenceUtil.storeStringValue;

public class HomeActivity extends BaseActivity /*implements HomeApplicationAdapter.ItemClickListener*/ {
    private RecyclerView r_home_applciations;
    //private HomeApplicationAdapter homeApplicationAdapter;
    private String TAG = "HomeActivity";
    private Gson gson = new Gson();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        getSupportActionBar().setDisplayShowHomeEnabled(false);
        String BASE_SITE = getStringValue(this,BASEURL);

      //     r_home_applciations = findViewById(R.id.home_applications);

      //  RetrofitUtil.createProviderAPI().homeAppList().enqueue(setHomeAplicatinList());

    }



    public void getBed(View view) {
        openAcitivty(ItemList.class);
    }
/*
    private Callback<ResponseModel> setHomeAplicatinList() {
        showProgressDialog("Loading...");
        return  new Callback<ResponseModel>() {
            @Override
            public void onResponse(Call<ResponseModel> call, Response<ResponseModel> response) {
                Log.d(TAG, "onResponse: "+response.body().getEvolveToken());
                if ( response.body().getResult() != null) {

                    String js = gson.toJson(response.body().getResult());

                    ArrayList<HomeApplicationModel> list = (ArrayList<HomeApplicationModel>) gson.fromJson(js,
                            new TypeToken<ArrayList<HomeApplicationModel>>() {
                            }.getType());

                    Log.d(TAG, "onResponse: "+list.get(1).getEvolveApp_Code());
                    setHomeApplications(list);
                    hideProgressDialog();
                   *//* List<HomeApplicationModel> list = gson.fromJson(js,List<HomeApplicationModel>);
                    openAcitivty(MainActivity.class);
                    hideProgressDialog();*//*
                }
            }

            @Override
            public void onFailure(Call<ResponseModel> call, Throwable t) {
                hideProgressDialog();
                Log.d(TAG, "onFailure: "+t.getCause());
            }
        };
    }


    private void setHomeApplications(ArrayList<HomeApplicationModel> list) {
        int numberOfColumns = 2;
        r_home_applciations.setLayoutManager(new GridLayoutManager(this, numberOfColumns));
        homeApplicationAdapter = new HomeApplicationAdapter(this, list);
        homeApplicationAdapter.setClickListener(this);
        r_home_applciations.setAdapter(homeApplicationAdapter);
    }

    @Override
    public void onItemClick(View view, int position) {
        Log.i("TAG", "You clicked number " + homeApplicationAdapter.getItem(position).getEvolveApp_Name().toLowerCase() + ", which is at cell position " + position);
        if (homeApplicationAdapter.getItem(position).getEvolveApp_Code().toLowerCase().contains("eapprovals")){
            startActivity(new Intent(HomeActivity.this, KonnectDevices.class));
        }
    }*/

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.home_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()){
            case R.id.server_setting :
                saveServerInfo(HomeActivity.this);
                return true;

            case R.id.logout:
                storeStringValue(HomeActivity.this,TOKEN,"");
                openAcitivtyC(LoginActivity.class);
                return true;
            default: return super.onOptionsItemSelected(item);
        }

    }

    public void addCycleCount(View view) {
        openAcitivty(CycleCountActivity.class);
    }

    public void showItemLIst(View view) {
        openAcitivty(ItemList.class);
    }

    public void locationList(View view) {
        openAcitivty(LocationLists.class);
    }

    public void countHistry(View view) {
        openAcitivty(CycleHistory.class);

    }
}
