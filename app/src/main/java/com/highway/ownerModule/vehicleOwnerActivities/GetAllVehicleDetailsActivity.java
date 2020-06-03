package com.highway.ownerModule.vehicleOwnerActivities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.highway.BuildConfig;
import com.highway.R;
import com.highway.ownerModule.vehileOwnerModelsClass.getAllVehicle.VehicleDetail;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class GetAllVehicleDetailsActivity extends AppCompatActivity {

    ArrayList<VehicleDetail> vehicleDetail;
    String Latitude,Longitude,VehicleName,VehicleNumber,VehicleModelNo,VehicleDescription,VehicleCapacity,VehicleSize,VehicleImage,DriverName,Mobile,Email;
    TextView tv1DriverName,tv2VehicleName,tv3VehicleNumber,tv4VehicleModelNo,tv5VehicleLoadCapicity,tv6VehicleSize,tv7VehicleDescription;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vehical_activity);
        ImageView image = findViewById(R.id.image);
        Button btnLocation = findViewById(R.id.btnLocation);
        ImageView ivback = findViewById(R.id.iv_back);

        tv1DriverName = findViewById(R.id.Tv1DriverName);
        tv2VehicleName = findViewById(R.id.Tv2VehicleName);
        tv3VehicleNumber = findViewById(R.id.Tv3VehicleNo);
        tv4VehicleModelNo = findViewById(R.id.Tv4VehicleModelNo);
        tv5VehicleLoadCapicity = findViewById(R.id.Tv5VehicleLoadCapicity);
        tv6VehicleSize = findViewById(R.id.Tv6VehicleSize);
        tv7VehicleDescription = findViewById(R.id.Tv7VehicleDescription);

       // vehicleDetail = (ArrayList<VehicleDetail>) getIntent().getSerializableExtra("mylist");


         Latitude = getIntent().getStringExtra("Latitude");
         Longitude = getIntent().getStringExtra("Longitude");
         VehicleName = getIntent().getStringExtra("VehicleName");
         VehicleNumber = getIntent().getStringExtra("VehicleNumber");
         VehicleModelNo = getIntent().getStringExtra("VehicleModelNo");
         VehicleDescription = getIntent().getStringExtra("VehicleDescription");
         VehicleCapacity = getIntent().getStringExtra("VehicleCapacity");
         VehicleSize = getIntent().getStringExtra("VehicleSize");

         VehicleImage = getIntent().getStringExtra("VehicleImage");
         DriverName = getIntent().getStringExtra("DriverName");
         Mobile = getIntent().getStringExtra("Mobile");
         Email = getIntent().getStringExtra("Email");

        btnLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(Latitude != null && !Longitude.isEmpty()) {
                    Intent i = new Intent(view.getContext(), GetAllVehicleDetailsActivity.class);
                    i.putExtra("lat", Latitude);
                    i.putExtra("long", Longitude);
                    i.putExtra("vehicalName",VehicleName);
                    view.getContext().startActivity(i);
                }else {

                    Toast.makeText(view.getContext(),"latitude and longitude not available", Toast.LENGTH_SHORT).show();
                }

            }
        });


        ivback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                finish();

            }
        });
        Picasso.with(getApplicationContext()).load(BuildConfig.API_SERVER_IP + VehicleImage)
                .error(R.mipmap.ic_launcher)
                .into(image);

        onsetText();
       // Log.e("Testttt", ":::" + vehicleDetail.get(0).getVehicleName());
      //  onsetText(Integer.valueOf(pos));

    }
    private void onsetText(){

        tv1DriverName.setText(DriverName);
        tv2VehicleName.setText(VehicleName);
        tv3VehicleNumber.setText(VehicleNumber);
        tv4VehicleModelNo.setText(VehicleModelNo);
        tv5VehicleLoadCapicity.setText(VehicleCapacity);
        tv6VehicleSize.setText(VehicleSize);
        tv7VehicleDescription.setText(VehicleDescription);

    }
}
