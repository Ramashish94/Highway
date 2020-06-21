package com.highway.ownerModule.vehicleOwnerfragment;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.highway.R;
import com.highway.common.base.activity.DashBoardActivity;
import com.highway.commonretrofit.RestClient;
import com.highway.ownerModule.vehileOwnerModelsClass.assignD2V.assignD2V_Model.AssignD2VRequest;
import com.highway.ownerModule.vehileOwnerModelsClass.assignD2V.assignD2V_Model.AssignD2VResponse;
import com.highway.ownerModule.vehileOwnerModelsClass.assignD2V.driverAssignSpinner.DataModel;
import com.highway.ownerModule.vehileOwnerModelsClass.assignD2V.driverAssignSpinner.DriverDatum;
import com.highway.ownerModule.vehileOwnerModelsClass.assignD2V.driverAssignSpinner.DriverDropDownRequest;
import com.highway.ownerModule.vehileOwnerModelsClass.assignD2V.driverAssignSpinner.DriverDropDownResponse;
import com.highway.ownerModule.vehileOwnerModelsClass.assignD2V.vehicleAssignSpinner.VehicleAssignDropDowanRequest;
import com.highway.ownerModule.vehileOwnerModelsClass.assignD2V.vehicleAssignSpinner.VehicleAssignDropDowanResponse;
import com.highway.ownerModule.vehileOwnerModelsClass.assignD2V.vehicleAssignSpinner.VehicleData;
import com.highway.ownerModule.vehileOwnerModelsClass.assignD2V.vehicleAssignSpinner.VehicleDataName;
import com.highway.utils.Constants;
import com.highway.utils.HighwayPrefs;
import com.highway.utils.Utils;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Assign_D2V_FragmentForVehicleOwner extends Fragment {

    public Spinner driverSpinner;
    public Button btnAssignD2V;
    private String userId;
    private String ownerId;
    private String user_Id;
    DriverDropDownResponse driverDropDownResponse;
    List<String> vehicleNameWithNum;
    List<String> driverNames;
    private Spinner driversAssignSpinner;
    private Spinner vehiclesAssignSpinner;
    String driverId, vehicleId;
    VehicleAssignDropDowanResponse vehicleAssignDropDowanResponse;
    DashBoardActivity dashBoardActivity;
    private DashBoardFragmentForVehicleOwner dashBoardFragmentForVehicleOwner;

    public Assign_D2V_FragmentForVehicleOwner() {
        // Required empty public constructor
    }


    public static Assign_D2V_FragmentForVehicleOwner newInstance() {
        Assign_D2V_FragmentForVehicleOwner fragment = new Assign_D2V_FragmentForVehicleOwner();
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
        View view = inflater.inflate(R.layout.fragment_assign__d2_v__fragment_for_vehicle_owner, container, false);

        driversAssignSpinner = view.findViewById(R.id.DriversSpinner);
        vehiclesAssignSpinner = view.findViewById(R.id.vehicleTypeSpinner);
        btnAssignD2V = view.findViewById(R.id.BtnAssignD2V);

        getdriverListSpinner();
        getVehicleListSpinner();

        clickListener();

        return view;
    }

    public void clickListener() {

        driversAssignSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                if (driverDropDownResponse != null && driverDropDownResponse.getData() != null
                        && driverDropDownResponse.getData().getDriverData() != null
                        && driverDropDownResponse.getData().getDriverData().size() > 0) {
                    driverId = driverDropDownResponse.getData().getDriverData().get(position).getDriverId();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                Toast.makeText(getActivity(), "NO! Item  in this driver spinner", Toast.LENGTH_SHORT).show();

            }
        });


        vehiclesAssignSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                if (vehicleAssignDropDowanResponse != null && vehicleAssignDropDowanResponse.getVehicleData() != null
                        && vehicleAssignDropDowanResponse.getVehicleData().getVehicleDataName() != null
                        && vehicleAssignDropDowanResponse.getVehicleData().getVehicleDataName().size() > 0) {

                    vehicleId = vehicleAssignDropDowanResponse.getVehicleData().getVehicleDataName().get(position).getVehicleNameWithNumberId();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                Toast.makeText(getActivity(), "No! item in the vehicle spinner", Toast.LENGTH_SHORT).show();

            }
        });


        btnAssignD2V.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                assignD2V();
            }
        });
    }


    public void getdriverListSpinner() {

        DriverDropDownRequest driverDropDownRequest = new DriverDropDownRequest();
        user_Id = HighwayPrefs.getString(getActivity(), Constants.ID);
        Log.d("Id", user_Id);
        driverDropDownRequest.setUserId(user_Id);

        RestClient.getDriverList(driverDropDownRequest, new Callback<DriverDropDownResponse>() {
            @Override
            public void onResponse(Call<DriverDropDownResponse> call, Response<DriverDropDownResponse> response) {
                if (response.body() != null) {
                    if (response.body().getStatus()) {
                        driverDropDownResponse = response.body();
                        DataModel data = null;
                        Log.d("data", "" + response.body());
                        if (driverDropDownResponse.getData() != null) {
                            data = driverDropDownResponse.getData();
                            DriverDatum driverDatum = new DriverDatum();
                            driverDatum.setDriverName("--- Select Driver Name ---");
                            data.getDriverData().add(0, driverDatum);
                        } else {
                            data = new DataModel();
                            DriverDatum driverDatum = new DriverDatum();
                            driverDatum.setDriverName("--- Select Driver Name ---");
                            data.getDriverData().add(0, driverDatum);
                        }


                        if (data != null && data.getDriverData().size() > 0) {

                            driverNames = new ArrayList<>();

                            for (DriverDatum driverDatum1 : driverDropDownResponse.getData().getDriverData()) {
                                driverNames.add(driverDatum1.getDriverName());
                            }

                            ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, driverNames);
                            dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                            driversAssignSpinner.setAdapter(dataAdapter);
                        }


                    }
                }
            }

            @Override
            public void onFailure(Call<DriverDropDownResponse> call, Throwable t) {
                Toast.makeText(getContext(), "Failure", Toast.LENGTH_SHORT).show();
            }
        });

    }

    public void getVehicleListSpinner() {

        VehicleAssignDropDowanRequest vehicleAssignDropDowanRequest = new VehicleAssignDropDowanRequest();

        user_Id = HighwayPrefs.getString(getActivity(), Constants.ID);
        Log.d("Id", user_Id);
        vehicleAssignDropDowanRequest.setUserId(user_Id);

        RestClient.getVehicleAssignList(vehicleAssignDropDowanRequest, new Callback<VehicleAssignDropDowanResponse>() {
            @Override
            public void onResponse(Call<VehicleAssignDropDowanResponse> call, Response<VehicleAssignDropDowanResponse> response) {


                if (response.body() != null) {
                try {


                    if (response.body().getStatus()) {
                        vehicleAssignDropDowanResponse = response.body();
                        VehicleData vehicleData = null;
                        Log.d("data", "" + response.body());
                        if (vehicleAssignDropDowanResponse.getVehicleData() != null) {
                            vehicleData = vehicleAssignDropDowanResponse.getVehicleData();
                            if (vehicleData != null) {
                                VehicleDataName vehicleDataName = new VehicleDataName();
                                vehicleDataName.setVehicleNameWithNumber("--- Select Vehicle Name ---");
                                vehicleData.getVehicleDataName().add(0, vehicleDataName);
                            }else{

                            }

                        }

                        if (vehicleData != null && vehicleData.getVehicleDataName().size() > 0) {

                            vehicleNameWithNum = new ArrayList<>();
                            for (VehicleDataName vehicleDataName1 : vehicleAssignDropDowanResponse.getVehicleData().getVehicleDataName()) {
                                vehicleNameWithNum.add(vehicleDataName1.getVehicleNameWithNumber());
                            }

                            ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, vehicleNameWithNum);
                            dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                            vehiclesAssignSpinner.setAdapter(dataAdapter);
                        }

                    }
                }catch (Exception e){
                    e.printStackTrace();
                }
                }
            }

            @Override
            public void onFailure(Call<VehicleAssignDropDowanResponse> call, Throwable t) {
                Toast.makeText(getActivity(), "Failure! No Vehicle Name found", Toast.LENGTH_SHORT).show();

            }
        });

    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

    }

    public void assignD2V() {
        AssignD2VRequest assignD2VRequest = new AssignD2VRequest();
        assignD2VRequest.setDriverId(driverId);
        assignD2VRequest.setVehicleId(vehicleId);
        ownerId = HighwayPrefs.getString(getActivity(), Constants.ID);
        Log.d("Id", ownerId);
        assignD2VRequest.setOwnerId(ownerId);
        Utils.showProgressDialog(getContext());
        RestClient.assign_D2V(assignD2VRequest, new Callback<AssignD2VResponse>() {
            @Override
            public void onResponse(Call<AssignD2VResponse> call, Response<AssignD2VResponse> response) {
                Utils.dismissProgressDialog();
                if (response.body() != null) {
                    if (response.body().getStatus()) {

                       /* Intent intent = new Intent(getActivity(), DashBoardActivity.class);
                        startActivity(intent);*/
                        dashBoardFragmentForVehicleOwner = DashBoardFragmentForVehicleOwner.newInstance();
                        Bundle bundle = new Bundle();
                        dashBoardFragmentForVehicleOwner.setArguments(bundle);
                        ((DashBoardActivity) getActivity()).replaceFragment(dashBoardFragmentForVehicleOwner, " ");

                        Toast.makeText(getActivity(), "Assign Driver to  Vehicle   SuccessFully", Toast.LENGTH_SHORT).show();
                        // getActivity().finish();
                    }else {
                        Toast.makeText(getActivity(), response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<AssignD2VResponse> call, Throwable t) {
                Toast.makeText(getActivity(), "Response failed!  Assign D2V", Toast.LENGTH_SHORT).show();
            }
        });

    }


    @Override
    public void onDetach() {
        super.onDetach();
    }


}
