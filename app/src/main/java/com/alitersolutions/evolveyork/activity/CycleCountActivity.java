package com.alitersolutions.evolveyork.activity;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.http.SslCertificate;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;

import com.alitersolutions.evolveyork.R;
import com.alitersolutions.evolveyork.Retrofit.RetrofitUtil;
import com.alitersolutions.evolveyork.adapter.CycleHistoryAdapter;
import com.alitersolutions.evolveyork.authenticate.LoginActivity;
import com.alitersolutions.evolveyork.database.DatabaseHelper;
import com.alitersolutions.evolveyork.model.BedSizes;
import com.alitersolutions.evolveyork.model.BedTypes;
import com.alitersolutions.evolveyork.model.MasterItems;
import com.alitersolutions.evolveyork.model.MasterLocation;
import com.alitersolutions.evolveyork.model.ResponseModel;
import com.alitersolutions.evolveyork.model.SentModel;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import me.dm7.barcodescanner.zbar.Result;
import me.dm7.barcodescanner.zbar.ZBarScannerView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.alitersolutions.evolveyork.utils.AppUtils.BASE_SITE;
import static com.alitersolutions.evolveyork.utils.AppUtils.BASE_URL;
import static com.alitersolutions.evolveyork.utils.AppUtils.deviceIMEI;
import static com.alitersolutions.evolveyork.utils.AppUtils.getCurrentTimeStamp;
import static com.alitersolutions.evolveyork.utils.AppUtils.inputStreamToString;
import static com.alitersolutions.evolveyork.utils.AppUtils.saveServerInfo;
import static com.alitersolutions.evolveyork.utils.AppUtils.sendMessage;
import static com.alitersolutions.evolveyork.utils.Constants.EVOLVEUSRID;
import static com.alitersolutions.evolveyork.utils.Constants.EVOLVEUSRNAME;
import static com.alitersolutions.evolveyork.utils.Constants.ITEMINFO;
import static com.alitersolutions.evolveyork.utils.Constants.LOCATIONINFO;
import static com.alitersolutions.evolveyork.utils.Constants.USERLOGINDETAILS;
import static com.alitersolutions.evolveyork.utils.Constants.str;
import static com.alitersolutions.evolveyork.utils.SharedPreferenceUtil.getStringValue;
import static com.alitersolutions.evolveyork.utils.SharedPreferenceUtil.storeStringValue;

public class CycleCountActivity extends BaseActivity implements ZBarScannerView.ResultHandler {
    private String TAG = "CycleCountActivity";
    public static ZBarScannerView mScannerView;
    AutoCompleteTextView _itemID,_loc_id;
    EditText _quantity,_batch_no,_remarks;
    ImageView qr_code;
    TextView dateTV,itemDesc;
    Spinner bedSizes,bedTypes;
    int bedSizeId,bedTypeId;
    int EvolveLocId = 0;
    int EvovlePartId = 0;
    String itemDescription;

    String DLname,DIname;
    Spinner units;

    Button save;
    final Calendar myCalendar = Calendar.getInstance();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cycle_count);
        setTitle("Cycle Count");
        if (!checkPermission()) {
            //startActivity(new Intent(this,BarcodeScanner.class));
            //main logic or main code
            requestPermission();
            // . write your main code to execute, It will execute if the permission is already given.

        }
