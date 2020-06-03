package com.highway.customer.customerActivity;

import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.highway.R;
import com.highway.common.base.commonModel.customerDiverOwnerModelsClass.allHighwayTripModel.userTrip.CancelTrip;
import com.highway.common.base.commonModel.customerDiverOwnerModelsClass.allHighwayTripModel.userTrip.CompletedTrip;
import com.highway.common.base.commonModel.customerDiverOwnerModelsClass.allHighwayTripModel.userTrip.OngoingTrip;
import com.highway.common.base.commonModel.customerDiverOwnerModelsClass.allHighwayTripModel.userTrip.UpcomingTrip;

import java.util.ArrayList;
import java.util.List;

public class CustomerAllTripsActivityNew extends AppCompatActivity {

   /* public List<CompletedTrip> getCompletedTrips() {
        return completedTrips;
    }

    public void setCompletedTrips(List<CompletedTrip> completedTrips) {
        this.completedTrips = completedTrips;
    }

    public List<OngoingTrip> getOngoingTrips() {
        return ongoingTrips;
    }

    public void setOngoingTrips(List<OngoingTrip> ongoingTrips) {
        this.ongoingTrips = ongoingTrips;
    }

    public List<UpcomingTrip> getUpcomingTrips() {
        return upcomingTrips;
    }

    public void setUpcomingTrips(List<UpcomingTrip> upcomingTrips) {
        this.upcomingTrips = upcomingTrips;
    }

    public List<CancelTrip> getCancelTrips() {
        return cancelTrips;
    }

    public void setCancelTrips(List<CancelTrip> cancelTrips) {
        this.cancelTrips = cancelTrips;
    }

    private List<CompletedTrip> completedTrips = new ArrayList<>();
    private List<OngoingTrip> ongoingTrips = new ArrayList<>();
    private List<UpcomingTrip> upcomingTrips = new ArrayList<>();
    private List<CancelTrip> cancelTrips = new ArrayList<>();

*/
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_all_trip_new);
/*
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            getSupportActionBar().setTitle("Customer All Trips");
        }*/

    }



  /*  @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        onBackPressed();
        return super.onOptionsItemSelected(item);
    }*/
}
