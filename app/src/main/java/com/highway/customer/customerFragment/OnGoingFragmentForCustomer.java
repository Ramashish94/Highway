//package com.highway.customer.customerFragment;
//
//import android.content.Context;
//import android.content.Intent;
//import android.os.Bundle;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.Toast;
//
//import androidx.fragment.app.Fragment;
//import androidx.recyclerview.widget.DefaultItemAnimator;
//import androidx.recyclerview.widget.LinearLayoutManager;
//import androidx.recyclerview.widget.RecyclerView;
//
//import com.highway.R;
//import com.highway.common.base.activity.DashBoardActivity;
//import com.highway.common.base.commonModel.customerDiverOwnerModelsClass.allHighwayTripModel.userTrip.OngoingTrip;
//import com.highway.customer.customerActivity.BookingConformedActivity;
//import com.highway.customer.customerActivity.CustomerAllTripsActivity;
//import com.highway.customer.customerActivity.TripTrackingByCustomerActivity;
//import com.highway.customer.customerAdapter.OnGoingTripAdapterForCustomer;
//
//import java.util.ArrayList;
//import java.util.List;
//
//import static android.content.Intent.FLAG_ACTIVITY_NEW_TASK;
//
//
//public class OnGoingFragmentForCustomer extends Fragment {
//    private RecyclerView onGoingRecyclerForCustomer;
//  //  DashBoardActivity dashBoardActivity;
//    CustomerAllTripsActivity customerAllTripsActivity;
//    OnGoingTripAdapterForCustomer onGoingTripAdapterForCustomer;
//    List<OngoingTrip>ongoingTrips = new ArrayList<>();
//
//    public OnGoingFragmentForCustomer() {
//        // Required empty public constructor
//    }
//
//
//    public static OnGoingFragmentForCustomer newInstance() {
//        OnGoingFragmentForCustomer fragment = new OnGoingFragmentForCustomer();
//        Bundle args = new Bundle();
//        fragment.setArguments(args);
//        return fragment;
//    }
//
//    @Override
//    public void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        if (getArguments() != null) {
//
//        }
//    }
//
//    @Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
//        // Inflate the layout for this fragment
//        View view = inflater.inflate(R.layout.fragment_customer_on_going, container, false);
//        onGoingRecyclerForCustomer = view.findViewById(R.id.onGoingRecyclerForCustomer);
//        TanOnGoingTrip();
//        return view;
//    }
//
//    private void TanOnGoingTrip() {
//        if (ongoingTrips != null && ongoingTrips.size() > 0) {
//            if (onGoingTripAdapterForCustomer != null) {
//                onGoingTripAdapterForCustomer.setOngoingBookTripInterface(new OnGoingTripAdapterForCustomer.OnGoingBookTripInterface() {
//                    @Override
//                    public void onGoingBookTrip(String sourceLat, String sourceLong, String destinationLat, String destinationLong,
//                                                String name, String role, String vehicleName, String vehicleNumber, String fare,
//                                                String status, String tripType, String startDate, String endDate, String pickupTime,
//                                                String dropTime) {
//
//
//                        Intent intent = new Intent(getActivity(), TripTrackingByCustomerActivity.class);
//                        intent.addFlags(FLAG_ACTIVITY_NEW_TASK);
//                        intent.putExtra("sourceLat", sourceLat);
//                        intent.putExtra("sourceLong", sourceLong);
//                        intent.putExtra("destinationLat", destinationLat);
//                        intent.putExtra("destinationLong", destinationLong);
//                        intent.putExtra("name", name);
//                        intent.putExtra("role", role);
//                        intent.putExtra("vehicleName", vehicleName);
//                        intent.putExtra("vehicleNumber", vehicleNumber);
//                        intent.putExtra("fare", fare);
//                        intent.putExtra("status", status);
//                        intent.putExtra("tripType", tripType);
//                        intent.putExtra("startDate", startDate);
//                        intent.putExtra("endDate", endDate);
//                        intent.putExtra("pickupTime", pickupTime);
//                        intent.putExtra("dropTime", dropTime);
//
//                        getActivity().startActivity(intent);
//
//                    }
//                });
//            }
//        }
//    }
//
//    public void replaceFragment(Fragment fragment, String tag) {
//        try {
//            androidx.fragment.app.FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
//            if (fragmentManager != null) {
//                androidx.fragment.app.FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
//                fragmentTransaction.replace(R.id.nav_host_fragment, fragment, tag);
//                fragmentTransaction.setTransition(androidx.fragment.app.FragmentTransaction.TRANSIT_FRAGMENT_FADE);
//                fragmentTransaction.commitAllowingStateLoss();
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//
//    @Override
//    public void onAttach(Context context) {
//        super.onAttach(context);
//       // dashBoardActivity = (DashBoardActivity) getActivity();
//        customerAllTripsActivity = (CustomerAllTripsActivity) getActivity();
//    }
//
//    @Override
//    public void onDetach() {
//        super.onDetach();
//    }
//
//    public void ongoingUpdatedTripListForCustomer(List<OngoingTrip> ongoingTrips) {
//        // Log.d("OncreateView ", "cancelF");
//        if (ongoingTrips != null && ongoingTrips.size() > 0) {
//            onGoingTripAdapterForCustomer = new OnGoingTripAdapterForCustomer(ongoingTrips, getContext());
//            RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
//            onGoingRecyclerForCustomer.setLayoutManager(layoutManager);
//            onGoingRecyclerForCustomer.setItemAnimator(new DefaultItemAnimator());
//            onGoingRecyclerForCustomer.setAdapter(onGoingTripAdapterForCustomer);
//
//        } else {
//            Toast.makeText(customerAllTripsActivity, "No ongoing data for customer !", Toast.LENGTH_SHORT).show();
//
//        }
//    }
//}
