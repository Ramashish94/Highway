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
import com.highway.common.base.commonModel.customerDiverOwnerModelsClass.allHighwayTripModel.userTrip.OngoingTrip;
import com.highway.ownerModule.vehicleOwnerActivities.OwnerAllTripActivity;
import com.highway.ownerModule.vehicleOwnerAdapter.OnGoingTripAdapterForVehicleOwner;

import java.util.ArrayList;
import java.util.List;


public class OnGoingFragmentForVehicleOwner extends Fragment {
    List<OngoingTrip> ongoingTrips = new ArrayList<>();
    RecyclerView vehicleOngoingRecycler;
    //DashBoardActivity dashBoardActivity;
     OwnerAllTripActivity ownerAllTripActivity;
    Context context;
    OnGoingTripAdapterForVehicleOwner onGoingTripAdapterForVehicleOwner;




    public OnGoingFragmentForVehicleOwner() { }


    public static OnGoingFragmentForVehicleOwner newInstance() {
        OnGoingFragmentForVehicleOwner fragment = new OnGoingFragmentForVehicleOwner();
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
        View view =  inflater.inflate(R.layout.fragment_vehicle_on_going, container, false);
        vehicleOngoingRecycler  = view.findViewById(R.id.vehicleOnGoiingRecy);
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


      public void vehicleOnGoingUpdateList(List<OngoingTrip>ongoingTrips){
        if (ongoingTrips!=null && ongoingTrips.size()>0){
            onGoingTripAdapterForVehicleOwner = new OnGoingTripAdapterForVehicleOwner(ongoingTrips,getActivity());
            RecyclerView.LayoutManager layoutManager  = new LinearLayoutManager(getContext());
            vehicleOngoingRecycler.setLayoutManager(layoutManager);
            vehicleOngoingRecycler.setItemAnimator(new DefaultItemAnimator());
            vehicleOngoingRecycler.setAdapter(onGoingTripAdapterForVehicleOwner);
        }else {
            Toast.makeText(getActivity(), "no any ongoing trip ", Toast.LENGTH_SHORT).show();
        }
      }


}
