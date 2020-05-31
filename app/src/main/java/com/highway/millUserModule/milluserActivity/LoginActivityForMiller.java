package com.highway.millUserModule.milluserActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.highway.R;
import com.highway.common.base.activity.MobileOtpVerificationActivity;
import com.highway.common.base.commonModel.login.LoginReqUpdated;
import com.highway.commonretrofit.RestClient;
import com.highway.utils.Constants;
import com.highway.utils.HighwayPrefs;
import com.highway.utils.Utils;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivityForMiller extends AppCompatActivity {

    private EditText millerPhoneNo;
    private Button btnMillerOtp;
    String phone_number;
    private int backpress;
    private String millerLoginRoleId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_miller_login);

        millerPhoneNo = findViewById(R.id.edtTxtMillerMobNo);
        btnMillerOtp = findViewById(R.id.btnSendMillerOtp);

        // millerLoginRoleId = getIntent().getStringExtra("millerRoleId");
        millerLoginRoleId = HighwayPrefs.getString(getApplicationContext(), "millerRoleId");


        btnMillerOtp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                validationMillerLogin();
            }
        });
    }

    public boolean inputMillerVatidation() {
        boolean check = true;

        phone_number = millerPhoneNo.getText().toString();

        if (phone_number.isEmpty() && millerPhoneNo.length() == 10) {
            millerPhoneNo.setError(" enter a valid phone number ");
            check = false;
        } else {
            millerPhoneNo.setError(null);
            check = true;
        }

        return check;
    }

    public void validationMillerLogin() {

        if (inputMillerVatidation()) {

           /* LoginRegisterRequest loginRegisterRequest = new LoginRegisterRequest();
            loginRegisterRequest.setMobile(phone_number);*/

            LoginReqUpdated loginReqUpdated = new LoginReqUpdated();
            loginReqUpdated.setMobile(phone_number);
            loginReqUpdated.setRoleId(millerLoginRoleId);

            if (Utils.isInternetConnected(this)) {

                Utils.showProgressDialog(this);

                RestClient.loginUser(loginReqUpdated, new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        Utils.dismissProgressDialog();
                        if (response.body() != null) {
                            if (response.code() == 200) {
                                Intent intent = new Intent(LoginActivityForMiller.this, MobileOtpVerificationActivity.class);

                                HighwayPrefs.putString(LoginActivityForMiller.this, Constants.USERMOBILE, phone_number);
                                HighwayPrefs.putString(getApplicationContext(), Constants.ROLEID, millerLoginRoleId);
                                intent.putExtra("LoginRoleId", millerLoginRoleId);
                                startActivity(intent);
                                finish();
                                Toast.makeText(LoginActivityForMiller.this, "pls Verify Otp", Toast.LENGTH_SHORT).show();
                            }
                        }else{

                            try {
                                String  rawJson = response.errorBody().string();
                                if (!TextUtils.isEmpty(rawJson)){
                                    JSONObject reObject = new JSONObject(rawJson);
                                    Toast.makeText(LoginActivityForMiller.this,reObject.optString("message"),Toast.LENGTH_LONG).show();
                                }

                            } catch (IOException e) {
                                e.printStackTrace();
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                        Toast.makeText(LoginActivityForMiller.this, "Login failed", Toast.LENGTH_SHORT).show();

                    }
                });

            }
        }
    }

    boolean doubleBackToExitPressedOnce = false;

    @Override
    public void onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed();
            return;
        }

        this.doubleBackToExitPressedOnce = true;

        Toast.makeText(this, "Please click BACK again to exit", Toast.LENGTH_SHORT).show();

        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                doubleBackToExitPressedOnce = false;
            }
        }, 2000);
    }


}
