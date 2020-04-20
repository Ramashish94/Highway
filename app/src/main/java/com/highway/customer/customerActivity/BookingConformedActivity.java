package com.highway.customer.customerActivity;

import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.location.Location;
import android.net.ConnectivityManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.DrawableRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MapStyleOptions;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.android.libraries.places.api.Places;
import com.highway.R;
import com.highway.broadCastReceiver.MyIntentService;
import com.highway.broadCastReceiver.MySenderBroadCast;
import com.highway.common.base.HighwayApplication;
import com.highway.common.base.commonModel.bookingHTrip.BookingHTripResponse;
import com.highway.common.base.firebaseService.NotificationPushData;
import com.highway.customer.customerFragment.InvoiceBottomDialogFragmentForCustomer;
import com.highway.customer.customerModelClass.bookingVehicleList.BookingVehicleListResponse;
import com.highway.customer.helper.FetchURL;
import com.highway.customer.helper.TaskLoadedCallback;
import com.highway.drivermodule.drivermodels.TripStatus;
import com.highway.utils.BaseUtil;
import com.highway.utils.Constants;
import com.highway.utils.HighwayPrefs;
import com.highway.utils.Utils;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

import static com.highway.utils.Constants.ARRIVED;
import static com.highway.utils.Constants.COMPLETED;
import static com.highway.utils.Constants.DRIVER_NAME;
import static com.highway.utils.Constants.DROPPED;
import static com.highway.utils.Constants.INVOICE;
import static com.highway.utils.Constants.PICKEDUP;
import static com.highway.utils.Constants.PUSH_MOBILE;
import static com.highway.utils.Constants.PUSH_NEW_BOOKING_TRIP_DATA_KEY;
import static com.highway.utils.Constants.PUSH_TYPE;
import static com.highway.utils.Constants.RATING;
import static com.highway.utils.Constants.RECEIVERPHONENO;
import static com.highway.utils.Constants.TRIP_ACCEPTED;
import static com.highway.utils.Constants.TRIP_CANCELED;
import static com.highway.utils.Constants.TRIP_NEW;
import static com.highway.utils.Constants.VEHICLE_NUMBER;
import static com.highway.utils.Constants.VEHICLE_TYPE;

