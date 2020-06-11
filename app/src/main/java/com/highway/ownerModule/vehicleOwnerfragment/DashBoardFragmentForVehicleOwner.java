package com.highway.ownerModule.vehicleOwnerfragment;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;
import com.highway.R;
import com.highway.common.base.commonModel.customerDiverOwnerModelsClass.allHighwayTripModel.userTrip.GetAllTripByUserIdRequest;
import com.highway.common.base.commonModel.customerDiverOwnerModelsClass.allHighwayTripModel.userTrip.GetAllTripByUserIdResponse;
import com.highway.commonretrofit.RestClient;
import com.highway.ownerModule.vehicleOwnerActivities.OwnerAllTripActivity;
import com.highway.ownerModule.vehicleOwnerAdapter.FragmentTabAdapterForVehicleOwner;
import com.highway.utils.Constants;
import com.highway.utils.HighwayPrefs;
import com.highway.utils.Utils;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DashBoardFragmentForVehicleOwner extends Fragment {

    private TabLayout vehicleOwnerTabMode;
    private ViewPager vehicleOwnerViewPager;

    DashBoardFragmentForVehicleOwner dashBoardFragmentForVehicleOwner;
   // DashBoardActivity dashBoardActivity;
    OwnerAllTripActivity ownerAllTripActivity;
    UpComingFragmentForVehicleOwner upComingFragmentForVehicleOwner;
    OnGoingFragmentForVehicleOwner onGoingFragmentForVehicleOwner;
    PendingFragmentForVehicleOwner pendingFragmentForVehicleOwner;
    CompletedFragmentForVehicleOwner completedFragmentForVehicleOwner;
    CancelFragmentForVehicleOwner cancelFragmentForVehicleOwner;

    List<Fragment> vehicleOwnerfragmentlist = new ArrayList<>();
    private String userId;

    public DashBoardFragmentForVehicleOwner() {
    }

    public static DashBoardFragmentForVehicleOwner newInstance() {
        DashBoardFragmentForVehicleOwner fragment = new DashBoardFragmentForVehicleOwner();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_vehicle_dash_board, container, false);

        vehicleOwnerViewPager = view.findViewById(R.id.VehicleOwnerViewPager);
        vehicleOwnerTabMode = view.findViewById(R.id.VehicleOwnertabMode);

        upComingFragmentForVehicleOwner = new UpComingFragmentForVehicleOwner();
        onGoingFragmentForVehicleOwner = new OnGoingFragmentForVehicleOwner();
        //  pendingFragmentForVehicleOwner = new PendingFragmentForVehicleOwner();
        completedFragmentForVehicleOwner = new CompletedFragmentForVehicleOwner();
        cancelFragmentForVehicleOwner = new CancelFragmentForVehicleOwner();

        vehicleOwnerfragmentlist.add(upComingFragmentForVehicleOwner);
        vehicleOwnerfragmentlist.add(onGoingFragmentForVehicleOwner);
        // vehicleOwnerfragmentlist.add(pendingFragmentForVehicleOwner);
        vehicleOwnerfragmentlist.add(completedFragmentForVehicleOwner);
        vehicleOwnerfragmentlist.add(cancelFragmentForVehicleOwner);

        FragmentTabAdapterForVehicleOwner fragmentTabAdapterForVehicleOwner =
                new FragmentTabAdapterForVehicleOwner(getActivity().getSupportFragmentManager(), vehicleOwnerfragmentlist);

        vehicleOwnerViewPager.setAdapter(fragmentTabAdapterForVehicleOwner);
        vehicleOwnerViewPager.setOffscreenPageLimit(4);
        vehicleOwnerTabMode.setupWithViewPager(vehicleOwnerViewPager);

        vehicleOwnerTabMode.addOnTabSelectedListener(new TabLayout.BaseOnTabSelectedListener() {
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
        vehicleOwnerViewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
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
     // dashBoardActivity = (DashBoardActivity) getActivity();
         ownerAllTripActivity = new OwnerAllTripActivity();
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }


    @Override
    public void onResume() {
        super.onResume();
        getVehivleOwnerCompletedDetail();

    }

    public void getVehivleOwnerCompletedDetail() {

        GetAllTripByUserIdRequest getAllTripByUserIdRequest = new GetAllTripByUserIdRequest();
        userId = HighwayPrefs.getString(getContext(), Constants.ID);
        getAllTripByUserIdRequest.setUserId(userId);

        Utils.showProgressDialog(getContext());

        RestClient.allVehicleOwnerTrip(getAllTripByUserIdRequest, new Callback<GetAllTripByUserIdResponse>() {
            @Override
            public void onResponse(Call<GetAllTripByUserIdResponse> call, Response<GetAllTripByUserIdResponse> response) {
                Utils.dismissProgressDialog();
                if (response.body() != null && response.body().getStatus() != null) {
                    if (response.body().getStatus()) {

                        GetAllTripByUserIdResponse getAllTripByUserIdResponse = response.body();
                        if (getAllTripByUserIdResponse != null) {

                            if (getAllTripByUserIdResponse.getCompletedTrips() != null && getAllTripByUserIdResponse.getCompletedTrips().size() > 0) {
                                ownerAllTripActivity.setCompletedTrips(getAllTripByUserIdResponse.getCompletedTrips());
                            }
                            if (getAllTripByUserIdResponse.getOngoingTrips() != null && getAllTripByUserIdResponse.getOngoingTrips().size() > 0) {
                                ownerAllTripActivity.setOngoingTrips(getAllTripByUserIdResponse.getOngoingTrips());
                            }
                            if (getAllTripByUserIdResponse.getUpcomingTrips() != null && getAllTripByUserIdResponse.getUpcomingTrips().size() > 0) {
                                ownerAllTripActivity.setUpcomingTrips(getAllTripByUserIdResponse.getUpcomingTrips());
                            }
                            if (getAllTripByUserIdResponse.getCancelTrips() != null && getAllTripByUserIdResponse.getCancelTrips().size() > 0) {
                                ownerAllTripActivity.setCancelTrips(getAllTripByUserIdResponse.getCancelTrips());
                            }
                            updateAllVehicleOwnerFragment();
                        }
                    }
                } else {
                    Toast.makeText(getActivity(), "Response failed", Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(Call<GetAllTripByUserIdResponse> call, Throwable t) {
                Toast.makeText(getActivity(), "failed", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void updateAllVehicleOwnerFragment() {
        completedFragmentForVehicleOwner.vehicleCompletedUpdatedTripList(ownerAllTripActivity.getCompletedTrips());
        onGoingFragmentForVehicleOwner.vehicleOnGoingUpdateList(ownerAllTripActivity.getOngoingTrips());
        cancelFragmentForVehicleOwner.vehicleCancleUpdatedTripList(ownerAllTripActivity.getCancelTrips());
        upComingFragmentForVehicleOwner.vehicleUpcomingUpdatedTripList(ownerAllTripActivity.getUpcomingTrips());
    }

}
