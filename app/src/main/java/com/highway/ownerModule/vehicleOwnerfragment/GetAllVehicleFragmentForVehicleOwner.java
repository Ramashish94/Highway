package com.highway.ownerModule.vehicleOwnerfragment;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.highway.R;
import com.highway.common.base.activity.DashBoardActivity;
import com.highway.commonretrofit.RestClient;
import com.highway.ownerModule.ownerRequest.vehicleOnOff.VehicleOnOffReq;
import com.highway.ownerModule.ownerrrModel.VehicleOnOffResp.VehicleOnOffResponse;
import com.highway.ownerModule.vehicleOwnerAdapter.GetAllVehicleAdapterForVehicleOwner;
import com.highway.ownerModule.vehileOwnerModelsClass.getAllVehicle.DataVehicle;
import com.highway.ownerModule.vehileOwnerModelsClass.getAllVehicle.GetAllVehicleRequest;
import com.highway.ownerModule.vehileOwnerModelsClass.getAllVehicle.GetAllVehicleResponse;
import com.highway.ownerModule.vehileOwnerModelsClass.getAllVehicle.VehicleDetail;
import com.highway.utils.Constants;
import com.highway.utils.HighwayPrefs;
import com.highway.utils.Utils;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GetAllVehicleFragmentForVehicleOwner extends Fragment {
    Toolbar getAllVehicleToolbar;
    RecyclerView getAllVehicleRecyclerView;

    ArrayList<VehicleDetail> vehicleDetails = new ArrayList<>();
    GetAllVehicleFragmentForVehicleOwner getAllVehicleFragmentForVehicleOwner;
    GetAllVehicleAdapterForVehicleOwner getAllVehicleAdapterForVehicleOwner;
    String userId;
    GetAllVehicleResponse getAllVehicleResponse;

    public GetAllVehicleFragmentForVehicleOwner() {
    }


    public static GetAllVehicleFragmentForVehicleOwner newInstance() {
        GetAllVehicleFragmentForVehicleOwner fragment = new GetAllVehicleFragmentForVehicleOwner();
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
        View view = inflater.inflate(R.layout.fragment_get_all_vehicle_fragment_for_vehicle_owner, container, false);
        getAllVehicleRecyclerView = view.findViewById(R.id.GetAllVehicleRecyclerView);
        ((DashBoardActivity)getActivity()).setOnAllvehicalList();
        getAllVehicle();

        return view;
    }




    @Override
    public void onResume() {
        super.onResume();
        getAllVehicle();
        recyclerOperation();
    }

    public void recyclerOperation() {

        getAllVehicleAdapterForVehicleOwner = new GetAllVehicleAdapterForVehicleOwner(vehicleDetails, getContext(),GetAllVehicleFragmentForVehicleOwner.this);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        getAllVehicleRecyclerView.setLayoutManager(layoutManager);
        getAllVehicleRecyclerView.setItemAnimator(new DefaultItemAnimator());
        getAllVehicleRecyclerView.setAdapter(getAllVehicleAdapterForVehicleOwner);
    }

    public void getAllVehicle() {
        GetAllVehicleRequest getAllVehicleRequest = new GetAllVehicleRequest();
        userId = HighwayPrefs.getString(getContext(), Constants.ID);
        Log.d("UserId", "" + userId);
        getAllVehicleRequest.setOwnerId(userId);

        if (Utils.isInternetConnected(getActivity())) {
            Utils.showProgressDialog(getActivity());
            RestClient.getAllVehicleDetails(getAllVehicleRequest, new Callback<GetAllVehicleResponse>() {
                @Override
                public void onResponse(Call<GetAllVehicleResponse> call, Response<GetAllVehicleResponse> response) {
                    Utils.dismissProgressDialog();
                    if (response.body() != null) {
                        if (response.body().getStatus()) {

                            getAllVehicleResponse = response.body();
                            DataVehicle data = getAllVehicleResponse.getData();

                            vehicleDetails = data.getVehicleDetails();

                            if (vehicleDetails.size() > 0) {
                                if (getAllVehicleAdapterForVehicleOwner != null) {
                                    getAllVehicleAdapterForVehicleOwner.setData(vehicleDetails);
                                    getAllVehicleAdapterForVehicleOwner.notifyDataSetChanged();
                                }
                            } else {

                                Toast.makeText(getActivity(), "No vehicle details is added! pls add Vehicle details", Toast.LENGTH_SHORT).show();

                            }
                        }else {

                        }
                    }
                }

                @Override
                public void onFailure(Call<GetAllVehicleResponse> call, Throwable t) {
                    Toast.makeText(getActivity(), "Something went wrong", Toast.LENGTH_SHORT).show();
                }
            });
        }

    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

    }

    @Override
    public void onDetach() {
        super.onDetach();
    }


    public void getonlineoffline(String UserId,String VehicleId,String VehicleOnOff){


        VehicleOnOffReq vehicleOnOffReq = new VehicleOnOffReq();
            userId = HighwayPrefs.getString(getContext(), Constants.ID);
            Log.d("vehicleOnOffReq", "" + userId);
        vehicleOnOffReq.setUserId(UserId);
        vehicleOnOffReq.setVehicleId(VehicleId);
        vehicleOnOffReq.setVehicleOnOff(VehicleOnOff);

            if (Utils.isInternetConnected(getActivity())) {
                Utils.showProgressDialog(getActivity());
                RestClient.getvehicleOnOff(vehicleOnOffReq, new Callback<VehicleOnOffResponse>() {
                    @Override
                    public void onResponse(Call<VehicleOnOffResponse> call, Response<VehicleOnOffResponse> response) {
                        Utils.dismissProgressDialog();
                        if (response.body() != null) {
                            if (response.body().getStatus()) {
                                vehicleDetails.clear();
                              //  Toast.makeText(getActivity(), response.body().getMessage(), Toast.LENGTH_SHORT).show();
                                getAllVehicle();
                            }else {

                                Toast.makeText(getActivity(), response.body().getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<VehicleOnOffResponse> call, Throwable t) {
                        Toast.makeText(getActivity(), "Something went wrong", Toast.LENGTH_SHORT).show();
                    }
                });
            }



    }
}
