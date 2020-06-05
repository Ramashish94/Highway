package com.highway.millUserModule.milluserAdapter;

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

public class OnGoingTripAdapterForMiller extends RecyclerView.Adapter<OnGoingTripAdapterForMiller.ViewHolder> {
    Context context;
    List<OngoingTrip>ongoingTrips;

    public OnGoingTripAdapterForMiller(List<OngoingTrip>ongoingTrips , Context context1){
        this.context = context1;
        this.ongoingTrips= ongoingTrips;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_recycler_item,parent,false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        OngoingTrip ongoingTrip = ongoingTrips.get(position);

        LatLng sourceAddressLatLng = new LatLng(Double.parseDouble(ongoingTrip.getSourceLat()),Double.parseDouble(ongoingTrip.getSourceLong()));
        LatLng destAddressLatLng = new LatLng(Double.parseDouble(ongoingTrip.getDestinationLat()),Double.parseDouble(ongoingTrip.getDestinationLong()));
        holder.tv1CompleteDate.setText(""+ongoingTrip.getEndDate());
        holder.tv2SourceAddress.setText(""+ Utils.getAddress(context,sourceAddressLatLng));
        holder.tv4DestAddress.setText("" +Utils.getAddress(context,destAddressLatLng));
        holder.tv3SourceTime.setText(""+ongoingTrip.getPickupTime());
        holder.tv5DestTime.setText(""+ongoingTrip.getDropTime());
        holder.tv6VehicleName.setText(""+ongoingTrip.getVehicleName());
        holder.tv7FairCharge.setText(""+ongoingTrip.getFare());
        // load img

    }

    @Override
    public int getItemCount() {
        return ongoingTrips.size();
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
