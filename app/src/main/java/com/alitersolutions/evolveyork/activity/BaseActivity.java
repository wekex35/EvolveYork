package com.alitersolutions.evolveyork.activity;

import android.Manifest;
import android.app.Activity;
import android.app.Dialog;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.content.ContextCompat;

import com.alitersolutions.evolveyork.R;
import com.alitersolutions.evolveyork.utils.Constants;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Set;

import static com.alitersolutions.evolveyork.utils.AppUtils.intJsonreader;

public class BaseActivity extends AppCompatActivity {

    private ProgressDialog progressDialog;
    public Gson gson = new Gson();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        //  if (getStringValue(this,TOKEN).length()>50)
          //  openAcitivty(MainActivity.class);
    /*    if (!isServerAvailable()){
            showToast("Server Unavailable");
        }*/
        progressDialog = new ProgressDialog(this);
        progressDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);

        setActionBar();
        intiBuletooh();

//        getHashKey(this);
    }


    public void showToast(String str){
        Toast.makeText(this, str, Toast.LENGTH_SHORT).show();
    }



    protected void setActionBar() {

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
    }

    public void showProgressDialog(String msg) {
        try {
            if (progressDialog != null && !progressDialog.isShowing()) {
                progressDialog.setMessage(msg);
                progressDialog.show();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void showProgressDialog() {
        showProgressDialog("Loading...");
    }

    public void hideProgressDialog() {
        try {
            if (progressDialog != null && progressDialog.isShowing()) {
                progressDialog.dismiss();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    protected void openAcitivty(Class<?> cls) {
        Intent intent = new Intent(this, cls);
        //intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    protected void openAcitivtyC(Class<?> cls) {
        Intent intent = new Intent(this, cls);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == android.R.id.home) {
            onBackPressed();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

  /*  @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(
                R.anim.no_anim, R.anim.slide_right_out);
        AppUtils.hideSoftKeyboard(this);
    }*/
 /*
  protected void openAcitivty(Intent intent2, Class<?> cls) {
        Intent intent = new Intent(this, cls);
        if (intent2 != null) {
            intent = intent2;
            intent.setClass(this, cls);
        }
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    protected void openAcitivty(Bundle bundle, Class<?> cls) {
        Intent intent = new Intent(this, cls);
        intent.putExtra(Constants.DATA, bundle);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    protected void openAcitivty(ArrayList<String> categories, Class<?> cls, int code, String title, String seletectedItem) {
        Intent newIntent = new Intent(this, cls);
        newIntent.putStringArrayListExtra(Constants.DATA, categories);
        newIntent.putExtra(Constants.TITLE, title);
        newIntent.putExtra(Constants.SELECTED_ITEM,seletectedItem);
        startActivityForResult(newIntent, code);
    }

    protected void openActivityForResult(Class<?> cls) {
        Intent intent = new Intent(this, cls);
        intent.putExtra(Constants.IS_RESULT_ACTIVITY, true);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivityForResult(intent, Constants.REQUEST_KEY_LOGIN);
    }*/

    private void setStatus(CharSequence subTitle) {
        setTitle(subTitle);
    }

    private Set<BluetoothDevice> mPairedDevices;
    private BluetoothAdapter mBluetoothAdapter = null;
    private ArrayAdapter<String> mBTArrayAdapter;
    private static final String TAG = "DeviceListActivity";
    public static String EXTRA_DEVICE_ADDRESS = "device_address";
    private static final int REQUEST_CONNECT_DEVICE_SECURE = 1;
    private static final int REQUEST_CONNECT_DEVICE_INSECURE = 2;
    private static final int REQUEST_ENABLE_BT = 3;
    public static BluetoothChatService mChatService = null;
    private String mConnectedDeviceName = null;
    Dialog dialog;
    private static String address;


    @Override
    public void onStart() {
        super.onStart();
        // If BT is not on, request that it be enabled.
        // setupChat() will then be called during onActivityResult
        if (!mBluetoothAdapter.isEnabled()) {
            Intent enableIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(enableIntent, REQUEST_ENABLE_BT);
            // Otherwise, setup the chat session
        }
    }

    public void intiBuletooh() {
        mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        mChatService = new BluetoothChatService(getBaseContext());
        mChatService.start();

        // If the adapter is null, then Bluetooth is not supported

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED)
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_COARSE_LOCATION}, 1);
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
                    // setupChat();
                } else {
                    // User did not enable Bluetooth or an error occurred
                    Log.d(TAG, "onActivityResult: ");
                    Toast.makeText(this, "bt_not_enabled_leaving",
                            Toast.LENGTH_SHORT).show();
                    finish();
                }
        }
    }

    private void connectDevice(Intent data, boolean secure) {
        // Get the device MAC address
        address = data.getExtras().getString(BluetoothDeviceListActivity.EXTRA_DEVICE_ADDRESS);
        // Get the BluetoothDevice object
        BluetoothDevice device = mBluetoothAdapter.getRemoteDevice(address);
        // Attempt to connect to the device
        mChatService.connect(device, secure);
    }

    public  void  logError(String Tag, String Msg){
        Log.e(Tag, "logError: "+Msg);
    }

    private BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            updateUI(intent);
        }
    };

    private void updateUI(Intent intent) {
//        findViewById(R.id.progressBar).setVisibility(View.GONE);
        String Rdata = intent.getStringExtra("datafromService");
        Log.d(TAG, "updateUI: "+Rdata);
        JSONObject jsonObject = null;
        try {
            jsonObject = new JSONObject(Rdata);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        int what = intJsonreader(jsonObject, "what");
        Log.d(TAG, "updateUI:what "+what);
        switch (what) {
            case Constants.MESSAGE_STATE_CHANGE:
                int what2 = intJsonreader(jsonObject, "bytes");
                Log.d(TAG, "updateUI:what2 "+what);
                switch (what2) {
                    case BluetoothChatService.STATE_CONNECTED:
                        setStatus(getString(R.string.title_connected_to, mConnectedDeviceName));
                        Nofication(getString(R.string.title_connected_to, mConnectedDeviceName));

                        // mConversationArrayAdapter.clear();
                        break;
                    case BluetoothChatService.STATE_CONNECTING:

                        setStatus(getString(R.string.title_connecting));
                        Nofication(getString(R.string.title_connecting));

                        break;
                    case BluetoothChatService.STATE_LISTEN:
                    case BluetoothChatService.STATE_NONE:
                        Nofication("Disconnected");
                        setStatus(getString(R.string.title_not_connected));
                        break;
                }
                break;
            case Constants.MESSAGE_DEVICE_NAME:
                // save the connected device's name
                //mConnectedDeviceName = StringJsonreader(jsonObject, "msg");

                Toast.makeText(this, "Connected to "
                        + mConnectedDeviceName, Toast.LENGTH_SHORT).show();

                break;

            case Constants.MESSAGE_READ:

//                String readMessage = StringJsonreader(jsonObject, "msg");
//                String bytes = StringJsonreader(jsonObject, "bytes");

                //datahandler.addlayout(scrollView,messageHolder,BluetoothTerminal.this,readMessage,"RX",StringJsonreader(jsonObject, "bytes"));
//                Log.d(TAG, " handleMessage2: "+readMessage);
                break;
            case Constants.MESSAGE_WRITE:
//                String writeMessage = StringJsonreader(jsonObject, "msg");
                //datahandler.addlayout(scrollView,messageHolder,BluetoothTerminal.this,writeMessage,"TX",StringJsonreader(jsonObject, "bytes"));
//                Log.d(TAG, " handleMessage2: "+writeMessage);
                break;
//            case Constants.MESSAGE_TOAST:
//                Toast.makeText(MainActivity.this, StringJsonreader(jsonObject, "msg"),ast.LENGTH_SHORT).show();

//                break;

        }


    }


    @Override
    public void onResume() {
        super.onResume();

        registerReceiver(broadcastReceiver, new IntentFilter(BluetoothChatService.BROADCAST_ACTION));
    }

    @Override
    public void onPause() {
        super.onPause();
        unregisterReceiver(broadcastReceiver);
    }

    private void Nofication(String message) {
        String title = "Printer";
        // String message = "Disconnected ";
        Intent intent = new Intent(this, HomeActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);


        String channelId = "fd634dgdft5";
        Uri defaultSoundUri= RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this, channelId)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle(title)
                .setContentText(message)
                // .setLargeIcon(emailObject.getSenderAvatar())
                .setStyle(new NotificationCompat.BigTextStyle()
                        .bigText(message))
                .setAutoCancel(true)
                .setSound(defaultSoundUri)
                .setContentIntent(pendingIntent);


        NotificationManager notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        // Since android Oreo notification channel is needed.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(channelId,
                    "Channel human readable title",
                    NotificationManager.IMPORTANCE_DEFAULT);
            notificationManager.createNotificationChannel(channel);
        }

        notificationManager.notify(0 /* ID of notification */,notificationBuilder.build());

    }

}
