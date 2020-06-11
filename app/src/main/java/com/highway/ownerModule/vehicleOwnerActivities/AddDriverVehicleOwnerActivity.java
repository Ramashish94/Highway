package com.highway.ownerModule.vehicleOwnerActivities;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.highway.R;
import com.highway.common.base.activity.DashBoardActivity;
import com.highway.commonretrofit.RestClient;
import com.highway.ownerModule.ownerrrModel.cityResp.CityResp;
import com.highway.ownerModule.ownerrrModel.stateResp.StateResp;
import com.highway.ownerModule.vehicleOwnerfragment.DashBoardFragmentForVehicleOwner;
import com.highway.ownerModule.vehileOwnerModelsClass.addNewDriverThroughVehicleOwner.AddNewDriverRequest;
import com.highway.ownerModule.vehileOwnerModelsClass.addNewDriverThroughVehicleOwner.AddNewDriverResponse;
import com.highway.utils.Constants;
import com.highway.utils.HighwayPrefs;
import com.highway.utils.Utils;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddDriverVehicleOwnerActivity extends AppCompatActivity {

    RecyclerView addDriverRecyView;
    private Toolbar addDriverToolbar;
    public EditText edtTxtDriverName, edtTxtDriverMobNo, edtTxtDriverEmail,
            edtTxtDriverAddress, edtTxtDlNumber;
    TextView edtTxtDlExpireDate;
    public ImageView imgDatePicker;
    public Button btnAddNewDriver;
    String driverName, driverEmail, driverMobNo, driverDlNo, dlExpireDate, driverAddress, user_Id;
    private DatePickerDialog datePickerDialog;
    private Spinner vehicleSpinners;
    private String vehicleId;
    List<String> vehicleNames;
    private String userId;
    private DashBoardFragmentForVehicleOwner dashBoardFragmentForVehicleOwner;


    ArrayList<String> state = new ArrayList<>();
    ArrayList<String> stateID = new ArrayList<>();

    ArrayList<String> city = new ArrayList<>();
    ArrayList<String> cityID = new ArrayList<>();
    Spinner spState, spCity;
    String stateId = "";
    String cityIDdddd = "";
    DashBoardActivity dashBoardActivity;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_add_new_driver);

        edtTxtDriverName = findViewById(R.id.EdtTxtDriverName);
        edtTxtDriverMobNo = findViewById(R.id.EdtTxtDriverMobNo);
        edtTxtDriverEmail = findViewById(R.id.EdtTxtDriverEmailNos);
        edtTxtDlNumber = findViewById(R.id.EdtTxtDlNumber);
        edtTxtDlExpireDate = findViewById(R.id.EdtTxtDlExpireDate);
        imgDatePicker = findViewById(R.id.ImgDatePicker);
        // vehicleSpinners = view.findViewById(R.id.VehicleSpinner);
        edtTxtDriverAddress = findViewById(R.id.EdtTxtEnterDriverAdd);
        btnAddNewDriver = findViewById(R.id.BtnAddNewDriver);
        spState = findViewById(R.id.spState);
        spCity = findViewById(R.id.spCity);
        TextView toolbar_title = findViewById(R.id.toolbar_title);
        ImageView iv_back = findViewById(R.id.iv_back);
        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        toolbar_title.setText("Add Driver");
        onSpinner();
        onStateDropdown();

        //  vehicleSpinnersList();
        clickListener();


    }

    private void onSpinner() {


        spState.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                stateId = stateID.get(position);
                //   onDistric();
                if (stateId.equalsIgnoreCase("125254555")) {
                    onCityDropdown(stateId);

                } else {
                    onCityDropdown(stateId);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

                // sometimes you need nothing here
            }
        });
        spCity.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                cityIDdddd = cityID.get(position);
                /* onTown();*/
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

                // sometimes you need nothing here
            }
        });

    }

    public void clickListener() {

        btnAddNewDriver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                 ValidationAddDriver();

               /* if (getAllDriverFragmentForVehicleOwner == null) {
                    getAllDriverFragmentForVehicleOwner = GetAllDriverFragmentForVehicleOwner.newInstance();
                }
                replaceFragment(getAllDriverFragmentForVehicleOwner, "");*/

                //     getApplicationContext().getSupportFragmentManager().beginTransaction().replace(R.id.your_container_layout,ob).addToBackStack(null).commit();

            }
        });

        imgDatePicker.setOnClickListener(new View.OnClickListener() {
            private int mYear, mMonth, mDay;

            @Override
            public void onClick(View v) {
                final Calendar c = Calendar.getInstance();
                mYear = c.get(Calendar.YEAR);
                mMonth = c.get(Calendar.MONTH);
                mDay = c.get(Calendar.DAY_OF_MONTH);

                datePickerDialog = new DatePickerDialog(AddDriverVehicleOwnerActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        datePickerDialog.getDatePicker().setMinDate(c.getTimeInMillis());
                        //dobDate.setText(dayOfMonth + "/" + (monthOfYear + 1) + "/" + year);
                        edtTxtDlExpireDate.setText(year + "-" + (monthOfYear + 1) + "-" + dayOfMonth);
                    }
                }, mYear, mMonth, mDay);
                datePickerDialog.show();
            }
        });


    }


    public boolean inputValidation() {
        boolean check = true;

        driverName = edtTxtDriverName.getText().toString().trim();
        driverMobNo = edtTxtDriverMobNo.getText().toString().trim();
        driverEmail = edtTxtDriverEmail.getText().toString().trim();
        driverDlNo = edtTxtDlNumber.getText().toString().trim();
        dlExpireDate = edtTxtDlExpireDate.getText().toString().trim();
        driverAddress = edtTxtDriverAddress.getText().toString().trim();

        if (driverName.isEmpty() && edtTxtDriverName.length() != 3) {
            edtTxtDriverName.setError("pls enter name");
            check = false;
        } else {
            edtTxtDriverName.setError(null);
            check = true;
        }

        if (driverMobNo.isEmpty() && edtTxtDriverMobNo.length() != 10) {
            edtTxtDriverMobNo.setError("pls Enter valid mobile number");
            check = false;
        } else {
            edtTxtDriverMobNo.setError(null);
            check = true;
        }

        if (driverDlNo.isEmpty() && edtTxtDlNumber.length() != 16) {
            edtTxtDlNumber.setError("pls Enter valid dl number");
            check = false;
        } else {
            edtTxtDlNumber.setError(null);
            check = true;
        }

        if (dlExpireDate.isEmpty()) {
            edtTxtDlExpireDate.setError("pls Enter valid dl expiry date in format yy-mm-dd ");
            check = false;
        } else {
            edtTxtDlNumber.setError(null);
            check = true;
        }

       /* if (TextUtils.isEmpty(vehicleSpinners)) {
            Utils.displayToast(getContext(), "Please select state");
         check =false;
        }else{
        }*/

      /*  if (driverAddress.isEmpty()) {
            edtTxtDriverAddress.setError("pls Enter valid dl number");
            check = false;
        } else {
            edtTxtDriverAddress.setError(null);
            check = true;
        }*/
        return check;

    }


    public void ValidationAddDriver() {
        if (inputValidation()) {

            if (TextUtils.isEmpty(cityIDdddd) || cityIDdddd.equalsIgnoreCase("125142414")) {
                Toast.makeText(getApplicationContext(), "Select the City", Toast.LENGTH_SHORT).show();
                return;
            } else if (TextUtils.isEmpty(stateId) || stateId.equalsIgnoreCase("125254555")) {

                Toast.makeText(getApplicationContext(), "Select the state", Toast.LENGTH_SHORT).show();
                return;
            }


            AddNewDriverRequest addNewDriverRequest = new AddNewDriverRequest();
            addNewDriverRequest.setDriverName(driverName);
            addNewDriverRequest.setDriverMobile(driverMobNo);
            addNewDriverRequest.setDriverEmail(driverEmail);
            addNewDriverRequest.setDriverDLNo(driverDlNo);
            addNewDriverRequest.setDlexpiryDate(dlExpireDate);
            addNewDriverRequest.setDriverAddress(driverAddress);
            //  addNewDriverRequest.setVehicleId(vehicleId);
            userId = HighwayPrefs.getString(getApplicationContext(), Constants.ID);
            addNewDriverRequest.setOwnerId(userId);
            addNewDriverRequest.setCityId(cityIDdddd);
            addNewDriverRequest.setStateId(stateId);

            Utils.showProgressDialog(getApplicationContext());


            RestClient.addNewDriver(addNewDriverRequest, new Callback<AddNewDriverResponse>() {
                @Override
                public void onResponse(Call<AddNewDriverResponse> call, Response<AddNewDriverResponse> response) {
                    Utils.dismissProgressDialog();

                    if (response.body() != null) {
                        if (response.body().getStatus()) {
                            Gson gson = new GsonBuilder().setPrettyPrinting().create();
                            Log.e("response  :: ", gson.toJson(response.body()));

                            // if (response.body().getId()) {
                          /*  dashBoardFragmentForVehicleOwner = DashBoardFragmentForVehicleOwner.newInstance();
                            Bundle bundle = new Bundle();
                            dashBoardFragmentForVehicleOwner.setArguments(bundle);
                            ((DashBoardActivity) getApplicationContext()).replaceFragment(dashBoardFragmentForVehicleOwner, " ");
                                //Intent intent = new Intent(getApplicationContext(), DashBoardActivity.class);
                                //startActivity(intent);
                              //  getApplicationContext().finish();*/

                            edtTxtDriverName.setText("");
                            edtTxtDriverMobNo.setText("");
                            edtTxtDriverEmail.setText("");
                            edtTxtDriverAddress.setText("");
                            edtTxtDlNumber.setText("");
                            edtTxtDlExpireDate.setText("");


                            Toast.makeText(getApplicationContext(), response.body().getMessage(), Toast.LENGTH_SHORT).show();


                            finish();

                        } else {
                            Toast.makeText(getApplicationContext(), response.body().getMessage(), Toast.LENGTH_SHORT).show();

                        }
                    }
                }

                @Override
                public void onFailure(Call<AddNewDriverResponse> call, Throwable t) {
                    Toast.makeText(getApplicationContext(), "Add new Driver Failed " + t.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        }
    }


    public void onStateDropdown() {


        Utils.showProgressDialog(getApplicationContext());

        RestClient.onState(new Callback<StateResp>() {
            @Override
            public void onResponse(Call<StateResp> call, Response<StateResp> response) {
                Utils.dismissProgressDialog();

                try {


                    if (response.body() != null) {
                        if (response.body().getStatus()) {
                            // if (response.body().getId()) {
                            state.clear();
                            stateID.clear();
                            state.add("Select State");
                            stateID.add("125254555");

                            for (int i = 0; i < response.body().getStateDropdown().size(); i++) {

                                state.add(response.body().getStateDropdown().get(i).getStateName());
                                stateID.add(response.body().getStateDropdown().get(i).getStateId());
                            }

                            ArrayAdapter<String> dataAdapter12 = new ArrayAdapter<String>(getApplicationContext(), R.layout.simple_spinne_state, state);

                            // Drop down layout style - list view with radio button
                            dataAdapter12.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

                            // attaching data adapter to spinner
                            spState.setAdapter(dataAdapter12);

                            //}
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<StateResp> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "state resp Failed ", Toast.LENGTH_SHORT).show();
            }
        });

    }

    public void onCityDropdown(String stateId) {

        Log.e("stateId", "::" + stateId);

        Utils.showProgressDialog(getApplicationContext());
        city.clear();
        city.add("Select City");
        cityID.add("125142414");
        ArrayAdapter<String> dataAdapter12 = new ArrayAdapter<String>(getApplicationContext(), R.layout.simple_spinne_city, city);

        // Drop down layout style - list view with radio button
        dataAdapter12.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // attaching data adapter to spinner
        spCity.setAdapter(dataAdapter12);
        RestClient.onCity(stateId, new Callback<CityResp>() {
            @Override
            public void onResponse(Call<CityResp> call, Response<CityResp> response) {
                Utils.dismissProgressDialog();

                if (response.body() != null) {
                    if (response.body().getStatus()) {
                        // if (response.body().getId()) {
                        city.clear();
                        cityID.clear();
                        city.add("Select City");
                        cityID.add("125142414");

                        for (int i = 0; i < response.body().getCityDropdown().size(); i++) {

                            city.add(response.body().getCityDropdown().get(i).getCityName());
                            cityID.add(response.body().getCityDropdown().get(i).getCityId());
                        }

                        ArrayAdapter<String> dataAdapter12 = new ArrayAdapter<String>(getApplicationContext(), R.layout.simple_spinne_city, city);

                        // Drop down layout style - list view with radio button
                        dataAdapter12.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

                        // attaching data adapter to spinner
                        spCity.setAdapter(dataAdapter12);


                        //}
                    }
                }
            }

            @Override
            public void onFailure(Call<CityResp> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "city resp Failed ", Toast.LENGTH_SHORT).show();
            }
        });

    }

}
