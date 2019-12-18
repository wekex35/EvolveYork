package com.alitersolutions.evolveyork.activity;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.alitersolutions.evolveyork.R;
import com.alitersolutions.evolveyork.Retrofit.RetrofitUtil;
import com.alitersolutions.evolveyork.model.AddBedModel;
import com.alitersolutions.evolveyork.model.BedSizes;
import com.alitersolutions.evolveyork.model.BedTypes;
import com.alitersolutions.evolveyork.model.ResponseModel;
import com.google.gson.reflect.TypeToken;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import me.dm7.barcodescanner.zbar.Result;
import me.dm7.barcodescanner.zbar.ZBarScannerView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TagRegisterActivity extends BaseActivity implements ZBarScannerView.ResultHandler {
    private String TAG = "TagRegisterActivity";
    public static ZBarScannerView mScannerView;
    AutoCompleteTextView _itemID,_loc_id;
    EditText _quantity,_batch_no,_remarks;
    ImageView qr_code;
    TextView dateTV;
    Spinner bedSizes,bedTypes;
    int bedSizeId,bedTypeId;
    Button save;
    final Calendar myCalendar = Calendar.getInstance();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tag_register);
        setTitle("Cycle Count");
//        if (checkPermission()) {
//            //startActivity(new Intent(this,BarcodeScanner.class));
//            //main logic or main code
//            requestPermission();
//            // . write your main code to execute, It will execute if the permission is already given.
//
//        } else {
//            requestPermission();
//        }

        initView();

        qr_code.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(TagRegisterActivity.this, "Scan Item Code", Toast.LENGTH_SHORT).show();
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

        save = findViewById(R.id.save);

//        RetrofitUtil.createProviderAPIV2(this).getallTypes().enqueue(loadTypes());
 //       RetrofitUtil.createProviderAPIV2(this).getAllSizes().enqueue(loadSizes());

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

                ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(TagRegisterActivity.this,android.R.layout.simple_spinner_item, spinnerSizes);
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

                ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(TagRegisterActivity.this,android.R.layout.simple_spinner_item, spinnerTypes);
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
        new AlertDialog.Builder(TagRegisterActivity.this)
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


    public void addBed(View view) {
        String itemId = _itemID.getText().toString();
        String batchNo = _batch_no.getText().toString();
        String quantity = _quantity.getText().toString();
        String remarks = _remarks.getText().toString();
        String loc_id = _loc_id.getText().toString();

/*        AddBedModel Abm = new AddBedModel();
        if(bedId.length() < 1){
            showToast("RFID is required");
        }else if(des.length() < 1){

            showToast("Please Add Description");

        }else if (date.toLowerCase().contains("select")){
            Toast.makeText(this, "Select Date", Toast.LENGTH_SHORT).show();
        }else {
            Abm.setEvolveBed_Make(date);
            Abm.setEvolveBed_RFID(bedId);
            Abm.setEvolveBedSize_ID(bedSizeId);
            Abm.setEvolveBedType_ID(bedTypeId);
            Abm.setEvolveBed_Desc(des);

            RetrofitUtil.createProviderAPIV2(this).addBed(Abm).enqueue(bedAdded());
        }*/



    }

    private Callback<ResponseModel> bedAdded() {
        showProgressDialog();
        return new Callback<ResponseModel>() {
            @Override
            public void onResponse(Call<ResponseModel> call, Response<ResponseModel> response) {
                Toast.makeText(TagRegisterActivity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                if(response.body().getMessage().toLowerCase().contains("success")){
                    TagRegisterActivity.this.finish();
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
}
