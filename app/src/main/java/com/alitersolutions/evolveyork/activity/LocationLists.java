package com.alitersolutions.evolveyork.activity;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.alitersolutions.evolveyork.R;
import com.alitersolutions.evolveyork.Retrofit.RetrofitUtil;
import com.alitersolutions.evolveyork.adapter.LocationListsAdapter;
import com.alitersolutions.evolveyork.model.MasterLocation;
import com.alitersolutions.evolveyork.model.ResponseModel;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.alitersolutions.evolveyork.utils.AppUtils.BASE_SITE;
import static com.alitersolutions.evolveyork.utils.AppUtils.saveServerInfo;
import static com.alitersolutions.evolveyork.utils.Constants.FAILURE;
import static com.alitersolutions.evolveyork.utils.Constants.LOCATIONINFO;
import static com.alitersolutions.evolveyork.utils.SharedPreferenceUtil.getStringValue;
import static com.alitersolutions.evolveyork.utils.SharedPreferenceUtil.storeStringValue;

public class LocationLists extends BaseActivity implements TextWatcher {

    RecyclerView recycler;
    String TAG = "LocationLists";
    EditText _itemID,_loc_id;
    List<MasterLocation> masterLocations;
    LocationListsAdapter LocationListsAdapter;
    private String locationInfoString;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location_lists);
        setTitle(R.string.location_list);
        initView();
//        loadItems();
        locationInfoString = getStringValue(this,LOCATIONINFO);
        if (locationInfoString.isEmpty())
            getLocationList();
        else
            loadItems();

        // getbedList();
    }

    private void initView() {
        _itemID = findViewById(R.id.item_id);
        recycler = findViewById(R.id.recycler);
        recycler.setLayoutManager(new LinearLayoutManager(this));
    }

    private void getLocationList() {
        if (BASE_SITE.length() > 5) {
            RetrofitUtil.createProviderAPI().getAllLocation().enqueue(LoadLocationList());
        }else {
            saveServerInfo(LocationLists.this);
        }
    }
    private void loadItems() {
       // String myJson= inputStreamToString(this.getResources().openRawResource(R.raw.location_sapi_response));
        masterLocations = gson.fromJson(locationInfoString,new TypeToken<List<MasterLocation>>(){}.getType());

        if (masterLocations.isEmpty()){
            Toast.makeText(LocationLists.this, "Not Available", Toast.LENGTH_SHORT).show();
        }

        LocationListsAdapter = new LocationListsAdapter(LocationLists.this,masterLocations);
        recycler.setAdapter(LocationListsAdapter);

        _itemID.addTextChangedListener(this);
    }

    private Callback<ResponseModel> LoadLocationList() {
        showProgressDialog();
        return  new Callback<ResponseModel>() {
            @Override
            public void onResponse(Call<ResponseModel> call, Response<ResponseModel> response) {
                locationInfoString = gson.toJson(response.body().getLocations());
               // List<MasterLocation> masterLocations = gson.fromJson(js,new TypeToken<List<MasterLocation>>(){}.getType());
                if (locationInfoString.length() < 10){
                    Toast.makeText(LocationLists.this, "Not Available", Toast.LENGTH_SHORT).show();
                }else {
                   storeStringValue(LocationLists.this,LOCATIONINFO,locationInfoString);
                    loadItems();
                }
                hideProgressDialog();
            }

            @Override
            public void onFailure(Call<ResponseModel> call, Throwable t) {
                hideProgressDialog();
                logError(TAG,FAILURE);
                showToast("Server is not reachable");
            }
        };
    }



    public void refresh(View view){
        getLocationList();
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        Log.d(TAG, "beforeTextChanged: "+s);
    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        List<MasterLocation> filteredItem = new ArrayList<>();
        for (MasterLocation masterLocation : masterLocations){
            if (masterLocation.getEvolveItemLocation() != null)
                if (masterLocation.getEvolveItemLocation().startsWith(s.toString())){
                    filteredItem.add(masterLocation);
                }
        }
        if (filteredItem.size() > 0) {
            LocationListsAdapter = new LocationListsAdapter(LocationLists.this, filteredItem);
            recycler.setAdapter(LocationListsAdapter);
            LocationListsAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void afterTextChanged(Editable s) {
        Log.d(TAG, "afterTextChanged: "+s.toString());
    }
}
