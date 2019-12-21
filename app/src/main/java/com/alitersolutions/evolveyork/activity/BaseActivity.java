package com.alitersolutions.evolveyork.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.Window;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.Gson;

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

    public  void  logError(String Tag, String Msg){
        Log.e(Tag, "logError: "+Msg);
    }
}
