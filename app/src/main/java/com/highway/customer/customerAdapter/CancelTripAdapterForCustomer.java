package com.highway.customer.customerAdapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.maps.model.LatLng;
import com.highway.R;
import com.highway.common.base.commonModel.customerDiverOwnerModelsClass.allHighwayTripModel.userTrip.CancelTrip;
import com.highway.customer.customerActivity.CancelTripDetailsForCustomerActivity;
import com.highway.customer.customerActivity.CompletedTripDetailsForCustomersActivity;
import com.highway.utils.Utils;

import java.util.List;

public class CancelTripAdapterForCustomer extends RecyclerView.Adapter<CancelTripAdapterForCustomer.ViewHolder> {

    CancelBookTripInterface CancelBookTripInterface;
    Context context;
    List<CancelTrip>cancelTrips;

    public CancelTripAdapterForCustomer(List<CancelTrip>cancelTrips1, Context context1){
        this.cancelTrips = cancelTrips1;
        this.context = context1;
    }



    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_recycler_item, parent,false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        CancelTrip cancelTrip = cancelTrips.get(position);

        LatLng sourceAddLatLng = new LatLng(Double.parseDouble(cancelTrip.getSourceLat()),Double.parseDouble(cancelTrip.getSourceLong()));
        LatLng destAddLatLng = new LatLng(Double.parseDouble(cancelTrip.getDestinationLat()), Double.parseDouble(cancelTrip.getDestinationLong()));

        holder.tv1CompleteDate.setText(""+ cancelTrip.getEndDate());
        holder.tv2SourceAddress.setText(" " + Utils.getAddress(context, sourceAddLatLng));
        holder.tv4DestAddress.setText(" " + Utils.getAddress(context, destAddLatLng));
        holder.tv3SourceTime.setText("" + cancelTrip.getPickupTime());
        holder.tv5DestTime.setText("" + cancelTrip.getDropTime());
        holder.tv6VehicleName.setText("" + cancelTrip.getVehicleName());
        holder.tv7FairCharge.setText("" + cancelTrip.getFare());
        // img
        
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, CancelTripDetailsForCustomerActivity.class);
                intent.putExtra("sourceLat",cancelTrips.get(holder.getAdapterPosition()).getSourceLat());
                intent.putExtra("sourceLong",cancelTrips.get(holder.getAdapterPosition()).getSourceLong());
                intent.putExtra("destinationLat",cancelTrips.get(holder.getAdapterPosition()).getDestinationLat());
                intent.putExtra("destinationLong",cancelTrips.get(holder.getAdapterPosition()).getDestinationLong());
                intent.putExtra("name",cancelTrips.get(holder.getAdapterPosition()).getName());
                intent.putExtra("role",cancelTrips.get(holder.getAdapterPosition()).getRole());
                intent.putExtra("vehicleName",cancelTrips.get(holder.getAdapterPosition()).getVehicleName());
                intent.putExtra("vehicleNumber",cancelTrips.get(holder.getAdapterPosition()).getVehicleNumber());
                intent.putExtra("fare",cancelTrips.get(holder.getAdapterPosition()).getFare());
                intent.putExtra("status",cancelTrips.get(holder.getAdapterPosition()).getStatus());
                intent.putExtra("tripType",cancelTrips.get(holder.getAdapterPosition()).getTripType());
                intent.putExtra("startDate",cancelTrips.get(holder.getAdapterPosition()).getStartDate());
                intent.putExtra("endDate",cancelTrips.get(holder.getAdapterPosition()).getEndDate());
                intent.putExtra("pickupTime",cancelTrips.get(holder.getAdapterPosition()).getPickupTime());
                intent.putExtra("dropTime",cancelTrips.get(holder.getAdapterPosition()).getDropTime());
                context.startActivity(intent);

//                if (CancelBookTripInterface != null) {
//                    CancelBookTripInterface.cancelBookTripClick(
//                           cancelTrips.get(holder.getAdapterPosition()).getSourceLat(),
//                            cancelTrips.get(holder.getAdapterPosition()).getSourceLong(),
//                            cancelTrips.get(holder.getAdapterPosition()).getDestinationLat(),
//                            cancelTrips.get(holder.getAdapterPosition()).getDestinationLong(),
//                            cancelTrips.get(holder.getAdapterPosition()).getName(),
//                            cancelTrips.get(holder.getAdapterPosition()).getRole(),
//                            cancelTrips.get(holder.getAdapterPosition()).getVehicleName(),
//                            cancelTrips.get(holder.getAdapterPosition()).getVehicleNumber(),
//                            cancelTrips.get(holder.getAdapterPosition()).getFare(),
//                            cancelTrips.get(holder.getAdapterPosition()).getStatus(),
//                            cancelTrips.get(holder.getAdapterPosition()).getTripType(),
//                            cancelTrips.get(holder.getAdapterPosition()).getStartDate(),
//                            cancelTrips.get(holder.getAdapterPosition()).getEndDate(),
//                            cancelTrips.get(holder.getAdapterPosition()).getPickupTime(),
//                            cancelTrips.get(holder.getAdapterPosition()).getDropTime()
//                    );
//
//                }
            }
        });

    }

    public void setCancelBookTripInterface(CancelBookTripInterface  CancelBookTripInterface1) {
        this.CancelBookTripInterface = CancelBookTripInterface1;
    }

    public interface CancelBookTripInterface {       ///
        public void cancelBookTripClick(String sourceLat, String sourceLong, String destinationLat, String destinationLong,
                                   String name, String role, String vehicleName, String vehicleNumber, String fare,
                                   String status, String tripType, String startDate, String endDate, String pickupTime,
                                   String dropTime
        );
    }





    @Override
    public int getItemCount() {
        return cancelTrips.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView tv1CompleteDate, tv2SourceAddress, tv3SourceTime, tv4DestAddress, tv5DestTime, tv6VehicleName, tv7FairCharge;
        private ImageView img1SourceIndicator, img2DestIndicator, img3_gmap_locator, img4VehicleImg;
        private LinearLayout linearLayout;
        private CardView cardView;
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
            cardView = itemView.findViewById(R.id.CardView);
            linearLayout = itemView.findViewById(R.id.LLout1);


        }
    }
}


