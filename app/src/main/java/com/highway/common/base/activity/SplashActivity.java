package com.highway.common.base.activity;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;
import com.highway.R;
import com.highway.common.base.firebaseService.NotificationPushData;
import com.highway.commonretrofit.RestClient;
import com.highway.utils.BaseUtil;
import com.highway.utils.Constants;
import com.highway.utils.HighwayPrefs;


public class SplashActivity extends AppCompatActivity {

    private Handler handler;
    private static int SPLASH_TIME_OUT = 1000;
    private String TAG = getClass().getSimpleName();


    private static boolean hasPermissions(Context context, String... permissions) {
        if (Build.VERSION.SDK_INT >= 23&& context != null && permissions != null) {
            for (String permission : permissions) {
                if (ActivityCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED) {
                    return false;
                }
            }
        }
        return true;
    }
    final int REQUEST = 112;



    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        if (Build.VERSION.SDK_INT >= 23) {
            String[] PERMISSIONS = {Manifest.permission.CALL_PHONE,Manifest.permission.ACCESS_FINE_LOCATION,Manifest.permission.CAMERA,Manifest.permission.READ_EXTERNAL_STORAGE};
            if (!hasPermissions(this, PERMISSIONS)) {
                ActivityCompat.requestPermissions(this, PERMISSIONS, REQUEST );
            } else {
                splashScreenHandler();
            }
        } else {
            splashScreenHandler();
        }

    }

    public void splashScreenHandler() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                if (HighwayPrefs.getBoolean(SplashActivity.this, Constants.LOGGED_IN)) {
                    Intent i = new Intent(SplashActivity.this, DashBoardActivity.class);
                    if (getIntent().getExtras() != null) {
                        NotificationPushData data = getIntent().getParcelableExtra(Constants.PUSH_NEW_BOOKING_TRIP_DATA_KEY);
                        i.putExtra(Constants.PUSH_NEW_BOOKING_TRIP_DATA_KEY, data);
                        Log.e(TAG, BaseUtil.jsonFromModel(data));
                    }
//                    String data = getIntent().getStringExtra(Constants.PUSH_NEW_BOOKING_TRIP_DATA_KEY);
//                    Log.e(getClass().getSimpleName(), data);
                    String token = HighwayPrefs.getString(SplashActivity.this, "device_token");
                    System.out.println("asdf fcm --- : " + token);
                    startActivity(i);
                    finish();
                } else {
                    Intent i = new Intent(SplashActivity.this, LoginOptionActivity.class);
                    startActivity(i);
                    finish();
                }
            }
        }, SPLASH_TIME_OUT);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,  String[] permissions,  int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case REQUEST: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    splashScreenHandler();
                    //Do here
                } else {
                    //Toast.makeText(this, "The app was not allowed to write to your storage.", Toast.LENGTH_LONG).show();
                }
            }
        }
    }

}
