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
import com.google.android.material.textfield.TextInputLayout;
import com.google.gson.Gson;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.alitersolutions.evolveyork.utils.AppUtils.saveServerInfo;
import static com.alitersolutions.evolveyork.utils.Constants.APIROUTE;
import static com.alitersolutions.evolveyork.utils.Constants.BASEURL;
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

        if (getStringValue(this,TOKEN).length()>50)
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
            UserModel model = new UserModel();
            model.setUserEmail(userId.getEditText().getText().toString());
            model.setUserPassword(password.getEditText().getText().toString());
            Gson gson = new Gson();
            String str = gson.toJson(model);
            Log.d(TAG, "SignIn: " + str);
            //RetrofitUtil.createProviderAPI().loginUser(model).enqueue(serverResponse());
        }

    }

    private Callback<ResponseModel> serverResponse() {
        showProgressDialog(getString(R.string.authenticating));
        return new Callback<ResponseModel>() {
            @Override
            public void onResponse(Call<ResponseModel> call, Response<ResponseModel> response) {
                Log.d(TAG, "onResponse: "+response.body().getEvolveToken());
                if ( response.body().getResult() != null) {
                    Gson gson = new Gson();
                    String js = gson.toJson(response.body().getResult());
                    storeStringValue(LoginActivity.this,USERLOGINDETAILS,js);
                    storeStringValue(LoginActivity.this,TOKEN,response.body().getEvolveToken());
                    Log.d(TAG, "onResponse: " + gson.fromJson(js,UserModel.class).getEvolveUserName());
                    openAcitivty(HomeActivity.class);
                    hideProgressDialog();
                }
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
