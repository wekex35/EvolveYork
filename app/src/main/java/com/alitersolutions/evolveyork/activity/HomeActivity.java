package com.alitersolutions.evolveyork.activity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.provider.Settings;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.alitersolutions.evolveyork.R;
import com.alitersolutions.evolveyork.authenticate.LoginActivity;
import com.alitersolutions.evolveyork.utils.AppUtils;
import com.google.gson.Gson;

import java.util.ArrayList;

import static com.alitersolutions.evolveyork.utils.AppUtils.deviceIMEI;
import static com.alitersolutions.evolveyork.utils.AppUtils.saveServerInfo;
import static com.alitersolutions.evolveyork.utils.AppUtils.sendMessage;
import static com.alitersolutions.evolveyork.utils.Constants.BASEURL;
import static com.alitersolutions.evolveyork.utils.Constants.TOKEN;
import static com.alitersolutions.evolveyork.utils.SharedPreferenceUtil.getStringValue;
import static com.alitersolutions.evolveyork.utils.SharedPreferenceUtil.storeStringValue;

public class HomeActivity extends BaseActivity /*implements HomeApplicationAdapter.ItemClickListener*/ {
    private RecyclerView r_home_applciations;
    //private HomeApplicationAdapter homeApplicationAdapter;
    private String TAG = "HomeActivity";
    private Gson gson = new Gson();
    private static final int REQUEST_CONNECT_DEVICE_SECURE = 1;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        getSupportActionBar().setDisplayShowHomeEnabled(false);
        String BASE_SITE = getStringValue(this,BASEURL);
        CheckPermissionAndStartIntent();
    }

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
            case R.id.secure_connect_scan: {
                // Launch the BluetoothDeviceListActivity to see devices and do scan
                if (!mBluetoothAdapter.isEnabled()) {
                    Intent enableIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
                    startActivityForResult(enableIntent, REQUEST_ENABLE_BT);
                    // Otherwise, setup the chat session
                }else {
                    selectbluetooth();
                }


                return true;
            }
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

    public void printLt(View view) {
      //  printZplTemplate("hello","hello");
        sendMessage(this,"hello");

    }

    private void CheckPermissionAndStartIntent() {
        if (ContextCompat.checkSelfPermission(HomeActivity.this, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED
                || ContextCompat.checkSelfPermission(HomeActivity.this, Manifest.permission.READ_PHONE_STATE) == PackageManager.PERMISSION_DENIED) {
            ActivityCompat.requestPermissions(HomeActivity.this, new String[]{Manifest.permission.READ_PHONE_STATE}, 1);
            //SEY SOMTHING LIKE YOU CANT ACCESS WITHOUT PERMISSION
        } else {
            doSomthing();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String permissions[], @NonNull int[] grantResults) {
        switch (requestCode) {
            case 1: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    doSomthing();
                } else if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_DENIED) {
                    //SEY SOMTHING LIKE YOU CANT ACCESS WITHOUT PERMISSION
                    //you can show something to user and open setting -> apps -> youApp -> permission
                    // or unComment below code to show permissionRequest Again
                    //ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.READ_PHONE_STATE}, 1);
                }
            }
        }
    }


    public  void doSomthing() {
        AppUtils.deviceIMEI = getDeviceIMEI(HomeActivity.this);
        Log.d(TAG, "doSomthing: "+deviceIMEI);
        //andGoToYourNextStep
    }

    public String getDeviceIMEI(Activity activity) {

        String deviceUniqueIdentifier = null;
        /*TelephonyManager tm = (TelephonyManager) activity.getSystemService(Context.TELEPHONY_SERVICE);
        if (null != tm) {
            if (ActivityCompat.checkSelfPermission(activity, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED)
                ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.READ_PHONE_STATE}, 1);
            else
                deviceUniqueIdentifier = tm.getDeviceId();
            if (null == deviceUniqueIdentifier || 0 == deviceUniqueIdentifier.length())
                deviceUniqueIdentifier = Settings.Secure.getString(HomeActivity.this.getContentResolver(), Settings.Secure.ANDROID_ID);

        }*/
        deviceUniqueIdentifier = Settings.Secure.getString(HomeActivity.this.getContentResolver(), Settings.Secure.ANDROID_ID);
        return deviceUniqueIdentifier;
    }



    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case REQUEST_CONNECT_DEVICE_SECURE:
                // When DeviceListActivity returns with a device to connect
                if (resultCode == Activity.RESULT_OK) {
                    connectDevice(data, true);
                }
                break;
            case REQUEST_CONNECT_DEVICE_INSECURE:
                // When DeviceListActivity returns with a device to connect
                if (resultCode == Activity.RESULT_OK) {
                    connectDevice(data, false);
                }
                break;
            case REQUEST_ENABLE_BT:
                // When the request to enable Bluetooth returns
                if (resultCode == Activity.RESULT_OK) {
                    // Bluetooth is now enabled, so set up a chat session
                    selectbluetooth();
                } else {
                    // User did not enable Bluetooth or an error occurred
                    Log.d(TAG, "onActivityResult: ");
                    Toast.makeText(this, "Turn on bluetooth",
                            Toast.LENGTH_SHORT).show();
//                    finish();
                }
        }
    }

    private void selectbluetooth() {
        intiBuletooh();
        Intent serverIntent = new Intent(this, BluetoothDeviceListActivity.class);
        startActivityForResult(serverIntent, REQUEST_CONNECT_DEVICE_SECURE);
    }


}
