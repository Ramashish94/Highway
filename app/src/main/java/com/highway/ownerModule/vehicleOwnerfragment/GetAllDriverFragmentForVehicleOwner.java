package com.highway.ownerModule.vehicleOwnerfragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.highway.R;
import com.highway.commonretrofit.RestClient;
import com.highway.ownerModule.vehicleOwnerActivities.AddDriverVehicleOwnerActivity;
import com.highway.ownerModule.vehicleOwnerAdapter.GetAllDriverAdapterForVehicleOwner;
import com.highway.ownerModule.vehileOwnerModelsClass.getAllDriver.Data;
import com.highway.ownerModule.vehileOwnerModelsClass.getAllDriver.DriverDetail;
import com.highway.ownerModule.vehileOwnerModelsClass.getAllDriver.GetAllDriverRequest;
import com.highway.ownerModule.vehileOwnerModelsClass.getAllDriver.GetAllDriverResponse;
import com.highway.utils.Constants;
import com.highway.utils.HighwayPrefs;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GetAllDriverFragmentForVehicleOwner extends Fragment {

    RecyclerView getAllDriverListRecyc;

    List<DriverDetail> driverDetails = new ArrayList<>();
    GetAllDriverAdapterForVehicleOwner getAllDriverAdapterForVehicleOwner;
    String userId;
    GetAllDriverResponse getAllDriverResponse;
    LinearLayout llDriver;
    Button btnSendDriverOtp;

    public GetAllDriverFragmentForVehicleOwner() {
        // Required empty public constructor
    }


    public static GetAllDriverFragmentForVehicleOwner newInstance() {
        GetAllDriverFragmentForVehicleOwner fragment = new GetAllDriverFragmentForVehicleOwner();
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
        View view = inflater.inflate(R.layout.fragment_get_all_driver_fragment_for_vehicle_owner, container, false);
        getAllDriverListRecyc = view.findViewById(R.id.getAllDriverListRecy);
        llDriver = view.findViewById(R.id.llDriver);
        btnSendDriverOtp = view.findViewById(R.id.btnSendDriverOtp);
        // Adapter yhi  initialize krna tha kyunki starting me driverDetails data nhi hoga aur     recyclerOperation();  me check ga hai ki size 0 se greater ho tbhi set kre  iski vjh se hi jb api response ata hai to adapter null mil jata hai
       // getAllDriverAdapterForVehicleOwner = new GetAllDriverAdapterForVehicleOwner(driverDetails,getContext());
        llDriver.setVisibility(View.GONE);
        getAllDriverList();
        btnSendDriverOtp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent ii = new Intent(getActivity(), AddDriverVehicleOwnerActivity.class);
                startActivity(ii);
            }
        });

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        recyclerOperation();

    }


    public void recyclerOperation() {
        //if (driverDetails !=null && driverDetails.size()>0){
        getAllDriverAdapterForVehicleOwner = new GetAllDriverAdapterForVehicleOwner(driverDetails, getContext());
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        getAllDriverListRecyc.setLayoutManager(layoutManager);
        getAllDriverListRecyc.setItemAnimator(new DefaultItemAnimator());
        getAllDriverListRecyc.setAdapter(getAllDriverAdapterForVehicleOwner);

        /*}else{
            Toast.makeText(getActivity(), "Some thing is wrong", Toast.LENGTH_SHORT).show();*//*
        }*/

    }

    public void getAllDriverList() {

        GetAllDriverRequest getAllDriverRequest = new GetAllDriverRequest();
        userId = HighwayPrefs.getString(getActivity(), Constants.ID);
        getAllDriverRequest.setOwnerId(userId);

        RestClient.getAllDriverDetails(getAllDriverRequest, new Callback<GetAllDriverResponse>() {
            @Override
            public void onResponse(Call<GetAllDriverResponse> call, Response<GetAllDriverResponse> response) {
                if (response.body() != null) {
                    if (response.body().getStatus()) {

                        getAllDriverResponse = response.body();
                        Data data = getAllDriverResponse.getData();
                        driverDetails = data.getDriverDetails();

                        if (driverDetails.size() > 0) {
                            llDriver.setVisibility(View.GONE);
                            getAllDriverListRecyc.setVisibility(View.VISIBLE);
                            if (getAllDriverAdapterForVehicleOwner != null) {
                                getAllDriverAdapterForVehicleOwner.setData(driverDetails);
                                getAllDriverAdapterForVehicleOwner.notifyDataSetChanged();
                            }
                        } else {
                            getAllDriverListRecyc.setVisibility(View.GONE);
                            llDriver.setVisibility(View.GONE);
                            Toast.makeText(getActivity(), "No Driver is added ! pls add Driver", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<GetAllDriverResponse> call, Throwable t) {
                Toast.makeText(getActivity(), "Response Failed", Toast.LENGTH_SHORT).show();

            }
        });

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

    }

    @Override
    public void onDetach() {
        super.onDetach();
    }


}
