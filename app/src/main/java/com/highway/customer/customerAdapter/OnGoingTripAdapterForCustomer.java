package com.highway.customer.customerAdapter;

import android.content.Context;
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
import com.highway.common.base.commonModel.customerDiverOwnerModelsClass.allHighwayTripModel.userTrip.OngoingTrip;
import com.highway.utils.Utils;

import java.util.List;

public class OnGoingTripAdapterForCustomer extends RecyclerView.Adapter<OnGoingTripAdapterForCustomer.ViewHolder> {

    Context context;
    List<OngoingTrip> ongoingTrips;
    OnGoingBookTripInterface onGoingBookTripInterface;

    public OnGoingTripAdapterForCustomer(List<OngoingTrip> ongoingTrips, Context context) {
        this.context = context;
        this.ongoingTrips = ongoingTrips;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_recycler_item, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        OngoingTrip ongoingTrip = ongoingTrips.get(position);

        LatLng sourceAddLatLng = new LatLng(Double.parseDouble(ongoingTrip.getSourceLat()), Double.parseDouble(ongoingTrip.getSourceLong()));
        LatLng destAddLatLng = new LatLng(Double.parseDouble(ongoingTrip.getDestinationLat()), Double.parseDouble(ongoingTrip.getDestinationLong()));

        holder.tv1CompleteDate.setText("" + ongoingTrip.getEndDate());
        holder.tv2SourceAddress.setText(" " + Utils.getAddress(context, sourceAddLatLng));
        holder.tv4DestAddress.setText(" " + Utils.getAddress(context, destAddLatLng));
        holder.tv3SourceTime.setText("" + ongoingTrip.getPickupTime());
        holder.tv5DestTime.setText("" + ongoingTrip.getDropTime());
        holder.tv6VehicleName.setText("" + ongoingTrip.getVehicleName());
        holder.tv7FairCharge.setText("" + ongoingTrip.getFare());
        // img

       /* holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onGoingBookTripInterface != null) {
                     *//*onGoingBookTripInterface.onGoingBookTrip(

                           ongoingTrips.get(position).getSourceLat(), ongoingTrips.get(position).getSourceLong(),
                            ongoingTrips.get(position).getDestinationLat(), ongoingTrips.get(position).getDestinationLong(),
                            ongoingTrips.get(position).getName(), ongoingTrips.get(position).getRole(),
                            ongoingTrips.get(position).getVehicleName(), ongoingTrips.get(position).getVehicleNumber(),
                            ongoingTrips.get(position).getFare(), ongoingTrips.get(position).getStatus(),
                            ongoingTrips.get(position).getTripType(), ongoingTrips.get(position).getStartDate(),
                            ongoingTrips.get(position).getEndDate(), ongoingTrips.get(position).getPickupTime(),
                            ongoingTrips.get(position).getDropTime());*//*

                            onGoingBookTripInterface.onGoingBookTrip(ongoingTrip.getSourceLat(),
                                    ongoingTrip.getSourceLong(), ongoingTrip.getDestinationLat(),
                                    ongoingTrip.getDestinationLong(), ongoingTrip.getName(),
                                    ongoingTrip.getRole(), ongoingTrip.getVehicleName(),
                                    ongoingTrip.getVehicleNumber(), ongoingTrip.getFare(),
                                    ongoingTrip.getStatus(), ongoingTrip.getTripType(),
                                    ongoingTrip.getStartDate(), ongoingTrip.getEndDate(),
                                    ongoingTrip.getPickupTime(), ongoingTrip.getDropTime()
                            );
                }
            }
        });*/

        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onGoingBookTripInterface != null) {

                    onGoingBookTripInterface.onGoingBookTrip(
                         /*   ongoingTrips.get(position).getSourceLat(), ongoingTrips.get(position).getSourceLong(),
                            ongoingTrips.get(position).getDestinationLat(), ongoingTrips.get(position).getDestinationLong(),
                            ongoingTrips.get(position).getName(), ongoingTrips.get(position).getRole(),
                            ongoingTrips.get(position).getVehicleName(), ongoingTrips.get(position).getVehicleNumber(),
                            ongoingTrips.get(position).getFare(), ongoingTrips.get(position).getStatus(),
                            ongoingTrips.get(position).getTripType(), ongoingTrips.get(position).getStartDate(),
                            ongoingTrips.get(position).getEndDate(), ongoingTrips.get(position).getPickupTime(),
                            ongoingTrips.get(position).getDropTime());*/

                    /*onGoingBookTripInterface.onGoingBookTrip(ongoingTrip.getSourceLat(),
                            ongoingTrip.getSourceLong(), ongoingTrip.getDestinationLat(),
                            ongoingTrip.getDestinationLong(), ongoingTrip.getName(),
                            ongoingTrip.getRole(), ongoingTrip.getVehicleName(),
                            ongoingTrip.getVehicleNumber(), ongoingTrip.getFare(),
                            ongoingTrip.getStatus(), ongoingTrip.getTripType(),
                            ongoingTrip.getStartDate(), ongoingTrip.getEndDate(),
                            ongoingTrip.getPickupTime(), ongoingTrip.getDropTime()*/

                            ongoingTrips.get(holder.getAdapterPosition()).getSourceLat(),
                            ongoingTrips.get(holder.getAdapterPosition()).getSourceLong(),
                            ongoingTrips.get(holder.getAdapterPosition()).getDestinationLat(),
                            ongoingTrips.get(holder.getAdapterPosition()).getDestinationLong(),
                            ongoingTrips.get(holder.getAdapterPosition()).getName(),
                            ongoingTrips.get(holder.getAdapterPosition()).getRole(),
                            ongoingTrips.get(holder.getAdapterPosition()).getVehicleName(),
                            ongoingTrips.get(holder.getAdapterPosition()).getVehicleNumber(),
                            ongoingTrips.get(holder.getAdapterPosition()).getFare(),
                            ongoingTrips.get(holder.getAdapterPosition()).getStatus(),
                            ongoingTrips.get(holder.getAdapterPosition()).getTripType(),
                            ongoingTrips.get(holder.getAdapterPosition()).getStartDate(),
                            ongoingTrips.get(holder.getAdapterPosition()).getEndDate(),
                            ongoingTrips.get(holder.getAdapterPosition()).getPickupTime(),
                            ongoingTrips.get(holder.getAdapterPosition()).getDropTime()

                    );
                }
            }
        });

    }

    public void setOngoingBookTripInterface(OnGoingBookTripInterface onGoingBookTripInterface1) {
        this.onGoingBookTripInterface = onGoingBookTripInterface1;
    }

    public interface OnGoingBookTripInterface {       ///
        public void onGoingBookTrip(String sourceLat, String sourceLong, String destinationLat, String destinationLong,
                                    String name, String role, String vehicleName, String vehicleNumber, String fare,
                                    String status, String tripType, String startDate, String endDate, String pickupTime,
                                    String dropTime);
    }


    @Override
    public int getItemCount() {
        return ongoingTrips.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView tv1CompleteDate, tv2SourceAddress, tv3SourceTime, tv4DestAddress, tv5DestTime, tv6VehicleName, tv7FairCharge;
        private ImageView img1SourceIndicator, img2DestIndicator, img3_gmap_locator, img4VehicleImg;
        CardView cardView;
        LinearLayout linearLayout;

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
