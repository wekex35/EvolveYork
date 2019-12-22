/*
 * Created by Nirmal Mandal on 10/10/19 4:25 PM
 * Copyright (c) 2019 . All rights reserved.
 * Last modified 10/10/19 4:25 PM
 */
package com.alitersolutions.evolveyork.utils;

import android.app.Activity;
import android.app.Dialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.alitersolutions.evolveyork.R;
import com.alitersolutions.evolveyork.activity.BluetoothChatService;
import com.google.gson.annotations.SerializedName;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.net.InetAddress;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static com.alitersolutions.evolveyork.activity.BaseActivity.mChatService;
import static com.alitersolutions.evolveyork.authenticate.LoginActivity.BASE_SITE;
import static com.alitersolutions.evolveyork.authenticate.LoginActivity.BASE_URL;
import static com.alitersolutions.evolveyork.utils.Constants.APIROUTE;
import static com.alitersolutions.evolveyork.utils.Constants.BASEURL;
import static com.alitersolutions.evolveyork.utils.SharedPreferenceUtil.storeStringValue;
import static java.lang.System.in;

public class AppUtils {

    private static final String EMPTY_STRING = "";
    private static String TAG = "AppUtils";
    public static String HASHKEY;


    public static void hideSoftKeyboard(Activity activity) {
        final InputMethodManager inputMethodManager = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        if (inputMethodManager.isActive()) {
            if (activity.getCurrentFocus() != null) {
                inputMethodManager.hideSoftInputFromWindow(activity.getCurrentFocus().getWindowToken(), 0);
            }
        }
    }

    public static String getDay(String day) {
        Date date = getDate(day);
        SimpleDateFormat outFormat = new SimpleDateFormat("EEEE");
        String goal = outFormat.format(date);
        return goal;
    }

