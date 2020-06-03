package com.highway.customer.customerFragment;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.highway.R;
import com.highway.common.base.HighwayApplication;
import com.highway.common.base.commonModel.customerDiverOwnerModelsClass.allHighwayTripModel.userRating.UpdateTripRatingByUserReq;
import com.highway.common.base.commonModel.customerDiverOwnerModelsClass.allHighwayTripModel.userRating.UpdateTripRatingByUserResp;
import com.highway.commonretrofit.RestClient;
import com.highway.utils.Constants;
import com.highway.utils.HighwayPrefs;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RatingBottomDialogFragmentForCustomer extends BottomSheetDialogFragment {

    public static final String TAG = "ActionBottomDialog";
    CircleImageView avatar;
    TextView providerName;
    RatingBar rating;
    EditText comment;
    String cmnt;
    Button submit;
    private OnFragmentInteractionListener mListener;
    private String userId;
    private String tripId;
    private DashBordFragmentForCustomer dashBordFragmentForCustomer;
    private String ratingBar;

    public RatingBottomDialogFragmentForCustomer() {
        // Required empty public constructor
    }


    public static RatingBottomDialogFragmentForCustomer newInstance() {
        RatingBottomDialogFragmentForCustomer fragment = new RatingBottomDialogFragmentForCustomer();

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_reating_bottom_dialog_fragment_for_customer, container, false);

        avatar = view.findViewById(R.id.avatar);
        providerName = view.findViewById(R.id.provider_name);
        rating = view.findViewById(R.id.rating);
        comment = view.findViewById(R.id.comment);
        submit = view.findViewById(R.id.submit);

        ratingAndCmnt();

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                afterCmpltRidCustomerStatus();
            }
        });

        return view;
    }

    public boolean ratingAndCmnt() {
        cmnt = comment.getText().toString().trim();

           /* if (cmmnt.isEmpty()) {
                comment.setError("pls enter comments");
                return false;
            } else {
                comment.setError(null);
            }

        return false;*/

        ratingBar = String.valueOf(rating.getRating());
        //  Toast.makeText(getActivity(), ratingBar, Toast.LENGTH_LONG).show();
        return true;
    }


    public void afterCmpltRidCustomerStatus() {

        ratingBar = String.valueOf(rating.getRating());
        cmnt = comment.getText().toString().trim();

        userId = HighwayPrefs.getString(getActivity(), Constants.ID);
      //  tripId = HighwayPrefs.getString(getActivity(), "BookingId");

        UpdateTripRatingByUserReq updateTripRatingByUserReq = new UpdateTripRatingByUserReq();
        updateTripRatingByUserReq.setRatingStatus("1");
        updateTripRatingByUserReq.setUserId(userId);
        updateTripRatingByUserReq.setRatingComment(cmnt);
        updateTripRatingByUserReq.setRatingRate(ratingBar);
        updateTripRatingByUserReq.setTripId(HighwayApplication.getInstance().getCurrentTripId());

        RestClient.getRatingUser(updateTripRatingByUserReq, new Callback<UpdateTripRatingByUserResp>() {
            @Override
            public void onResponse(Call<UpdateTripRatingByUserResp> call, Response<UpdateTripRatingByUserResp> response) {
                if (response != null && response.code() == 200 && response.body() != null) {
                    if (response.body().getStatus()) {
                        dismiss();
                    }else {
                        Toast.makeText(getActivity(), response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<UpdateTripRatingByUserResp> call, Throwable t) {
                Toast.makeText(getActivity(), "failure", Toast.LENGTH_SHORT).show();
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
        mListener = null;
    }

    public void show(String tag) {

    }

    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(Uri uri);
    }
}
