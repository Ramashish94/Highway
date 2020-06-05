package com.highway.ownerModule.vehicleOwnerfragment;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.highway.R;
import com.highway.commonretrofit.RestClient;
import com.highway.ownerModule.ownerrrModel.cityResp.CityResp;
import com.highway.ownerModule.ownerrrModel.stateResp.StateResp;
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


public class AddDriverFragmentForVehicleOwner extends Fragment {
    RecyclerView addDriverRecyView;
    private Toolbar addDriverToolbar;
    public EditText edtTxtDriverName, edtTxtDriverMobNo, edtTxtDriverEmail,
            edtTxtDriverAddress, edtTxtDlNumber, edtTxtDlExpireDate;
    public ImageView imgDatePicker;
    public Button btnAddNewDriver;
    String driverName, driverEmail, driverMobNo, driverDlNo, dlExpireDate, driverAddress ,user_Id;
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
    String cityIDd = "";
    

    public AddDriverFragmentForVehicleOwner() {
        // Required empty public constructor
    }

    public static AddDriverFragmentForVehicleOwner newInstance() {
        AddDriverFragmentForVehicleOwner fragment = new AddDriverFragmentForVehicleOwner();
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
        View view = inflater.inflate(R.layout.fragment_add_new_driver, container, false);

        edtTxtDriverName = view.findViewById(R.id.EdtTxtDriverName);
        edtTxtDriverMobNo = view.findViewById(R.id.EdtTxtDriverMobNo);
        edtTxtDriverEmail = view.findViewById(R.id.EdtTxtDriverEmailNos);
        edtTxtDlNumber = view.findViewById(R.id.EdtTxtDlNumber);
        edtTxtDlExpireDate = view.findViewById(R.id.EdtTxtDlExpireDate);
        imgDatePicker = view.findViewById(R.id.ImgDatePicker);
       // vehicleSpinners = view.findViewById(R.id.VehicleSpinner);
        edtTxtDriverAddress = view.findViewById(R.id.EdtTxtEnterDriverAdd);
        btnAddNewDriver = view.findViewById(R.id.BtnAddNewDriver);
        spState = view.findViewById(R.id.spState);
        spCity = view.findViewById(R.id.spCity);
        onStateDropdown();

        onSpinner();


      //  vehicleSpinnersList();
        clickListener();


        view.setFocusableInTouchMode(true);
        view.requestFocus();
        view.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                Log.i("asds", "keyCode: " + keyCode);
                if( keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_UP) {
                    Log.i("Dsdsdds", "onKey Back listener is working!!!");
                    getFragmentManager().popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
                    return true;
                }
                return false;
            }
        });



        return view;
    }
    private void onSpinner(){


        spState.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                stateId = stateID.get(position);
             //   onDistric();
                if (stateId.equalsIgnoreCase("125254555")){
                    onCityDropdown(stateId);

                }else {
                    onCityDropdown(stateId );
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
              cityIDd = cityID.get(position);
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

                datePickerDialog = new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {
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
            Toast.makeText(getActivity(), "Data is Valid ", Toast.LENGTH_SHORT).show();

            if (TextUtils.isEmpty(cityIDd) || cityIDd.equalsIgnoreCase("125")){
                Toast.makeText(getActivity(),"Select the City",Toast.LENGTH_SHORT).show();
                return;
            }else if (TextUtils.isEmpty(stateId)  ||  stateId.equalsIgnoreCase("125254555")){

                Toast.makeText(getActivity(),"Select the state",Toast.LENGTH_SHORT).show();
                return;
            }
            Toast.makeText(getActivity(), "City state valid ", Toast.LENGTH_SHORT).show();


            AddNewDriverRequest addNewDriverRequest = new AddNewDriverRequest();
            addNewDriverRequest.setDriverName(driverName);
            addNewDriverRequest.setDriverMobile(driverMobNo);
            addNewDriverRequest.setDriverEmail(driverEmail);
            addNewDriverRequest.setDriverDLNo(driverDlNo);
            addNewDriverRequest.setDlexpiryDate(dlExpireDate);
            addNewDriverRequest.setDriverAddress(driverAddress);
          //  addNewDriverRequest.setVehicleId(vehicleId);
            userId = HighwayPrefs.getString(getActivity(),Constants.ID);
            addNewDriverRequest.setOwnerId(userId);
            addNewDriverRequest.setCityId(cityIDd);
            addNewDriverRequest.setStateId(stateId);

            Utils.showProgressDialog(getActivity());
            Toast.makeText(getActivity(), "Going to hit Api ", Toast.LENGTH_SHORT).show();

            RestClient.addNewDriver(addNewDriverRequest, new Callback<AddNewDriverResponse>() {
                @Override
                public void onResponse(Call<AddNewDriverResponse> call, Response<AddNewDriverResponse> response) {
                    Utils.dismissProgressDialog();
                    Toast.makeText(getActivity(), "Add new Driver response ==="+response.body().getStatus(), Toast.LENGTH_SHORT).show();

                    if (response.body() != null) {
                        if (response.body().getStatus()) {
                            Gson gson = new GsonBuilder().setPrettyPrinting().create();
                            Log.e("response  :: ", gson.toJson(response.body()));

                            // if (response.body().getId()) {
                          /*  dashBoardFragmentForVehicleOwner = DashBoardFragmentForVehicleOwner.newInstance();
                            Bundle bundle = new Bundle();
                            dashBoardFragmentForVehicleOwner.setArguments(bundle);
                            ((DashBoardActivity) getActivity()).replaceFragment(dashBoardFragmentForVehicleOwner, " ");
                                //Intent intent = new Intent(getActivity(), DashBoardActivity.class);
                                //startActivity(intent);
                              //  getActivity().finish();*/

                            edtTxtDriverName.setText("");
                            edtTxtDriverMobNo.setText("");
                            edtTxtDriverEmail.setText("");
                            edtTxtDriverAddress.setText("");
                            edtTxtDlNumber.setText("");
                            edtTxtDlExpireDate.setText("");



                                Toast.makeText(getActivity(), response.body().getMessage(), Toast.LENGTH_SHORT).show();
                            //getFragmentManager().popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
                            //}
                        }else {
                            Toast.makeText(getActivity(), response.body().getMessage(), Toast.LENGTH_SHORT).show();

                        }
                    }
                }

                @Override
                public void onFailure(Call<AddNewDriverResponse> call, Throwable t) {
                    Toast.makeText(getActivity(), "Add new Driver Failed "+t.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        }
    }



    public void onStateDropdown() {


            Utils.showProgressDialog(getActivity());

            RestClient.onState( new Callback<StateResp>() {
                @Override
                public void onResponse(Call<StateResp> call, Response<StateResp> response) {
                    Utils.dismissProgressDialog();

                   try {


                    if (response.body() != null) {
                        if (response.body().getStatus()) {
                            // if (response.body().getId()) {

                            state.add("Select State");
                            stateID.add("125254555");

                            for (int i = 0; i <response.body().getStateDropdown().size(); i++) {

                                state.add(response.body().getStateDropdown().get(i).getStateName());
                                stateID.add(response.body().getStateDropdown().get(i).getStateId());
                            }

                            ArrayAdapter<String> dataAdapter12 = new ArrayAdapter<String>(getActivity(), R.layout.simple_spinne_state, state);

                            // Drop down layout style - list view with radio button
                            dataAdapter12.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

                            // attaching data adapter to spinner
                            spState.setAdapter(dataAdapter12);

                            //}
                        }
                    }
                   }catch (Exception e){
                       e.printStackTrace();
                   }
                }

                @Override
                public void onFailure(Call<StateResp> call, Throwable t) {
                    Toast.makeText(getActivity(), "state resp Failed ", Toast.LENGTH_SHORT).show();
                }
            });

    }

    public void onCityDropdown(String stateId) {

         Log.e("stateId","::"+stateId);

        Utils.showProgressDialog(getActivity());
        city.clear();
        city.add("Select City");
        cityID.add("125");
        ArrayAdapter<String> dataAdapter12 = new ArrayAdapter<String>(getActivity(), R.layout.simple_spinne_city, city);

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
                        city.add("Select City");
                        cityID.add("125");

                        for (int i = 0; i <response.body().getCityDropdown().size(); i++) {

                            city.add(response.body().getCityDropdown().get(i).getCityName());
                            cityID.add(response.body().getCityDropdown().get(i).getCityId());
                        }

                        ArrayAdapter<String> dataAdapter12 = new ArrayAdapter<String>(getActivity(), R.layout.simple_spinne_city, city);

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
                Toast.makeText(getActivity(), "city resp Failed ", Toast.LENGTH_SHORT).show();
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