    @Nullable
    private static Date getDate(String day) {
        SimpleDateFormat inFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = null;
        try {
            date = inFormat.parse(day);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }

    public static String getParsedDate(String day) {

        Date date = getDate(day);
        SimpleDateFormat outFormat = new SimpleDateFormat("dd-MM-yyyy");
        String goal = outFormat.format(date);

        return goal;
    }


    public static String longToDate(String val, String pattern, int multiplier) {
        try {
            if (val != null && !val.isEmpty()) {
                Date date = new Date(Long.parseLong(val) * multiplier);
                SimpleDateFormat df2 = new SimpleDateFormat(pattern);
                return df2.format(date);
            } else {
                return EMPTY_STRING;
            }
        }catch (Exception e){
            e.printStackTrace();
        }

        return EMPTY_STRING;

    }

    public static String getHashKey(Context pContext) {
        try {
            PackageInfo info = pContext.getPackageManager().getPackageInfo(pContext.getPackageName(), PackageManager.GET_SIGNATURES);
            for (Signature signature : info.signatures) {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                HASHKEY = new String(Base64.encode(md.digest(), 0));
                return HASHKEY.trim().trim();
            }
        } catch (NoSuchAlgorithmException e) {
            Log.e(TAG, "printHashKey()", e);
        } catch (Exception e) {
            Log.e(TAG, "printHashKey()", e);
        }
        return null;
    }

    public static boolean isServerAvailable(String base_url){
        try {
            InetAddress.getByName(base_url).isReachable(2000);
            return true;
        }catch (Exception e){
            return false;
        }
    }

    public static void saveServerInfo(final Context context) {
        final Dialog dialog = new Dialog(context);
        View view1 = LayoutInflater.from(context).inflate(R.layout.ip_config,null,false);


        final EditText ip = view1.findViewById(R.id.ip);
        final EditText port = view1.findViewById(R.id.port);
        if (BASE_SITE != null)
        if (BASE_SITE.length() > 15 && BASE_SITE.contains(":")){
            String[] ipPort = BASE_SITE.split(":");
            if (ipPort.length==3) {
                ip.setText(ipPort[ipPort.length - 2].replace("//",""));
                port.setText(ipPort[ipPort.length - 1]);
            }

        }

        view1.findViewById(R.id.save).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String base_url = "http://"+ip.getText().toString()+":"+port.getText().toString();
                Log.d(TAG, "onClick: "+base_url);
               // if (isServerAvailable(base_url)){
                    storeStringValue(context,BASEURL,base_url);
                    BASE_SITE = base_url;
                BASE_URL = base_url + APIROUTE;
                Log.d(TAG, BASE_URL+" onClick: "+BASE_SITE);

                Toast.makeText(context, "Saved Successfully", Toast.LENGTH_SHORT).show();
                    dialog.dismiss();
              //  }else{
                   // Toast.makeText(context, "Server not Reachable", Toast.LENGTH_SHORT).show();
                    dialog.dismiss();
               // }
            }
        });



        dialog.setContentView(view1);
        dialog.show();
    }

    public static String getDurationFromSeconds(int totalSecs){
        int hours = totalSecs / 3600;
        int minutes = (totalSecs % 3600) / 60;
        int seconds = totalSecs % 60;

        return String.format("%02d:%02d:%02d", hours, minutes, seconds);
    }

    public static String inputStreamToString(InputStream inputStream) {
        try {
            byte[] bytes = new byte[inputStream.available()];
            inputStream.read(bytes, 0, bytes.length);
            String json = new String(bytes);
            return json;
        } catch (IOException e) {
            return null;
        }
    }

    public static String getCurrentTimeStamp(){
        try {

            SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
            String currentTimeStamp = dateFormat.format(new Date()); // Find todays date

            return currentTimeStamp;
        } catch (Exception e) {
            e.printStackTrace();

            return null;
        }
    }

    public static ContentValues objectToContentValues(Object o, Field... ignoredFields) {
        try {
            ContentValues values = new ContentValues();

            //Will ignore any of the fields you pass in here
            List<Field> fieldsToIgnore = Arrays.asList(ignoredFields);

            for(Field field : o.getClass().getDeclaredFields()) {
                field.setAccessible(true);

                if(fieldsToIgnore.contains(field))
                    continue;

                Object value = field.get(o);
                if(value != null) {
                    //This part just makes sure the content values can handle the field
                    if(value instanceof Double || value instanceof Integer || value instanceof String || value instanceof Boolean
                            || value instanceof Long || value instanceof Float || value instanceof Short) {
                        values.put(field.getAnnotation(SerializedName.class).value(), value.toString());
                    }
                }
                else
                    Log.d("value is null"," so we don't include it");
            }

            return values;
        } catch(Exception e) {
            Log.d(TAG, "objectToContentValues: "+e);
            throw new NullPointerException("content values failed to build");
        }
    }

    public static int intJsonreader(JSONObject jsonObject, String what) {

        try {
            return (int) jsonObject.get(what);
        } catch (JSONException e) {
            e.printStackTrace();
            return Integer.parseInt("0");
        }
    }

    public static void sendMessage(Context context,String message) {
        StringBuffer mOutStringBuffer = new StringBuffer("");
        // Check that we're actually connected before trying anything
        if (mChatService.getState() != BluetoothChatService.STATE_CONNECTED) {
            Toast.makeText(context, R.string.not_connected, Toast.LENGTH_SHORT).show();
            // return;
        }
        Toast.makeText(context,String.valueOf(mChatService.getState()), Toast.LENGTH_SHORT).show();

        // Check that there's actually something to send
        if (message.length() > 0) {
            // Get the message bytes and tell the BluetoothChatService to write
            byte[] send = message.getBytes();
            mChatService.write(send);
            // Reset out string buffer to zero and clear the edit text field
            mOutStringBuffer.setLength(0);
            //mOutEditText.setText(mOutStringBuffer);
        }
    }


