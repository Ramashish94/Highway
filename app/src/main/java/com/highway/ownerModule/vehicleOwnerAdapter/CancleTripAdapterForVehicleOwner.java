package com.highway.ownerModule.vehicleOwnerAdapter;

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
import com.highway.common.base.commonModel.customerDiverOwnerModelsClass.allHighwayTripModel.userTrip.CancelTrip;
import com.highway.utils.Utils;


import java.util.List;

public class CancleTripAdapterForVehicleOwner extends RecyclerView.Adapter<CancleTripAdapterForVehicleOwner.ViewHolder> {

    Context context;
    List<CancelTrip> cancelTrips;

    public CancleTripAdapterForVehicleOwner(List<CancelTrip> cancelTrips, Context context) {
        this.cancelTrips = cancelTrips;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.fragment_recycler_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

       CancelTrip cancelTrip = cancelTrips.get(position);

        LatLng sourceAddLatLng = new LatLng(Double.parseDouble(cancelTrip.getSourceLat()),Double.parseDouble(cancelTrip.getSourceLong()));
        LatLng destAddLatLng = new LatLng(Double.parseDouble(cancelTrip.getDestinationLat()),Double.parseDouble(cancelTrip.getDestinationLong()));

        holder.tv1CompleteDate.setText(""+cancelTrip.getEndDate());
        holder.tv2SourceAddress.setText(""+ Utils.getAddress(context,sourceAddLatLng));
        holder.tv4DestAddress.setText("" +Utils.getAddress(context,destAddLatLng));
        holder.tv3SourceTime.setText(""+cancelTrip.getPickupTime());
        holder.tv5DestTime.setText(""+cancelTrip.getDropTime());
        holder.tv6VehicleName.setText(""+cancelTrip.getVehicleName());
        holder.tv7FairCharge.setText(""+cancelTrip.getFare());


    }

    @Override
    public int getItemCount() {
        return cancelTrips.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView tv1CompleteDate, tv2SourceAddress, tv3SourceTime, tv4DestAddress, tv5DestTime, tv6VehicleName, tv7FairCharge;
        private ImageView img1SourceIndicator, img2DestIndicator, img3_gmap_locator, img4VehicleImg;

        public ViewHolder(@NonNull View itemView) {
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
