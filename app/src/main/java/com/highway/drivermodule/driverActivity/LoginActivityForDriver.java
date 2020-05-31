package com.highway.drivermodule.driverActivity;

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
import com.highway.common.base.commonModel.login.LoginRegisterRequest;
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

public class LoginActivityForDriver extends AppCompatActivity {

    private EditText edtDriverMobNo;
    private Button sendOtp;
    String phone_number;
    public String driverLoginRoleId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_driver_login);

        edtDriverMobNo = findViewById(R.id.edtTxtDriverMobNo);
        sendOtp = findViewById(R.id.btnSendDriverOtp);

        //  driverLoginRoleId = getIntent().getStringExtra("driverRoleId");
        driverLoginRoleId = HighwayPrefs.getString(getApplicationContext(), "driverRoleId");

        sendOtp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validateDrivwerLogin();
            }
        });
    }

    public boolean inputValidation() {

        boolean check = true;

        phone_number = edtDriverMobNo.getText().toString();

        if (phone_number.isEmpty() && edtDriverMobNo.length() == 10) {
            edtDriverMobNo.setError(" Enter a valid phone number ");
            check = false;
        } else {
            edtDriverMobNo.setError(null);
            check = true;
        }

        return check;
    }

    public void validateDrivwerLogin() {

        if (inputValidation()) {

            LoginReqUpdated loginReqUpdated = new LoginReqUpdated();
            loginReqUpdated.setMobile(phone_number);
            loginReqUpdated.setRoleId(driverLoginRoleId);

            if (Utils.isInternetConnected(this)) {

                Utils.showProgressDialog(this);

                RestClient.loginUser(loginReqUpdated, new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        Utils.dismissProgressDialog();

                        if (response.body() != null) {
                            if (response.code() == 200) {
                                Intent intent = new Intent(LoginActivityForDriver.this, MobileOtpVerificationActivity.class);
                                HighwayPrefs.putString(LoginActivityForDriver.this, Constants.USERMOBILE, phone_number);
                                HighwayPrefs.putString(getApplicationContext(), Constants.ROLEID, driverLoginRoleId);
                                intent.putExtra("LoginRoleId", driverLoginRoleId);
                                startActivity(intent);
                                finish();
                                Toast.makeText(LoginActivityForDriver.this, "Pls verify Otp ", Toast.LENGTH_SHORT).show();
                            }
                        } else {

                            try {
                                String rawJson = response.errorBody().string();
                                if (!TextUtils.isEmpty(rawJson)) {
                                    JSONObject reObject = new JSONObject(rawJson);
                                    Toast.makeText(LoginActivityForDriver.this, reObject.optString("message"), Toast.LENGTH_LONG).show();
                                }

                            } catch (IOException e) {
                                e.printStackTrace();
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable throwable) {
                        Toast.makeText(LoginActivityForDriver.this, "Login failed", Toast.LENGTH_SHORT).show();

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