/*
    public static void openWebview(Context context, String url, String title) {
        Intent intent = new Intent(context, WebviewActivity.class);
        intent.putExtra(Constants.URL, url);
        intent.putExtra(Constants.TITLE, title);
        context.startActivity(intent);
    }

    public static void updateUserToken(int uid, String token) {
        if (token != null && !token.isEmpty()) {
            RetrofitUtil.createProviderAPI().saveUserToken(new TokenRequestParam(token, uid))
                    .enqueue(sendRequest(null));
        }
    }

    public static void sendUserTokenRequest(String token) {
        User user = DatabaseUtil.getInstance().getUser();

        if (user != null) {
            updateUserToken(user.getData().getID(), token);
            updateTokenOnFirebase(token, user);
        }

    }

    public static void updateTokenOnFirebase(String token, User user) {
        UserObject sender = new UserObject();
        sender.setName(user.getData().getData().getDisplayName());
        sender.setImageUrl(user.getData().getData().getAvatar());
        sender.setOnline(true);
        sender.setDeviceID(token);
        sender.setUserId(user.getData().getData().getID());
        updateMeta(user, sender);
    }


    public static void updateMeta(User user, UserObject sender) {
        FirebaseDatabase.getInstance().getReference()
                .child(USERS_CHILD).child(user.getData().getID() + "").child(META_DATA)
                .setValue(sender);
    }

    public static void updateOnline(boolean val) {
        try {
            Map<String, Object> hopperUpdates = new HashMap<>();
            hopperUpdates.put("online", val);

            if (DatabaseUtil.getInstance().getUser() != null) {
                DatabaseReference ref = FirebaseDatabase.getInstance().getReference()
                        .child(USERS_CHILD).child(DatabaseUtil.getInstance().getUser().getData().getID() + "")
                        .child(META_DATA);
                ref.updateChildren(hopperUpdates);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public static void showDialog(Context context, String msg, final DialogInteractionListener listener) {
        AlertDialog.Builder builder;
        builder = new AlertDialog.Builder(context);
        builder
                .setMessage(msg)
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        if (listener != null) {
                            listener.onPositiveClick(null);
                        }
                        dialog.dismiss();
                    }
                })
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setCancelable(false)
                .show();
    }

    public static String getProviderId(String path, String id) {
        String providerID = path.replace(id, "");
        providerID = providerID.replace("_", "");
        return providerID;
    }

    public static String getSmsTodayYestFromMilli(long msgTimeMillis) {

        Calendar messageTime = Calendar.getInstance();
        messageTime.setTimeInMillis(msgTimeMillis);
        // get Currunt time
        Calendar now = Calendar.getInstance();

        final String strTimeFormate = "h:mm aa";
        final String strDateFormate = "dd/MM/yyyy h:mm aa";

        if (now.get(Calendar.DATE) == messageTime.get(Calendar.DATE)
                &&
                ((now.get(Calendar.MONTH) == messageTime.get(Calendar.MONTH)))
                &&
                ((now.get(Calendar.YEAR) == messageTime.get(Calendar.YEAR)))
                ) {

            return "today at " + DateFormat.format(strTimeFormate, messageTime);

        } else if (
                ((now.get(Calendar.DATE) - messageTime.get(Calendar.DATE)) == 1)
                        &&
                        ((now.get(Calendar.MONTH) == messageTime.get(Calendar.MONTH)))
                        &&
                        ((now.get(Calendar.YEAR) == messageTime.get(Calendar.YEAR)))
                ) {
            return "yesterday at " + DateFormat.format(strTimeFormate, messageTime);
        } else {
            return DateFormat.format(strDateFormate, messageTime).toString();
        }
    }

    public static boolean isconnected() {
        ConnectivityManager connMan = (ConnectivityManager) ServiceProviderApplication.getInstance().getApplicationContext()
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connMan != null) {
            NetworkInfo active = connMan.getActiveNetworkInfo();
            return active != null && active.isConnectedOrConnecting();
        } else {
            return false;
        }
    }

    public static String getCountryCode(Context context) {
        TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        String CountryID = "";
        String CountryZipCode = "";

        if(telephonyManager != null && telephonyManager.getSimCountryIso() != null) {
            CountryID = telephonyManager.getSimCountryIso().toUpperCase();
            String[] rl = context.getResources().getStringArray(R.array.CountryCodes);
            for (int i = 0; i < rl.length; i++) {
                String[] g = rl[i].split(",");
                if (g[1].trim().equals(CountryID.trim())) {
                    CountryZipCode = "+" + g[0];
                    break;
                }
            }
        }

        return CountryZipCode;
    }

    public static void showDialog(Context context, String msg, final DialogInteractionListener listener) {
        AlertDialog.Builder builder;
        builder = new AlertDialog.Builder(context);
        builder
                .setMessage(msg)
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        if (listener != null) {
                            listener.onPositiveClick(null);
                        }
                        dialog.dismiss();
                    }
                })
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setCancelable(false)
                .show();
    }*/

}
