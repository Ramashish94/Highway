package com.highway.ownermodule.vehicleOwner.vehicleOwnerfragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.highway.R;
import com.highway.common.base.activity.DashBoardActivity;
import com.highway.commonretrofit.RestClient;
import com.highway.ownermodule.vehicleOwner.vehileOwnerModelsClass.AddNewVehicleModel.AddNewVehicleRequest;
import com.highway.ownermodule.vehicleOwner.vehileOwnerModelsClass.AddNewVehicleModel.AddNewVehicleResponse;
import com.highway.utils.Utils;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddNewVehicleFragment extends Fragment {

    RecyclerView addVehicleRecycView;
    Toolbar addVehicleToolbar;
    private EditText edtVehicleDescription, edtTxtVehicleNos, edtTxtvehicleModelNos, edtTxtvehicleName;
    private Button btnAddNewVehicle;
    private String vehicleName;
    private String vehicleModelNos;
    private String vehicleNos;
    private String vehicleDescription;
    String textEd,txtEnd,isReached;

    public AddNewVehicleFragment() {
        // Required empty public constructor
    }


    public static AddNewVehicleFragment newInstance() {
        AddNewVehicleFragment fragment = new AddNewVehicleFragment();
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
        View view = inflater.inflate(R.layout.fragment_add_new_vehicle, container, false);

        edtTxtvehicleName = view.findViewById(R.id.EdtTxtvehicleName);
        edtTxtvehicleModelNos = view.findViewById(R.id.EdtTxtvehicleModelNos);
        edtTxtVehicleNos = view.findViewById(R.id.EdtTxtVehicleNos);
        btnAddNewVehicle = view.findViewById(R.id.BtnAddNewVehicle);
        edtVehicleDescription = view.findViewById(R.id.EdtVehicleDescription);

        clickListener();

        return view;

    }

    public void clickListener(){
        btnAddNewVehicle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ValidationAddNewVehicle();
            }
        });

    }

    public boolean inputValidation() {
        boolean check = false;

        vehicleName = edtTxtvehicleName.getText().toString().trim();
        vehicleModelNos = edtTxtvehicleModelNos.getText().toString().trim();
        vehicleNos = edtTxtVehicleNos.getText().toString().trim();
        vehicleDescription = edtVehicleDescription.getText().toString().trim();

        if (vehicleName.isEmpty() && edtTxtvehicleName.length() != 3) {
            edtTxtvehicleName.setError("pls enter valid vehicle name.. max length 3");
            check = false;
        } else {
            edtTxtvehicleName.setError(null);
            check = true;
        }

        if (vehicleNos.isEmpty() && edtTxtVehicleNos.length() != 3) {
            edtTxtVehicleNos.setError("pls enter valid vehicle number.. max length 3");
            check = false;
        } else {
            edtTxtVehicleNos.setError(null);
            check = true;
        }

        if (vehicleModelNos.isEmpty() && edtTxtVehicleNos.length() != 3) {
            edtTxtvehicleModelNos.setError("pls enter valid model number.. max length 3");
            check = false;
        } else {
            edtTxtvehicleModelNos.setError(null);
            check = true;
        }

        if (vehicleDescription.isEmpty() && edtVehicleDescription.length() != 10) {
            edtVehicleDescription.setError("pls enter valid vehicle name max length 3");
            check = false;
        } else {
            edtVehicleDescription.setError(null);
            check = true;
        }

        return check;
    }

    public void ValidationAddNewVehicle() {

        if (inputValidation()) {
            AddNewVehicleRequest addNewVehicleRequest = new AddNewVehicleRequest();
            addNewVehicleRequest.setVehicleName(vehicleName);
            addNewVehicleRequest.setVehicleNumber(vehicleNos);
            addNewVehicleRequest.setVehicleModelNo(vehicleModelNos);
            addNewVehicleRequest.setVehicleDescription(vehicleDescription);
            addNewVehicleRequest.setOwnerId("19");

            Utils.showProgressDialog(getActivity());
            RestClient.addNewVehicle(addNewVehicleRequest, new Callback<AddNewVehicleResponse>() {
                @Override
                public void onResponse(Call<AddNewVehicleResponse> call, Response<AddNewVehicleResponse> response) {
                    Utils.dismissProgressDialog();
                    if (response.body() != null) {
                        if (response.body().getStatus()) {
                            Intent intent = new Intent(getActivity(), DashBoardActivity.class);
                            startActivity(intent);
                            getActivity().finish();
                            Toast.makeText(getActivity(), "New vehicle added Successfully", Toast.LENGTH_SHORT).show();
                        }
                    }
                }

                @Override
                public void onFailure(Call<AddNewVehicleResponse> call, Throwable t) {
                    Toast.makeText(getActivity(), "Faield Add Vehicle", Toast.LENGTH_SHORT).show();
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


}