public class BookingConformedActivity extends AppCompatActivity implements OnMapReadyCallback, GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener,
        LocationListener, TaskLoadedCallback, View.OnClickListener {


    public static final int MY_PERMISSIONS_REQUEST_LOCATION = 99;
    private static final int SELECT_TYPE = 4;
    private static final int BOUND_PADDING = 100;
    public String TAG = getClass().getSimpleName();
    ;
    public TextView bookTruckTv, phoneNoTv, nameTv, editTV;
    public String userName, userMobNo;
    MarkerOptions markerOptions1;
    MarkerOptions markerOptions2;
    List<Marker> markers = new ArrayList<>();
    RecyclerView recyclerView;
    GoogleApiClient mGoogleApiClient;
    Location mLastLocation;
    Marker mCurrLocationMarker;
    LocationRequest mLocationRequest;
    TextView edtDropLocation;
    LinearLayout sourceLL;
    LinearLayout destLL;
    LinearLayout bookingSearchingLayout, LLoutPhoneCall;
    public JSONObject pushData;


    private Toolbar toolbar;
    private TextView sourceTV, destTV, driverName, vehicleName,
            bookingInfoForDriverAllocationTime, fareValue, cancelTripTV, infoTV;
    private ImageView imgTruckIV, callActionIV;
    TextView goodtype;
    private Polyline currentPolyline;
    private GoogleMap mMap;
    private Button back_button;
    private Button confirmBooking;
    private double sourceLatitude, sourceLongitude;
    private double destLatitude, destLongitude;
    private String sourceName;
    private String destName;
    private boolean isSelected;
    private String gdTypeId, gdTypeText;
    BookingVehicleListResponse bookingVehicleListResponse;
    private String tripId, userRecvNO, userMobileNO;
    public String bookTripIdCode, bookId, vehicleTypeId;
    boolean timeUp;
    String bookVehicleName;
    public TextView rejTV, acptTripTv;
    RelativeLayout mylocation;
    BookingConformedActivity bookingConformedActivity;

    MySenderBroadCast mySenderBroadCast = new MySenderBroadCast();


    private String driverMobile;
    private CountDownTimer countDownTimer;
    private Iterable<? extends LatLng> list;
    private NotificationPushData data = new NotificationPushData();
    private String mobileNo;
    private String userId;
    private TripStatus tripStatus;
    private String sourceTV1;
    private String destTV1;
    private String driverName1;
    private String fareValue1;
    private BookingHTripResponse bookingHTripResponse;
    private String completeDate1;
    private String pickUp1;
    private String dropTime1;
    private String vehicleNumber1;

    String name, role,getVehicleName, vehicleNumber, faireChargeVal,
            status, tripType, tripStartDate, tripEndDate, pickUpTime, dropTime;



    public static void start(ConformBookingActivity activity,
                             String bookTripIdCode, String bookId,
                             String vTypeId, String user_id, String tripRecevirId,
                             String GoodsTypeId, String couponId,
                             double sourceLat, double sourceLong, double destLat,
                             double destLong, String tripFare, String sourceAddress,
                             String destAddress) {

        Intent intent = new Intent(activity, BookingConformedActivity.class);
        intent.putExtra("bookTripIdCode", bookTripIdCode);
        intent.putExtra("bookId", bookId);
        intent.putExtra("vTypeId", vTypeId);
        // Asked by sir
        intent.putExtra("User_id", user_id);
        intent.putExtra("tripRecevirId", tripRecevirId);
        intent.putExtra("GoodsTypeId", GoodsTypeId);
        intent.putExtra("CouponId", couponId);
        intent.putExtra("SourceLat", sourceLat);
        intent.putExtra("SourceLong", sourceLong);
        intent.putExtra("DestLat", destLat);
        intent.putExtra("DestLong", destLong);
        intent.putExtra("TripFare", tripFare);
        intent.putExtra("sourceAddress", sourceAddress);
        intent.putExtra("destAddress", destAddress);

        activity.startActivity(intent);

    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirmed_booking);

        sourceTV = findViewById(R.id.sourceTV);
        destTV = findViewById(R.id.destTV);
        driverName = findViewById(R.id.driverName);
        vehicleName = findViewById(R.id.vehicleName);
        bookingInfoForDriverAllocationTime = findViewById(R.id.bookingInfoForDriverAllocation);
        fareValue = findViewById(R.id.fareValue);
        callActionIV = findViewById(R.id.callActionIV);
        bookingSearchingLayout = findViewById(R.id.bookingSearchingLayout);
        LLoutPhoneCall = findViewById(R.id.LLoutPhoneCall);
        cancelTripTV = findViewById(R.id.cancelTripTV);
        infoTV = findViewById(R.id.tripInfo);
        toolbar = findViewById(R.id.toolbar);
        mylocation = findViewById(R.id.mylocation);
        mylocation.setOnClickListener(this);

        // Using Broadcast for Customer
        /*rejTV = findViewById(R.id.TripRejectedTv);
        acptTripTv = findViewById(R.id.TripAcceptTv);
*/
        userName = HighwayPrefs.getString(getApplicationContext(), Constants.RECEIVERNAME);
        userMobNo = HighwayPrefs.getString(getApplicationContext(), RECEIVERPHONENO);
        bookVehicleName = HighwayPrefs.getString(getApplicationContext(), "bookVehicleName");  // booking vehicle nane
        vehicleName.setText(bookVehicleName);

        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);

            if (getIntent().hasExtra("bookTripIdCode")) {
                bookTripIdCode = getIntent().getStringExtra("bookTripIdCode");
                bookId = getIntent().getStringExtra("bookId");
                vehicleTypeId = getIntent().getStringExtra("vTypeId");
                getSupportActionBar().setTitle("TRIP " + bookTripIdCode);
                HighwayApplication.getInstance().setCurrentTripId(bookId);
                HighwayPrefs.putString(getApplicationContext(), "vechicleId", vehicleTypeId);
//
//              HighwayPrefs.putString(getApplicationContext(), "bookTripIdCode", bookTripIdCode); // for invoiceBottomDialogfragmentForDriver
                HighwayPrefs.putString(getApplicationContext(), "BookingId", bookId);
            } else {

                tripStatus = HighwayApplication.getInstance().getTripStatus();
                if (tripStatus != null) {


                    bookTripIdCode = tripStatus.getBookingTripCode();
                    bookId = tripStatus.getBookingTripId();
                    vehicleTypeId = tripStatus.getVehicleType();
                    getSupportActionBar().setTitle("TRIP " + bookTripIdCode);

                    HighwayApplication.getInstance().setCurrentTripId(bookId);


                    destName = tripStatus.getDestinationAddress();
                    sourceName = tripStatus.getSourceAddress();

                    destLatitude = Double.parseDouble(tripStatus.getDropLat());
                    destLongitude = Double.parseDouble(tripStatus.getDropLong());


                    sourceLatitude = Double.parseDouble(tripStatus.getSourceLat());
                    sourceLongitude = Double.parseDouble(tripStatus.getSourceLong());


                    sourceTV.setText("" + sourceName);
                    destTV.setText("" + destName);

                    markerOptions1 = new MarkerOptions().position(new LatLng(sourceLatitude, sourceLongitude));
                    markerOptions1.icon(BitmapDescriptorFactory.fromBitmap(createCustomMarker(R.drawable.ic_pins)));

                    markerOptions2 = new MarkerOptions().position(new LatLng(destLatitude, destLongitude));
                    markerOptions2.icon(BitmapDescriptorFactory.fromBitmap(createCustomMarker(R.drawable.ic_pin)));
                    performAfterNotification(tripStatus.getCurrentTripStatus());


                }
            }

        }
        if (HighwayApplication.getInstance().getBookingHTripRequest() != null) {
            initLocations();
        }
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        if (mapFragment != null) {
            mapFragment.getMapAsync(this);
        }

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        new FetchURL(BookingConformedActivity.this).execute(getUrl(markerOptions1.getPosition(), markerOptions2.getPosition(), "driving"), "driving");

        Places.initialize(this, "AIzaSyDRMI4wJHUfwtsX3zoNqVaTReXyHtIAT6U");

        if (!Places.isInitialized()) {
            Places.initialize(this, "AIzaSyDRMI4wJHUfwtsX3zoNqVaTReXyHtIAT6U");
        }

        IntentFilter intentFilter = new IntentFilter("com.highway.customer.customerActivity.ACTION_SEND");
        registerReceiver(mySenderBroadCast, intentFilter);

        clicklistener();
        bookingTimer();


        Log.e(TAG, BaseUtil.jsonFromModel(pushData));


        intentFilter = new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);
        intentFilter.addAction(Intent.ACTION_TIME_TICK);
        registerReceiver(mySenderBroadCast, intentFilter);

        //tapToReturnUpComing();
        getLandingfromUpcomming();
    }




    // USING BROAD CAST RECEIVER
    public void broadCastMessage(View view) {
        Intent serviceIntent = new Intent(this, MyIntentService.class);
        serviceIntent.putExtra("key", "Inital Value");
        startService(serviceIntent);

    }

    // USING BROAD CAST RECEIVER --- registered
