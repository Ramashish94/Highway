package com.highway.drivermodule.driverAdapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.maps.model.LatLng;
import com.highway.R;
import com.highway.common.base.commonModel.customerDiverOwnerModelsClass.allHighwayTripModel.userTrip.OngoingTrip;
import com.highway.utils.Utils;

import java.util.List;

public class OnGoingTripAdapterForDriver extends RecyclerView.Adapter<OnGoingTripAdapterForDriver.MyViewHolder> {

    private List<OngoingTrip> ongoingTrips;
    private Context context;

    public OnGoingTripAdapterForDriver(List<OngoingTrip> ongoingTrips, Context context) {

        this.ongoingTrips = ongoingTrips;
        this.context = context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_recycler_item, parent, false);

        return new MyViewHolder(itemView);
    }


    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        OngoingTrip ongoingTrip = ongoingTrips.get(position);

        LatLng sourceAddLatLng = new LatLng(Double.parseDouble(ongoingTrip.getSourceLat()), Double.parseDouble(ongoingTrip.getSourceLong()));
        LatLng destAddLatLng = new LatLng(Double.parseDouble(ongoingTrip.getDestinationLat()), Double.parseDouble(ongoingTrip.getDestinationLong()));
        holder.tv1CompleteDate.setText(" " + ongoingTrip.getEndDate());
        holder.tv2SourceAddress.setText(" " + Utils.getAddress(context, sourceAddLatLng));
        holder.tv4DestAddress.setText(" " + Utils.getAddress(context, destAddLatLng));
        holder.tv3SourceTime.setText("" + ongoingTrip.getPickupTime());
        holder.tv5DestTime.setText("" + ongoingTrip.getDropTime());
        holder.tv6VehicleName.setText("" + ongoingTrip.getVehicleName());
        holder.tv7FairCharge.setText("" + ongoingTrip.getFare());

    }

    @Override
    public int getItemCount() {
        return ongoingTrips.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        private TextView tv1CompleteDate, tv2SourceAddress, tv3SourceTime, tv4DestAddress, tv5DestTime, tv6VehicleName, tv7FairCharge;
        private ImageView img1SourceIndicator, img2DestIndicator, img3_gmap_locator, img4VehicleImg;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            tv1CompleteDate = itemView.findViewById(R.id.Tv1CompleteDate);
            tv2SourceAddress = itemView.findViewById(R.id.Tv2SourceAddress);
            tv3SourceTime = itemView.findViewById(R.id.Tv3SourceTime);
            tv4DestAddress = itemView.findViewById(R.id.Tv4DestAddress);
            tv5DestTime = itemView.findViewById(R.id.Tv5DestTime);
            tv6VehicleName = itemView.findViewById(R.id.Tv6VehicleName);
            tv7FairCharge = itemView.findViewById(R.id.Tv7FairCharge);
            img1SourceIndicator = itemView.findViewById(R.id.Img1SourceIndicator);
            img2DestIndicator = itemView.findViewById(R.id.Img2DestIndicator);
            img4VehicleImg = itemView.findViewById(R.id.Img4VehicleImg);


        }
    }
}
