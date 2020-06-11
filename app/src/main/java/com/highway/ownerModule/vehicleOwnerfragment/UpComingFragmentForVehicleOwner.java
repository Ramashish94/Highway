package com.highway.ownerModule.vehicleOwnerfragment;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.highway.R;
import com.highway.common.base.activity.DashBoardActivity;
import com.highway.common.base.commonModel.customerDiverOwnerModelsClass.allHighwayTripModel.userTrip.UpcomingTrip;
import com.highway.ownerModule.vehicleOwnerActivities.OwnerAllTripActivity;
import com.highway.ownerModule.vehicleOwnerAdapter.UpcomingTripAdapterForVehicleOwner;


import java.util.ArrayList;
import java.util.List;

public class UpComingFragmentForVehicleOwner extends Fragment {

    List<UpcomingTrip> upcomingTrips = new ArrayList<>();
    RecyclerView vehicleUpcomingRecycler;
    // DashBoardActivity dashBoardActivity;
    OwnerAllTripActivity ownerAllTripActivity;
    Context context;
    UpcomingTripAdapterForVehicleOwner upcomingTripAdapterForVehicleOwner;

    public UpComingFragmentForVehicleOwner() { }

    public static UpComingFragmentForVehicleOwner newInstance() {
        UpComingFragmentForVehicleOwner fragment = new UpComingFragmentForVehicleOwner();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments()!= null) {

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_vehicle_up_coming, container, false);
        vehicleUpcomingRecycler = view.findViewById(R.id.vehicleUpcomingRecycler);
        return view;

    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        //dashBoardActivity = (DashBoardActivity) getActivity();
        ownerAllTripActivity = new OwnerAllTripActivity();

    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    public  void vehicleUpcomingUpdatedTripList(List<UpcomingTrip>upcomingTrips){
        if (upcomingTrips !=null && upcomingTrips.size()>0){
            upcomingTripAdapterForVehicleOwner = new UpcomingTripAdapterForVehicleOwner(upcomingTrips,getContext());
            RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
            vehicleUpcomingRecycler.setLayoutManager(layoutManager);
            vehicleUpcomingRecycler.setItemAnimator(new DefaultItemAnimator());
            vehicleUpcomingRecycler.setAdapter(upcomingTripAdapterForVehicleOwner);

        }else{
            Toast.makeText(getActivity(), "No upcoming trip ", Toast.LENGTH_SHORT).show();
        }
    }

}
