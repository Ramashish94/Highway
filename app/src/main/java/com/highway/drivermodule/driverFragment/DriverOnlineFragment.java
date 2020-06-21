package com.highway.drivermodule.driverFragment;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.highway.R;
import com.highway.common.base.HighwayApplication;
import com.highway.common.base.activity.DashBoardActivity;
import com.highway.commonretrofit.RestClient;
import com.highway.customer.customerModelClass.vehicleInfo.BookingVehicleInfoRequest;
import com.highway.customer.customerModelClass.vehicleInfo.BookingVehicleInfoResponse;
import com.highway.utils.Constants;
import com.highway.utils.HighwayPrefs;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class DriverOnlineFragment extends Fragment implements View.OnClickListener, OnMapReadyCallback, GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener,
        LocationListener {

    private GoogleMap mMap;
    GoogleApiClient mGoogleApiClient;
    Location mLastLocation;
    Marker mCurrLocationMarker;
    LocationRequest mLocationRequest;

    FrameLayout onlineFramelayout;

    RelativeLayout mylocation;
    private Button onlineOfflineButton;
    private ImageView offlineHintImage;
    private TextView locationAddressTV, lblLocationType;
    private ImageView navigateToMap;
    private DashBoardActivity activity;
    private SupportMapFragment mapFragment;
    private String tripId;


    public static DriverOnlineFragment newInstance() {
        DriverOnlineFragment fragment = new DriverOnlineFragment();

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_driver_online, container, false);
        onlineOfflineButton = view.findViewById(R.id.btnGoOffline);
        offlineHintImage = view.findViewById(R.id.offlineHintImage);
        locationAddressTV = view.findViewById(R.id.lblLocationName);
        lblLocationType = view.findViewById(R.id.lblLocationType);
        navigateToMap = view.findViewById(R.id.navigation_img);
        onlineFramelayout = view.findViewById(R.id.online);
        mylocation = view.findViewById(R.id.mylocation);


        mapFragment = (SupportMapFragment) getChildFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        return view;
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ((DashBoardActivity) getActivity()).appBarLayout.setVisibility(View.VISIBLE);

        onlineOfflineButton.setOnClickListener(this);
        navigateToMap.setOnClickListener(this);
        mylocation.setOnClickListener(this);

        if (HighwayPrefs.getString(getActivity(), Constants.driverVerifyBy).equalsIgnoreCase("0")){


            if (HighwayPrefs.getBoolean(activity, Constants.ONLINE)) {
                onlineOfflineButton.setText("Click here to go online");
                onlineOfflineButton.setBackgroundColor(activity.getResources().getColor(R.color.red));
                onlineFramelayout.setVisibility(View.GONE);
                offlineHintImage.setVisibility(View.VISIBLE);

            } else {
                onlineOfflineButton.setText("Click here to go offline");
                onlineOfflineButton.setBackgroundColor(activity.getResources().getColor(R.color.green));
                onlineFramelayout.setVisibility(View.VISIBLE);
                offlineHintImage.setVisibility(View.GONE);

            }

        }else {

            onlineOfflineButton.setVisibility(View.GONE);

        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        activity = (DashBoardActivity) getActivity();

    }

    @Override
    public void onDetach() {
        super.onDetach();

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnGoOffline:
                if (HighwayPrefs.getBoolean(activity, Constants.ONLINE)) {
                    onlineOfflineButton.setText("Click here to go offline");
                    onlineOfflineButton.setBackgroundColor(activity.getResources().getColor(R.color.green));
                    onlineFramelayout.setVisibility(View.VISIBLE);
                    offlineHintImage.setVisibility(View.GONE);
                    HighwayPrefs.putBoolean(activity, Constants.ONLINE, false);

                } else {
                    onlineOfflineButton.setText("Click here to go online");
                    onlineOfflineButton.setBackgroundColor(activity.getResources().getColor(R.color.red));
                    onlineFramelayout.setVisibility(View.GONE);
                    offlineHintImage.setVisibility(View.VISIBLE);
                    HighwayPrefs.putBoolean(activity, Constants.ONLINE, true);

                }

                break;
            case R.id.navigation_img:
                break;


            case R.id.mylocation:
                updateMyLocation();
                break;
        }


    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.getUiSettings().setMyLocationButtonEnabled(true);
        // mMap.getUiSettings().setZoomControlsEnabled(true);
        mMap.setMinZoomPreference(10.0f);
        mMap.setMaxZoomPreference(18.0f);

//        try {
//            mMap.setMapStyle(
//                    MapStyleOptions.loadRawResourceStyle(getActivity(), R.raw.style));
//        } catch (Resources.NotFoundException e) {
//            e.printStackTrace();
//            // Oops, looks like the map style resource couldn't be found!
//        }
        mMap.getUiSettings().setMyLocationButtonEnabled(false);
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

            if (ContextCompat.checkSelfPermission(activity,
                    Manifest.permission.ACCESS_FINE_LOCATION)
                    == PackageManager.PERMISSION_GRANTED) {
                buildGoogleApiClient();
                mMap.setMyLocationEnabled(true);
            }
        } else {
            buildGoogleApiClient();
            mMap.setMyLocationEnabled(true);
        }
    }


    protected synchronized void buildGoogleApiClient() {
        mGoogleApiClient = new GoogleApiClient.Builder(activity)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
        mGoogleApiClient.connect();
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(1000);
        mLocationRequest.setFastestInterval(1000);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY);
        if (ContextCompat.checkSelfPermission(activity,
                Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, this);
        }
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    @Override
    public void onLocationChanged(Location location) {
        mLastLocation = location;
        //Place current location marker
        updateMyLocation();
    }

    private void updateMyLocation() {
        if (mLastLocation == null) {
            return;
        }
        LatLng latLng = new LatLng(mLastLocation.getLatitude(), mLastLocation.getLongitude());

        //move map camera
        mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
        mMap.animateCamera(CameraUpdateFactory.zoomTo(14));


        //stop location updates
        if (mGoogleApiClient != null) {
            LocationServices.FusedLocationApi.removeLocationUpdates(mGoogleApiClient, this);
        }
    }

    public String userId;

    public void onDriverOnLineOffline() {

        userId = HighwayPrefs.getString(getActivity(), Constants.ID);
        BookingVehicleInfoRequest bookingVehicleInfoRequest = new BookingVehicleInfoRequest();
        bookingVehicleInfoRequest.setUserId(userId);
        bookingVehicleInfoRequest.setVehicleTypeId(HighwayApplication.getInstance().getVehicleType());
        bookingVehicleInfoRequest.setVehicleTypeId("");

        RestClient.onDriverOnLineOffline(bookingVehicleInfoRequest, new Callback<BookingVehicleInfoResponse>() {
            @Override
            public void onResponse(Call<BookingVehicleInfoResponse> call, Response<BookingVehicleInfoResponse> response) {
                if (response.body() != null) {
                    /*if (response.body().getStatus()) {

                        List<VehicleTypeInfo> vehicleTypeInfos = response.body().getVehicleTypeInfo();
                        bookingVehicleInfoAdapter = new BookingVehicleInfoAdapter(vehicleTypeInfos, getApplicationContext());
                        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
                        bookInfoRecycler.setLayoutManager(layoutManager);
                        bookInfoRecycler.setItemAnimator(new DefaultItemAnimator());
                        bookInfoRecycler.setAdapter(bookingVehicleInfoAdapter);
                    }*/
                } else {
                    Toast.makeText(getActivity(), "error", Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<BookingVehicleInfoResponse> call, Throwable t) {
                Toast.makeText(getActivity(), "Failure booking Vehicle info", Toast.LENGTH_SHORT).show();

            }
        });
    }


}



