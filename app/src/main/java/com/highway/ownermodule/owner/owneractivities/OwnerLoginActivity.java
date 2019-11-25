package com.highway.ownermodule.owner.owneractivities;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;


import com.highway.R;
import com.highway.common.base.activity.MobileOtpVerificationActivity;
import com.highway.common.base.commonModel.login.LoginRegisterRequest;
import com.highway.common.base.commonModel.login.LoginRegisterResponse;
import com.highway.commonretrofit.RestClient;
import com.highway.utils.HighwayPrefs;
import com.highway.utils.Utils;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.highway.utils.Constants.USERMOBILE;


public class OwnerLoginActivity extends AppCompatActivity {

    private EditText ownerPhoneNo;
    private Button btnOwnerOtp;
    String phone_number;
    private int backpress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_owner_login);

        ownerPhoneNo = findViewById(R.id.edtTxtOwnerMobNo);
        btnOwnerOtp = findViewById(R.id.btnSendOwnerOtp);

        btnOwnerOtp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                validationLoginOwner();
            }
        });
    }


    private boolean inputOwnerValidate() {
        boolean check = true;

        phone_number = ownerPhoneNo.getText().toString();

        if (phone_number.isEmpty() && ownerPhoneNo.length()==10) {
            ownerPhoneNo.setError(" enter a valid phone number ");
            check = false;
        } else {
            ownerPhoneNo.setError(null);
            check = true;
        }
        return check;
    }


    public void validationLoginOwner() {

        if (inputOwnerValidate()){

            LoginRegisterRequest loginRegisterRequest = new LoginRegisterRequest();
            loginRegisterRequest.setMobile(phone_number);

            if (Utils.isInternetConnected(this)) {

                Utils.showProgressDialog(this);

                RestClient.loginUser(loginRegisterRequest, new Callback<LoginRegisterResponse>() {
                    @Override
                    public void onResponse(Call<LoginRegisterResponse> call, Response<LoginRegisterResponse> response) {
                        Utils.dismissProgressDialog();
                        if (response.body() != null) {
                            if (response.body().getStatus() == true) {
                                Intent intent = new Intent(OwnerLoginActivity.this, MobileOtpVerificationActivity.class);
                                HighwayPrefs.putString(OwnerLoginActivity.this, USERMOBILE, phone_number);
                                startActivity(intent);
                                finish();
                                Toast.makeText(OwnerLoginActivity.this, "pls Verify Otp", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                    @Override
                    public void onFailure(Call<LoginRegisterResponse> call, Throwable t) {
                        Toast.makeText(OwnerLoginActivity.this, "Login failed", Toast.LENGTH_SHORT).show();

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
