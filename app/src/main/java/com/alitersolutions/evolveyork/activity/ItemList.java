package com.alitersolutions.evolveyork.activity;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.Toast;

import com.alitersolutions.evolveyork.R;
import com.alitersolutions.evolveyork.Retrofit.RetrofitUtil;
import com.alitersolutions.evolveyork.adapter.LocationListsAdapter;
import com.alitersolutions.evolveyork.model.BedInfoModel;
import com.alitersolutions.evolveyork.model.MasterItems;
import com.alitersolutions.evolveyork.model.MasterLocation;
import com.alitersolutions.evolveyork.model.ResponseModel;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;

import com.alitersolutions.evolveyork.adapter.ItemListAdapter;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.alitersolutions.evolveyork.authenticate.LoginActivity.BASE_SITE;
import static com.alitersolutions.evolveyork.authenticate.LoginActivity.BASE_URL;
import static com.alitersolutions.evolveyork.utils.AppUtils.inputStreamToString;
import static com.alitersolutions.evolveyork.utils.AppUtils.saveServerInfo;
import static com.alitersolutions.evolveyork.utils.Constants.FAILURE;
import static com.alitersolutions.evolveyork.utils.Constants.ITEMINFO;
import static com.alitersolutions.evolveyork.utils.Constants.LOCATIONINFO;
import static com.alitersolutions.evolveyork.utils.SharedPreferenceUtil.getStringValue;
import static com.alitersolutions.evolveyork.utils.SharedPreferenceUtil.storeStringValue;

public class ItemList extends BaseActivity implements TextWatcher {

    RecyclerView recycler;
    String TAG = "ItemList";
    EditText _itemID,_loc_id;
    List<MasterItems> masterItems;
    ItemListAdapter itemListAdapter;
    String ItemInfoString;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_list);
        setTitle(R.string.axle_list);
        initView();
        //loadItems();
       // getbedList();
        ItemInfoString = getStringValue(this,ITEMINFO);
        if (ItemInfoString.isEmpty())
            getItemsList();
        else
            loadItems();
    }

    private void initView() {
        _itemID = findViewById(R.id.item_id);
        recycler = findViewById(R.id.recycler);
        recycler.setLayoutManager(new LinearLayoutManager(this));
    }

    private void loadItems() {
        masterItems = gson.fromJson(ItemInfoString,new TypeToken<List<MasterItems>>(){}.getType());
        
        if (masterItems.isEmpty()){
            Toast.makeText(ItemList.this, "Not Available", Toast.LENGTH_SHORT).show();
        }

        itemListAdapter = new ItemListAdapter(ItemList.this,masterItems);
        recycler.setAdapter(itemListAdapter);

        _itemID.addTextChangedListener(this);
        
    }

    private void getItemsList() {
        Log.d(TAG, "getItemsList: "+BASE_URL);
        if (BASE_SITE.length() > 5) {
            RetrofitUtil.createProviderAPI().getallItems().enqueue(LoadItemList());
        }else {
            saveServerInfo(ItemList.this);
        }

    }
    
    private Callback<ResponseModel> LoadItemList() {
        showProgressDialog();
        return  new Callback<ResponseModel>() {
            @Override
            public void onResponse(Call<ResponseModel> call, Response<ResponseModel> response) {
                ItemInfoString = gson.toJson(response.body().getItems());
                Log.d(TAG, "onResponse: "+ItemInfoString);
                // List<MasterLocation> masterLocations = gson.fromJson(js,new TypeToken<List<MasterLocation>>(){}.getType());
                if (ItemInfoString.length() < 10){
                    Toast.makeText(ItemList.this, "Not Available", Toast.LENGTH_SHORT).show();
                }else {
                    storeStringValue(ItemList.this,ITEMINFO,ItemInfoString);
                    loadItems();
                }
                hideProgressDialog();
            }

            @Override
            public void onFailure(Call<ResponseModel> call, Throwable t) {
                hideProgressDialog();
                showToast("Server Not Connected");
                logError(TAG,FAILURE);
            }
        };
    }


    public void refresh(View view){
        getItemsList();
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        Log.d(TAG, "beforeTextChanged: "+s);
    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        List<MasterItems> filteredItem = new ArrayList<>();
       for (MasterItems masterItems : masterItems){
           if (masterItems.getEvolveItemPart() != null)
           if (masterItems.getEvolveItemPart().startsWith(s.toString())){
               filteredItem.add(masterItems);
           }
       }
       if (filteredItem.size() > 0) {
           itemListAdapter = new ItemListAdapter(ItemList.this, filteredItem);
           recycler.setAdapter(itemListAdapter);
           itemListAdapter.notifyDataSetChanged();
       }
    }

    @Override
    public void afterTextChanged(Editable s) {
        Log.d(TAG, "afterTextChanged: "+s.toString());
    }
}
