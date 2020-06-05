package com.highway.customer.customerFragment;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;
import com.highway.R;
import com.highway.common.base.activity.DashBoardActivity;
import com.highway.common.base.commonModel.customerDiverOwnerModelsClass.allHighwayTripModel.userTrip.GetAllTripByUserIdRequest;
import com.highway.common.base.commonModel.customerDiverOwnerModelsClass.allHighwayTripModel.userTrip.GetAllTripByUserIdResponse;
import com.highway.commonretrofit.RestClient;
import com.highway.customer.customerActivity.CustomerAllTripsActivity;
import com.highway.customer.customerAdapter.FragmentTabModeAdapterForCustomer;
import com.highway.drivermodule.driverActivity.DriverAllTripsActivity;
import com.highway.utils.Constants;
import com.highway.utils.HighwayPrefs;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DashBordFragmentForCustomer extends Fragment {

    private TabLayout myBookingTabLayout;
    private ViewPager myBookingViewPager;
    CancelFragmentForCustomer cancelFragmentForCustomer;
    CompletedFragmentForCustomer completedFragmentForCustomer;
    //OnGoingFragmentForCustomer onGoingFragmentForCustomer;
    UpCommingFragmentForCustomer upCommingFragmentForCustomer;
    PendingFragmentForCustomer pendingFragmentForCustomer;
   // DashBoardActivity dashBoardActivity;
    CustomerAllTripsActivity customerAllTripsActivity;

    List<Fragment> customerfragmentList = new ArrayList<>();
    private String userId;

    public static DashBordFragmentForCustomer newInstance() {
        DashBordFragmentForCustomer fragment = new DashBordFragmentForCustomer();
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



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_customer_dash_bord, container, false);

        myBookingTabLayout = view.findViewById(R.id.tabModeOfMyBooking);
        myBookingViewPager = view.findViewById(R.id.myBookingViewPager);

        upCommingFragmentForCustomer = new UpCommingFragmentForCustomer();
        //onGoingFragmentForCustomer = new OnGoingFragmentForCustomer();
       // pendingFragmentForCustomer = new PendingFragmentForCustomer();
        completedFragmentForCustomer = new CompletedFragmentForCustomer();
        cancelFragmentForCustomer = new CancelFragmentForCustomer();

        customerfragmentList.add(upCommingFragmentForCustomer);
        //customerfragmentList.add(onGoingFragmentForCustomer);
        //customerfragmentList.add(pendingFragmentForCustomer);
        customerfragmentList.add(completedFragmentForCustomer);
        customerfragmentList.add(cancelFragmentForCustomer);

        FragmentTabModeAdapterForCustomer fragmentAdapter = new FragmentTabModeAdapterForCustomer(getActivity().
                getSupportFragmentManager(), customerfragmentList);

        myBookingViewPager.setAdapter(fragmentAdapter);
        myBookingViewPager.setOffscreenPageLimit(4);
        myBookingTabLayout.setupWithViewPager(myBookingViewPager);

        myBookingTabLayout.addOnTabSelectedListener(new TabLayout.BaseOnTabSelectedListener() {
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
        myBookingViewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
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


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        customerAllTripsActivity = (CustomerAllTripsActivity) getActivity();

    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    @Override
    public void onResume() {
        super.onResume();
        getAllTripByCustomer();
    }

    public void getAllTripByCustomer() {

        GetAllTripByUserIdRequest getAllTripByUserIdRequest = new GetAllTripByUserIdRequest();
        userId = HighwayPrefs.getString(getContext(), Constants.ID);
        getAllTripByUserIdRequest.setUserId(userId);

        RestClient.allCustomerTrip(getAllTripByUserIdRequest, new Callback<GetAllTripByUserIdResponse>() {
            @Override
            public void onResponse(Call<GetAllTripByUserIdResponse> call, Response<GetAllTripByUserIdResponse> response) {

                if (response.body() != null) {
                    if (response.body().getStatus()) {

                        GetAllTripByUserIdResponse getAllTripByUserIdResponse = response.body();
                        if (getAllTripByUserIdResponse != null) {
                            if (getAllTripByUserIdResponse.getCompletedTrips() != null && getAllTripByUserIdResponse.getCompletedTrips().size() > 0) {
                                customerAllTripsActivity.setCompletedTrips(getAllTripByUserIdResponse.getCompletedTrips());
                            }

                            if (getAllTripByUserIdResponse.getUpcomingTrips() != null && getAllTripByUserIdResponse.getUpcomingTrips().size() > 0) {
                                customerAllTripsActivity.setUpcomingTrips(getAllTripByUserIdResponse.getUpcomingTrips());
                            }
                            if (getAllTripByUserIdResponse.getCancelTrips() != null && getAllTripByUserIdResponse.getCancelTrips().size() > 0) {
                                customerAllTripsActivity.setCancelTrips(getAllTripByUserIdResponse.getCancelTrips());
                            }
                            updateAllCustomerFragment();
                        }
                    }
                } else {
                    Toast.makeText(customerAllTripsActivity, "response failed", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<GetAllTripByUserIdResponse> call, Throwable t) {
                Toast.makeText(customerAllTripsActivity, "failed", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void updateAllCustomerFragment() {
        upCommingFragmentForCustomer.upcomingUpdatedTripListForCustomer(customerAllTripsActivity.getUpcomingTrips());
        //onGoingFragmentForCustomer.ongoingUpdatedTripListForCustomer(customerAllTripsActivity.getOngoingTrips());
        completedFragmentForCustomer.completedUpdatedTripListForCustomer(customerAllTripsActivity.getCompletedTrips());
        cancelFragmentForCustomer.cancelUpdatedTripListForCustomer(customerAllTripsActivity.getCancelTrips());
    }
}
