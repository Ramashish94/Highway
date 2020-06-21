package com.highway.customer.customerFragment;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Interpolator;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.DrawableRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MapStyleOptions;
import com.google.android.gms.maps.model.Marker;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.widget.Autocomplete;
import com.google.android.libraries.places.widget.AutocompleteActivity;
import com.google.android.libraries.places.widget.model.AutocompleteActivityMode;
import com.highway.R;
import com.highway.common.base.HighwayApplication;
import com.highway.common.base.activity.DashBoardActivity;
import com.highway.common.base.commonModel.bookingHTrip.BookingHTripRequest;
import com.highway.commonretrofit.RestClient;
import com.highway.customer.customerActivity.BookingWithDetailsActivity;
import com.highway.customer.customerModelClass.driverLocation.NearByDriverLocationResponse;
import com.highway.utils.Utils;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.app.Activity.RESULT_CANCELED;
import static android.app.Activity.RESULT_OK;

public class NewBookingFragment extends Fragment implements OnMapReadyCallback, GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener,
        LocationListener {
    int AUTOCOMPLETE_REQUEST_CODE_SOURCE = 1;
    int AUTOCOMPLETE_REQUEST_CODE_DEST = 2;

    private GoogleMap mMap;
    GoogleApiClient mGoogleApiClient;
    Location mLastLocation;
    Marker mCurrLocationMarker;
    LocationRequest mLocationRequest;


    TextView edtSourceLOcationEDT;
    TextView edtDropLocation;

    LinearLayout sourceLL;
    LinearLayout destLL;


    private boolean isMarkerRotating;
    private double sourceLatitude, sourceLongitude;
    private double destLatitude, destLongitude;
    private String sourceName;
    private String destName;
    private DashBoardActivity mActivity;
    View mapView;


    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        mActivity = (DashBoardActivity) getActivity();
    }

    public NewBookingFragment() {
        // Required empty public constructor
    }

    public static NewBookingFragment newInstance() {
        NewBookingFragment fragment = new NewBookingFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.map_view_fragment, container, false);

        edtSourceLOcationEDT = view.findViewById(R.id.edtSourceLOcation);
        edtDropLocation = view.findViewById(R.id.edtDropLocation);
        sourceLL = view.findViewById(R.id.sourceLL);
        destLL = view.findViewById(R.id.destLL);

        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        Places.initialize(mActivity, "AIzaSyDRMI4wJHUfwtsX3zoNqVaTReXyHtIAT6U");

        if (!Places.isInitialized()) {
            Places.initialize(mActivity, "AIzaSyDRMI4wJHUfwtsX3zoNqVaTReXyHtIAT6U");
        }

        sourceLL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                List<Place.Field> fields = Arrays.asList(Place.Field.ID, Place.Field.NAME, Place.Field.ADDRESS, Place.Field.LAT_LNG);

                Intent intent = new Autocomplete.IntentBuilder(
                        AutocompleteActivityMode.FULLSCREEN, fields)
                        .setCountry("IN")
                        .build(mActivity);
                startActivityForResult(intent, AUTOCOMPLETE_REQUEST_CODE_SOURCE);

            }
        });

        destLL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                List<Place.Field> fields = Arrays.asList(Place.Field.ID, Place.Field.NAME, Place.Field.ADDRESS, Place.Field.LAT_LNG);

                Intent intent = new Autocomplete.IntentBuilder(
                        AutocompleteActivityMode.FULLSCREEN, fields)
                        .setCountry("IN")
                        //.setTypeFilter(TypeFilter.ADDRESS)
                        .build(mActivity);

                startActivityForResult(intent, AUTOCOMPLETE_REQUEST_CODE_DEST);

            }
        });


        edtSourceLOcationEDT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                List<Place.Field> fields = Arrays.asList(Place.Field.ID, Place.Field.NAME, Place.Field.LAT_LNG);

                Intent intent = new Autocomplete.IntentBuilder(
                        AutocompleteActivityMode.FULLSCREEN, fields)
                        .build(mActivity);
                startActivityForResult(intent, AUTOCOMPLETE_REQUEST_CODE_SOURCE);
            }
        });

        if (Utils.isInternetConnected(getContext())) {
            Utils.showProgressDialog(getContext());
            getNearByDriverLocation();
        }

        return view;
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {
            if (requestCode == AUTOCOMPLETE_REQUEST_CODE_SOURCE) {

                Place place = Autocomplete.getPlaceFromIntent(data);
                LatLng latLng = place.getLatLng();
                Log.e("TAG", "Place: " + place.getName() + ", " + place.getAddress());
                Log.e("TAG", "Place1: " + place.getLatLng() + ", " + place.getAddress());

                if (!TextUtils.isEmpty(place.getName())) {
                    if (TextUtils.isEmpty(Utils.getAddress(mActivity, latLng))) {
                        edtSourceLOcationEDT.setText("" + Utils.getAddress(mActivity, latLng));
                    } else {
                        edtSourceLOcationEDT.setText(Utils.getAddress(mActivity, latLng));
                    }
                    if (mMap != null && place.getLatLng() != null) {


                        sourceName = Utils.getAddress(mActivity, latLng);
                        sourceLatitude = latLng.latitude;
                        sourceLongitude = latLng.longitude;
                        CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(latLng, 14);
                        mMap.moveCamera(cameraUpdate);

                        if (sourceLatitude > 0 && destLatitude > 0)
                            openBookingActivity();

                    }

                }


                double MyLat = latLng.latitude;
                double MyLong = latLng.longitude;
                Geocoder geocoder = new Geocoder(getActivity(), Locale.getDefault());
                try {
                    List<Address> addresses = geocoder.getFromLocation(MyLat, MyLong, 1);
                    String stateName = addresses.get(0).getAddressLine(0);
                    String cityName = addresses.get(0).getLocality();
                    Log.e("stateName", "::" + stateName);

                    Log.e("stateName12", "::" + Utils.getAddress(mActivity, latLng));

                    //  edit_profile_city_editText.setText(place.getName() + "," + stateName);
                } catch (IOException e) {
                    e.printStackTrace();

                }


            } else if (requestCode == AUTOCOMPLETE_REQUEST_CODE_DEST) {

                Place placeDest = Autocomplete.getPlaceFromIntent(data);

                if (!TextUtils.isEmpty(placeDest.getName())) {

                    if (TextUtils.isEmpty(placeDest.getAddress())) {
                        edtDropLocation.setText("" + placeDest.getName());
                    } else {
                        edtDropLocation.setText(placeDest.getName() + "" + placeDest.getAddress());
                    }
                    if (mMap != null && placeDest.getLatLng() != null) {

                        LatLng latLng = placeDest.getLatLng();

                        destName = placeDest.getName();
                        destLatitude = latLng.latitude;
                        destLongitude = latLng.longitude;
                        if (sourceLatitude > 0 && destLatitude > 0)
                            openBookingActivity();

                    }

                }
            }
        } else if (resultCode == AutocompleteActivity.RESULT_ERROR) {
            Status status = Autocomplete.getStatusFromIntent(data);
            Log.i("TAG", status.getStatusMessage());
        } else if (resultCode == RESULT_CANCELED) {
        }

    }

    private void openBookingActivity() {
        HighwayApplication.getInstance().setBookingHTripRequest(new BookingHTripRequest());
        if (!TextUtils.isEmpty(sourceName) && !TextUtils.isEmpty(destName)) {
            HighwayApplication.getInstance().getBookingHTripRequest().setSourceAddress(sourceName);
            HighwayApplication.getInstance().getBookingHTripRequest().setSourceLat(sourceLatitude);
            HighwayApplication.getInstance().getBookingHTripRequest().setSourceLong(sourceLongitude);
            HighwayApplication.getInstance().getBookingHTripRequest().setDestAddress(destName);
            HighwayApplication.getInstance().getBookingHTripRequest().setDestLat(destLatitude);
            HighwayApplication.getInstance().getBookingHTripRequest().setDestLong(destLongitude);
            BookingWithDetailsActivity.start(mActivity);
            // getActivity().finish();  //
        }
    }

    public Bitmap createCustomMarker(@DrawableRes int resource) {

        View marker = ((LayoutInflater) mActivity.getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.custom_marker_layout, null);

        ImageView markerImage = marker.findViewById(R.id.imgLogo);
        markerImage.setImageResource(resource);


        DisplayMetrics displayMetrics = new DisplayMetrics();
        mActivity.getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
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
    public void onDetach() {
        super.onDetach();
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.getUiSettings().setMyLocationButtonEnabled(true);
        // mMap.getUiSettings().setZoomControlsEnabled(true);
        mMap.setMinZoomPreference(10.0f);
        mMap.setMaxZoomPreference(18.0f);
///////////////////////////////////////////////////////////// Asked by sir
        if (mapView != null && mapView.findViewById(Integer.parseInt("1")) != null) {
            View locationButton = ((View) mapView.findViewById(Integer.parseInt("1")).getParent()).findViewById(Integer.parseInt("2"));
            RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) locationButton.getLayoutParams();
            layoutParams.addRule(RelativeLayout.ALIGN_PARENT_RIGHT, 0);
            layoutParams.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM, RelativeLayout.TRUE);
            layoutParams.setMargins(0, 180, 180, 0);
        }

        try {
            mMap.setMapStyle(
                    MapStyleOptions.loadRawResourceStyle(getActivity(), R.raw.style));
        } catch (Resources.NotFoundException e) {
            e.printStackTrace();
            // Oops, looks like the map style resource couldn't be found!
        }


        //Initialize Google Play Services
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(mActivity,
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
        mGoogleApiClient = new GoogleApiClient.Builder(mActivity)
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
        if (ContextCompat.checkSelfPermission(mActivity,
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
        if (mCurrLocationMarker != null) {
            mCurrLocationMarker.remove();
        }

        //Place current location marker
        LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());

        sourceName = Utils.getAddress(mActivity, latLng);
        sourceLatitude = latLng.latitude;
        sourceLongitude = latLng.longitude;
        edtSourceLOcationEDT.setText("" + sourceName);

        //move map camera
        mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
        mMap.moveCamera(CameraUpdateFactory.zoomTo(14));

        Location location1 = new Location("");
        location1.setLatitude(30.5518691);
        location1.setLongitude(75.6513571);
        // showMarker(location1);
        //stop location updates
        if (mGoogleApiClient != null) {
            LocationServices.FusedLocationApi.removeLocationUpdates(mGoogleApiClient, this);
        }
    }


    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */


    public static final int MY_PERMISSIONS_REQUEST_LOCATION = 99;

    public boolean checkLocationPermission() {
        if (ContextCompat.checkSelfPermission(mActivity,
                Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {

            // Asking user if explanation is needed
            if (ActivityCompat.shouldShowRequestPermissionRationale(mActivity,
                    Manifest.permission.ACCESS_FINE_LOCATION)) {

                // Show an explanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.

                //Prompt the user once explanation has been shown
                ActivityCompat.requestPermissions(mActivity,
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        MY_PERMISSIONS_REQUEST_LOCATION);


            } else {
                // No explanation needed, we can request the permission.
                ActivityCompat.requestPermissions(mActivity,
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        MY_PERMISSIONS_REQUEST_LOCATION);
            }
            return false;
        } else {
            return true;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_LOCATION: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    // permission was granted. Do the
                    // contacts-related task you need to do.
                    if (ContextCompat.checkSelfPermission(mActivity,
                            Manifest.permission.ACCESS_FINE_LOCATION)
                            == PackageManager.PERMISSION_GRANTED) {

                        if (mGoogleApiClient == null) {
                            buildGoogleApiClient();
                        }
                        mMap.setMyLocationEnabled(true);
                    }

                } else {

                    // Permission denied, Disable the functionality that depends on this permission.
                    Toast.makeText(mActivity, "permission denied", Toast.LENGTH_LONG).show();
                }
                return;
            }

            // other 'case' lines to check for other permissions this app might request.
            // You can add here other case statements according to your requirement.
        }
    }

//    private void showMarker(@NonNull Location currentLocation) {
//        LatLng latLng = new LatLng(currentLocation.getLatitude(), currentLocation.getLongitude());
//        if (mCurrLocationMarker == null)
//            mCurrLocationMarker = mMap.addMarker(new MarkerOptions().icon(BitmapDescriptorFactory.fromBitmap(getBitmapFromVectorDrawable(getContext(),R.drawable.ic_navigation))).position(latLng));
//        else
//            MarkerAnimation.animateMarkerToGB(mCurrLocationMarker, latLng, new LatLngInterpolator.Spherical());
//        rotateMarker(mCurrLocationMarker, 23.9F);
//    }

//    public static Bitmap getBitmapFromVectorDrawable(Context context, int drawableId) {
//        Drawable drawable = ContextCompat.getDrawable(context, drawableId);
//        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
//            drawable = (DrawableCompat.wrap(drawable)).mutate();
//        }
//
//        Bitmap bitmap = Bitmap.createBitmap(drawable.getIntrinsicWidth(),
//                drawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
//        Canvas canvas = new Canvas(bitmap);
//        drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
//        drawable.draw(canvas);
//        return bitmap;
//    }

    private double bearingBetweenLocations(LatLng latLng1, LatLng latLng2) {

        double PI = 3.14159;
        double lat1 = latLng1.latitude * PI / 180;
        double long1 = latLng1.longitude * PI / 180;
        double lat2 = latLng2.latitude * PI / 180;
        double long2 = latLng2.longitude * PI / 180;

        double dLon = (long2 - long1);

        double y = Math.sin(dLon) * Math.cos(lat2);
        double x = Math.cos(lat1) * Math.sin(lat2) - Math.sin(lat1)
                * Math.cos(lat2) * Math.cos(dLon);

        double brng = Math.atan2(y, x);

        brng = Math.toDegrees(brng);
        brng = (brng + 360) % 360;

        return brng;
    }

    private void rotateMarker(final Marker marker, final float toRotation) {
        if (!isMarkerRotating) {
            final Handler handler = new Handler();
            final long start = SystemClock.uptimeMillis();
            final float startRotation = marker.getRotation();
            final long duration = 1000;

            final Interpolator interpolator = new LinearInterpolator();

            handler.post(new Runnable() {
                @Override
                public void run() {
                    isMarkerRotating = true;

                    long elapsed = SystemClock.uptimeMillis() - start;
                    float t = interpolator.getInterpolation((float) elapsed / duration);

                    float rot = t * toRotation + (1 - t) * startRotation;

                    marker.setRotation(-rot > 180 ? rot / 2 : rot);
                    if (t < 1.0) {
                        // Post again 16ms later.
                        handler.postDelayed(this, 16);
                    } else {
                        isMarkerRotating = false;
                    }
                }
            });
        }
    }

    public void getNearByDriverLocation() {
        RestClient.getNearByDriverLocation(new Callback<NearByDriverLocationResponse>() {
            @Override
            public void onResponse(Call<NearByDriverLocationResponse> call, Response<NearByDriverLocationResponse> response) {
                Utils.dismissProgressDialog();
                if (response.code() == 200 && response.body() != null) {
                    NearByDriverLocationResponse resp = response.body();

                }
            }

            @Override
            public void onFailure(Call<NearByDriverLocationResponse> call, Throwable t) {
                Utils.dismissProgressDialog();

            }
        });
    }

}