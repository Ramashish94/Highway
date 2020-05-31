package com.highway.ownerModule.vehicleOwnerActivities;

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


public class LoginActivityForVehicleOwner extends AppCompatActivity {

    private EditText ownerPhoneNo;
    private Button btnOwnerOtp;
    String phone_number;
    private int backpress;
    public String OwnerLoginRoleId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_owner_login);

        ownerPhoneNo = findViewById(R.id.edtTxtOwnerMobNo);
        btnOwnerOtp = findViewById(R.id.btnSendOwnerOtp);

        OwnerLoginRoleId = HighwayPrefs.getString(getApplicationContext(),"ownerRoleId");

        btnOwnerOtp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                validationLoginUser();
            }
        });
    }


    private boolean validateInput() {
        boolean check = true;

        phone_number = ownerPhoneNo.getText().toString();

        if (phone_number.isEmpty() || ownerPhoneNo.length()<10) {
            ownerPhoneNo.setError(" Enter a valid phone number ");
            check = false;
        } else {
            ownerPhoneNo.setError(null);
            check = true;
        }

        return check;
    }


    public void validationLoginUser() {

        if (validateInput()){


            LoginReqUpdated loginReqUpdated = new LoginReqUpdated();
            loginReqUpdated.setMobile(phone_number);
            loginReqUpdated.setRoleId(OwnerLoginRoleId);

            if (Utils.isInternetConnected(this)) {

                Utils.showProgressDialog(this);

                RestClient.loginUser(loginReqUpdated, new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        Utils.dismissProgressDialog();
                        if (response.body() != null) {
                            if (response.code()==200) {
                                Intent intent = new Intent(getApplicationContext(),MobileOtpVerificationActivity.class);
                                HighwayPrefs.putString(getApplicationContext(), Constants.USERMOBILE, phone_number);
                                HighwayPrefs.putString(getApplicationContext(), Constants.ROLEID, OwnerLoginRoleId);
                                intent.putExtra("LoginRoleId", OwnerLoginRoleId);
                                startActivity(intent);
                                finish();
                                Toast.makeText(getApplicationContext(), "Pls verify Otp  !", Toast.LENGTH_SHORT).show();
                            }
                        }else{

                            try {
                                String  rawJson = response.errorBody().string();
                                if (!TextUtils.isEmpty(rawJson)){
                                    JSONObject reObject = new JSONObject(rawJson);
                                    Toast.makeText(LoginActivityForVehicleOwner.this,reObject.optString("message"),Toast.LENGTH_LONG).show();
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
                        Utils.dismissProgressDialog();
                        Toast.makeText(getApplicationContext(), "Login failed", Toast.LENGTH_SHORT).show();

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
