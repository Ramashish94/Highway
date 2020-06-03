package com.highway.customer.customerFragment;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;

import android.provider.ContactsContract;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.highway.R;
import com.highway.common.base.HighwayApplication;
import com.highway.commonretrofit.RestClient;
import com.highway.customer.customerActivity.BookingWithDetailsActivity;
import com.highway.customer.customerActivity.ConformBookingActivity;
import com.highway.customer.customerModelClass.updateReceiverModel.UpdateReceiverPhoneNoAndNameRequest;
import com.highway.customer.customerModelClass.updateReceiverModel.UpdateReceiverPhoneNoAndNameResponse;
import com.highway.utils.Constants;
import com.highway.utils.HighwayPrefs;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.app.Activity.RESULT_OK;

public class ReceiverBottomSheetFragment extends BottomSheetDialogFragment {

    public static final String TAG = "ActionBottomDialog";
    EditText edtTextMobNo, edtTextName;
    ImageView imageView;
    private static final int PICK_CONTACT = 1;
    private static final int RESULT_PICK_CONTACT = 1;
    String receiverPhoneNo = null;
    String receiverName;
    TextView updateReceiverNameTv;
    private String userId;
    LinearLayout receiverLayOut;
    private Object supportFragmentManager;
    ReceiverBottomSheetFragment receiverBottomSheetFragment;
    LinearLayout recLayout;

    public ReceiverBottomSheetFragment() {

    }

    public static ReceiverBottomSheetFragment newInstance() {
        ReceiverBottomSheetFragment fragment = new ReceiverBottomSheetFragment();
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
        View view = inflater.inflate(R.layout.fragment_receiver_bottim_sheet, container, false);

        edtTextMobNo = view.findViewById(R.id.mobileNumber);
        edtTextName = view.findViewById(R.id.user_name);
        imageView = view.findViewById(R.id.phoneImg);
        updateReceiverNameTv = view.findViewById(R.id.UpdateTV);
        recLayout = view.findViewById(R.id.receiverLayOut);


        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK, ContactsContract.CommonDataKinds.Phone.CONTENT_URI);
                startActivityForResult(intent, PICK_CONTACT);
            }
        });

        updateReceiverNameTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateReceiverName();
            }
        });


        return view;
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.i(TAG, "onActivityResult()");
        if (requestCode == PICK_CONTACT) {
            switch (resultCode) {
                case RESULT_OK:
                    contactPicked(data);
            }

        } else {
            Toast.makeText(getActivity(), "Failed", Toast.LENGTH_SHORT).show();
        }


    }


    public void contactPicked(Intent data) {
        Cursor cursor = null;
        try {
            Uri contactsData = data.getData();

            cursor = getContext().getContentResolver().query(contactsData, null, null, null, null);
            cursor.moveToFirst();

            int phoneIndex = cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER);
            String contactId = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts._ID));
            String name=cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
            String hasPhone = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER));



            //Log.i(TAG, "Contacts Phone Number: " + cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER)));
            receiverPhoneNo = cursor.getString(phoneIndex);

            edtTextMobNo.setText(receiverPhoneNo.trim());

            receiverName = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
            edtTextName.setText(receiverName);

        } catch (Exception e) {
            e.printStackTrace();

        }
    }

    public boolean inputValidationReceiverDetial() {
        boolean check = true;

        receiverPhoneNo = edtTextMobNo.getText().toString().trim();
        receiverName = edtTextName.getText().toString().trim();

        if (receiverPhoneNo.isEmpty()/*&& edtTextMobNo.length()>=10 || edtTextMobNo.length()<=13*/) {
            edtTextMobNo.setError("Enter a valid mobile no");
            return false;
        } else {
            edtTextMobNo.setError(null);
            check = true;
        }

        if (receiverName.isEmpty()) {
            edtTextName.setError("Enter a receiver name");
            check = false;
        } else {
            edtTextName.setError(null);
            check = true;
        }

        return check;
    }


    public void updateReceiverName() {
        if (inputValidationReceiverDetial()) {

            UpdateReceiverPhoneNoAndNameRequest updateReceiverPhoneNoAndNameRequest = new UpdateReceiverPhoneNoAndNameRequest();
            updateReceiverPhoneNoAndNameRequest.setReceiverMobile(receiverPhoneNo);
            updateReceiverPhoneNoAndNameRequest.setReceiverName(receiverName);

            userId = HighwayPrefs.getString(getActivity(), Constants.ID);
            updateReceiverPhoneNoAndNameRequest.setUserId(userId);

            HighwayPrefs.putString(getActivity(),Constants.RECEIVERNAME,receiverName);
            HighwayPrefs.putString(getActivity(),Constants.RECEIVERPHONENO,receiverPhoneNo);

            RestClient.updateReceiverNameOrNumber(updateReceiverPhoneNoAndNameRequest, new Callback<UpdateReceiverPhoneNoAndNameResponse>() {
                @Override
                public void onResponse(Call<UpdateReceiverPhoneNoAndNameResponse> call, Response<UpdateReceiverPhoneNoAndNameResponse> response) {

                    if (response.body() != null) {
                        if (response.body().getStatus()) {

                            HighwayApplication.getInstance().getBookingHTripRequest().setTripRecevirId(""+response.body().getId());
                            ConformBookingActivity.start((BookingWithDetailsActivity) getActivity());
                            getActivity().finish();

                        }
                    }
                }

                @Override
                public void onFailure(Call<UpdateReceiverPhoneNoAndNameResponse> call, Throwable t) {
                    Toast.makeText(getActivity(), "Failed update receiver mobile and name", Toast.LENGTH_SHORT).show();
                }
            });
        }
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
