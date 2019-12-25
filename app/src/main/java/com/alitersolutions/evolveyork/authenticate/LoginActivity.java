package com.alitersolutions.evolveyork.authenticate;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.alitersolutions.evolveyork.MainActivity;
import com.alitersolutions.evolveyork.R;
import com.alitersolutions.evolveyork.Retrofit.DataLoadListener;
import com.alitersolutions.evolveyork.Retrofit.RetrofitUtil;
import com.alitersolutions.evolveyork.activity.BaseActivity;
import com.alitersolutions.evolveyork.activity.HomeActivity;
import com.alitersolutions.evolveyork.model.ResponseModel;
import com.alitersolutions.evolveyork.model.UserModel;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.textfield.TextInputLayout;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.alitersolutions.evolveyork.utils.AppUtils.saveServerInfo;
import static com.alitersolutions.evolveyork.utils.Constants.APIROUTE;
import static com.alitersolutions.evolveyork.utils.Constants.BASEURL;
import static com.alitersolutions.evolveyork.utils.Constants.EVOLVEUSRID;
import static com.alitersolutions.evolveyork.utils.Constants.EVOLVEUSRNAME;
import static com.alitersolutions.evolveyork.utils.Constants.TOKEN;
import static com.alitersolutions.evolveyork.utils.Constants.USERLOGINDETAILS;
import static com.alitersolutions.evolveyork.utils.SharedPreferenceUtil.getStringValue;
import static com.alitersolutions.evolveyork.utils.SharedPreferenceUtil.storeStringValue;

public class LoginActivity extends BaseActivity implements DataLoadListener {

    String TAG = "LoginActivity";


    private TextInputLayout userId,password;
    public static String BASE_SITE;
    public static String BASE_URL;
    Button signin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        BASE_SITE = getStringValue(this,BASEURL);
        BASE_URL = BASE_SITE + APIROUTE;
        if (BASE_SITE.length() < 16){
            Toast.makeText(this, "Configure Server IP", Toast.LENGTH_SHORT).show();
            saveServerInfo(LoginActivity.this);
        }

        if (getStringValue(this,USERLOGINDETAILS).length()>1)
            openAcitivty(HomeActivity.class);
        initView();
    }

    private void initView() {
        userId = findViewById(R.id.userid);
        password = findViewById(R.id.password);
        signin = findViewById(R.id.signin);
    }

    public void SignIn(View view) {
        if (BASE_SITE.length() < 16){
            Toast.makeText(this, "Configure Server IP", Toast.LENGTH_SHORT).show();
            saveServerInfo(LoginActivity.this);
        }else {
            String S_userId = userId.getEditText().getText().toString();
            String S_password = password.getEditText().getText().toString();
            if (S_userId.length() > 2 && S_password.length() > 2)
                logItnow(S_userId,S_password);
            else {
                Toast.makeText(this, "Please Enter Valid UserId and Password", Toast.LENGTH_SHORT).show();
            }
           /* UserModel model = new UserModel();
            model.setEvolveUserEmail(userId.getEditText().getText().toString());
            model.setEvolveUserPassword(password.getEditText().getText().toString());
            Gson gson = new Gson();
            String str = gson.toJson(model);
            Log.d(TAG, "SignIn: " + str);
            RetrofitUtil.createProviderAPI().loginUser(model).enqueue(serverResponse());*/
        }

    }

    private void logItnow(final String userID, final String passWord) {
        String url = BASE_URL+"API/user/login";
        Log.d(TAG, "logItnow: "+url);
        showProgressDialog("Validating...");
        StringRequest putRequest = new StringRequest(Request.Method.POST, url,
                new com.android.volley.Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("Response", response);
                        hideProgressDialog();
                        ResponseModel responseModel = gson.fromJson(response,ResponseModel.class);
                        if ( responseModel.getMessage().contains("successful")) {
                            Gson gson = new Gson();
                            String js = gson.toJson(responseModel.getData());
                            Log.d(TAG, "onResponse: hey "+js);
                            try {
                                JSONObject jsonObject = new JSONObject(js);
                                Log.d(TAG, " onResponse: "+jsonObject.getString(EVOLVEUSRNAME));
//                                Log.d(TAG, " onResponse: "+jsonObject.getString(EVOLVEUSRID));
                                storeStringValue(LoginActivity.this,USERLOGINDETAILS,jsonObject.getString(EVOLVEUSRNAME));
                                storeStringValue(LoginActivity.this,EVOLVEUSRID,String.valueOf(jsonObject.getInt(EVOLVEUSRID)));
                                openAcitivty(HomeActivity.class);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                         /* storeStringValue(LoginActivity.this,USERLOGINDETAILS,js);
                            storeStringValue(LoginActivity.this,TOKEN,response.body().getEvolveToken());
                            Log.d(TAG, "onResponse: " + gson.fromJson(js,UserModel.class).getEvolveUserName());
                            openAcitivty(HomeActivity.class);*/
                        }else {
                            showToast("Wrong Credentials");
                        }
                    }
                }, new com.android.volley.Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                logError("ErrorVolley",error.toString());
                showToast("Server is not reachable");
                hideProgressDialog();

            }
        }
        ) {

            @Override
            protected Map<String, String> getParams()
            {
                Map<String, String>  params = new HashMap<String, String>();
                params.put("EvolveUserEmail", userID);
                params.put("EvolveUserPassword", passWord);
                return params;
            }

        };
        RequestQueue queue = Volley.newRequestQueue(this);
        queue.add(putRequest);
    }

    private Callback<ResponseModel> serverResponse() {
        showProgressDialog(getString(R.string.authenticating));
        return new Callback<ResponseModel>() {
            @Override
            public void onResponse(Call<ResponseModel> call, Response<ResponseModel> response) {
                Log.d(TAG, "onResponse: "+response.body().getData());


                if ( response.body().getMessage().contains("successful")) {
                    Gson gson = new Gson();
                    String js = gson.toJson(response.body().getData());
                    Log.d(TAG, "onResponse: hey "+js);
                    try {
                        JSONObject jsonObject = new JSONObject(js);
                        Log.d(TAG, js+" onResponse: "+jsonObject.getString(EVOLVEUSRNAME));
                        storeStringValue(LoginActivity.this,USERLOGINDETAILS,jsonObject.getString(EVOLVEUSRNAME));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                 /*   storeStringValue(LoginActivity.this,USERLOGINDETAILS,js);
                    storeStringValue(LoginActivity.this,TOKEN,response.body().getEvolveToken());
                    Log.d(TAG, "onResponse: " + gson.fromJson(js,UserModel.class).getEvolveUserName());
                    openAcitivty(HomeActivity.class);*/

                }else {
                    showToast("Wrong Credentials");
                }

                hideProgressDialog();
            }

            @Override
            public void onFailure(Call<ResponseModel> call, Throwable t) {
                Log.d(TAG, "onFailure: ");
                hideProgressDialog();
            }
        };
    }

    @Override
    public void dataLoad(Object object) {

    }

    public void skip(View view) {
        startActivity(new Intent(this,HomeActivity.class));
    }

    public void setIp(View view) {
        saveServerInfo(LoginActivity.this);
    }
}
