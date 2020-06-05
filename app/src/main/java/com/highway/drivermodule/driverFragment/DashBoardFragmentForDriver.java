package com.highway.drivermodule.driverFragment;

import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;
import com.highway.R;
import com.highway.common.base.commonModel.customerDiverOwnerModelsClass.allHighwayTripModel.userTrip.GetAllTripByUserIdRequest;
import com.highway.common.base.commonModel.customerDiverOwnerModelsClass.allHighwayTripModel.userTrip.GetAllTripByUserIdResponse;
import com.highway.commonretrofit.RestClient;
import com.highway.drivermodule.driverActivity.DriverAllTripsActivity;
import com.highway.drivermodule.driverActivity.LocationTrack;
import com.highway.drivermodule.driverAdapter.FragmentTabModeAdapterForDriver;
import com.highway.utils.Constants;
import com.highway.utils.HighwayPrefs;
import com.highway.utils.Utils;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.Manifest.permission.ACCESS_COARSE_LOCATION;
import static android.Manifest.permission.ACCESS_FINE_LOCATION;


public class DashBoardFragmentForDriver extends Fragment /*implements LocationListener*/ {

    private TabLayout driverTabLayout;
    private ViewPager driverViewPager;
    public TextView gpsTv;
    public LocationManager locationManager;

    /////////////////// code  on Service class ///////////////////////////
    private ArrayList<String> permissionsToRequest;
    private ArrayList<String> permissionsRejected = new ArrayList<>();
    private ArrayList<String> permissions = new ArrayList<>();
    private final static int ALL_PERMISSIONS_RESULT = 101;
    public LocationTrack locationTrack;

    public int TIMECOUNT = 10000;
    public boolean ISTRAVERSING = true;
    Handler handler = new Handler();


    DriverAllTripsActivity dashBoardActivity;
    UpComingFragmentForDriver upComingFragmentForDriver;
   // OnGoingFragmentForDriver onGoingFragmentForDriver;
    PendingFragmentForDriver pendingFragmentForDriver;
    CompletedFragmentForDriver completedFragmentForDriver;
    CancelFragmentForDriver cancelFragmentForDriver;

    List<Fragment> driverFragmentList = new ArrayList<>();
    private String userId;


    public DashBoardFragmentForDriver() {
    }

    public static DashBoardFragmentForDriver newInstance() {
        DashBoardFragmentForDriver fragment = new DashBoardFragmentForDriver();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_driver_dash_board, container, false);

        driverTabLayout = view.findViewById(R.id.drivertabMode);
        driverViewPager = view.findViewById(R.id.driverViewPager);
        //gpsTv = view.findViewById(R.id.gpsTv);
        //gpsTrackingWithOutServiceClass();


        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                handler.removeCallbacks(sendData);
                ISTRAVERSING = false;
              //  gpsTrackingWithServiceClass();
            }
        }, 5000);


        upComingFragmentForDriver = new UpComingFragmentForDriver();
       // onGoingFragmentForDriver = new OnGoingFragmentForDriver();
        //   pendingFragmentForDriver = new PendingFragmentForDriver();
        completedFragmentForDriver = new CompletedFragmentForDriver();
        cancelFragmentForDriver = new CancelFragmentForDriver();

        driverFragmentList.add(upComingFragmentForDriver);
        //driverFragmentList.add(onGoingFragmentForDriver);
        //    driverFragmentList.add(pendingFragmentForDriver);
        driverFragmentList.add(completedFragmentForDriver);
        driverFragmentList.add(cancelFragmentForDriver);

        FragmentTabModeAdapterForDriver fragmentTabModeAdapterForDriver = new FragmentTabModeAdapterForDriver(getActivity().
                getSupportFragmentManager(), driverFragmentList);

        driverViewPager.setAdapter(fragmentTabModeAdapterForDriver);
        driverViewPager.setOffscreenPageLimit(4);
        driverTabLayout.setupWithViewPager(driverViewPager);

        driverTabLayout.addOnTabSelectedListener(new TabLayout.BaseOnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        driverViewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        return view;

    }
