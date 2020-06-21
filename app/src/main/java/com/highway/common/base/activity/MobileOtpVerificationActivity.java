package com.highway.common.base.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.highway.R;
import com.highway.common.base.commonModel.login.LoginReqUpdated;
import com.highway.common.base.commonModel.otpverify.VerifyOtpRequest;
import com.highway.common.base.commonModel.otpverify.VerifyOtpResponse;
import com.highway.commonretrofit.RestClient;
import com.highway.customer.customerActivity.LoginActivityForCustomer;
import com.highway.drivermodule.driverActivity.LoginActivityForDriver;
import com.highway.interfaces.SmsListener;
import com.highway.millUserModule.milluserActivity.LoginActivityForMiller;
import com.highway.ownerModule.vehicleOwnerActivities.LoginActivityForVehicleOwner;
import com.highway.ownerModule.vehicleOwnerActivities.WelcomeOwnerActivity;
import com.highway.reciever.SmsReceiver;
import com.highway.utils.Constants;
import com.highway.utils.HighwayPrefs;
import com.highway.utils.Utils;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MobileOtpVerificationActivity extends AppCompatActivity implements SmsListener {

    private EditText verifyPin;
    private Button btnVerify;
    private ImageView backImage;
    public TextView resend;
    public TextView mobileNumberTV, changeNumberTv;
    String userId;


    SmsReceiver smsReceiver = new SmsReceiver();
    public String otpNumber;
    public String usermobileNumber;
    public String userLoginRoleId;
    String LoginRoleId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mobile_otp_verification);

        verifyPin = findViewById(R.id.verifyPin_edittext);
        btnVerify = findViewById(R.id.verifyPin_btn);
        resend = findViewById(R.id.resend);
        mobileNumberTV = findViewById(R.id.mobileNumber);
        changeNumberTv = findViewById(R.id.changeNumber);
        Bundle b = getIntent().getExtras();
         LoginRoleId = b.getString("LoginRoleId");

        timerInOtp();                          // time count down of otp

        mobileNumberTV.setText(HighwayPrefs.getString(this, Constants.USERMOBILE));
        usermobileNumber = HighwayPrefs.getString(getApplicationContext(), Constants.USERMOBILE);
        userLoginRoleId = HighwayPrefs.getString(getApplicationContext(), Constants.ROLEID);
       // mobileNumberTV.setText(usermobileNumber);

        smsReceiver.bindListener(this);

        btnVerify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                verifyPinOperation(); // otp option perform
            }
        });

        changeNumberTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String driverRoleId = HighwayPrefs.getString(getApplicationContext(), "driverRoleId");

                Log.e("driver","::"+driverRoleId);

                if (LoginRoleId.equalsIgnoreCase("2")) {
                    Intent intent = new Intent(MobileOtpVerificationActivity.this, LoginActivityForMiller.class);
                    // bookVehicleName = HighwayPrefs.getString(getApplicationContext(), "bookVehicleName");
                    startActivity(intent);

                    finish();
                } if (LoginRoleId.equalsIgnoreCase("3")) {
                    Intent intent = new Intent(MobileOtpVerificationActivity.this, LoginActivityForDriver.class);
                    // bookVehicleName = HighwayPrefs.getString(getApplicationContext(), "bookVehicleName");
                    startActivity(intent);

                    finish();
                } if (LoginRoleId.equalsIgnoreCase("4")) {
                    Intent intent = new Intent(MobileOtpVerificationActivity.this, LoginActivityForCustomer.class);
                    // bookVehicleName = HighwayPrefs.getString(getApplicationContext(), "bookVehicleName");
                    startActivity(intent);

                    finish();
                }else {

                    Intent intent = new Intent(MobileOtpVerificationActivity.this, LoginActivityForVehicleOwner.class);
                    // bookVehicleName = HighwayPrefs.getString(getApplicationContext(), "bookVehicleName");
                    startActivity(intent);
                    finish();
                }
            }
        });


        resend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resendOtpOperation();

            }
        });

    }

    private void resendOtpOperation() {
        LoginReqUpdated loginReqUpdated = new LoginReqUpdated();
        loginReqUpdated.setRoleId(userLoginRoleId);
        loginReqUpdated.setMobile(usermobileNumber);

        RestClient.loginUser(loginReqUpdated, new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response != null && response.code() == 200 && response.body() != null) {
                    Toast.makeText(MobileOtpVerificationActivity.this, "Otp resend on your mobile number!", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toast.makeText(MobileOtpVerificationActivity.this, "Otp resend Failed!", Toast.LENGTH_SHORT).show();
            }
        });


    }


    public void timerInOtp() {

        new CountDownTimer(60000, 1000) {

            public void onTick(long millisUntilFinished) {
            }

            public void onFinish() {
                resend.setEnabled(true);
            }

        }.start();

    }

    public void verifyPinOperation() {

        boolean check = true;
        otpNumber = verifyPin.getText().toString().trim();
        //  usermobileNumber = HighwayPrefs.getString(getApplicationContext(), Constants.USERMOBILE);

        if (otpNumber.isEmpty()) {
            verifyPin.setError("enter a valid otp");
            check = false;
        } else {
            verifyPin.setError(null);
        }

        if (check) {

            VerifyOtpRequest verifyOtpRequest = new VerifyOtpRequest();
            verifyOtpRequest.setOtp(otpNumber);
            verifyOtpRequest.setMobile(usermobileNumber);

            if (Utils.isInternetConnected(this)) {

                Utils.showProgressDialog(this);

                RestClient.otpVerify(verifyOtpRequest, new Callback<VerifyOtpResponse>() {
                    @Override
                    public void onResponse(Call<VerifyOtpResponse> call, Response<VerifyOtpResponse> response) {
                        Utils.dismissProgressDialog();
                        if (response.body() != null) {
                          //  if (response.body().getStatus()) {
                                if (TextUtils.isEmpty(response.body().getUser().getName())) {
                                    Intent intent = new Intent(MobileOtpVerificationActivity.this, RegistrationDetailsActivity.class);

                                    userId = response.body().getUser().getUserId();
                                    HighwayPrefs.putString(getApplicationContext(), Constants.ID, userId);

                                    Log.e("verifyDriver", "::" + response.body().getUser().getDriverVerifyBy());

                                    HighwayPrefs.putString(getApplicationContext(), Constants.driverVerifyBy, response.body().getUser().getDriverVerifyBy());


                                    startActivity(intent);
                                    finish();
                                } else {
                                    gotoDashboardAfterLogin(response.body());

                               /* Intent intent = new Intent(MobileOtpVerificationActivity.this, DashBoardActivity.class);

                                HighwayPrefs.putBoolean(getApplicationContext(), Constants.LOGGED_IN, true);
                                HighwayPrefs.putString(getApplicationContext(), Constants.NAME, response.body().getName());
                                HighwayPrefs.putString(getApplicationContext(), Constants.USERMOBILE, response.body().getMobile());
                                HighwayPrefs.putString(getApplicationContext(), Constants.IMAGE, response.body().getImage());
                                HighwayPrefs.putString(getApplicationContext(), Constants.EMAIL, response.body().getEmail());
                                HighwayPrefs.putString(getApplicationContext(), Constants.GENDER, response.body().getGender());
                                HighwayPrefs.putString(getApplicationContext(), Constants.ROLEID, response.body().getRoleId());
                                HighwayPrefs.putString(getApplicationContext(), Constants.ADDRESS, response.body().getAddress());

                                startActivity(intent);
                                Toast.makeText(MobileOtpVerificationActivity.this, "Wlcm to Highway", Toast.LENGTH_SHORT).show();
                                finish();*/
                                }
                           /* }else {
                                Toast.makeText(MobileOtpVerificationActivity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                            }*/
                        } else {
                            Toast.makeText(MobileOtpVerificationActivity.this, "Invalid OTP", Toast.LENGTH_SHORT).show();

                        }
                    }

                    @Override
                    public void onFailure(Call<VerifyOtpResponse> call, Throwable t) {
                        Toast.makeText(MobileOtpVerificationActivity.this, "Otp verification failed", Toast.LENGTH_SHORT).show();

                    }
                });
            }
        }
    }

    private void gotoDashboardAfterLogin(VerifyOtpResponse verifyOtpResponse) {

        Intent intent = new Intent(MobileOtpVerificationActivity.this, DashBoardActivity.class);

        HighwayPrefs.putBoolean(getApplicationContext(), Constants.LOGGED_IN, true);
        HighwayPrefs.putString(getApplicationContext(), Constants.ID, verifyOtpResponse.getUser().getUserId());
        Log.e("verifyDriverTest","::"+verifyOtpResponse.getUser().getDriverVerifyBy());
        HighwayPrefs.putString(getApplicationContext(), Constants.driverVerifyBy, verifyOtpResponse.getUser().getDriverVerifyBy());
        HighwayPrefs.putString(getApplicationContext(), Constants.NAME, verifyOtpResponse.getUser().getName());
        HighwayPrefs.putString(getApplicationContext(), Constants.USERMOBILE, verifyOtpResponse.getUser().getMobile());
        HighwayPrefs.putString(getApplicationContext(), Constants.User_statuss, verifyOtpResponse.getUser().getUserStatus());

        System.out.println("User Status otp_verify" + verifyOtpResponse.getUser().getUserStatus());

        HighwayPrefs.putString(getApplicationContext(), Constants.IMAGE, verifyOtpResponse.getUser().getImage());
        HighwayPrefs.putString(getApplicationContext(), Constants.EMAIL, verifyOtpResponse.getUser().getEmail());
        HighwayPrefs.putString(getApplicationContext(), Constants.GENDER, verifyOtpResponse.getUser().getGender());
        HighwayPrefs.putString(getApplicationContext(), Constants.ROLEID, verifyOtpResponse.getUser().getRoleId());
        HighwayPrefs.putString(getApplicationContext(), Constants.ADDRESS, verifyOtpResponse.getUser().getAddress());

        startActivity(intent);
        Toast.makeText(MobileOtpVerificationActivity.this, "Wlcm to Highway ", Toast.LENGTH_SHORT).show();
        finish();
    }

    // onBacked pressed
    boolean doubleBackToExitPressedOnce = false;

    @Override
    public void onBackPressed() {
        if (doubleBackToExitPressedOnce) {
           try {


            if (smsReceiver != null)
                unregisterReceiver(smsReceiver);
           }catch (Exception e){
               e.printStackTrace();
           }
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

    @Override
    public void messageReceived(String messageText) {
        verifyPin.setText(messageText);
        verifyPinOperation();

    }
    @Override
    protected void onStop() {
        super.onStop();
        SmsReceiver.unBindListener();

    }
}