//    @Override
//    protected void onStart() {
//        super.onStart();
//        IntentFilter intentFilter = new IntentFilter(MyIntentService.MY_SERVICE_INTENT);
//        LocalBroadcastManager.getInstance(getApplicationContext())
//                .registerReceiver(mReceiver,intentFilter);
//    }
//// USING BROAD CAST RECEIVER --- Unregistered
//    @Override
//    protected void onStop() {
//        super.onStop();
//        LocalBroadcastManager.getInstance(getApplicationContext())
//                .unregisterReceiver(mReceiver);
//    }
//
//// USING BROAD CAST RECEIVER --- Unregistered
    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(mySenderBroadCast);
    }

    public void clicklistener() {
        cancelTripTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), CancelOrderTripByCustomerWithReasonActivity.class);
                //  userId = HighwayPrefs.putString(getApplicationContext(),Constants.ID);
                intent.putExtra("bookTripIdCode", bookTripIdCode);
                intent.putExtra("bookId", bookId);
                intent.putExtra("vTypeId", vehicleTypeId);
                startActivityForResult(intent, 1000);

            }
        });

        infoTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getApplicationContext(), BookingInfoDetailsActivity.class);
                // asked by sir not sure
//                HighwayPrefs.putString(getApplicationContext(),Constants.BOOKINGTRIPID,tripId);
//                HighwayPrefs.putString(getApplicationContext(),Constants.USERMOBILE,userMobileNO);
//                HighwayPrefs.putString(getApplicationContext(),RECEIVERPHONENO,userRecvNO);
                startActivity(intent);
                //finish();
            }
        });


        callActionIV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel:" + driverMobile));
                startActivity(intent);
            }
        });
    }


    @Override
    protected void onStart() {
        super.onStart();
        LocalBroadcastManager.getInstance(this).registerReceiver((mMessageReceiver),
                new IntentFilter("MyData")
        );
    }

    @Override
    protected void onStop() {
        super.onStop();
        LocalBroadcastManager.getInstance(this).unregisterReceiver(mMessageReceiver);
    }


    private BroadcastReceiver mMessageReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {

            try {
                pushData = new JSONObject(intent.getStringExtra(PUSH_NEW_BOOKING_TRIP_DATA_KEY));

                if (pushData != null && pushData.getString(PUSH_TYPE).equalsIgnoreCase(TRIP_ACCEPTED)) {
                    driverName.setText("" + pushData.getString(DRIVER_NAME));
                    vehicleName.setText("" + pushData.getString(VEHICLE_TYPE) + " - " + pushData.getString(VEHICLE_NUMBER));
                    driverMobile = pushData.getString(PUSH_MOBILE);
                    bookingSearchingLayout.setVisibility(View.GONE);
                    LLoutPhoneCall.setVisibility(View.VISIBLE);
                    callActionIV.setVisibility(View.VISIBLE);
                    if (countDownTimer != null) {
                        countDownTimer.cancel();
                    }

                } else if (pushData.getString(PUSH_TYPE).equalsIgnoreCase(TRIP_CANCELED)) {
                    finish();

                } else if (pushData.getString(PUSH_TYPE).equalsIgnoreCase(DROPPED)) {
                    performAfterNotification(pushData.getString(PUSH_TYPE));
                }


                Toast.makeText(BookingConformedActivity.this, "Dta" + pushData.toString(), Toast.LENGTH_LONG).show();
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
    };


    public void initLocations() {
        destName = HighwayApplication.getInstance().getBookingHTripRequest().getDestAddress();
        sourceName = HighwayApplication.getInstance().getBookingHTripRequest().getSourceAddress();

        destLatitude = HighwayApplication.getInstance().getBookingHTripRequest().getDestLat();
        destLongitude = HighwayApplication.getInstance().getBookingHTripRequest().getDestLong();

        sourceLatitude = HighwayApplication.getInstance().getBookingHTripRequest().getSourceLat();
        sourceLongitude = HighwayApplication.getInstance().getBookingHTripRequest().getSourceLong();

        sourceTV.setText("" + sourceName);
        destTV.setText("" + destName);

        markerOptions1 = new MarkerOptions().position(new LatLng(sourceLatitude, sourceLongitude));
        markerOptions1.icon(BitmapDescriptorFactory.fromBitmap(createCustomMarker(R.drawable.ic_pins)));

        markerOptions2 = new MarkerOptions().position(new LatLng(destLatitude, destLongitude));
        markerOptions2.icon(BitmapDescriptorFactory.fromBitmap(createCustomMarker(R.drawable.ic_pin)));

    }





    public Bitmap createCustomMarker(@DrawableRes int resource) {

        View marker = ((LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.custom_marker_layout, null);

        ImageView markerImage = marker.findViewById(R.id.imgLogo);
        markerImage.setImageResource(resource);

        DisplayMetrics displayMetrics = new DisplayMetrics();
        this.getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        marker.setLayoutParams(new ViewGroup.LayoutParams(52, ViewGroup.LayoutParams.WRAP_CONTENT));
        marker.measure(displayMetrics.widthPixels, displayMetrics.heightPixels);
        marker.layout(0, 0, displayMetrics.widthPixels, displayMetrics.heightPixels);
        marker.buildDrawingCache();
        Bitmap bitmap = Bitmap.createBitmap(marker.getMeasuredWidth(), marker.getMeasuredHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        marker.draw(canvas);

        return bitmap;

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.getUiSettings().setMyLocationButtonEnabled(false);
        mMap.getUiSettings().setZoomControlsEnabled(false);
        mMap.setMinZoomPreference(2.0f);
        mMap.setMaxZoomPreference(18.0f);

        try {
            mMap.setMapStyle(
                    MapStyleOptions.loadRawResourceStyle(this, R.raw.style));
        } catch (Resources.NotFoundException e) {
            e.printStackTrace();
            // Oops, looks like the map style resource couldn't be found!
        }

        mMap.setOnMapLoadedCallback(new GoogleMap.OnMapLoadedCallback() {
            @Override
            public void onMapLoaded() {
                //Your code where exception occurs goes here..
                //
                if (destLatitude > 0 && destLongitude > 0) {

                    Marker markerS = mMap.addMarker(markerOptions1);
                    markers.add(markerS);
                    Marker markerD = mMap.addMarker(markerOptions2);
                    markers.add(markerD);

                    LatLngBounds.Builder builder = new LatLngBounds.Builder();
                    for (Marker marker : markers) {
                        builder.include(marker.getPosition());
                    }
                    LatLngBounds bounds = builder.build();
                    int padding = 40; // offset from edges of the map in pixels
                    CameraUpdate cu = CameraUpdateFactory.newLatLngBounds(bounds, padding);

                    mMap.moveCamera(cu);
                }
            }
        });

        //Initialize Google Play Services
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(this,
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
        mGoogleApiClient = new GoogleApiClient.Builder(this)
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
        if (ContextCompat.checkSelfPermission(this,
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

    ///**********************
    @Override
    public void onLocationChanged(Location location) {

        mLastLocation = location;
        updateMyLocation();

        if (mCurrLocationMarker != null) {
            mCurrLocationMarker.remove();
        }

        //Place current location marker
        LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());

        //move map camera
        mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
        mMap.moveCamera(CameraUpdateFactory.zoomTo(10));

        //stop location updates
        if (mGoogleApiClient != null) {
            LocationServices.FusedLocationApi.removeLocationUpdates(mGoogleApiClient, this);
        }


    }


    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_LOCATION: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {


                    if (ContextCompat.checkSelfPermission(this,
                            Manifest.permission.ACCESS_FINE_LOCATION)
                            == PackageManager.PERMISSION_GRANTED) {

                        if (mGoogleApiClient == null) {
                            buildGoogleApiClient();
                        }
                    }

                } else {

                    Toast.makeText(this, "permission denied", Toast.LENGTH_LONG).show();
                }
                return;
            }

        }
    }

    private String getUrl(LatLng origin, LatLng dest, String directionMode) {
        // Origin of route
        String str_origin = "origin=" + origin.latitude + "," + origin.longitude;
        // Destination of route
        String str_dest = "destination=" + dest.latitude + "," + dest.longitude;
        // Mode
        String mode = "mode=" + directionMode;
        // Building the parameters to the web service
        String parameters = str_origin + "&" + str_dest + "&" + mode;
        // Output format
        String output = "json";
        // Building the url to the web service
        String url = "https://maps.googleapis.com/maps/api/directions/" + output + "?" + parameters + "&key=" + "AIzaSyDRMI4wJHUfwtsX3zoNqVaTReXyHtIAT6U";
        return url;
    }

    @Override
    public void onTaskDone(Object... values) {
        if (currentPolyline != null) {
            currentPolyline.remove();
            currentPolyline = mMap.addPolyline((PolylineOptions) values[0]);

          /*  LatLngBounds.Builder builder = new LatLngBounds.Builder();
            for (LatLng latLng : list) {
                builder.include(latLng);
            }

            final LatLngBounds bounds = builder.build();
            //BOUND_PADDING is an int to specify padding of bound.. try 100.
            CameraUpdate cu = CameraUpdateFactory.newLatLngBounds(bounds, BOUND_PADDING);
            mMap.animateCamera(cu);*/

        }


    }


    public void bookingTimer() {
        if (tripStatus != null)
            return;

        countDownTimer = new CountDownTimer(60 * 1000, 1000) {

            public void onTick(long millisUntilFinished) {
                String text = String.format(Locale.getDefault(), "%02d: %02d",
                        TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished) % 60,
                        TimeUnit.MILLISECONDS.toSeconds(millisUntilFinished) % 60);
                bookingInfoForDriverAllocationTime.setText(text);

            }

            public void onFinish() {
                bookingInfoForDriverAllocationTime.setText("Time up!");
                timeUp = true;
                showAlertDiolog("");

            }
        }.start();
    }

    private void showAlertDiolog(String message) {

        final android.app.AlertDialog.Builder dialogBuilder = new android.app.AlertDialog.Builder(this);

        LayoutInflater inflater = this.getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.activity_show_alert_dialog_driver_not_responding, null);
        dialogBuilder.setView(dialogView);

        final android.app.AlertDialog dialog = dialogBuilder.create();
        Button done = dialogView.findViewById(R.id.btn_done);

        TextView text_cancel = dialogView.findViewById(R.id.btnCancel);
        text_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                dialog.dismiss();
            }
        });

        try {

        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            if (!isFinishing())
                dialogBuilder.create().show();

        } catch (Exception e) {
            e.printStackTrace();

        }

    }

    //by sir notes
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RESULT_OK) {

            if (data.hasExtra("isCancelled") && data.getBooleanExtra("isCancelled", false)) {
                finish();
            }

            if (data.hasExtra("isVehicleInfo") && data.getBooleanExtra("isVehicleInfo", false)) {
                finish();
            }
        }
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        onBackPressed();
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {

            case R.id.mylocation:          // for location
                updateMyLocation();
                break;
        }
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


    void performAfterNotification(String status) {

        switch (status) {

            case TRIP_NEW:
                sourceTV.setText("" + sourceName);
                destTV.setText("" + destName);
                break;

            case TRIP_ACCEPTED:

                if (tripStatus != null) {
                    driverName.setText("" + tripStatus.getName());
                    vehicleName.setText("" + tripStatus.getBookingTripCode() + " - " + tripStatus.getName());
                    sourceTV.setText("" + tripStatus.getSourceAddress());
                    destTV.setText("" + tripStatus.getDestinationAddress());

                    driverMobile = tripStatus.getMobile();
                    bookingSearchingLayout.setVisibility(View.GONE);
                    LLoutPhoneCall.setVisibility(View.VISIBLE);
                    callActionIV.setVisibility(View.VISIBLE);
                    if (countDownTimer != null) {
                        countDownTimer.cancel();
                    }
                }

                break;

            case ARRIVED:

                break;

            case PICKEDUP:

                break;

            case DROPPED:

                if (tripStatus != null) {
                    driverName.setText("" + tripStatus.getName());
                    vehicleName.setText("" + tripStatus.getBookingTripCode() + " - " + tripStatus.getName());
                    sourceTV.setText("" + tripStatus.getSourceAddress());
                    destTV.setText("" + tripStatus.getDestinationAddress());

                    driverMobile = tripStatus.getMobile();
                    bookingSearchingLayout.setVisibility(View.GONE);
                    LLoutPhoneCall.setVisibility(View.VISIBLE);
                    callActionIV.setVisibility(View.VISIBLE);
                    if (countDownTimer != null) {
                        countDownTimer.cancel();
                    }
                }


                finish();
                break;

            case COMPLETED:

                finish();
                break;

            case RATING:
                break;

            case INVOICE:
                break;

        }

    }


    public void tapToReturnUpComing() {

        Bundle bundle = getIntent().getExtras();

        sourceTV1 = bundle.getString("SourceAddLatlog");
        destTV1 = bundle.getString("DestAddLatlog");
        driverName1 = bundle.getString("VehicleName");       // after change////
        fareValue1 = bundle.getString("GetFairCharge");
        ///////////////////////////////////////////////////
        completeDate1 = bundle.getString("CompleteDate");
        pickUp1 = bundle.getString("PickupTime");
        dropTime1 = bundle.getString("DropTime");
        vehicleNumber1 = bundle.getString("VehicleNumber");

        sourceTV.setText(sourceTV1);
        destTV.setText(destTV1);
        driverName.setText(driverName1);
        fareValue.setText(fareValue1);

     /*   BookingHTripRequest bookingHTripRequest = new BookingHTripRequest();
        sourceTV.setText(bookingHTripRequest.getSourceAddress());
        destTV.setText(bookingHTripRequest.getSourceAddress());
        driverName.setText(bookingHTripRequest.getVehicleTypeId());
        fareValue.setText(bookingHTripRequest.getTripFare());

        TripStatus tripStatus1 = new TripStatus();
       // if (bookingHTripResponse  != null && bookingHTripResponse.getStatus ().toString().equalsIgnoreCase("1")) {

        sourceTV.setText(tripStatus1.getSourceAddress());
        destTV.setText(tripStatus1.getSourceAddress());
        driverName.setText(tripStatus1.getName());
        fareValue.setText(tripStatus1.getDropLat());
        //}*/

    }

    private void getLandingfromUpcomming() {

        Bundle bundle = getIntent().getExtras();

        LatLng sourceAddLatLng = new LatLng(Double.parseDouble(""+bundle.getString("sourceLat")),
                Double.parseDouble(""+bundle.getString("sourceLong")));
        LatLng destAddLatLng = new LatLng(Double.parseDouble(""+ bundle.getString("destinationLat")),
                Double.parseDouble(""+bundle.getString("destinationLong")));

        sourceLatitude = Double.parseDouble(bundle.getString("sourceLat"));
        sourceLongitude= Double.parseDouble(bundle.getString("sourceLong"));

        destLatitude = Double.parseDouble(bundle.getString("destinationLat"));
        destLongitude= Double.parseDouble(bundle.getString("destinationLong"));

        sourceTV.setText(""+ Utils.getAddress(getApplicationContext(),sourceAddLatLng));
        destTV.setText(""+Utils.getAddress(getApplicationContext(),destAddLatLng));

        markerOptions1 = new MarkerOptions().position(new LatLng(sourceLatitude, sourceLongitude));
        markerOptions1.icon(BitmapDescriptorFactory.fromBitmap(createCustomMarker(R.drawable.ic_pins)));

        markerOptions2 = new MarkerOptions().position(new LatLng(destLatitude, destLongitude));
        markerOptions2.icon(BitmapDescriptorFactory.fromBitmap(createCustomMarker(R.drawable.ic_pin)));

        userName = bundle.getString("name");
        role = bundle.getString("role");
        getVehicleName = bundle.getString("vehicleName");
        vehicleNumber = bundle.getString("vehicleNumber");
        faireChargeVal = bundle.getString("fare");
        status = bundle.getString("status");
        tripType = bundle.getString("tripType");
        tripStartDate = bundle.getString("startDate");
        tripEndDate = bundle.getString("endDate");
        pickUpTime = bundle.getString("pickupTime");
        dropTime = bundle.getString("dropTime");

        driverName.setText(userName);
        vehicleName.setText(getVehicleName);
        fareValue.setText(faireChargeVal);


    }


}