/*
    @Override
    public void onStart() {
        super.onStart();
        getDriverCompletedDetail();

    }

}*/

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        dashBoardActivity = (DriverAllTripsActivity) getActivity();

    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    @Override
    public void onResume() {
        super.onResume();
       getDriverCompletedDetail();
    }


    public void getDriverCompletedDetail() {

        GetAllTripByUserIdRequest getAllTripByUserIdRequest = new GetAllTripByUserIdRequest();
        userId = HighwayPrefs.getString(getContext(), Constants.ID);
        getAllTripByUserIdRequest.setUserId(userId);

        Utils.showProgressDialog(getContext());
        RestClient.allDriverTrips(getAllTripByUserIdRequest, new Callback<GetAllTripByUserIdResponse>() {
            @Override
            public void onResponse(Call<GetAllTripByUserIdResponse> call, Response<GetAllTripByUserIdResponse> response) {
                Utils.dismissProgressDialog();
                if (response.body() != null && response.body().getStatus() != null) {

                    if (response.body().getStatus()) {
                        GetAllTripByUserIdResponse getAllTripByUserIdResponse = response.body();
                        if (getAllTripByUserIdResponse != null) {
                            if (getAllTripByUserIdResponse.getCancelTrips() != null && getAllTripByUserIdResponse.getCancelTrips().size() > 0) {
                                dashBoardActivity.setCancelTrips(getAllTripByUserIdResponse.getCancelTrips());
                            }
                            if (getAllTripByUserIdResponse.getCompletedTrips() != null && getAllTripByUserIdResponse.getCompletedTrips().size() > 0) {
                                dashBoardActivity.setCompletedTrips(getAllTripByUserIdResponse.getCompletedTrips());
                            }

                            if (getAllTripByUserIdResponse.getUpcomingTrips() != null && getAllTripByUserIdResponse.getUpcomingTrips().size() > 0) {
                                dashBoardActivity.setUpcomingTrips(getAllTripByUserIdResponse.getUpcomingTrips());
                            }
                            updateAllFragment();
                        }
                    }

                } else {
                    Toast.makeText(dashBoardActivity, "some thig went wrong", Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<GetAllTripByUserIdResponse> call, Throwable t) {
                Toast.makeText(dashBoardActivity, " Failure", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void updateAllFragment() {
        upComingFragmentForDriver.upcomingUpdatedTripListForDriver(dashBoardActivity.getUpcomingTrips());
        //onGoingFragmentForDriver.onGoingUpdatedTripListForDriver(dashBoardActivity.getOngoingTrips());
        completedFragmentForDriver.completedUpdatedTripListForDriver(dashBoardActivity.getCompletedTrips());
        cancelFragmentForDriver.cancleUpdatedTripListForDriver(dashBoardActivity.getCancelTrips());
    }


    public void gpsTrackingWithServiceClass() {

        permissions.add(ACCESS_FINE_LOCATION);
        permissions.add(ACCESS_COARSE_LOCATION);

        permissionsToRequest = findUnAskedPermissions(permissions);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

            if (permissionsToRequest.size() > 0)
                requestPermissions(permissionsToRequest.toArray(new String[permissionsToRequest.size()]), ALL_PERMISSIONS_RESULT);
        }

        locationTrack = new LocationTrack(getActivity());

        if (locationTrack.canGetLocation()) {

            double longitude = locationTrack.getLongitude();
            double latitude = locationTrack.getLatitude();
            Toast.makeText(getActivity(), "Longitude:" + Double.toString(longitude) + "\n" +
                    "Latitude:" + Double.toString(latitude), Toast.LENGTH_SHORT).show();

            ISTRAVERSING = true;
            handler.postDelayed(sendData, TIMECOUNT);

        } else {

            locationTrack.showSettingsAlert();
        }

    }

    private Runnable sendData = new Runnable() {
        public void run() {
            try {
                //prepare and send the data here..
               // gpsTrackingWithServiceClass();
                if (ISTRAVERSING) {
                    handler.postDelayed(sendData, TIMECOUNT);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    };


    private ArrayList<String> findUnAskedPermissions(ArrayList<String> wanted) {
        ArrayList<String> result = new ArrayList<String>();

        for (String perm : wanted) {
            if (!hasPermission(perm)) {
                result.add(perm);
            }
        }

        return result;
    }

    private boolean hasPermission(String permission) {
        if (canMakeSmores()) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                return (getActivity().checkSelfPermission(permission) == PackageManager.PERMISSION_GRANTED);
            }
        }
        return true;
    }

    private boolean canMakeSmores() {
        return (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP_MR1);
    }


    @TargetApi(Build.VERSION_CODES.M)
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {

        switch (requestCode) {

            case ALL_PERMISSIONS_RESULT:
                for (String perms : permissionsToRequest) {
                    if (!hasPermission(perms)) {
                        permissionsRejected.add(perms);
                    }
                }

                if (permissionsRejected.size() > 0) {


                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        if (shouldShowRequestPermissionRationale(permissionsRejected.get(0))) {
                            showMessageOKCancel("These permissions are mandatory for the application. Please allow access.",
                                    new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                                                requestPermissions(permissionsRejected.toArray(new String[permissionsRejected.size()]), ALL_PERMISSIONS_RESULT);
                                            }
                                        }
                                    });
                            return;
                        }
                    }

                }

                break;
        }

    }

    private void showMessageOKCancel(String message, DialogInterface.OnClickListener okListener) {
        new AlertDialog.Builder(getActivity())
                .setMessage(message)
                .setPositiveButton("OK", okListener)
                .setNegativeButton("Cancel", null)
                .create()
                .show();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
      //  locationTrack.stopListener();
    }

}