//        else {
//            requestPermission();
//        }

        initView();

        qr_code.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(CycleCountActivity.this, "Scan Item Code", Toast.LENGTH_SHORT).show();
                //readBarcode();
                readBarcode(_itemID);

            }

        });
    }

    private void initView() {
        _itemID = findViewById(R.id.item_id);
        _quantity = findViewById(R.id.quant);
        qr_code = findViewById(R.id.qr_code);
        _batch_no = findViewById(R.id.batch_no);
        _remarks = findViewById(R.id.remarks);
        _loc_id = findViewById(R.id.loc_id);
        itemDesc = findViewById(R.id.itemDesc);
        save = findViewById(R.id.save);
        units = findViewById(R.id.units);

//        RetrofitUtil.createProviderAPIV2(this).getallTypes().enqueue(loadTypes());
 //       RetrofitUtil.createProviderAPIV2(this).getAllSizes().enqueue(loadSizes());

        loadItems();
        loadLocations();

    }

    private void loadItems() {
        String myJson;// = inputStreamToString(this.getResources().openRawResource(R.raw.items_api_response));
        myJson = getStringValue(this,ITEMINFO);
        if (myJson.isEmpty()){
            openAcitivty(ItemList.class);
            finish();
        }else {
            final List<MasterItems> masterItems = gson.fromJson(myJson, new TypeToken<List<MasterItems>>() {
            }.getType());
            final ArrayList<String> LocAdaptList = new ArrayList<>();
            for (MasterItems masterItem : masterItems) {
                if (masterItem.getEvolveItemPart() != null)
                    LocAdaptList.add(masterItem.getEvolveItemPart().trim());
                Log.d(TAG, "loadItems: " + masterItem.getEvolveItemPart());
            }

            ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(CycleCountActivity.this, android.R.layout.simple_spinner_item, LocAdaptList);
            spinnerArrayAdapter.setDropDownViewResource(R.layout.autocomplete_item_dropdown_item); // The drop down vieww
            _itemID.setAdapter(spinnerArrayAdapter);

            _itemID.setThreshold(1);


            _itemID.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Log.d(TAG, "onItemSelected: " + masterItems.get(position).getEvolveItemDescription());
                    itemDescription = masterItems.get(position).getEvolveItemDescription();

                    itemDesc.setText(masterItems.get(position).getEvolveItemDescription());
                    EvovlePartId = masterItems.get(position).getEvolveItemID();
                    DIname = masterItems.get(position).getEvolveItemPart();
                    itemDesc.setVisibility(View.VISIBLE);
                }
            });
        }
    }

    private void loadLocations() {
        String myJson = getStringValue(this,LOCATIONINFO);
        if (myJson.isEmpty()){
            openAcitivty(LocationLists.class);
            finish();
        }else {
            //MasterLocation myModel = new Gson().fromJson(myJson, MasterLocation.class);
            final List<MasterLocation> masterLocations = gson.fromJson(myJson, new TypeToken<List<MasterLocation>>() {
            }.getType());

            ArrayList<String> LocAdaptList = new ArrayList<>();
            for (MasterLocation masterLocation : masterLocations) {
                LocAdaptList.add(masterLocation.getEvolveItemLocation().trim());
            }

            ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(CycleCountActivity.this, android.R.layout.simple_spinner_item, LocAdaptList);
            spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item); // The drop down vieww
            _loc_id.setAdapter(spinnerArrayAdapter);
            _loc_id.setThreshold(1);
            _loc_id.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    EvolveLocId = masterLocations.get(position).getEvolveItemLocationID();
                    DLname = masterLocations.get(position).getEvolveItemLocation();
                }
            });
        }
    }

    private Callback<ResponseModel> loadSizes() {
        return new Callback<ResponseModel>() {
            @Override
            public void onResponse(Call<ResponseModel> call, Response<ResponseModel> response) {

                String js = gson.toJson(response.body().getResult());
                final List<BedSizes> bedSizesArray = gson.fromJson(js,new TypeToken<List<BedSizes>>(){}.getType());
                ArrayList<String> spinnerSizes = new ArrayList<>();

                for (BedSizes bedsize: bedSizesArray) {
                    spinnerSizes.add(bedsize.getEvolveSize_Name());
                }

                ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(CycleCountActivity.this,android.R.layout.simple_spinner_item, spinnerSizes);
                spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item); // The drop down vieww
                bedSizes.setAdapter(spinnerArrayAdapter);

                bedSizes.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        bedSizeId = bedSizesArray.get(position).getEvolveSize_ID();
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                });


                Log.d(TAG, "onResponse: "+bedSizesArray.get(0).getEvolveSize_ID());
            }

            @Override
            public void onFailure(Call<ResponseModel> call, Throwable t) {

            }
        };
    }


    private Callback<ResponseModel> loadTypes() {
        return new Callback<ResponseModel>() {
            @Override
            public void onResponse(Call<ResponseModel> call, Response<ResponseModel> response) {
                String js = gson.toJson(response.body().getResult());
                final List<BedTypes> bedTypesArray = gson.fromJson(js,new TypeToken<List<BedTypes>>(){}.getType());
                ArrayList<String> spinnerTypes = new ArrayList<>();
                for (BedTypes bedtype: bedTypesArray) {
                    spinnerTypes.add(bedtype.getEvolveType_Name());
                }

                ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(CycleCountActivity.this,android.R.layout.simple_spinner_item, spinnerTypes);
                spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item); // The drop down vieww
                bedTypes.setAdapter(spinnerArrayAdapter);

                bedTypes.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        bedTypeId = bedTypesArray.get(position).getEvolveType_ID();
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                });


                Log.d(TAG, "onResponse: "+bedTypesArray.get(0).getEvolveType_ID());

            }

            @Override
            public void onFailure(Call<ResponseModel> call, Throwable t) {

            }
        };
    }

    private static final int PERMISSION_REQUEST_CODE = 200;

    private boolean checkPermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED) {
            // Permission is not granted
            return false;
        }
        return true;
    }

    private void requestPermission() {

        ActivityCompat.requestPermissions(this,
                new String[]{Manifest.permission.CAMERA},
                PERMISSION_REQUEST_CODE);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case PERMISSION_REQUEST_CODE:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    //Toast.makeText(getApplicationContext(), "Permission Granted", Toast.LENGTH_SHORT).show();

                    // main logic
                } else {
                    Toast.makeText(getApplicationContext(), "Permission Denied", Toast.LENGTH_SHORT).show();
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
                                != PackageManager.PERMISSION_GRANTED) {
                            showMessageOKCancel("You need to allow access permissions",
                                    new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                                                requestPermission();
                                            }
                                        }
                                    });
                        }
                    }
                }
                break;
        }
    }

    private void showMessageOKCancel(String message, DialogInterface.OnClickListener okListener) {
        new AlertDialog.Builder(CycleCountActivity.this)
                .setMessage(message)
                .setPositiveButton("OK", okListener)
                .setNegativeButton("Cancel", null)
                .create()
                .show();
    }


    boolean viewInit = true;
    Dialog dialog;
    EditText readBarCode;

    public void readBarcode(EditText editText) {
        readBarCode = editText;
        dialog = new Dialog(this,android.R.style.Theme_Black_NoTitleBar_Fullscreen);

        mScannerView = new ZBarScannerView(this);


        mScannerView.startCamera();          // Start camera on resume
        mScannerView.setResultHandler(this);
//        if (viewInit) {
        dialog.setContentView(mScannerView);
        viewInit = false;
//        }
        dialog.show();
        dialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                Log.d(TAG, "onDismiss: dj");
                mScannerView.stopCamera();           // Stop camera on pause

            }
        });
    }
    @Override
    public void handleResult(Result rawResult) {
        Log.v(TAG, rawResult.getContents()); // Prints scan results
        Log.v(TAG, rawResult.getBarcodeFormat().getName()); // Prints the scan format (qrcode, pdf417 etc.)
        readBarCode.setText(rawResult.getContents());
        dialog.dismiss();
    }

    public void datePicker(View view) {
        new DatePickerDialog(this, date, myCalendar
                .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                myCalendar.get(Calendar.DAY_OF_MONTH)).show();
    }

    DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {

        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear,
                              int dayOfMonth) {
            // TODO Auto-generated method stub
            myCalendar.set(Calendar.YEAR, year);
            myCalendar.set(Calendar.MONTH, monthOfYear);
            myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            updateLabel();
        }

    };

    private void updateLabel() {
        String myFormat = "yyyy-MM-dd"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        dateTV.setText(sdf.format(myCalendar.getTime()));
    }

    private Callback<ResponseModel> addData() {
        showProgressDialog();
        return new Callback<ResponseModel>() {
            @Override
            public void onResponse(Call<ResponseModel> call, Response<ResponseModel> response) {
                Toast.makeText(CycleCountActivity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                if(response.body().getMessage().toLowerCase().contains("success")){
                    CycleCountActivity.this.finish();
                }
                hideProgressDialog();
            }

            @Override
            public void onFailure(Call<ResponseModel> call, Throwable t) {
                logError("bedAdded",t.getMessage());
                hideProgressDialog();
            }
        };
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }

    public void print(View view) {

       // int itemId = Integer.parseInt(_itemID.getText().toString());
        final String batchNo = _batch_no.getText().toString();
        int quantity = 0;
        if (!_quantity.getText().toString().isEmpty())
          quantity = Integer.parseInt(_quantity.getText().toString());
        final String remarks = _remarks.getText().toString();
       // int loc_id = Integer.parseInt(_loc_id.getText().toString());
//        if (batchNo.isEmpty())
//            showToast("Batch No. cannot be empty");
//        else
        if (_quantity.getText().toString().isEmpty())
            showToast("Qunatity cannot be empty");
        else if (remarks.isEmpty() )
            showToast("Remarks cannot be empty");
        else if (_itemID.getText().toString().isEmpty())
            showToast("Select Valid Part");
        else if (EvovlePartId == 0)
            showToast("Select Valid Part");
        else if (_loc_id.getText().toString().isEmpty())
            showToast("Select Valid Location");
        else if (EvolveLocId == 0)
            showToast("Select Valid Location");
        else {
            final SentModel sentModel = new SentModel();
            DatabaseHelper databaseHelper = new DatabaseHelper(this);

            sentModel.setEvolveItemID(EvovlePartId);
            sentModel.setEvolveBatchNo(batchNo);
            sentModel.setEvolveItemQty(quantity);
            sentModel.setEvolveItemRemarks(remarks);
            sentModel.setEvolveItemLocationID(EvolveLocId);
            sentModel.setEvolveItemMeasuringUnits(units.getSelectedItem().toString());
            sentModel.setEvolveLocationName(DLname);
            sentModel.setEvolveItemName(DIname);
            sentModel.setUpdatedDate(getCurrentTimeStamp());
            sentModel.setUploadStatus("1");
            sentModel.setUploadStatus("1");

            String QRText = "PART ID :"+DIname
                    +"\n LOC ID : "+DLname
                    +"\n DES : "+itemDescription;

            final String sb2 = "! 0 200 200 424 1"+str
                    +"IN-DOTS"+str
                    +"B QR 455 5 M 2 U 3"+str
                    +"MA, "+QRText+" "+ str
                    +"ENDQR"+str
                    +"CENTER"+str
//                +"UNDERLINE OFF"+str
                    +"TEXT 4 0 20 15 Stock Tracking"+str
//                +"UNDERLINE ON"+str
                    +"LEFT"+str
                    +"TEXT 5 0 20 90 ITEM CODE : "+DIname+str
                    +"TEXT 7 0 20 120 DESC : "+itemDescription+str
                    +"TEXT 5 0 20 170 QTY : "+quantity+" "+units.getSelectedItem().toString()+str
//                +"RIGHT"+str
                    +"TEXT 5 0 240 170 LOCATION : "+DLname+str
//                +"LEFT"+str
                    +"TEXT 5 0 20 200 STOCK DATE : "+getCurrentTimeStamp()+str
                    +"TEXT 5 0 20 250 STOCK TAKEN : "+" User "+str
                    +"PRINT"+str;
            sentModel.setPrinterText(sb2);

            Log.d(TAG, "print: "+sb2);

            if(databaseHelper.insertCycleCount(sentModel) > 0 ){

            if (BASE_SITE.length() > 5) {
                showProgressDialog("Uploading...");
                final int finalQuantity = quantity;
                String url = BASE_URL+"Items_API/update_item_history";
                StringRequest putRequest = new StringRequest(Request.Method.POST, url,
                        new com.android.volley.Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                Log.d("Response", response);
                                hideProgressDialog();
                                sendMessage(CycleCountActivity.this,sb2);//Send Message to the printer
                                ResponseModel responseModel = gson.fromJson(response,ResponseModel.class);
                                if ( responseModel.getError().contains("false")) {
                                    Toast.makeText(CycleCountActivity.this, "Updated to Server", Toast.LENGTH_SHORT).show();
                                    _batch_no.setText("");
                                    _itemID.setText("");
                                    _loc_id.setText("");
                                    _quantity.setText("");
                                    _remarks.setText("");
                                    itemDesc.setVisibility(View.GONE);
                                }else {
                                    showToast("Not Added");
                                }
                            }
                        }, new com.android.volley.Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        showToast("Server Not Connected");
                        hideProgressDialog();
                    }
                }
                ) {

                    @Override
                    protected Map<String, String> getParams()
                    {
                        Map<String, String>  params = new HashMap<String, String>();
                        params.put("EvolveBatchNo", batchNo);
                        params.put("EvolveItemID", String.valueOf(EvovlePartId));
                        params.put("EvolveItemLocationID", String.valueOf(EvolveLocId));
                        params.put("EvolveItemMeasuringUnits", units.getSelectedItem().toString());
                        params.put("EvolveItemHistoryRemark", remarks);
                        params.put("EvolveItemQty", String.valueOf(finalQuantity));
                        params.put("EvolveItemDescription", itemDescription);
                        params.put("EvolveUsr_ID", getStringValue(CycleCountActivity.this,EVOLVEUSRID));
                        params.put("EvolveItemHistoryMacAddress", deviceIMEI);
                        Log.d(TAG, "getParams: "+params.toString());
                        return params;
                    }

                };
                RequestQueue queue = Volley.newRequestQueue(this);
                queue.add(putRequest);
            }else {
                saveServerInfo(CycleCountActivity.this);
            }
            }else
                Log.d(TAG, "Print: Not Saved");

            Log.d(TAG, "print: " + gson.toJson(sentModel));




        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.cycle_count_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()){
            case R.id.show_history :
                saveServerInfo(CycleCountActivity.this);
                return true;

            case R.id.server_setting :
                openAcitivty(CycleHistoryAdapter.class);
                return true;

            default: return super.onOptionsItemSelected(item);
        }

    }



}
